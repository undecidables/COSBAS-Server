package cosbas.biometric.validators.fingerprint;


import cosbas.biometric.data.BiometricDataDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * {@author Vivian Venter}
 */
public class FingerprintTemplateCreator {

    public FingerprintTemplateData createTemplateFingerprintData(String userID, BufferedImage image, boolean registrationOnly, BiometricDataDAO fingerprintRepository) {

        Fingerprint finger = new Fingerprint(image);
        finger.setColors(Color.black, Color.green);
        finger.binarizeMean();
        finger.binarizeLocalMean();
        finger.addBorders(1);
        finger.removeNoise();
        finger.removeNoise();
        finger.removeNoise();
        finger.skeletonize();
        Fingerprint.direction[][] dir = finger.getDirections();

        BufferedImage i5 = finger.directionToBufferedImage(dir);
        Point core = finger.getCore(dir);
        int coreRadius = i5.getWidth() / 2;

        ArrayList<Point> bifurcations = finger.getMinutiaeIntersections(core, coreRadius);
        ArrayList<Point> endPoints = finger.getMinutiaeEndpoints(core, coreRadius);

        FingerprintTemplateData fingerprintData = new FingerprintTemplateData(userID, bifurcations, endPoints);

        if(registrationOnly) {
            fingerprintRepository.save(fingerprintData);
            return fingerprintData;
        }
        else {
            return fingerprintData;
        }

    }

}
