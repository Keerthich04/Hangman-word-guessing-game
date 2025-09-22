import java.awt.*;
import java.awt.event.*;

public class HangmanUI extends Frame implements ActionListener {
    private HangmanGame game;
    private Label wordLabel, messageLabel;
    private TextField inputField;
    private Button guessButton;
    private Canvas hangmanCanvas;

    public HangmanUI() {
        startNewGame();

        setTitle("Hangman Game");

        // ✅ Open in full screen
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        setResizable(true);

        // Word display label (larger font for fullscreen)
        wordLabel = new Label(game.getWordProgress());
        wordLabel.setFont(new Font("Monospaced", Font.BOLD, 36));
        add(wordLabel);

        inputField = new TextField(2);
        inputField.setFont(new Font("Arial", Font.PLAIN, 24));
        add(inputField);

        guessButton = new Button("Guess");
        guessButton.setFont(new Font("Arial", Font.BOLD, 20));
        guessButton.addActionListener(this);
        add(guessButton);

        messageLabel = new Label("Enter a letter and click Guess.");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        add(messageLabel);

        // Bigger canvas for full-screen experience
        hangmanCanvas = new Canvas() {
            public void paint(Graphics g) {
                drawHangman(g, game.getWrongGuesses());
            }
        };
        hangmanCanvas.setPreferredSize(new Dimension(500, 500));
        add(hangmanCanvas);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        setVisible(true);
    }

    private void startNewGame() {
        game = new HangmanGame(WordList.getRandomWord(), 6);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (game.isGameOver()) return;

        String text = inputField.getText();
        if (text.length() == 1) {
            char guess = text.charAt(0);
            boolean correct = game.guessLetter(guess);
            wordLabel.setText(game.getWordProgress());
            if (!correct) messageLabel.setText("Wrong guess!");
            else messageLabel.setText("Good guess!");
            hangmanCanvas.repaint();
        }
        inputField.setText("");

        if (game.isGameOver()) {
            if (game.isWordGuessed())
                messageLabel.setText("🎉 You Win!");
            else
                messageLabel.setText("☠️ Game Over!");
            showGameEndDialog();
        }
    }

    private void drawHangman(Graphics g, int wrongGuesses) {
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        g.drawLine(150, 450, 350, 450); // base
        g.drawLine(250, 450, 250, 100); // pole
        g.drawLine(250, 100, 350, 100); // top
        g.drawLine(350, 100, 350, 150); // rope

        if (wrongGuesses > 0) g.drawOval(335, 150, 30, 30);  // head
        if (wrongGuesses > 1) g.drawLine(350, 180, 350, 260); // body
        if (wrongGuesses > 2) g.drawLine(350, 200, 320, 230); // left arm
        if (wrongGuesses > 3) g.drawLine(350, 200, 380, 230); // right arm
        if (wrongGuesses > 4) g.drawLine(350, 260, 320, 300); // left leg
        if (wrongGuesses > 5) g.drawLine(350, 260, 380, 300); // right leg
    }

    private void showGameEndDialog() {
        Dialog dialog = new Dialog(this, "Game Result", true);
        dialog.setLayout(new BorderLayout(10, 10));

        String message = game.isWordGuessed() ? "🎉 You guessed it right!" : "☠️ You lost!";
        Label resultLabel = new Label(message + "\nPlay again?", Label.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 18));
        dialog.add(resultLabel, BorderLayout.CENTER);

        Panel buttonPanel = new Panel();
        Button yes = new Button("Play Again");
        Button no = new Button("Exit");
        yes.setFont(new Font("Arial", Font.BOLD, 16));
        no.setFont(new Font("Arial", Font.BOLD, 16));
        buttonPanel.add(yes);
        buttonPanel.add(no);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        yes.addActionListener(e -> {
            startNewGame();
            wordLabel.setText(game.getWordProgress());
            messageLabel.setText("Enter a letter and click Guess.");
            hangmanCanvas.repaint();
            dialog.dispose();
        });

        no.addActionListener(e -> System.exit(0));

        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}
