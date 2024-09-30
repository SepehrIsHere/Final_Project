package util;

import entities.Specialist;
import service.SpecialistService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;

public class PersonalImageUtil {
    private final SpecialistService specialistService;
    private static final long MAX_IMAGE_SIZE = 300 * 1024;


    public PersonalImageUtil(SpecialistService specialistService) {
        this.specialistService = specialistService;
    }

    public void displayPersonalImage(Specialist specialist) {
        try {
            if (specialistService.checkSpecialistImage(specialist)) {
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

    private boolean isImageValid(File inputFile) {
        try {

            if (Files.size(inputFile.toPath()) > MAX_IMAGE_SIZE) {
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
        }
        return false;
    }
}
