package dstu.csae.secure.doc.dto;

import lombok.Data;

@Data
public class SigninRequest {
    private String login;
    private String password;
}
