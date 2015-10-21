package cosbas.biometric.validators.fingerprint;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by VivianWork on 10/21/2015.
 */
public class Fingerprint {

     /**
      * Pixel direction Enumeration
      */
        public enum direction { NONE, HORIZONTAL, VERTICAL, POSITIVE, NEGATIVE};


        Color DEFAULT_ZERO_COLOR = Color.black;         // Default color of FALSE pixels
        Color DEFAULT_ONE_COLOR = Color.pink;           // Default color of TRUE pixels

        boolean binMap [][];  // Binary picture
        int [][] greyMap; // Grey level picture
        int greymean; // Global mean of greylevel map

        int width;// Dimensions
        int height;

        Color zeroColor; // Colors
        Color oneColor;


        /**
         * Build a fingerprint from a BufferedImage
         */
        public Fingerprint(BufferedImage image)
        {
            // Initialize colors
            zeroColor = DEFAULT_ZERO_COLOR;
            oneColor = DEFAULT_ONE_COLOR;

            // Create the binary picture
            width = image.getWidth();
            height = image.getHeight();

            greyMap = new int [width][height];
            binMap = new boolean [width][height];

            // Generate greymap
            int curColor;
            for (int i = 0 ; i < width ; ++i)
            {
                for (int j = 0 ; j < height ; ++j)
                {
                    // Split the integer color
                    curColor = image.getRGB(i,j);
                    int R = (curColor >>16 ) & 0xFF;
                    int G = (curColor >> 8 ) & 0xFF;
                    int B = (curColor      ) & 0xFF;

                    int greyVal = (R + G + B) / 3;
                    greyMap[i][j] = greyVal;
                }
            }

            // Get the grey mean
            greymean = getGreylevelMean(greyMap, width, height);
        }

        public void printGreyMap(int[][]map, int w, int h){
            for (int i = 0 ; i < w ; ++i) {
                for (int j = 0; j < h; ++j) {
                    System.out.print(map[i][j]);
                }
                System.out.println();
            }
        }

        public void printGreyMaptoFile(int[][]map, int w, int h, String filen) throws IOException {
            FileWriter fw = new FileWriter(filen);
            File f = new File(String.valueOf(fw));
            for (int i = 0 ; i < w ; ++i) {
                for (int j = 0; j < h; ++j) {
                    System.out.print(map[i][j]);
                    fw.write(map[i][j]);
                }
                System.out.println();
                fw.write("\n");
            }
            fw.close();
        }

        /**
         * Get the width
         *
         * @return the width of the fingerprint image
         */
        public int getWidth()
        {
            return width;
        }

        /**
         * Get the height
         *
         * @return the height of the fingerprint image
         */
        public int getHeight()
        {
            return height;
        }

        /**
         * Set the colors used when converting the binary image to buffered image
         *
         * @param zeroColor Color of zero (off) pixels
         * @param zeroColor Color of one (on) pixels
         */
        public void setColors(Color zeroColor, Color oneColor)
        {
            this.zeroColor = zeroColor;
            this.oneColor = oneColor;
        }

        /**
         * Extract intersection points from the binary image, which is a
         * kind of minutiae
         *
         * @param core the core point (center of fingerprint)
         * @param coreRadius radius from core that defines the considered area
         * @return the array of intersection points.
         */
        public ArrayList<Point> getMinutiaeIntersections (Point core, int coreRadius )
        {
            // Variables
            ArrayList<Point> minutiae = new ArrayList<Point>();
            int nbOnNeighbors;
            Point currentPoint;

            // Define bounds
            int minI = core.x - coreRadius;
            int maxI = core.x + coreRadius;
            int minJ = core.y - coreRadius;
            int maxJ = core.y + coreRadius;

            if (minI < 1)
                minI = 1;

            if (maxI > width - 2)
                maxI = width - 2;

            if (minJ < 1)
                minJ = 1;

            if (maxJ > height - 2)
                maxJ = height - 2;

            // Iterate on binary picture
            for (int i = minI ; i < maxI ; ++i)
            {
                for (int j = minJ ; j < maxJ ; ++j)
                {
                    currentPoint = new Point(i,j);

                    if (getDistance(currentPoint, core) < coreRadius)
                    {
                        // Determine if it is a intersection point
                        nbOnNeighbors = getSum(getFourNeigbors(i,j));

                        if ( (binMap[i][j] == true) && ((nbOnNeighbors == 3) || (nbOnNeighbors == 4)))
                        {
                            minutiae.add(currentPoint);
                        }
                    }
                }
            }

            return minutiae;
        }

