import java.util.HashSet;

public class HangmanGame {
    private String secretWord;
    private HashSet<Character> guessedLetters;
    private int maxLives;
    private int wrongGuesses;

    public HangmanGame(String secretWord, int maxLives) {
        this.secretWord = secretWord.toUpperCase();
        this.maxLives = maxLives;
        this.wrongGuesses = 0;
        guessedLetters = new HashSet<>();
    }

    public boolean guessLetter(char letter) {
        letter = Character.toUpperCase(letter);
        guessedLetters.add(letter);
        if (!secretWord.contains(String.valueOf(letter))) {
            wrongGuesses++;
            return false;
        }
        return true;
    }

    public String getWordProgress() {
        StringBuilder sb = new StringBuilder();
        for (char c : secretWord.toCharArray()) {
            sb.append(guessedLetters.contains(c) ? c : '_').append(' ');
        }
        return sb.toString().trim();
    }

    public boolean isGameOver() {
        return wrongGuesses >= maxLives || isWordGuessed();
    }

    public boolean isWordGuessed() {
        for (char c : secretWord.toCharArray()) {
            if (!guessedLetters.contains(c)) return false;
        }
        return true;
    }

    public int getWrongGuesses() {
        return wrongGuesses;
    }

    public int getMaxLives() {
        return maxLives;
    }
}
