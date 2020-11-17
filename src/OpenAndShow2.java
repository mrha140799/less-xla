import com.sun.corba.se.spi.ior.Writeable;
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

public class OpenAndShow2 extends Application {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
//    private static String FILE_NAME = "F:\\Photo\\DaNang\\Anh\\DSC02211.JPG";
    private static String FILE_NAME = "E:\\image.jpg";

    @Override
    public void start(Stage stage) throws Exception {
        WritableImage writableImage = loadAndConvertImage(FILE_NAME);
        ImageView imageView = new ImageView(writableImage);
        //Set vi tri cua hinh anh tren khung hinh
        imageView.setX(0);
        imageView.setY(0);
        //set chieu dai va rong cua hinh anh
        imageView.setFitHeight(500);
        imageView.setFitWidth(600 );
        // Set ti le khung hinh cua hinh anh: true -> ti le hinh anh mac dinh, false -> ti le man hinh se dc fill theo ti le cua hinh anh
        imageView.setPreserveRatio(true);
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

    private WritableImage loadAndConvertImage(String fileName) {
        Mat src = Imgcodecs.imread(fileName, Imgcodecs.IMREAD_GRAYSCALE);
        // tao ra 1 mang byte co do dai la tong so luong phan tu trong srcImage
        byte[] bytes = new byte[src.rows() * src.cols() * (int) src.elemSize()];
        // doc anh tu vi tri 0 0 trong mag bytes
        src.get(0, 0, bytes);
        BufferedImage bufImage = new BufferedImage(src.cols(), src.rows(),
                BufferedImage.TYPE_BYTE_GRAY);
        bufImage.getRaster().setDataElements(0, 0, src.cols(), src.rows(), bytes);
        WritableImage writableImage = SwingFXUtils.toFXImage(bufImage, null);
        return writableImage;

    }
    public static void main(String args[]) throws Exception {
        launch();
    }
}