        /**
         * Extract end points from the binary image, which is a
         * kind of minutiae
         *
         * @param core the core point (center of fingerprint)
         * @param coreRadius radius from core that defines the considered area
         * @return the array of end points.
         */
        public ArrayList<Point> getMinutiaeEndpoints (Point core, int coreRadius )
        {
            // Variables
            ArrayList<Point> minutiae = new ArrayList<Point>();
            int nbOnNeighbors;
            boolean [] neighbors;
            Point currentPoint;

            // Set iteration bounds
            int minI = core.x - coreRadius;
            int maxI = core.x + coreRadius;
            int minJ = core.y - coreRadius;
            int maxJ = core.y + coreRadius;

            if (minI < 1)
                minI = 1;

            if (maxI > width - 2)
                maxI = width - 2;

            if (minJ < 1)
                minJ = 1;

            if (maxJ > height - 2)
                maxJ = height - 2;

            // Iterate on the binary image
            for (int i = minI ; i < maxI ; ++i)
            {
                for (int j = minJ ; j < maxJ ; ++j)
                {
                    currentPoint = new Point(i,j);

                    if (getDistance(currentPoint, core) < coreRadius)
                    {
                        // Determine if it is a intersection point
                        neighbors = getNeigbors(binMap, i, j);
                        nbOnNeighbors = getSum(neighbors);

                        if (    (binMap[i][j] == true) &&
                                (nbOnNeighbors == 1) &&
                                ((neighbors[0] == true) || (neighbors[2] == true) || (neighbors[4] == true) || (neighbors[6] == true)))
                        {
                            minutiae.add(currentPoint);
                        }
                    }
                }
            }

            return minutiae;
        }

        /**
         * Convert the binary image to the buffered image (using the on and off colors)
         *
         * @return the buffered image
         */
        public BufferedImage toBufferedImage()
        {
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int i = 0 ; i < width ; ++i)
            {
                for (int j = 0 ; j < height ; ++j)
                {
                    // Binary value to color
                    if (binMap[i][j] == false)
                    {
                        bufferedImage.setRGB(i, j, zeroColor.getRGB());
                    }
                    else
                    {
                        bufferedImage.setRGB(i, j, oneColor.getRGB());
                    }
                }
            }

