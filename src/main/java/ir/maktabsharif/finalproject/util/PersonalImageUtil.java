package ir.maktabsharif.finalproject.util;


import ir.maktabsharif.finalproject.entities.Specialist;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

@Component
public class PersonalImageUtil {
    private static final long MAX_IMAGE_SIZE = 300 * 1024;

    public void displayPersonalImage(Specialist specialist) {
        try {
            if (specialist.getPersonalImage().length > 0) {
                byte[] imageData = specialist.getPersonalImage();
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageData);
                BufferedImage personsImage = ImageIO.read(byteArrayInputStream);

                ImageIO.write(personsImage, "jpg", new File("output.jpg"));
            }
        } catch (Exception e) {
            System.out.println("An error occured while generating personal image " + e.getMessage());
        }
    }

    public byte[] writeImage(File inputFile) {
        try {
            BufferedImage image = ImageIO.read(inputFile);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            System.out.println("An error occured while adding image  " + e.getMessage());
        }
        return null;
    }

    public boolean isImageValid(File inputFile) {
        try {

            if ((double) inputFile.length() / 1024 >= MAX_IMAGE_SIZE) {
                return false;
            }

            BufferedImage image = ImageIO.read(inputFile);
            if (image == null) {
                return false;
            }

            ByteArrayOutputStream testOutput = new ByteArrayOutputStream();
            return ImageIO.write(image, "jpg", testOutput);
        } catch (Exception e) {
            System.out.println("An error occured while adding image  " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}
