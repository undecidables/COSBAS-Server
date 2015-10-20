package cosbas.biometric.validators.facial;


/*
 * FaceRecognition.java
 *
 * Created on Dec 7, 2011, 1:27:25 PM
 *
 * Description: Recognizes faces.
 *
 * Copyright (C) Dec 7, 2011, Stephen L. Reed, Texai.org. (Fixed April 22, 2012, Samuel Audet)
 *
 * This file is a translation from the OpenCV example http://www.shervinemami.info/faceRecognition.html, ported
 * to Java using the JavaCV library.  Notable changes are the addition of the Java Logging framework and the
 * installation of image files in a data directory child of the working directory. Some of the code has
 * been expanded to make debugging easier.  Expected results are 100% recognition of the lower3.txt test
 * image index set against the all10.txt training image index set.  See http://en.wikipedia.org/wiki/Eigenface
 * for a technical explanation of the algorithm.
 *
 * stephenreed@yahoo.com
 *
 * FaceRecognition is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version (subject to the "Classpath" exception
 * as provided in the LICENSE.txt file that accompanied this code).
 *
 * FaceRecognition is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JavaCV.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

/**
 * An EigenFaces Face Recognizer
 * Adapted from an example at https://github.com/bytedeco/javacv/blob/11a29b21975c957a0e5990b07e8c2401ef1cccd4/samples/FaceRecognition.java
 *
 */

import cosbas.biometric.BiometricTypes;
import cosbas.biometric.data.BiometricData;
import cosbas.biometric.data.BiometricDataDAO;
import cosbas.biometric.validators.ValidationResponse;
import cosbas.biometric.validators.exceptions.ValidationException;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.FloatPointer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_legacy.*;

/**
 * Recognizes faces.
 * [@author Renette Ros}
 */
@Component
public class FaceRecognition {

    public class Trainer implements Runnable {
        @Override
        public void run() {
            trainFromDB();
        }
    }

    private volatile RecognizerData data;
    private volatile ReentrantLock dataUpdateLock = new ReentrantLock();
    private volatile ReentrantLock trainingLock = new ReentrantLock();

    private RecognizerDAO dataRepository;
    private BiometricDataDAO biometricsRepository;

    @Autowired
    public FaceRecognition(RecognizerDAO dataRepository, BiometricDataDAO biometricsRepository) {
        this.dataRepository = dataRepository;
        this.biometricsRepository = biometricsRepository;
    }

    @PostConstruct
    private void setup() {
        data = dataRepository.findFirstByOrderByUpdatedDesc();
        if (data == null) {
            new Thread(new Trainer()).start();
        }
    }

    public void trainFromDB() {
        try {
            trainingLock.lock();
            updateData();
            List<BiometricData> datalist = biometricsRepository.findByType(BiometricTypes.FACE);
            if (datalist != null && !datalist.isEmpty()) {
                learn(datalist);
            }
        }  finally {
            trainingLock.unlock();
        }
    }

    private IplImage createIPL(BiometricData d) {
        byte[] b = d.getData();
        return cvDecodeImage(cvMat(1, b.length, CV_8UC1, new BytePointer(b)), CV_LOAD_IMAGE_GRAYSCALE);
    }

    /**
     * Trains the recognizer and updates and persists the training data.
     * @param trainingData The faces to train on
     */
    void learn(List<BiometricData> trainingData) {
        try {
            trainingLock.lock();

            TemporaryRecognizerData data = new TemporaryRecognizerData();

            CvMat projectedTrainFace;

            loadFaceImageList(trainingData, data);

            //Principal Component Analysis
            doPCA(data);

            int nTrainFaces = data.getnFaces();
            int nEigens = data.getnEigens();

            // Project the training images onto the PCA subspace
            projectedTrainFace = cvCreateMat(
                    nTrainFaces, // rows
                    nEigens, // cols
                    CV_32FC1);

            // Initialize the training face matrix - for ease of debugging
            for (int i1 = 0; i1 < nTrainFaces; i1++) {
                for (int j1 = 0; j1 < nEigens; j1++) {
                    projectedTrainFace.put(i1, j1, 0.0);
                }
            }

            IplImage[] eigenVectors = data.getEigenVectors();
            final FloatPointer floatPointer = new FloatPointer(nEigens);
            for (int i = 0; i < nTrainFaces; i++) {

                cvEigenDecomposite(
                        data.getTrainingFaces().get(i),
                        nEigens,
                        eigenVectors,
                        0,
                        null,
                        data.getpAvgTrainImg(),
                        floatPointer);

                for (int j1 = 0; j1 < nEigens; j1++) {
                    projectedTrainFace.put(i, j1, floatPointer.get(j1));
                }
            }
            data.setProjectedTrainFace(projectedTrainFace);

            dataRepository.save(data.getFinalRecogznizerData());
            updateData();
        } finally {
            trainingLock.unlock();
        }

   }

