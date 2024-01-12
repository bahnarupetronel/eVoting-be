package com.example.demo.payload;

import com.example.demo.utils.ExpirationDate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class UserInfoResponse {
    private Long id;
    private String email;
    private List<String> roles;
    private LocalDateTime expirationDate;

    public UserInfoResponse(Long id, String email, List<String> roles) {
        this.id = id;
        this.email = email;
        this.roles = roles;
        this.expirationDate = ExpirationDate.createExpirationDate();
    }

}