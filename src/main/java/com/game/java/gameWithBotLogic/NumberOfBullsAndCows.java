package com.game.java.gameWithBotLogic;

public class NumberOfBullsAndCows {
    private String number;
    private int bulls;
    private int cows;

    public int getBulls() {
        return bulls;
    }

    public void setBulls(int bulls) {
        this.bulls = bulls;
    }

    public int getCows() {
        return cows;
    }

    public void setCows(int cows) {
        this.cows = cows;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public NumberOfBullsAndCows() {
    }

    public NumberOfBullsAndCows(int bulls, int cows) {
        this.bulls = bulls;
        this.cows = cows;
    }
}
