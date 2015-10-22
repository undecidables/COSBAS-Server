package cosbas.biometric.validators.facial;

import org.bytedeco.javacpp.opencv_core;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
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
    @Transient
    private boolean needsTraining;

    @Transient
    public List<String> personNames;
    @Transient
    public List<opencv_core.IplImage> trainingFaces;
    @Transient
    public opencv_core.IplImage[] eigenVectors;
    @Transient
    public opencv_core.IplImage pAvgTrainImg;
    @Transient
    public opencv_core.CvMat eigenValues;
    @Transient
    public opencv_core.CvMat projectedTrainFace;
    @Transient
    public opencv_core.CvMat personNumTruthMat;

    @Transient
    public LocalDateTime updated;

    @PersistenceConstructor
    private RecognizerData() {
        needsTraining = true;
        updated = LocalDateTime.now().minusDays(1);
    }
    //@PersistenceConstructor
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
