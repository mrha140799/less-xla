import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class OpenAndShow {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) throws IOException {
        Imgcodecs imageCodecs = new Imgcodecs();
        Mat img = imageCodecs.imread("E:\\image.jpg", Imgcodecs.IMREAD_GRAYSCALE);
        BufferedImage bi = Mat2BufferedImage(img);
        displayImage(bi);
    }

    public static BufferedImage Mat2BufferedImage(Mat matFile) throws IOException {
        // chuyen ma tran thanh ma tran byte voi
        // Imgcodecs.imencode("dinh dang anh", "ten file mat","object mac dinh cua MatOfByte ");
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", matFile, matOfByte);
        // chuyen doi tuong matOfByte thanh mang byte
        byte[] byteArray = matOfByte.toArray();
        // chuan bi doi tuong inputStream
        InputStream inputStream = new ByteArrayInputStream(byteArray);
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        return  bufferedImage;
    }
    // hien thi anh bang jfram
    private static void displayImage(Image img) {
        ImageIcon icon = new ImageIcon(img);
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(img.getWidth(null), img.getHeight(null));
        JLabel lbl = new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
