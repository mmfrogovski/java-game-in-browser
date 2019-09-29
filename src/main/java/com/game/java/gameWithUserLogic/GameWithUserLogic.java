package com.game.java.gameWithUserLogic;

import com.game.java.gameWithBotLogic.NumberOfBullsAndCows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameWithUserLogic {

    private List<Integer> secret = new ArrayList<>();

    public GameWithUserLogic() {
    }


    public String gameLogic(String userAnswer, String userSecretNumber) {
        NumberOfBullsAndCows numberOfBullsAndCows = new NumberOfBullsAndCows();
        String[] ans;
        ans = userAnswer.split("");

        String[] seq;
        seq = userSecretNumber.split("");

        int bullCount = 0;
        int cowCount = 0;

        for (int i = 0; i < seq.length; i++) {
            if (Integer.parseInt(seq[i]) == Integer.parseInt(ans[i])) {
                bullCount++;
            }
        }

        for (int i = 0; i < ans.length; i++) {
            for (int j = 0; j < ans.length; j++) {
                if (Integer.parseInt(ans[i]) == Integer.parseInt(seq[j]) && i != j) {
                    cowCount++;
                }
            }
        }
        numberOfBullsAndCows.setBulls(bullCount);
        numberOfBullsAndCows.setCows(cowCount);
        numberOfBullsAndCows.setNumber(userAnswer);
        return  "Bulls: " + numberOfBullsAndCows.getBulls() + " / Cows: " + numberOfBullsAndCows.getCows();
    }

}
