package cosbas.biometric.validators.facial;

import org.apache.tomcat.jni.Local;
import org.bytedeco.javacpp.opencv_core;
import org.springframework.data.annotation.PersistenceConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Renette
 */
public class RecognizerData {
    String id;

    public final List<String> personNames;
    public final List<opencv_core.IplImage> trainingFaces;
    public final opencv_core.IplImage[] eigenVectors;
    public final opencv_core.IplImage pAvgTrainImg;
    public final opencv_core.CvMat eigenValues;
    public final opencv_core.CvMat projectedTrainFace;
    public final opencv_core.CvMat personNumTruthMat;
    public final LocalDateTime updated;

    @PersistenceConstructor public RecognizerData(List<String> personNames,
                                                  List<opencv_core.IplImage> trainingFaces,
                                                  opencv_core.IplImage[] eigenVectors,
                                                  opencv_core.IplImage pAvgTrainImg,
                                                  opencv_core.CvMat eigenValues,
                                                  opencv_core.CvMat projectedTrainFace,
                                                  opencv_core.CvMat personNumTruthMat,
                                                  LocalDateTime updated) {
        this.personNames = personNames;
        this.trainingFaces = trainingFaces;
        this.eigenVectors = eigenVectors;
        this.pAvgTrainImg = pAvgTrainImg;
        this.eigenValues = eigenValues;
        this.projectedTrainFace = projectedTrainFace;
        this.personNumTruthMat = personNumTruthMat;
        this.updated = updated;
    }

    public RecognizerData(List<String> personNames,
                          List<opencv_core.IplImage> trainingFaces,
                          opencv_core.IplImage[] eigenVectors,
                          opencv_core.IplImage pAvgTrainImg,
                          opencv_core.CvMat eigenValues,
                          opencv_core.CvMat projectedTrainFace,
                          opencv_core.CvMat personNumTruthMat) {
        this(personNames, trainingFaces, eigenVectors, pAvgTrainImg, eigenValues, projectedTrainFace, personNumTruthMat, LocalDateTime.now());
    }


    public int getnEigens() {
        if (eigenVectors != null)
            return eigenVectors.length;
        else
            return 0;
    }

    public int getnFaces() {
        if (trainingFaces != null)
            return trainingFaces.size();
        else
            return 0;
    }
}
