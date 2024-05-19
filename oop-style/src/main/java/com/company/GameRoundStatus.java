package com.company;

public enum GameRoundStatus {
    WIN {
        @Override
        public String toString() {
            return "** Поздравляем, вы выграли!! **";
        }
    },
    LOSS {
        @Override
        public String toString() {
            return "** Поражение( **";
        }
    }
}