    /**
     * Recognizes the face in the given biometricData
     */
   public ValidationResponse recognizeFace(BiometricData face) throws ValidationException {
        updateData();
       if (data == null)
            throw new ValidationException("Face recognizer has not been initialized");
        IplImage testFace;

        float[] projectedTestFace;

        testFace = createIPL(face);
       int nEigens = data.eigenVectors.length;

        // project the test images onto the PCA subspace
        projectedTestFace = new float[nEigens];

        int iNearest;
        int nearest;

        // project the test image onto the PCA subspace
       cvEigenDecomposite(
                testFace, // obj
                nEigens, // nEigObjs
                data.eigenVectors, // eigInput (Pointer)
                0, // ioFlags
                null, // userData
                data.pAvgTrainImg, // avg
                projectedTestFace);  // coeffs


        final FloatPointer pConfidence = new FloatPointer(0);
        iNearest = findNearestNeighbor(projectedTestFace, pConfidence);
        double confidence = pConfidence.get();
       nearest = data.personNumTruthMat.data_i().get(iNearest);
       String emplid = data.personNames.get(nearest-1);

        return new ValidationResponse(true,  emplid, confidence);
    }

    /**
     * Loads Biometric Face Data list into the recognizer for training purposes.
     * @param dataList List of biometric data objects fetched from database.
     * @param data The data object to store recognizer variables in.
     * @return List of IPL images to process with JavaCV
     */

    private List<IplImage> loadFaceImageList(List<BiometricData> dataList, TemporaryRecognizerData data) {
        HashMap<String, Integer> personNumMap = new HashMap<>();
        List<String> personNames = new ArrayList<>();

        int nFaces = dataList.size();

        List<IplImage> faceImageList = new ArrayList<>(nFaces);
        CvMat personNumbersMatrix = cvCreateMat(
                1, // rows
                nFaces, // cols
                CV_32SC1);

        /* Initialize person number matrix */
        for (int j1 = 0; j1 < nFaces; j1++) {
            personNumbersMatrix.put(0, j1, 0);
        }

        int personCounter = 0;
        int faceCounter = 0;
        for (BiometricData biometricData : dataList) {

            /* Determine Person Number */
            String personID = biometricData.getUserID();
            Integer personNumber = personNumMap.get(personID);
            if (personNumber == null) {
                personNumber = ++personCounter;
                personNumMap.put(personID, personNumber);
                personNames.add(personID);
            }


            faceImageList.add(createIPL(biometricData));
            personNumbersMatrix.put(
                    0,
                    faceCounter++,
                    personNumber);
        }

        if (data != null) {
            data.setTrainingFaces(faceImageList);
            data.setPersonNames(personNames);
            data.setPersonNumTruthMat(personNumbersMatrix);
        }

        return faceImageList;
    }

