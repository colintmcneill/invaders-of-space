import javax.swing.*;

public class WinLosePanel extends JPanel {
    private JLabel winLabel;
    private JLabel loseLabel;
    private JLabel scoreLabel;
    private JButton playAgainButton;
    private JButton mainMenuButton;
    private GameplayPanel gameplayPanel;

    public WinLosePanel(GameplayPanel gameplayPanel) {
        this.gameplayPanel = gameplayPanel;

        winLabel = new JLabel("You Win!");
        loseLabel = new JLabel("You Lose!");
        scoreLabel = new JLabel("" + gameplayPanel.getScore());
        playAgainButton = new JButton("Play Again");
        mainMenuButton = new JButton("Return to Menu");
    }



}
