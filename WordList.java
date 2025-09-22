import java.util.Random;

public class WordList {
    private static String[] words = {
        "APPLE", "BANANA", "ORANGE", "ELEPHANT", "BTS","COMPUTER", "PYRAMID", "HANGMAN"
    };

    public static String getRandomWord() {
        Random r = new Random();
        return words[r.nextInt(words.length)];
    }
}
