import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.awt.image.BufferedImage;

public class TypeOfImage extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        WritableImage writableImage = loadAndConvert();

        // Setting the image view
        ImageView imageView = new ImageView(writableImage);

        // Setting the position of the image
        imageView.setX(0);
        imageView.setY(0);

        // setting the fit height and width of the image view
        imageView.setFitHeight(700);
        imageView.setFitWidth(900);

        // Setting the preserve ratio of the image view
        imageView.setPreserveRatio(false);

        // Creating a Group object
        Group root = new Group(imageView);

        // Creating a scene object
        Scene scene = new Scene(root, 900, 700);

        // Setting title to the Stage
        stage.setTitle("Reading image as grayscale");

        // Adding scene to the stage
        stage.setScene(scene);

        // Displaying the contents of the stage
        stage.show();
    }
    public WritableImage loadAndConvert() throws Exception {
        // Loading the OpenCV core library
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );

        // Instantiating the imagecodecs class
        Imgcodecs imageCodecs = new Imgcodecs();

        String input = "F:\\Photo\\DaNang\\Anh\\DSC02211.JPG";

        // Reading the image
        Mat src = imageCodecs.imread(input, Imgcodecs.IMREAD_GRAYSCALE);

        byte[] data1 = new byte[src.rows() * src.cols() * (int)(src.elemSize())];
        src.get(0, 0, data1);

        // Creating the buffered image
        BufferedImage bufImage = new BufferedImage(src.cols(),src.rows(),
                BufferedImage.TYPE_BYTE_GRAY);

        // Setting the data elements to the image
        bufImage.getRaster().setDataElements(0, 0, src.cols(), src.rows(), data1);

        // Creating a WritableImage
        WritableImage writableImage = SwingFXUtils.toFXImage(bufImage, null);
        System.out.println("F:\\Photo\\DaNang\\Anh\\DSC02211.JPG");
        return writableImage;
    }
    public static void main(String args[]) throws Exception {
        launch(args);
    }
}