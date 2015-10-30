package cosbas.biometric.helper;

import org.apache.commons.io.FileUtils;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_objdetect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * {@author Renette}
 * FaceDetector that uses OpenCV's CascasdeClassifier to detet and crop out faces.
 */
@Component
public class OpenCVFaceDetector extends FaceDetector<opencv_core.Mat> {

    private opencv_objdetect.CascadeClassifier faceDetector;
    @Value("${faces.classifierFile}")
    private String classifierFilename;

    public OpenCVFaceDetector(ImageProcessor<opencv_core.Mat> converter, String classifierFilename, int squareSize) {
        super(converter, squareSize, squareSize);
        this.classifierFilename = classifierFilename;
    }

    @Autowired
    protected OpenCVFaceDetector(ImageProcessor<opencv_core.Mat> converter) {
        super(converter);
    }

    @PostConstruct
    public void constructClassifier() throws IOException {

            faceDetector = new opencv_objdetect.CascadeClassifier(classifierFilename);

            byte[] image = FileUtils.readFileToByteArray(new File(classifierFilename));
    }

    @Override
    public List<Rectangle> detectFaces(opencv_core.Mat image) {
        opencv_core.Rect rects = new opencv_core.Rect();
        faceDetector.detectMultiScale(image, rects);

        opencv_core.CvRect b = rects.asCvRect();

        return splitRectangles(rects);
    }

    private List<Rectangle> splitRectangles(opencv_core.Rect rects) {
        List<Rectangle> rectlist = new ArrayList<>(rects.capacity());
        while (rects.position() < rects.capacity()) {
            opencv_core.CvRect current = rects.asCvRect();
            rectlist.add(new Rectangle(current.x(), current.y(), current.width(), current.height()));
            rects.position(rects.position() + 1);
            current.deallocate();
        }
        return rectlist;
    }

    @Override
    public Rectangle mostCenterRect(List<Rectangle> rects, double imageCenterX, double imageCenterY) {
        double bestDist = Integer.MAX_VALUE;

        Rectangle bestRect = null;
        for (Rectangle rect: rects) {
            double rectCenterX = rect.x + rect.width/2.0;
            double rectCenterY = rect.y + rect.height/2.0;

            double distance = Point.distance(rectCenterX, rectCenterY, imageCenterX, imageCenterY);
            if (distance < bestDist) {
                bestDist = distance;
                bestRect = rect;
            }
        }
        return bestRect;
    }



}
