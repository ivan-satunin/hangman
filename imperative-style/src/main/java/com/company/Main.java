package com.company;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final Random random = new Random();
    private static final List<String> WORDS = List.of("адреса", "болезни", "возвращения", "варвары", "городки", "залежи");
    private static final Scanner scanner = new Scanner(System.in);
    private static final String GAME_START = "начать";
    private static final String GAME_CLOSE = "выйти";
    private static final String UNDERLINE = "_";
    private static final int MAX_MISTAKES = 10;
    private static final String GAME_WIN = "** Поздравляем, вы выграли!! **";
    private static final String GAME_LOSS = "** Поражение( **";

    public static void main(String[] args) {
        startGame();
    }

    public static void startGame() {
        System.out.println("** Игра виселица **");
        var userStartAnswer = getUserStartAnswer();
        if (userStartAnswer.equals(GAME_START)) {
            var guessedWord = getRandomWord();
            startGameLoop(guessedWord);
        }
    }

    private static String getUserStartAnswer() {
        System.out.printf("** Что бы начать введите '%s', что бы выйти введите '%s' **%n", GAME_START, GAME_CLOSE);
        var userStartAnswer = scanner.nextLine();
        if (!List.of(GAME_START, GAME_CLOSE).contains(userStartAnswer))
            throw new IllegalArgumentException("Введите %s что бы начать, или %s чтобы выйти".formatted(GAME_START, GAME_CLOSE));
        return userStartAnswer;
    }

    public static void startGameLoop(String guessedWord) {
        var currentWord = encodeWord(guessedWord);
        var numberErrors = 0;
        System.out.println("** Слово загаданно!");
        printCurrentWordState(currentWord, numberErrors);
        while (isGameNotOver(currentWord, numberErrors)) {
            var resp = inputPlayerResponse();
            if (isGuessedSymbol(guessedWord, resp)) {
                updatedCurrentWord(guessedWord, currentWord, resp);
            } else {
                numberErrors++;
                onWrongLetter(numberErrors);
            }
            printCurrentWordState(currentWord, numberErrors);
        }
        onPostRound(guessedWord, currentWord);
    }

    public static void onPostRound(String guessedWord, StringBuilder finalPlayerWord) {
        var gameStatus = checkGameStatus(guessedWord, finalPlayerWord);
        System.out.println(gameStatus);
        System.out.println("** Желаете начать новый раунд? **");
        var userStartAnswer = getUserStartAnswer();
        if (userStartAnswer.equals(GAME_START))
            startGameLoop(getRandomWord());
    }

    public static String checkGameStatus(String guessedWord, StringBuilder finalPlayerWord) {
        return finalPlayerWord.toString().equals(guessedWord) ? GAME_WIN : GAME_LOSS;
    }

    public static String inputPlayerResponse() {
        System.out.println("** Введите символ **");
        return scanner.nextLine();
    }

    public static boolean isGameNotOver(StringBuilder currentWord, int numberErrors) {
        return currentWord.toString().contains(UNDERLINE) && numberErrors < MAX_MISTAKES;
    }

    public static void onWrongLetter(int numberErrors) {
        if (numberErrors == MAX_MISTAKES)
            return;
        System.out.println("Неверно!");
    }

    public static void updatedCurrentWord(String guessedWord, StringBuilder currentWord, String letter) {
        for (int i = 0; i < guessedWord.length(); i++) {
            if (String.valueOf(guessedWord.charAt(i)).equals(letter))
                currentWord.replace(i, i + 1, letter);
        }
    }

    public static boolean isGuessedSymbol(String guessedWord, String symbol) {
        return guessedWord.contains(symbol);
    }

    public static void printCurrentWordState(StringBuilder currentWord, int numberErrors) {
        System.out.println("** Текущее состояние: " + "| " + currentWord.toString() + " | **");
        if (numberErrors > 0)
            System.out.printf("** Вы допустили %d ошибок из %d! **%n", numberErrors, MAX_MISTAKES);
        System.out.println();
    }

    public static StringBuilder encodeWord(String word) {
        return new StringBuilder(UNDERLINE.repeat(word.length()));
    }

    public static String getRandomWord() {
        return WORDS.get(random.nextInt(WORDS.size()));
    }
}