import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WinLosePanel extends JPanel {
    private JLabel winLoseLabel;
    private JLabel scoreLabel;
    private JButton playAgainButton;
    private JButton mainMenuButton;
    private PanelManager panelManager;

    public WinLosePanel(PanelManager panelManager) {
        this.panelManager = panelManager;
        winLoseLabel = new JLabel("You Win!", SwingConstants.CENTER);
        winLoseLabel.setPreferredSize(new Dimension(GameApp.FRAME_WIDTH, 200));
        winLoseLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 60));
        scoreLabel = new JLabel("0", SwingConstants.CENTER);
        scoreLabel.setPreferredSize(new Dimension(GameApp.FRAME_WIDTH, 200));
        scoreLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 60));
        playAgainButton = new JButton("Play Again");
        mainMenuButton = new JButton("Return to Main Menu");

        this.add(winLoseLabel);
        this.add(scoreLabel);
        playAgainButton.addActionListener(new PlayAgainListener());
        this.add(playAgainButton);
        mainMenuButton.addActionListener(new MainMenuListener());
        this.add(mainMenuButton);
        this.setPreferredSize(new Dimension(GameApp.FRAME_WIDTH, GameApp.FRAME_HEIGHT));
    }

    private class PlayAgainListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            panelManager.changeCurrentState(PanelManager.PanelState.CONTROL);
        }
    }

    private class MainMenuListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            panelManager.changeCurrentState(PanelManager.PanelState.MAIN_MENU);
        }
    }

    public void updateWinLoseLabel(boolean win) {
        if (win) {
            winLoseLabel.setText("YOU WIN!");
        } else {
            winLoseLabel.setText("YOU LOSE!");
        }
    }

    public void updateScoreLabel() {
        scoreLabel.setText(Integer.toString(panelManager.getGameplayPanel().getScore()));
    }
}
