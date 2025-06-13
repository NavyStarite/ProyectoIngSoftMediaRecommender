package mx.ipn.media_recomender.dto;

import lombok.Data;

@Data
public class RegisterDTO {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
}