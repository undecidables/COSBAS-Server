package cosbas.biometric.validators.facial;

import org.bytedeco.javacpp.opencv_core;

import java.util.List;

/**
 * @author Renette
 */
class TemporaryRecognizerData {
    private List<String> personNames;
    private List<opencv_core.IplImage> trainingFaces;
    private opencv_core.IplImage[] eigenVectors;
    private opencv_core.IplImage pAvgTrainImg;
    private opencv_core.CvMat eigenValues;
    private opencv_core.CvMat projectedTrainFace;
    private opencv_core.CvMat personNumTruthMat;

    TemporaryRecognizerData() {
    }

    List<String> getPersonNames() {
        return personNames;
    }

    void setPersonNames(List<String> personNames) {
        this.personNames = personNames;
    }

    List<opencv_core.IplImage> getTrainingFaces() {
        return trainingFaces;
    }

    void setTrainingFaces(List<opencv_core.IplImage> trainingFaces) {
        this.trainingFaces = trainingFaces;
    }

    opencv_core.IplImage[] getEigenVectors() {
        return eigenVectors;
    }

    void setEigenVectors(opencv_core.IplImage[] eigenVectors) {
        this.eigenVectors = eigenVectors;
    }

    opencv_core.IplImage getpAvgTrainImg() {
        return pAvgTrainImg;
    }

    void setpAvgTrainImg(opencv_core.IplImage pAvgTrainImg) {
        this.pAvgTrainImg = pAvgTrainImg;
    }

    opencv_core.CvMat getEigenValues() {
        return eigenValues;
    }

    void setEigenValues(opencv_core.CvMat eigenValues) {
        this.eigenValues = eigenValues;
    }

    opencv_core.CvMat getProjectedTrainFace() {
        return projectedTrainFace;
    }

    void setProjectedTrainFace(opencv_core.CvMat projectedTrainFace) {
        this.projectedTrainFace = projectedTrainFace;
    }

    opencv_core.CvMat getPersonNumTruthMat() {
        return personNumTruthMat;
    }

    void setPersonNumTruthMat(opencv_core.CvMat personNumTruthMat) {
        this.personNumTruthMat = personNumTruthMat;
    }

    public int getnEigens() {
        if (getEigenVectors() != null)
            return getEigenVectors().length;
        else
            return 0;
    }

    public int getnFaces() {
        return getTrainingFaces().size();
    }

    public RecognizerData getFinalRecogznizerData() {
        return new RecognizerData(personNames, trainingFaces, eigenVectors, pAvgTrainImg, eigenValues, projectedTrainFace, personNumTruthMat);
    }


}
