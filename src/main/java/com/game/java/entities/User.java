package com.game.java.entities;

import java.util.List;

public class User {
    private String name;
    private boolean ready = false;

    public User(String name, boolean ready) {
        this.name = name;
        this.ready = ready;
    }

    public User(String username) {
        this.name=username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }


}
