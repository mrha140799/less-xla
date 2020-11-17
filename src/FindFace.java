import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class FindFace {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private static String FILE_NAME = "F:\\Photo\\DaNang\\Anh\\DSC02211.JPG";

    public static void main(String[] args) {
        Mat src = Imgcodecs.imread(FILE_NAME);
        // Instantiating the CascadeClassifier
        String xmlFile = "xml/lbpcascade_frontalface.xml";
        CascadeClassifier cascadeClassifier = new CascadeClassifier(xmlFile);
        // Detecting the face in the snap
        MatOfRect faceDetection = new MatOfRect();
        cascadeClassifier.detectMultiScale(src, faceDetection);
        for (Rect rect : faceDetection.toArray()) {
            Imgproc.rectangle(src, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 0, 255), 3);
        }
        Imgcodecs.imwrite("img/facedetect_output1.jpg", src);
    }
}
