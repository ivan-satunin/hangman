package com.company;

import java.util.Arrays;

public enum GameStartStatus {
    START {
        @Override
        public String toString() {
            return "начать";
        }
    },
    CLOSE {
        @Override
        public String toString() {
            return "выйти";
        }
    };

    public static boolean existsStatus(String status) {
        return Arrays.stream(GameStartStatus.values())
                .map(gameStartStatus -> gameStartStatus.toString())
                .anyMatch(gameStatus -> gameStatus.equals(status));
    }
}
