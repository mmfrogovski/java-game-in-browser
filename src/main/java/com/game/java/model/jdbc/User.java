package com.game.java.model.jdbc;

import com.game.java.gameWithBotLogic.GameWithBotLogic;

import java.util.Objects;

public class User {
    private int id;
    private String login;
    private String password;
    private boolean authorized;
    private boolean inGame;
    private int inRoom;
    private GameWithBotLogic gameWithBotLogic;

    public GameWithBotLogic getGameWithBotLogic() {
        return gameWithBotLogic;
    }

    public void setGameWithBotLogic(GameWithBotLogic gameWithBotLogic) {
        this.gameWithBotLogic = gameWithBotLogic;
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public int getInRoom() {
        return inRoom;
    }

    public void setInRoom(int inRoom) {
        this.inRoom = inRoom;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getAuthorized() {
        return authorized;
    }

    public void setAuthoried(boolean authorized) {
        this.authorized = authorized;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", authorized='" + authorized + "'" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password);
    }

    public enum AUTHORIZATION {
        AUTHORIZED, NOT_AUTHORIZED
    }
}