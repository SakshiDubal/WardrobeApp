package com.wardrobe.style.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Data
@Getter
@Setter
public class RegisterRequest {
    private String email;
    private String displayName;
    private String password;   // 👈 new field
    private Map<String, Object> preferences;

}

