package ir.maktabsharif.finalproject.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String email;
    private List<OrderDto> orders;
    private List<CommentDto> comments;

    public CustomerDto(String firstname, String lastname, String username, String password, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
