package com.game.java.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {
    private String name;
    private String text;

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    private String roomId;

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text){
        this.text=text;
    }
    public void setName(String name) {
        this.name = name;
    }
}
