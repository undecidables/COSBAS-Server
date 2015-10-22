package cosbas.biometric.validators.facial;

import org.bytedeco.javacpp.opencv_core;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Renette
 */
@Document(collection = "FaceRecognitionData")
public class RecognizerData {

    @Id
    String id;
    private boolean needsTraining;
    public final List<String> personNames;

    @PersistenceConstructor
    public RecognizerData(String id, boolean needsTraining, List<String> personNames, List<CvMatStorage> storeTrainingFaces, List<CvMatStorage> storeEigenVectors, CvMatStorage storePAvgTrainImg, CvMatStorage storeEigenValues, CvMatStorage storeProjectedTrainFace, CvMatStorage storePersonNymTurhMat, LocalDateTime updated) {
        this.id = id;
        this.needsTraining = needsTraining;
        this.personNames = personNames;
        this.updated = updated;

        this.storeTrainingFaces = storeTrainingFaces;
        this.trainingFaces = new ArrayList<>(this.storeTrainingFaces.stream().map(CvMatStorage::getIPL).collect(Collectors.toList()));
        this.storeEigenVectors = storeEigenVectors;
        this.eigenVectors = new ArrayList<>(this.storeEigenVectors.stream().map(CvMatStorage::getIPL).collect(Collectors.toList()));
        this.storePAvgTrainImg = storePAvgTrainImg;
        this.pAvgTrainImg = this.storePAvgTrainImg.getIPL();
        this.storeEigenValues = storeEigenValues;
        this.eigenValues = this.storeEigenValues.getCVMat();
        this.storeProjectedTrainFace = storeProjectedTrainFace;
        this.projectedTrainFace = this.storeProjectedTrainFace.getCVMat();
        this.storePersonNumTurhMat = storePersonNymTurhMat;
        this.personNumTruthMat = this.storePersonNumTurhMat.getCVMat();
    }

    @Transient
    public final List<opencv_core.IplImage> trainingFaces;
    public final List<CvMatStorage> storeTrainingFaces;
    @Transient
    public final List<opencv_core.IplImage> eigenVectors;
    public final List<CvMatStorage> storeEigenVectors;

    @Transient
    public final opencv_core.IplImage pAvgTrainImg;
    public final CvMatStorage storePAvgTrainImg;
    @Transient
    public final opencv_core.CvMat eigenValues;
    public final CvMatStorage storeEigenValues;
    @Transient
    public final opencv_core.CvMat projectedTrainFace;
    public final CvMatStorage storeProjectedTrainFace;
    @Transient
    public final opencv_core.CvMat personNumTruthMat;
    private final CvMatStorage storePersonNumTurhMat;


    public LocalDateTime updated;

    protected RecognizerData(String id, List<String> personNames,
                             List<opencv_core.IplImage> trainingFaces,
                             ArrayList<opencv_core.IplImage> eigenVectors,
                             opencv_core.IplImage pAvgTrainImg,
                             opencv_core.CvMat eigenValues,
                             opencv_core.CvMat projectedTrainFace,
                             opencv_core.CvMat personNumTruthMat,
                             LocalDateTime updated,
                             Boolean needsTraining) {
        this.id = id;
        this.personNames = personNames;

        this.trainingFaces = trainingFaces;
        this.storeTrainingFaces = new ArrayList<>(this.trainingFaces.stream().map(CvMatStorage::new).collect(Collectors.toList()));

        this.eigenVectors = eigenVectors;
        this.storeEigenVectors = new ArrayList<>(this.eigenVectors.stream().map(CvMatStorage::new).collect(Collectors.toList()));

        this.pAvgTrainImg = pAvgTrainImg;
        this.storePAvgTrainImg = new CvMatStorage(this.pAvgTrainImg);

        this.eigenValues = eigenValues;
        this.storeEigenValues = new CvMatStorage(this.eigenValues);

        this.projectedTrainFace = projectedTrainFace;
        this.storeProjectedTrainFace = new CvMatStorage(projectedTrainFace);

        this.personNumTruthMat = personNumTruthMat;
        this.storePersonNumTurhMat = new CvMatStorage(personNumTruthMat);

        this.updated = updated;
        this.needsTraining = needsTraining;
    }

    public RecognizerData(List<String> personNames,
                          List<opencv_core.IplImage> trainingFaces,
                          ArrayList<opencv_core.IplImage> eigenVectors,
                          opencv_core.IplImage pAvgTrainImg,
                          opencv_core.CvMat eigenValues,
                          opencv_core.CvMat projectedTrainFace,
                          opencv_core.CvMat personNumTruthMat) {
        this(null, personNames, trainingFaces, eigenVectors, pAvgTrainImg, eigenValues, projectedTrainFace, personNumTruthMat, LocalDateTime.now(), false);
    }


    public int getnEigens() {
        if (eigenVectors != null)
            return eigenVectors.size();
        else
            return 0;
    }

    public int getnFaces() {
        if (trainingFaces != null)
            return trainingFaces.size();
        else
            return 0;
    }

    public boolean needsTraining() {
        return needsTraining;
    }

    public void setNeedsTraining() {
        this.needsTraining = true;
    }
}
