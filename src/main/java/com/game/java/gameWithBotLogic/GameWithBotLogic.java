package com.game.java.gameWithBotLogic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameWithBotLogic {

    private List<Integer> secret = new ArrayList<>();

    private List<NumberOfBullsAndCows> answersForUser = new ArrayList<>();

    public GameWithBotLogic() {
        Integer[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        this.secret.addAll(Arrays.asList(numbers));
        Collections.shuffle(secret);
        this.secret = this.secret.subList(0,4);

    }

    public List<NumberOfBullsAndCows> getAnswersForUser() {
        return answersForUser;
    }

    public List<NumberOfBullsAndCows> gameLogic(String userAnswer) {
        NumberOfBullsAndCows numberOfBullsAndCows = new NumberOfBullsAndCows();
        String[] seq;
        seq = userAnswer.split("");
        System.out.println(secret);
        int bullCount = 0;
        int cowCount = 0;

        for (int i = 0; i < secret.size(); i++) {
            if (secret.get(i) == Integer.parseInt(seq[i])) {
                bullCount++;
            }
        }

        for (int i = 0; i < secret.size(); i++) {
            for (int j = 0; j < secret.size(); j++) {
                if (secret.get(i) == Integer.parseInt(seq[j]) && i != j) {
                    cowCount++;
                }
            }
        }
        numberOfBullsAndCows.setBulls(bullCount);
        numberOfBullsAndCows.setCows(cowCount);
        numberOfBullsAndCows.setNumber(userAnswer);
        answersForUser.add(numberOfBullsAndCows);

        return answersForUser;
    }
}
