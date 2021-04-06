package com.security.sample.dto;

import java.io.Serializable;

public class RolDto implements Serializable {

    private String name;

    public RolDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
