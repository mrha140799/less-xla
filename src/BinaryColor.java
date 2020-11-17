import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class BinaryColor extends Application {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    //    private static String FILE_NAME = "F:\\Photo\\DaNang\\Anh\\DSC02211.JPG";
    private static String FILE_NAME = "img/tracnghiem3.png";

    @Override
    public void start(Stage stage) throws Exception {
        WritableImage writableImage = loadAndConvertToBinaryColor(FILE_NAME);
        ImageView imageView = new ImageView(writableImage);
        //Set vi tri cua hinh anh tren khung hinh
        imageView.setX(0);
        imageView.setY(0);
        //set chieu dai va rong cua hinh anh
        imageView.setFitHeight(900);
        imageView.setFitWidth(1200);
        // Set ti le khung hinh cua hinh anh: true -> ti le hinh anh mac dinh, false -> ti le man hinh se dc fill theo ti le cua hinh anh
        imageView.setPreserveRatio(false);
        // tao 1 Group object
        Group root = new Group(imageView);
        // Creating a oject man hinh
        Scene scene = new Scene(root, 1200, 900);
        // Setting title to the Stage
        stage.setTitle("Reading image as grayscale");
        stage.setScene(scene);
        // Displaying the contents of the stage
        stage.show();

    }

    private WritableImage loadAndConvertToBinaryColor(String fileName) {
        // read image
        Mat fileSrc = Imgcodecs.imread(fileName, Imgcodecs.IMREAD_GRAYSCALE);
//        Mat adapt_thresh = new Mat();
//        Imgproc.adaptiveThreshold(blurry, adapt_thresh , 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY_INV, 11, 5);
        Mat equalizedImg = new Mat();
        Imgproc.equalizeHist(fileSrc, equalizedImg);
        MatOfDouble mu = new MatOfDouble();
        MatOfDouble sigma = new MatOfDouble();
        Core.meanStdDev(equalizedImg, mu, sigma);
        double median = mu.get(0, 0)[0] * 0.6;
        Mat thresh = new Mat();
        Imgproc.threshold(equalizedImg, thresh, median, 255, 1);
        Mat fileCont = new Mat();
        List<MatOfPoint> list = new ArrayList<>();
        List<MatOfPoint> list2 = new ArrayList<>();
        Imgproc.findContours(thresh, list, fileCont, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        System.out.println(list.size());
        for (MatOfPoint c : list) {
            Rect rect = Imgproc.boundingRect(c);
            float ar = rect.width / (float) (rect.height);
            if (rect.width >= 18 && rect.height >= 18 && ar >= 0.9 && ar <= 1.2) {
                list2.add(c);
            }
        }
        Imgproc.drawContours(fileSrc, list2, -1, new Scalar(0,0,255), Imgproc.LINE_4);
        return getByMatFile(thresh);
    }

    private WritableImage getByMatFile(Mat file) {
        byte[] bytes = new byte[file.rows() * file.cols() * (int) (file.elemSize())];
        file.get(0, 0, bytes);
        //Creating Buffered image using the data
        BufferedImage bufferedImage = new BufferedImage(file.cols(), file.rows(), BufferedImage.TYPE_BYTE_GRAY);
        // Setting the data elements to the image
        bufferedImage.getRaster().setDataElements(0, 0, file.cols(), file.rows(), bytes);
        // Creating a Writable image
        WritableImage writableImage = SwingFXUtils.toFXImage(bufferedImage, null);
        return writableImage;
    }

    public static void main(String[] args) {
        launch();
    }
}
