package com.bnta.word_guesser.controllers;

import com.bnta.word_guesser.models.Game;
import com.bnta.word_guesser.models.Guess;
import com.bnta.word_guesser.models.Reply;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/games") // localhost:8080/games - generic prefix for the routes below
public class GameController {

    private Game game;
    private String currentWord;
    private ArrayList<String> guessedLetters;

    @PostMapping
    public ResponseEntity<Reply> newGame(){
        game = new Game("hello"); // Spring serialises the object into JSON and sends it to the client
        this.currentWord = "*****";
        this.guessedLetters = new ArrayList<>();
        Reply reply = new Reply(currentWord, "New Game Started", false);
        return new ResponseEntity<>(reply, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Reply> getGameStatus(){
        Reply reply = new Reply(currentWord, "Game is in progress", false);
        return new ResponseEntity<>(reply, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<Reply> handleGuess(@RequestBody Guess guess){ // guess is in the shape of a request body since we input

        Reply reply;

        if (game == null){
            reply = new Reply(currentWord, "Game has not been started",false);
            return new ResponseEntity<>(reply, HttpStatus.BAD_REQUEST);
        }

        if(guessedLetters.contains(guess.getLetter())){
            reply = new Reply(
                    currentWord,
                    String.format("Already guessed the letter %s",guess.getLetter()), //format like in python %i for integer, %d for decimal
                    false
            );
            return new ResponseEntity<>(reply, HttpStatus.OK);
        }

        guessedLetters.add(guess.getLetter());

        if(!game.getWord().contains(guess.getLetter())){
            reply = new Reply(currentWord,
                    String.format("Word does not contain the letter %s",guess.getLetter()),
                    false);
            return new ResponseEntity<>(reply, HttpStatus.OK);
        }

        String runningSolution = game.getWord();

        for (Character letter: game.getWord().toCharArray()) {
            if(!guessedLetters.contains(letter.toString())){
                runningSolution = runningSolution.replace(letter, '*');
            }
        }

        currentWord = runningSolution;

        reply = new Reply(currentWord,
                String.format("%s is in the word", guess.getLetter()),
                true);
        return new ResponseEntity<>(reply, HttpStatus.OK);

    }

}
