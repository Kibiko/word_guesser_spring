package com.bnta.word_guesser.models;

public class Game { // POJO

    private String word;
    private int guesses; //finite amount of guesses
    private boolean complete; //need to know if the game has finished

    public Game(String word){
        this.word = word;
        this.guesses = 0;
        this.complete = false;
    }

    //default constructor is needed for Spring (refer to POJO notes)
    public Game(){
    }

    //getters and setters
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getGuesses() {
        return guesses;
    }

    public void setGuesses(int guesses) {
        this.guesses = guesses;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}
