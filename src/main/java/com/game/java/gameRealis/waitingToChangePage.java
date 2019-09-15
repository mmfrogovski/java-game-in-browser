package com.game.java.gameRealis;

import com.game.java.entities.User;

import java.util.List;

public class waitingToChangePage {
    private List<User> users;
    private int flag;

    public waitingToChangePage(List<User> users) {
        this.users = users;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }


}
