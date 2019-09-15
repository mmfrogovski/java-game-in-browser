package com.game.java.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {
    private String name;
    private String text;

    public void setName(String name) {
        this.name = name;
    }
}
