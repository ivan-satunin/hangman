package com.company;

import java.util.HashSet;
import java.util.Set;

public class MaskedWord {
    private final String word;
    private final String encryptionSymbol;
    private final StringBuilder encodedWord;
    private final Set<String> openingSymbols = new HashSet<>();

    private static final String UNDERLINE = "_";

    public MaskedWord(String word, String encryptionSymbol) {
        this.word = word;
        this.encryptionSymbol = encryptionSymbol;
        this.encodedWord = new StringBuilder(encryptionSymbol.repeat(word.length()));
    }

    public MaskedWord(String word) {
        this(word, UNDERLINE);
    }

    public boolean isUnsolvedWord() {
        return encodedWord.toString().contains(encryptionSymbol);
    }

    public boolean isCorrectSymbol(String symbol) {
        return word.contains(symbol);
    }


    public boolean isOpeningSymbol(String symbol) {
        return openingSymbols.contains(symbol);
    }

    public boolean tryOpenSymbol(String symbol) {
        if (!isCorrectSymbol(symbol)) return false;

        openingSymbols.add(symbol);
        for (int i = 0; i < word.length(); i++) {
            if (String.valueOf(word.charAt(i)).equals(symbol))
                encodedWord.replace(i, i + 1, symbol);
        }
        return true;
    }

    public void printState() {
        System.out.println("** Текущее состояние: " + "| " + encodedWord.toString() + " | **");
    }

    public String getWord() {
        return word;
    }
}
