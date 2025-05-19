package dstu.csae.secure.doc.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String login;
    private String password;
    private String phone;
    private String email;
}