    /**
     * Does the Principal Component Analysis, finding the average image and the eigenfaces that represent any image in the given dataset.
     */
    private void doPCA(TemporaryRecognizerData data) {
        List<IplImage> trainingFaces = data.getTrainingFaces();
        int nTrainFaces = data.getnFaces();
        int nEigens = nTrainFaces - 1;

        CvSize faceImgSize = new CvSize();
        faceImgSize.width(trainingFaces.get(0).width());
        faceImgSize.height(trainingFaces.get(0).height());

        IplImage[] eigenVectors = initEigenVectors(nEigens, faceImgSize);

        CvMat eigenValues = cvCreateMat(
                1, // rows
                nEigens, // cols
                CV_32FC1);

        IplImage averagedImage = cvCreateImage(
                faceImgSize, // size
                IPL_DEPTH_32F, // depth
                1);


        // Set the PCA termination criterion
        CvTermCriteria calcLimit = cvTermCriteria(
                CV_TERMCRIT_ITER, // type
                nEigens, // max_iter
                1); // epsilon


        // Compute average image, eigenvalues, and eigen vectors
        cvCalcEigenObjects(
                nTrainFaces, // nObjects
                trainingFaces.toArray(new IplImage[trainingFaces.size()]), // input
                eigenVectors, // output
                CV_EIGOBJ_NO_CALLBACK, // ioFlags
                0, // ioBufSize
                null, // userData
                calcLimit,
                averagedImage, // avg
                eigenValues.data_fl()); // eigVals

        //Normalize the Eigen Vectors
        cvNormalize(
                eigenValues, // src (CvArr)
                eigenValues, // dst (CvArr)
                1, // a
                0, // b
                CV_L1, // norm_type
                null); // mask

        data.setEigenValues(eigenValues);
        data.setpAvgTrainImg(averagedImage);
        data.setEigenVectors(eigenVectors);
    }

    private IplImage[] initEigenVectors(int nEigens, CvSize faceImgSize) {
        IplImage[] eigenVectors = new IplImage[nEigens];

        for (int i = 0; i < nEigens; i++) {
            eigenVectors[i] = cvCreateImage(
                    faceImgSize, // size
                    IPL_DEPTH_32F, // depth
                    1); // channels
        }
        return eigenVectors;
    }

    private void updateData() {
        try {
            dataUpdateLock.lock();
            RecognizerData newData = dataRepository.findFirstByOrderByUpdatedDesc();
            if (newData != null && (data == null || newData.updated.isAfter(data.updated))) {
                if (data == null || newData.updated.isAfter(data.updated)) {
                    this.data = newData;
                    dataRepository.deleteAll();
                    dataRepository.save(newData);
                }
            }
        }  finally {
                dataUpdateLock.unlock();
        }
    }

    /**
     * Find the most likely person based on a detection. Returns the index, and stores the confidence value into pConfidence.
     *
     * @param projectedTestFace  the projected test face
     * @param pConfidencePointer a pointer containing the confidence value.
     * @return the index
     */
    private int findNearestNeighbor(float projectedTestFace[], FloatPointer pConfidencePointer) {
        double leastDistSq = Double.MAX_VALUE;
        int i = 0;
        int iTrain = 0;
        int iNearest = 0;

        int nTrainFaces = data.trainingFaces.size();
        int nEigens = data.eigenVectors.length;
        for (iTrain = 0; iTrain < nTrainFaces; iTrain++) {

            double distSq = 0;

            for (i = 0; i < nEigens; i++) {

                float projectedTrainFaceDistance = (float) data.projectedTrainFace.get(iTrain, i);
                float d_i = projectedTestFace[i] - projectedTrainFaceDistance;
                distSq += d_i * d_i;
            }

            if (distSq < leastDistSq) {
                leastDistSq = distSq;
                iNearest = iTrain;
            }
        }

        // Return the confidence level based on the Euclidean distance,
        // so that similar images should give a confidence between 0.5 to 1.0,
        // and very different images should give a confidence between 0.0 to 0.5.
        float pConfidence = (float) (1.0f - Math.sqrt(leastDistSq / (float) (nTrainFaces * nEigens)) / 255.0f);
        pConfidencePointer.put(pConfidence);

        return iNearest;
    }

    public boolean needsTraining() {
        updateData();
        return data.needsTraining();
    }

    private class TrainingUpdater implements Runnable {

        @Override
        public void run() {
            try {
                updateData();
                trainingLock.lock();
                data.setNeedsTraining();
            } finally {
                trainingLock.unlock();
            }
        }
    }

    public void setNeedsTraining() {
        (new Thread(new TrainingUpdater())).start();
    }

}
