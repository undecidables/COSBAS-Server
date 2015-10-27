package cosbas.biometric.validators.fingerprint;

import cosbas.biometric.data.BiometricData;
import cosbas.biometric.request.DoorActions;
import cosbas.biometric.validators.ValidationResponse;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by VivianWork on 10/21/2015.
 */
public class FingerprintMatching {
    double matchingScore;
    InputStream in;
    BufferedImage originalImage;
    FingerprintTemplateData originalImageTemplateData;
    int Num_KeypointsFound;

    public FingerprintMatching(byte[] image) {
        try {

            this.matchingScore = 0.0;
            in = new ByteArrayInputStream(image);
            originalImage = getImage(in);

            // bifurcations and endpoints is stored in this object
            originalImageTemplateData = new FingerprintTemplateData();
            originalImageTemplateData.createTemplateFingerprintData(originalImage, false);

            Num_KeypointsFound = originalImageTemplateData.getEndPoints().size() + originalImageTemplateData.getBifurcations().size();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ValidationResponse matches(BiometricData DBitem, String userID, DoorActions action){

        int score = match(originalImageTemplateData.getEndPoints(), enter , originalImageTemplateData.getBifurcations(), enter );

        matchingScore = calculateMatchingScore(score);

        System.out.println("Keys: " + Num_KeypointsFound + "\nMatched: " + score + "\nMatching Score: " + matchingScore);

        //use the threshold in application.properties
        if (matchingScore >= 0.5) {
            return new ValidationResponse(true, "Match Found For " + userID, matchingScore);
        }
        else {
            return new ValidationResponse(false, "No Match Found For " + userID, matchingScore);
        }
    }

    private double calculateMatchingScore(int score) {
        double percentage = (score * 100) / Num_KeypointsFound;
        return Math.round(percentage) / 100.00;
    }

    private BufferedImage getImage(InputStream in) throws IOException {
        return ImageIO.read(in);
    }


    /**
     * The matching function to match two fingerprints.
     * @param a - endPoints of the original image
     * @param b - endPoints of the DB image
     * @param ab - bifurcations of the original image
     * @param bb - bifurcations of the DB image
     * @return score - the number of key points that matched
     */
    private int match(ArrayList<Point> a, ArrayList<Point> b, ArrayList<Point> ab, ArrayList<Point> bb) {
        HashMap<Point,ArrayList<Integer>> intersectionData = new HashMap<>();
        HashMap<Point,ArrayList<Integer>> endpointData = new HashMap<>();

        ArrayList<Point> in = new ArrayList<>();
        ArrayList<Point> end = new ArrayList<>();

        Integer[] distances_b = new Integer[b.size()];
        Integer[] distances_bb = new Integer[bb.size()];

        int dist, k = 0, score = 0;

        for(Point p1: a) {
            for(Point p2: b){
                dist = euclideanDistance(p1,p2);
                distances_b[k] = dist;
                k++;
            }
            k = 0;

            intersectionData.put(p1,new ArrayList<>(Arrays.asList(distances_b)));

            Arrays.fill(distances_b, 0);
        }

        for(Point p1_1: ab) {
            for (Point p2_1 : bb) {
                dist = euclideanDistance(p1_1, p2_1);
                distances_bb[k] = dist;
                k++;
            }
            k = 0;

            endpointData.put(p1_1,new ArrayList<>(Arrays.asList(distances_bb)));

            Arrays.fill(distances_bb, 0);
        }


        for(Map.Entry<Point,ArrayList<Integer>> entry : intersectionData.entrySet()) {
            //System.out.print(entry.getKey() + " --> ");
            ArrayList<Integer> data = entry.getValue();

            Collections.sort(data);

            if (!data.isEmpty()) {
                if (data.get(0) <= 10) {
                    score++;
                    in.add(entry.getKey());
                }
            }
            //System.out.print(entry.getKey() + " --> ");
            //printArrayLists(data);
        }

        for(Map.Entry<Point,ArrayList<Integer>> entry1 : endpointData.entrySet()) {
            //System.out.print(entry1.getKey() + " --> ");
            ArrayList<Integer> data1 = entry1.getValue();

            Collections.sort(data1);

            printArrayLists(data1);

            if (!data1.isEmpty()) {
                if (data1.get(0) <= 10) {
                    score++;
                    end.add(entry1.getKey());
                }
            }
            //System.out.print(entry1.getKey() + " --> ");

        }

        return score;
    }

    private int euclideanDistance(Point a, Point b) {
        return (int) Math.sqrt( (Math.pow((a.x - b.x),2)) + (Math.pow((a.y - b.y),2)));
    }


    private void printArrayLists(ArrayList<Integer> a) {
        int k = 0;

        System.out.print("\n[ ");
        for (Integer i : a) {
            if (k == a.size()-1) {
                System.out.print(i);
            }
            else {
                System.out.print(i + ",");
            }
            k++;
        }
        System.out.print(" ]\n");
    }
}