            return bufferedImage;
        }

        /**
         * Set the binary matrix of the object from the greylevel matrix, using
         * global grey mean as threshold.
         * Contract : the greylevel matrix has to be created before calling this
         * method.
         */
        public void binarizeMean()
        {
            // Generate the boolean value
            for (int i = 0 ; i < width ; ++i)
            {
                for (int j = 0 ; j < height ; ++j)
                {
                    binMap[i][j] = !(greyMap[i][j] > (greymean));
                }
            }
        }

        /**
         * Set the binary matrix of the object from the greylevel matrix, using
         * local grey mean as thresholds. This method requires more computation than
         * binarizeMean, but the result is better.
         * Contract : the greylevel matrix has to be created before calling this
         * method.
         */
        public void binarizeLocalMean()
        {
            // Variables
            int windowSize = 20;
            int step = 20;
            float localGreyMean;
            int ik, jk;

            // Iterate on the binary matrix
            for (int i = 0 ; i < width ; i += step)
            {
                for (int j = 0 ; j < height ; j += step)
                {
                    // Get local grey seum
                    localGreyMean = 0;
                    ik = 0;
                    jk = 0;

                    for (ik = i ; ik < (i + windowSize); ++ik)
                    {
                        if (ik >= width)
                            break;

                        for (jk = j ; jk < (j + windowSize); ++jk)
                        {
                            if (jk >= height)
                                break;

                            localGreyMean += greyMap[ik][jk];
                        }
                    }

                    // Calculate the mean
                    if (jk*ik != 0)
                    {
                        localGreyMean = localGreyMean / ((ik-i)*(jk-j));
                    }

                    // If the local grey mean is too high (too permissive)
                    // we take the global greymean
                    if (localGreyMean > greymean)
                    {
                        localGreyMean = 0.75f*greymean + 0.25f*localGreyMean;
                    }

                    // Binarize all the pixels in the window
                    for (ik = i ; ik < (i + windowSize); ++ik)
                    {
                        if (ik >= width)
                            break;

                        // Get greymean
                        for (jk = j ; jk < (j + windowSize); ++jk)
                        {
                            if (jk >= height)
                                break;

                            binMap[ik][jk] = !(greyMap[ik][jk] > (localGreyMean));
                        }
                    }
                }
            }
        }

        /**
         * Add or remove borders on the binary matrix
         *
         * @param size number of pixels to add (or remove if < 0)
         */
        public void addBorders(int size)
        {
            boolean [][] newmap = new boolean [width + 2*size][height + 2*size];

            int limitTop = size;
            int limitLeft = size;
            int limitRight = width;
            int limitBottom = height;

            width += 2*size;
            height += 2*size;

            for (int i = 0 ; i < width ; ++i)
            {
                for (int j = 0 ; j < height ; ++j)
                {
                    if ( i < limitLeft || i > limitRight || j < limitTop || j > limitBottom)
                    {
                        newmap[i][j] = false;
                    }
                    else
                    {
                        newmap[i][j] = binMap [i-size][j-size];
                    }
                }
            }

            binMap = newmap;
        }

        /**
         * Remove noise from the binary picture using mean algorithm
         */
        public void removeNoise()
        {
            // Mean filter
            float [][] filter = {   {0.0625f,0.1250f,0.0625f},
                    {0.1250f,0.2500f,0.1250f},
                    {0.0625f,0.1250f,0.0625f}};

            // Variables
            int limWidth  = width -1;
            int limHeight = height-1;
            boolean [][] newmap = new boolean [width][height];
            float val;

            copyMatrix(binMap, newmap);
            for (int i = 1 ; i < limWidth ; ++i)
            {
                for (int j = 1 ; j < limHeight ; ++j)
                {
                    val = 0;

                    // Apply the filter
                    for (int ik = -1 ; ik <= 1 ; ++ik)
                    {
                        for (int jk = -1 ; jk <= 1 ; ++jk)
                        {
                            val += booleanToInt(binMap[i+ik][j+jk])*filter[1+ik][1+jk];
                        }
                    }
                    newmap[i][j] = (val > 0.5);
                }
            }

            copyMatrix(newmap, binMap);
        }

        /**
         * Use the Zhang-Suen algorithm on the binary picture and alter
         * the actual binary picture.
         */
        public void skeletonize()
        {
            // Bounds
            int fstLin = 1;
            int lstLin = width - 1;
            int fstCol = 1;
            int lstCol = height - 1;

            // Variables
            boolean [][] prevM = new boolean [width][height];;
            boolean [][] newM = new boolean [width][height];;
            boolean [] neighbors;
            int A, B;

            // Initialize
            copyMatrix(binMap, prevM);

            // We skeletonize until there are no changes between two iterations
            while (true)
            {
                copyMatrix(prevM, newM);

                // First subiteration, for NW and SE neigbors
                for (int i = fstLin ; i < lstLin ; ++i)
                {
                    for (int j = fstCol ; j < lstCol ; ++j)
                    {
                        neighbors = getNeigbors(newM, i,j);

                        // Get the decision values
                        B = getSum(neighbors);
                        A = getTransitions(neighbors);

                        // Decide if we remove the pixel
                        if ( ( B >= 2 ) && ( B <= 6 ) )
                        {
                            if ( A == 1 )
                            {
                                if ((neighbors[0] && neighbors[2] && neighbors[4]) == false )
                                {
                                    if ((neighbors[2] && neighbors[4] && neighbors[5]) == false )
                                    {
                                        newM [i][j] = false;
                                    }
                                }
                            }
                        }
                    }
                }

                // Second subiteration, for NE and SW neigbors
                for (int i = fstLin ; i < lstLin ; ++i)
                {
                    for (int j = fstCol ; j < lstCol ; ++j)
                    {
                        neighbors = getNeigbors(newM,i,j);

                        // Get the decision values
                        B = getSum(neighbors);
                        A = getTransitions(neighbors);

                        // Decide if we remove the pixel
                        if ( ( B >= 2 ) && ( B <= 6 ) )
                        {
                            if ( A == 1 )
                            {
                                if ((neighbors[0] && neighbors[2] && neighbors[6]) == false )
                                {
                                    if ((neighbors[0] && neighbors[4] && neighbors[6]) == false )
                                    {
                                        newM [i][j] = false;
                                    }
                                }
                            }
                        }
                    }
                }

                // Stop conditions
                if (equal(newM, prevM, width, height))
                {
                    break;
                }
                else
                {
                    copyMatrix(newM, prevM);
                }
            }

            // Return matrix
            copyMatrix(newM, binMap);
        }

        /**
         * Convert the direction matrix to direction buffered image
         * @param dirMatrix
         * @return the buffered image
         */
        public BufferedImage directionToBufferedImage(direction [][] dirMatrix)
        {
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            for (int i = 0 ; i < width ; ++i)
            {
                for (int j = 0 ; j < height ; ++j)
                {
                    bufferedImage.setRGB(i, j, (dirToColor (dirMatrix[i][j])).getRGB());
                }
            }

            return bufferedImage;
        }

        /**
         * Calculate and return the directions of ridges (for each pixel)
         * @return the direction matrix
         */
        public direction [][] getDirections()
        {
            // Direction patterns
            direction [][] dirMatrix = new direction [width][height];

            int minI = 1;
            int maxI = width - 2;
            int minJ = 1;
            int maxJ = height - 2;

            for (int i = 0 ; i < width ; ++i)
            {
                for (int j = 0 ; j < height ; ++j)
                {
                    if ((binMap[i][j] == false) || (i < minI) || (i > maxI) || (j < minJ) || (j > maxJ) )
                        dirMatrix[i][j] = direction.NONE;
                    else if ((binMap[i-1][j+1] == true) && (binMap[i+1][j-1] == true))
                        dirMatrix[i][j] = direction.POSITIVE;
                    else if ((binMap[i-1][j-1] == true) && (binMap[i+1][j+1] == true))
                        dirMatrix[i][j] = direction.NEGATIVE;
                    else if ((binMap[i][j-1] == true) && (binMap[i][j+1] == true))
                        dirMatrix[i][j] = direction.VERTICAL;
                    else if ((binMap[i-1][j] == true) && (binMap[i+1][j] == true))
                        dirMatrix[i][j] = direction.HORIZONTAL;
                    else
                        dirMatrix[i][j] = direction.NONE;
                }
            }

            return dirMatrix;
        }

        /**
         * Calculate and return the core of the binary matrix.
         * @param dirMatrix
         * @return the core
         */
        public Point getCore (direction [][] dirMatrix)
        {
            // Private class to store partial results
            // of a window
            class coreInfos
            {
                private int nbVer, nbHor, nbPos, nbNeg;

                /**
                 * Return the indicator of the window
                 * @return indicator
                 */
                public float getIndex()
                {
                    // Store the number of pixel in each direction
                    float perVer, perHor, perPos, perNeg;
                    float total = nbVer + nbHor + nbPos + nbNeg;
                    float res;

                    if (total == 0)
                        return 1;

                    perVer = nbVer / total;
                    perHor = nbHor / total;
                    perPos = nbPos / total;
                    perNeg = nbNeg / total;

                    res =     Math.abs(perVer - .25f)
                            + Math.abs(perHor - .25f)
                            + Math.abs(perPos - .25f)
                            + Math.abs(perNeg - .25f);

                    return res;
                }

                /**
                 * Reset all values
                 */
                public void reset()
                {
                    nbVer = 0;
                    nbHor = 0;
                    nbPos = 0;
                    nbNeg = 0;
                }

                /**
                 * Take values from another object
                 * @param r read object
                 */
                public void copyFrom(coreInfos r)
                {
                    nbVer = r.nbVer;
                    nbHor = r.nbHor;
                    nbPos = r.nbPos;
                    nbNeg = r.nbNeg;
                }

                // Increment values
                public void incVertical  (){++nbVer;}
                public void incHorizontal(){++nbHor;}
                public void incPositive  (){++nbPos;}
                public void incNegative  (){++nbNeg;}
            }

            // Variables
            Point core = new Point();
            int windowSize = width / 6;

            int minIK, maxIK, minJK, maxJK;

            coreInfos bestCandidate = new coreInfos();
            coreInfos currentCandidate = new coreInfos();

            // Bounds
            int minI = windowSize;
            int maxI = width - windowSize;
            int minJ = windowSize;
            int maxJ = height - windowSize;

            bestCandidate.reset();

            // Iterate on the picture
            for (int i = minI ; i < maxI ; ++i)
            {
                for (int j = minJ ; j < maxJ ; ++j)
                {
                    // Reset current infos
                    currentCandidate.reset();

                    minIK = i - windowSize;
                    maxIK = i + windowSize;
                    minJK = j - windowSize;
                    maxJK = j + windowSize;

                    // Calculate direction proportions
                    for (int ik = minIK ; ik < maxIK ; ++ik)
                    {
                        for (int jk = minJK ; jk < maxJK; ++jk)
                        {
                            // Increment the good value
                            switch (dirMatrix[ik][jk])
                            {
                                case HORIZONTAL:
                                    currentCandidate.incHorizontal();
                                    break;

                                case POSITIVE:
                                    currentCandidate.incPositive();
                                    break;

                                case NEGATIVE:
                                    currentCandidate.incNegative();
                                    break;

                                case VERTICAL:
                                    currentCandidate.incVertical();
                                    break;
                            }
                        }
                    }

                    // Check if we keep the core
                    if (currentCandidate.getIndex() <= bestCandidate.getIndex())
                    {
                        bestCandidate.copyFrom(currentCandidate);
                        core.x = i;
                        core.y = j;
                    }
                }
            }
            return core;
        }

        //---------------------------------------------------- PRIVATE METHODS --//

        /**
         * Extract the 4-neighbors of a point on the binary image
         * @param i x value
         * @param j y value
         * @return the table of neighbors
         */
        private boolean [] getFourNeigbors(int i, int j)
        {
            boolean []neighbors = new boolean [4];
            neighbors[0] = binMap[i+0][j-1];
            neighbors[1] = binMap[i+1][j+0];
            neighbors[2] = binMap[i+0][j+1];
            neighbors[3] = binMap[i-1][j+0];

            return neighbors;
        }

        /**
         * Get the greylevel mean of the greylevel ma
         * @param greymap Grey level map
         * @param w width of the map
         * @param h height of the map
         */
        private int getGreylevelMean( int [][] greymap, int w, int h)
        {
            int total = 0;
            for (int i = 0 ; i < w ; ++i)
            {
                for (int j = 0 ; j < h ; ++j)
                {
                    total += greymap[i][j];
                }
            }

            return total/(w*h);
        }

        /**
         * Convert a boolean to an integer
         * (true  -> 1
         *  false -> 0)
         * @param b value
         * @return corresponding integer value
         */
        private int booleanToInt(boolean b)
        {
            return (b==true)?1:0;
        }

        /**
         * Copy source matrix to destination
         * @param src Source matrix
         * @param dst Destination matrix
         */
        private void copyMatrix (boolean [][] src, boolean [][] dst)
        {
            for (int i = 0 ; i < width ; ++i)
            {
                for (int j = 0 ; j < height ; ++j)
                {
                    dst[i][j] = src[i][j];
                }
            }
        }

        /**
         * Extract the 8-neighbors around the specified pixel.
         * @param mat input matrix
         * @param i x value
         * @param j y value
         * @return a table of the neighbors
         */
        private boolean[] getNeigbors(boolean [][] mat, int i, int j)
        {
            boolean[] neigbors = new boolean[8];

            neigbors[0] = mat[i-1][j+0];
            neigbors[1] = mat[i-1][j+1];
            neigbors[2] = mat[i+0][j+1];
            neigbors[3] = mat[i+1][j+1];
            neigbors[4] = mat[i+1][j+0];
            neigbors[5] = mat[i+1][j-1];
            neigbors[6] = mat[i+0][j-1];
            neigbors[7] = mat[i-1][j-1];

            return neigbors;
        }

        /**
         * Calculate the euclidian distance between two points
         * @param a first point
         * @param b second point
         * @return distance between two points
         */
        private float getDistance(Point a, Point b)
        {
            int deltaX = b.x - a.x;
            int deltaY = b.y - a.y;

            return (float)Math.sqrt(Math.abs(deltaX*deltaX)+Math.abs(deltaY*deltaY));
        }

        /**
         * Calculate the false->true transitions in neighbors table.
         * @param neighbors
         * @return the number of transitions
         */
        private int getTransitions (boolean [] neighbors)
        {
            int nbTransitions = 0;

            for (int k = 0 ; k < 7 ; ++k )
            {
                if ((neighbors[k] == false) && ((neighbors[k+1] == true)))
                    ++nbTransitions;
            }

            if ((neighbors[7] == false) && ((neighbors[0] == true)))
                ++nbTransitions;

            return nbTransitions;
        }

        /**
         * Calculate the number of ON pixels in a table
         * @param vals table
         * @return the number of ON pixels
         */
        private int getSum (boolean [] vals)
        {
            int max = vals.length;
            int sum = 0;

            for (int k = 0 ; k < max ; ++k )
            {
                sum += booleanToInt(vals[k]);
            }

            return sum;
        }

        /**
         * Indicates if two boolean matrices are equals
         * @param A first matrix
         * @param B second matrix
         * @param w width of the two matrices
         * @param h height of the two matrices
         * @return true if matrices are equal
         */
        private boolean equal(boolean [][] A, boolean [][] B, int w, int h)
        {
            for (int i = 0 ; i < w ; ++i)
            {
                for (int j = 0 ; j < h ; ++j)
                {
                    // If a value is different, matrices are different
                    if (A[i][j] != B[i][j])
                        return false;
                }
            }

            return true;
        }

        /**
         * Get the color of the given direction
         * @param dir direction
         * @return the corresponding color
         */
        private Color dirToColor(direction dir)
        {
            switch (dir)
            {
                case NONE:
                    return Color.black;

                case HORIZONTAL:
                    return Color.red;

                case POSITIVE:
                    return Color.green;

                case NEGATIVE:
                    return Color.yellow;

                case VERTICAL:
                    return Color.cyan;

                default:
                    return Color.black;
            }
        }

        /*public static void main(String[] args) {
            try {
                String path = "C:\\Users\\VivianWork\\Downloads\\WorkedEx\\WorkedEx\\DataBase\\Bmp\\test4\\";
                //String pic1 = "v_rThumb01.bmp";
                //String pic2 = "v_rThumb00.bmp";

                String pic1 = "test400.bmp";
                String pic2 = "test300.bmp";
                //String pic2 = "v_lIndex2012.bmp";
                //String pic2 = "v_rThumb00.bmp";
                //String pic2 = "v_lThumb01.bmp";
                //String pic2 = "v_rIndex00.bmp";

                FingerPrint finger = new FingerPrint(path + pic1);

                finger.setColors(Color.black, Color.green);
                finger.binarizeMean();
                BufferedImage i = finger.toBufferedImage();
                File f = new File(path + "temp1_1.bmp");
                ImageIO.write(i, "bmp", f);

                finger.binarizeLocalMean();
                //BufferedImage i1 = finger.toBufferedImage();
                //File f1 = new File(path + "temp1_2.bmp");
                //ImageIO.write(i1, "bmp", f1);

                finger.addBorders(1);
                finger.removeNoise();
                finger.removeNoise();
                finger.removeNoise();
                //BufferedImage i2 = finger.toBufferedImage();
                //File f2 = new File(path + "temp1_3.bmp");
                //ImageIO.write(i2, "bmp", f2);


                finger.skeletonize();
                //BufferedImage i3 = finger.toBufferedImage();
                //File f3 = new File(path + "temp1_4.bmp");
                //ImageIO.write(i3, "bmp", f3);

                direction[][] dir = finger.getDirections();
                //BufferedImage i4 = finger.directionToBufferedImage(dir);
                //File f4 = new File(path + "temp1_5.bmp");
                //ImageIO.write(i4, "bmp", f4);

                BufferedImage i5 = finger.directionToBufferedImage(dir);
                Point core = finger.getCore(dir);
                int coreR = i5.getWidth() / 2;

                BufferedImage i6 = finger.directionToBufferedImage(dir);
                ArrayList<Point> intersections = finger.getMinutiaeIntersections(core, coreR);
                ArrayList<Point> endPoints = finger.getMinutiaeEndpoints(core, coreR);

            /*for (Point p : intersections) {
                System.out.println(p.x + " : " + p.y);
            }
            System.out.println("\n");
            for (Point p : endPoints) {
                System.out.println(p.x + " : " + p.y);
            }*/

                /*Graphics g = i6.getGraphics();

                g.setColor(Color.magenta);
                for (Point point : intersections) {
                    g.fillOval(point.x - 2, point.y - 2, 4, 4);
                }

                g.setColor(Color.blue);
                for (Point point : endPoints)
                {
                    g.fillOval(point.x-2, point.y-2, 4, 4);
                }
                File f6 = new File(path + "temp1_6.bmp");
                ImageIO.write(i6, "bmp", f6);


                FingerPrint finger1 = new FingerPrint(path + pic2);

                finger1.setColors(Color.black, Color.green);
                finger1.binarizeMean();
                //BufferedImage ia = finger1.toBufferedImage();
                //File fa = new File(path + "temp1_1a.bmp");
                //ImageIO.write(ia, "bmp", fa);

                finger1.binarizeLocalMean();
                //BufferedImage i1a = finger1.toBufferedImage();
                //File f1a = new File(path + "temp1_2a.bmp");
                //ImageIO.write(i1a, "bmp", f1a);

                finger1.addBorders(1);
                finger1.removeNoise();
                finger1.removeNoise();
                finger1.removeNoise();
                //BufferedImage i2a = finger1.toBufferedImage();
                //File f2a = new File(path + "temp1_3a.bmp");
                //ImageIO.write(i2a, "bmp", f2a);


                finger1.skeletonize();
                //BufferedImage i3a = finger1.toBufferedImage();
                //File f3a = new File(path + "temp1_4a.bmp");
                //ImageIO.write(i3a, "bmp", f3a);

                direction[][] dira = finger1.getDirections();
                //BufferedImage i4a = finger1.directionToBufferedImage(dira);
                //File f4a = new File(path + "temp1_5a.bmp");
                //ImageIO.write(i4a, "bmp", f4a);

                BufferedImage i5a = finger1.directionToBufferedImage(dir);
                Point corea = finger1.getCore(dira);
                int coreRa = i5a.getWidth() / 2;

                BufferedImage i6a = finger1.directionToBufferedImage(dira);
                ArrayList<Point> intersectionsa = finger1.getMinutiaeIntersections(corea, coreRa);
                ArrayList<Point> endPointsa = finger1.getMinutiaeEndpoints(corea, coreRa);

                Graphics ga = i6a.getGraphics();

                ga.setColor(Color.magenta);
                for (Point point : intersectionsa) {
                    ga.fillOval(point.x - 2, point.y - 2, 4, 4);
                }

                ga.setColor(Color.blue);
                for (Point point : endPointsa) {
                    ga.fillOval(point.x - 2, point.y - 2, 4, 4);
                }
                File f6a = new File(path + "temp1_6a.bmp");
                ImageIO.write(i6a, "bmp", f6a);


                FileWriter fw = new FileWriter(path + "temp1.txt");
                for (Point p : endPointsa) {
                    fw.write(p.x + " : " + p.y + "\n");
                }
                fw.close();

                int score = finger.match(endPoints, endPointsa, intersections, intersectionsa);
                int size = endPoints.size() + intersections.size();
                double perc = (score*100)/size;

                System.out.println("Keys: " + size + "\nMatched: " + score + "\nPercentage: " + perc + "\nSend Percentage: " + (double)Math.round(perc)/100.00);

                BufferedImage i7 = finger.directionToBufferedImage(dir);

                Graphics g1 = i7.getGraphics();

                g1.setColor(Color.magenta);
                for (Point point : finger.end) {
                    g1.fillOval(point.x - 2, point.y - 2, 4, 4);
                }

                g1.setColor(Color.blue);
                for (Point point : finger.in)
                {
                    g1.fillOval(point.x-2, point.y-2, 4, 4);
                }
                File f7 = new File(path + "temp1_7.bmp");
                ImageIO.write(i7, "bmp", f7);

            } catch (IOException e) {
                e.printStackTrace();
            }

            }
        }*/
}

