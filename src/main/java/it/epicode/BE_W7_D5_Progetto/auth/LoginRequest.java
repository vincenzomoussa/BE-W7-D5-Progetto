package it.epicode.BE_W7_D5_Progetto.auth;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
