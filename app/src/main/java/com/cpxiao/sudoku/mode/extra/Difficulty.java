package com.cpxiao.sudoku.mode.extra;

import com.cpxiao.AppConfig;
import com.cpxiao.R;

/**
 * @author cpxiao on 2017/09/05.
 */

public final class Difficulty {
    public static final String KEY_DIFFICULTY = "KEY_DIFFICULTY";


    public static final int[] DIFFICULTY_EASY = {R.string.easy, 1};
    public static final int[] DIFFICULTY_NORMAL = {R.string.normal, 2};
    public static final int[] DIFFICULTY_HARD = {R.string.hard, 3};
    public static final int[] DIFFICULTY_INSANE = {R.string.insane, 4};
    public static final int[] DIFFICULTY_DEFAULT = DIFFICULTY_HARD;

    public static final int[][] DIFFICULTY_ARRAY = {
            DIFFICULTY_EASY
            , DIFFICULTY_NORMAL
            , DIFFICULTY_HARD
            , DIFFICULTY_INSANE
    };

    public static int getArrayIndex(int gameDifficulty) {
        int index = -1;
        for (int i = 0; i < Difficulty.DIFFICULTY_ARRAY.length; i++) {
            if (gameDifficulty == Difficulty.DIFFICULTY_ARRAY[i][1]) {
                index = i;
                break;
            }
        }
        if (AppConfig.DEBUG) {
            if (index == -1) {
                throw new IllegalArgumentException("index == -1");
            }
        }
        return index;
    }

    public static int getArrayNextIndex(int gameDifficulty) {
        int index = getArrayIndex(gameDifficulty);
        return (index + 1) % DIFFICULTY_ARRAY.length;
    }


}