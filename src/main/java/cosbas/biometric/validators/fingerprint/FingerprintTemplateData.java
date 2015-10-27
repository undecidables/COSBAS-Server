package cosbas.biometric.validators.fingerprint;

import com.google.api.client.util.Value;
import cosbas.biometric.BiometricTypes;
import cosbas.biometric.data.BiometricData;
import cosbas.biometric.data.BiometricDataDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

/**
 *  {@Author Vivian Venter}
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
