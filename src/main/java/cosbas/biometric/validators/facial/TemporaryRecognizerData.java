package cosbas.biometric.validators.facial;

import org.bytedeco.javacpp.opencv_core;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Renette
 */
class TemporaryRecognizerData {
    private List<String> personNames;
    private List<opencv_core.IplImage> trainingFaces;
    private ArrayList<opencv_core.IplImage> eigenVectors;
    private opencv_core.IplImage pAvgTrainImg;
    private opencv_core.CvMat eigenValues;
    private opencv_core.CvMat projectedTrainFace;
    private opencv_core.CvMat personNumTruthMat;

    TemporaryRecognizerData() {
    }

    public List<String> getPersonNames() {
        return personNames;
    }

    public void setPersonNames(List<String> personNames) {
        this.personNames = personNames;
    }

    public List<opencv_core.IplImage> getTrainingFaces() {
        return trainingFaces;
    }

    public void setTrainingFaces(List<opencv_core.IplImage> trainingFaces) {
        this.trainingFaces = trainingFaces;
    }

    public ArrayList<opencv_core.IplImage> getEigenVectors() {
        return eigenVectors;
    }

    public void setEigenVectors(ArrayList<opencv_core.IplImage> eigenVectors) {
        this.eigenVectors = eigenVectors;
    }

    public opencv_core.IplImage getpAvgTrainImg() {
        return pAvgTrainImg;
    }

    public void setpAvgTrainImg(opencv_core.IplImage pAvgTrainImg) {
        this.pAvgTrainImg = pAvgTrainImg;
    }

    public opencv_core.CvMat getEigenValues() {
        return eigenValues;
    }

    public void setEigenValues(opencv_core.CvMat eigenValues) {
        this.eigenValues = eigenValues;
    }

    public opencv_core.CvMat getProjectedTrainFace() {
        return projectedTrainFace;
    }

    public void setProjectedTrainFace(opencv_core.CvMat projectedTrainFace) {
        this.projectedTrainFace = projectedTrainFace;
    }

    public opencv_core.CvMat getPersonNumTruthMat() {
        return personNumTruthMat;
    }

    public void setPersonNumTruthMat(opencv_core.CvMat personNumTruthMat) {
        this.personNumTruthMat = personNumTruthMat;
    }

    public int getnEigens() {
        if (getEigenVectors() != null)
            return getEigenVectors().size();
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
