package com.game.java.entities;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
public class Message {
    private String name;
    private String text;
    private String roomId;
    private String userNumb;
    public String getUserNumb() {
        return userNumb;
    }

    public void setUserNumb(String userNumb) {
        this.userNumb = userNumb;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }



    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(name, message.name) &&
                text.equals(message.text) &&
                Objects.equals(roomId, message.roomId) &&
                Objects.equals(userNumb, message.userNumb);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, text, roomId, userNumb);
    }

    public void setText(String text){
        this.text=text;
    }
    public void setName(String name) {
        this.name = name;
    }
}
