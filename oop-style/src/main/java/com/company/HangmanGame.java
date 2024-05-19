package com.company;

import java.util.*;

public class HangmanGame {
    private final MaskedWord maskedWord;
    private final int maxMistakes;

    private final Dictionary dictionary = new Dictionary();
    private static final String GAME_START = GameStartStatus.START.toString();
    private static final String GAME_CLOSE = GameStartStatus.CLOSE.toString();
    private static final Scanner scanner = new Scanner(System.in);

    private static final int DEFAULT_MAX_MISTAKES = 8;

    public HangmanGame(int maxMistakes) {
        this.maskedWord = new MaskedWord(this.dictionary.getRandomWord());
        this.maxMistakes = maxMistakes;
    }

    public HangmanGame() {
        this(DEFAULT_MAX_MISTAKES);
    }

    public void start() {
        System.out.println("** Игра виселица **");
        var userStartAnswer = getUserStartAnswer();
        if (userStartAnswer.equals(GameStartStatus.START)) {
            startLoop();
        }
    }

    private void startLoop() {
        Set<String> wrongInputs = new HashSet<>();
        System.out.println("** Слово загаданно!");
        printGameState(wrongInputs);
        while (isGameNotOver(wrongInputs)) {
            var input = getPlayerInput();
            if (this.maskedWord.isOpeningSymbol(input)) {
                System.out.println("** Вы уже открыли эту букву попробуйте другую! **");
                input = getPlayerInput();
            }
            if (!this.maskedWord.tryOpenSymbol(input)) {
                wrongInputs.add(input);
                onWrongInput(wrongInputs.size());
            }
            printGameState(wrongInputs);
        }
        onPostRound();
    }

    private void printGameState(Set<String> wrongInputs) {
        this.maskedWord.printState();
        System.out.printf("** Ошибки (%d из %d): %s%n", wrongInputs.size(), maxMistakes, wrongInputs);
        System.out.println();
    }

    private void onPostRound() {
        var gameStatus = checkGameStatus();
        System.out.println(gameStatus.toString());
        if (gameStatus.equals(GameRoundStatus.LOSS))
            System.out.println("Загаданное слово: " + this.maskedWord.getWord());
        System.out.println("** Желаете начать новый раунд? **");
        var userStartAnswer = getUserStartAnswer();
        if (userStartAnswer.equals(GameStartStatus.START))
            new HangmanGame().startLoop();
    }

    private GameRoundStatus checkGameStatus() {
        return this.maskedWord.isUnsolvedWord() ? GameRoundStatus.LOSS : GameRoundStatus.WIN;
    }

    private void onWrongInput(int numberMistakes) {
        if (numberMistakes < maxMistakes)
            System.out.println("Неверно!");
    }

    private String getPlayerInput() {
        System.out.println("** Введите букву **");
        var input = scanner.nextLine();
        while (input.length() != 1) {
            System.out.println("** Вы должны ввести только одну не пустую букву!! **");
            input = scanner.nextLine();
        }
        return input;
    }

    private boolean isGameNotOver(Set<String> wrongInputs) {
        return this.maskedWord.isUnsolvedWord() && wrongInputs.size() < maxMistakes;
    }


    public GameStartStatus getUserStartAnswer() {
        System.out.printf("** Что бы начать введите '%s', что бы выйти введите '%s' **%n", GAME_START, GAME_CLOSE);
        var userStartAnswer = scanner.nextLine();
        while (!GameStartStatus.existsStatus(userStartAnswer)) {
            System.out.printf("Введите %s что бы начать, или %s чтобы выйти%n", GAME_START, GAME_CLOSE);
            userStartAnswer = scanner.nextLine();
        }
        return userStartAnswer.equals(GAME_START) ? GameStartStatus.START : GameStartStatus.CLOSE;
    }
}