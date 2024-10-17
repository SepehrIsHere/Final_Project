package ir.maktabsharif.finalproject.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {
    private CustomerDto customer;
    private SpecialistDto specialist;
}
