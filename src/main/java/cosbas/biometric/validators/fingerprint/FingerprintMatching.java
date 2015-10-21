package cosbas.biometric.validators.fingerprint;

import cosbas.biometric.data.BiometricData;
import cosbas.biometric.validators.ValidationResponse;

import java.awt.*;
import java.util.*;

/**
 * Created by VivianWork on 10/21/2015.
 */
public class FingerprintMatching {
    double matchingScore;

    public ValidationResponse match(BiometricData sample1, String userID) {

        int score = match(endPoints, endPointsa, intersections, intersectionsa);
        int size = endPoints.size() + intersections.size();
        double perc = (score*100)/size;

        System.out.println("Keys: " + size + "\nMatched: " + score + "\nPercentage: " + perc + "\nSend Percentage: " + (double)Math.round(perc)/100.00);

        return new ValidationResponse(true,"Match Found For " + userID, matchingScore);
    }

    public int match(ArrayList<Point> a, ArrayList<Point> b, ArrayList<Point> ab, ArrayList<Point> bb) {
        HashMap<Point,ArrayList<Integer>> intersectionData = new HashMap<>();
        HashMap<Point,ArrayList<Integer>> endpointData = new HashMap<>();

        in = new ArrayList<>();
        end = new ArrayList<>();

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

    public int euclideanDistance(Point a, Point b) {
        return (int) Math.sqrt( (Math.pow((a.x - b.x),2)) + (Math.pow((a.y - b.y),2)));
    }


    public void printArrayLists(ArrayList<Integer> a) {
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
