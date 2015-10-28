package cosbas.biometric.validators.fingerprint;

import cosbas.biometric.BiometricTypes;
import cosbas.biometric.data.BiometricData;
import org.springframework.data.annotation.PersistenceConstructor;
import java.awt.*;
import java.util.ArrayList;

/**
 *  {@author Vivian Venter}
 *  The class to save the template data of each fingerprint
 */
public class FingerprintTemplateData extends BiometricData{

    private ArrayList<Point> bifurcations;
    private ArrayList<Point> endPoints;

    @PersistenceConstructor
    public FingerprintTemplateData(String staffID, ArrayList<Point> bifurcations,ArrayList<Point> endPoints) {
        super(staffID, BiometricTypes.FINGER, null);
        this.bifurcations = bifurcations;
        this.endPoints = endPoints;
    }

    public ArrayList<Point> getBifurcations() {
        return bifurcations;
    }

    public ArrayList<Point> getEndPoints() {
        return endPoints;
    }

}
