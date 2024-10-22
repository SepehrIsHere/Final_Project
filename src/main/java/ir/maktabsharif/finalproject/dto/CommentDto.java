package ir.maktabsharif.finalproject.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {
    private String customerFirstName;
    private String customerLastName;
    private String specialistFirstName;
    private String specialistLastName;
    private String description;
    private double rating;
}
