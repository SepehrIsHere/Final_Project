package ir.maktabsharif.finalproject.controller;

import ir.maktabsharif.finalproject.entities.Specialist;
import ir.maktabsharif.finalproject.exception.ImageOperationException;
import ir.maktabsharif.finalproject.service.SpecialistService;
import ir.maktabsharif.finalproject.util.PersonalImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("api/image")
@RequiredArgsConstructor
public class PersonalImageController {
    private final PersonalImageUtil personalImageUtil;
    private final SpecialistService specialistService;

    @PostMapping("specialist/upload/{specialistFirstName}/{specialistLastName}")
    @PreAuthorize("hasRole('SPECIALIST')")
    public ResponseEntity<String> uploadPersonalImage(@PathVariable String specialistFirstName
            , @PathVariable String specialistLastName
            , @RequestParam("imageFile") MultipartFile file) {
        try {
            File imageFile = convertMultipartFileToFile(file);
            if (!personalImageUtil.isImageValid(imageFile)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid image format or size too large.");
            }
            byte[] imageBytes = personalImageUtil.writeImage(imageFile);
            Specialist specialist = specialistService.findByFirstNameAndLastName(specialistFirstName, specialistLastName);
            specialist.setPersonalImage(imageBytes);
            specialistService.update(specialist);
            return ResponseEntity.ok("Image uploaded successfully ");
        } catch (Exception e) {
            throw new ImageOperationException("An error occured while trying to add personal image");
        }
    }

    @GetMapping("specialist/view/{specialistFirstName}/{specialistLastName}")
    @PreAuthorize("hasRole('SPECIALIST')")
    ResponseEntity<byte[]> getPersonalImage(@PathVariable String specialistFirstName, @PathVariable String specialistLastName) {
        try {
            Specialist specialist = specialistService.findByFirstNameAndLastName(specialistFirstName, specialistLastName);
            byte[] personalImage = specialist.getPersonalImage();
            if (personalImage != null || personalImage.length == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"image.jpg\"")
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(personalImage);
        } catch (Exception e) {
            throw new ImageOperationException("An error occured while trying to display personal image ");
        }
    }

    private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(System.getProperty("java.io.tmpdir") + "/" + multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);
        return file;
    }
}
