package cosbas.biometric.validators.facial;

import org.bytedeco.javacpp.opencv_core;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Renette
 */
public class RecognizerData {

    public final List<String> personNames;
    public List<opencv_core.IplImage> trainingFaces;
    public List<opencv_core.IplImage> eigenVectors;
    public opencv_core.IplImage pAvgTrainImg;
    public opencv_core.CvMat eigenValues;
    public opencv_core.CvMat projectedTrainFace;
    public opencv_core.CvMat personNumTruthMat;
    public LocalDateTime updated;
    private boolean needsTraining;
    private String nameID;

    public RecognizerData(List<String> personNames,
                          List<opencv_core.IplImage> trainingFaces,
                          List<opencv_core.IplImage> eigenVectors,
                          opencv_core.IplImage pAvgTrainImg,
                          opencv_core.CvMat eigenValues,
                          opencv_core.CvMat projectedTrainFace,
                          opencv_core.CvMat personNumTruthMat,
                          LocalDateTime updated,
                          boolean needsTraining,
                          String nameID) {
        this.personNames = personNames;

        this.trainingFaces = trainingFaces;
        this.eigenVectors = eigenVectors;
        this.pAvgTrainImg = pAvgTrainImg;

        this.eigenValues = eigenValues;
        this.projectedTrainFace = projectedTrainFace;
        this.personNumTruthMat = personNumTruthMat;

        this.updated = updated;
        this.needsTraining = needsTraining;
        this.nameID = nameID;
    }

    public RecognizerData(List<String> personNames,
                          List<opencv_core.IplImage> trainingFaces,
                          ArrayList<opencv_core.IplImage> eigenVectors,
                          opencv_core.IplImage pAvgTrainImg,
                          opencv_core.CvMat eigenValues,
                          opencv_core.CvMat projectedTrainFace,
                          opencv_core.CvMat personNumTruthMat) {
        this(personNames, trainingFaces, eigenVectors, pAvgTrainImg, eigenValues, projectedTrainFace, personNumTruthMat, LocalDateTime.now(), false, "");
    }

    public void setNeedsTraining(boolean needsTraining) {
        this.needsTraining = needsTraining;
    }

    public String getNameID() {
        return nameID;
    }

    public void setNameID(String nameID) {
        this.nameID = nameID;
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
