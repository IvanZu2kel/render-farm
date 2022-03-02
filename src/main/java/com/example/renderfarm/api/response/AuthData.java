package com.example.renderfarm.api.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AuthData implements Dto {
    private String email;
}
