import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameApp extends JFrame {

    private MainMenuPanel mainMenuPanel;
    private ControlPanel controlPanel;
    private GameplayPanel gameplayPanel;
    private JPanel mainPanel;
    public static final int FRAME_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().height - Toolkit.getDefaultToolkit().getScreenSize().height / 10;
    public static final int FRAME_HEIGHT = FRAME_WIDTH;

    public GameApp() {
        super("Game");
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        gameplayPanel = new GameplayPanel(this);
        controlPanel = new ControlPanel(gameplayPanel);
        mainMenuPanel = new MainMenuPanel(this, mainPanel, controlPanel);
        mainPanel = new JPanel();
        this.add(mainPanel);
        mainPanel.add(mainMenuPanel);
        mainPanel.add(controlPanel);
        mainPanel.add(gameplayPanel);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new GameApp();
    }

}
