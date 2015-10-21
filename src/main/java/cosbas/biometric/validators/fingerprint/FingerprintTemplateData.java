package cosbas.biometric.validators.fingerprint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by VivianWork on 10/21/2015.
 */
public class FingerprintTemplateData {

    private Fingerprint finger;
    Point core;
    int coreRadius;

    ArrayList<Point> bifurcations;
    ArrayList<Point> endPoints;

    public FingerprintTemplateData() {
    }

    public void createTemplateFingerprintData(BufferedImage image, boolean registrationOnly) {

        finger = new Fingerprint(image);
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
        core = finger.getCore(dir);
        coreRadius = i5.getWidth() / 2;

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
            //store the data in a file and save in the db
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
}
