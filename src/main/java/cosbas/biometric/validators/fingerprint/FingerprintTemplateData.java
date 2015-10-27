package cosbas.biometric.validators.fingerprint;

import com.google.api.client.util.Value;
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
public class FingerprintTemplateData {

    private String staffID;
    private ArrayList<Point> bifurcations;
    private ArrayList<Point> endPoints;

    @Autowired
    private FingerprintDAO fingerprintRepository;

    @PersistenceConstructor
    public FingerprintTemplateData(String staffID, ArrayList<Point> bifurcations,ArrayList<Point> endPoints) {
        this.staffID = staffID;
        this.bifurcations = bifurcations;
        this.endPoints = endPoints;
    }

    public FingerprintTemplateData() {

    }

    public void createTemplateFingerprintData(BufferedImage image, boolean registrationOnly) {

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

        bifurcations = finger.getMinutiaeIntersections(core, coreRadius);
        endPoints = finger.getMinutiaeEndpoints(core, coreRadius);

        /*BufferedImage i6 = finger.directionToBufferedImage(dir);
        Graphics g = i6.getGraphics();

        g.setColor(Color.magenta);
        for (Point point : bifurcations) {
            g.fillOval(point.x - 2, point.y - 2, 4, 4);
        }

        g.setColor(Color.blue);
        for (Point point : endPoints)
        {
            g.fillOval(point.x-2, point.y-2, 4, 4);
        }*/

        if(registrationOnly) {
            //store the data in the db
            fingerprintRepository.save(new FingerprintTemplateData(getStaffID(),getBifurcations(),getEndPoints()));
        }
        else {
            // something else
        }
        //BufferedImage i1 = finger.toBufferedImage();
        //File f1 = new File(path + "temp1_2.bmp");
        //ImageIO.write(i1, "bmp", f1);

    }

    public ArrayList<Point> getBifurcations() {
        return bifurcations;
    }

    public ArrayList<Point> getEndPoints() {
        return endPoints;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }
}
