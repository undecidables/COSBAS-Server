package cosbas.biometric.validators.facial;

import org.bytedeco.javacpp.opencv_core;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Renette
 */
@Document(collection = "FaceRecognitionData")
public class RecognizerData {

    @Id
    String id;
    private boolean needsTraining;

    public final List<String> personNames;
    public final List<opencv_core.IplImage> trainingFaces;
    public final opencv_core.IplImage[] eigenVectors;
    public final opencv_core.IplImage pAvgTrainImg;
    public final opencv_core.CvMat eigenValues;
    public final opencv_core.CvMat projectedTrainFace;
    public final opencv_core.CvMat personNumTruthMat;
    public final LocalDateTime updated;

    @PersistenceConstructor
    protected RecognizerData(String id, List<String> personNames,
                             List<opencv_core.IplImage> trainingFaces,
                             opencv_core.IplImage[] eigenVectors,
                             opencv_core.IplImage pAvgTrainImg,
                             opencv_core.CvMat eigenValues,
                             opencv_core.CvMat projectedTrainFace,
                             opencv_core.CvMat personNumTruthMat,
                             LocalDateTime updated,
                             Boolean needsTraining) {
        this.id = id;
        this.personNames = personNames;
        this.trainingFaces = trainingFaces;
        this.eigenVectors = eigenVectors;
        this.pAvgTrainImg = pAvgTrainImg;
        this.eigenValues = eigenValues;
        this.projectedTrainFace = projectedTrainFace;
        this.personNumTruthMat = personNumTruthMat;
        this.updated = updated;
        this.needsTraining = needsTraining;
    }

    public RecognizerData(List<String> personNames,
                          List<opencv_core.IplImage> trainingFaces,
                          opencv_core.IplImage[] eigenVectors,
                          opencv_core.IplImage pAvgTrainImg,
                          opencv_core.CvMat eigenValues,
                          opencv_core.CvMat projectedTrainFace,
                          opencv_core.CvMat personNumTruthMat) {
        this(null, personNames, trainingFaces, eigenVectors, pAvgTrainImg, eigenValues, projectedTrainFace, personNumTruthMat, LocalDateTime.now(), false);
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

    public boolean needsTraining() {
        return needsTraining;
    }

    public void setNeedsTraining() {
        this.needsTraining = true;
    }
}