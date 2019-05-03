import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class MainMenuPanel extends JPanel {

    private JLabel title;
    private JButton startButton, quitButton;
    private ControlPanel controlPanel;
    private GameApp gameApp;
    private JPanel mainPanel;
    private final int TITLE_HEIGHT = 600;

    public MainMenuPanel(GameApp ga, JPanel mp, ControlPanel cp) {
        gameApp = ga;
        mainPanel = mp;
        controlPanel = cp;

        title = new JLabel("INVADERS OF SPACE", SwingConstants.CENTER);
        startButton = new JButton("Start");
        quitButton = new JButton("Quit");

        title.setPreferredSize(new Dimension(gameApp.getWidth(), gameApp.getHeight() / 2));
        title.setFont(new Font("Sans-Serif", Font.PLAIN, 60));

        quitButton.addActionListener(new ExitListener());
        startButton.addActionListener(new StartListener(this, controlPanel));

        this.add(title);
        this.add(startButton);
        this.add(quitButton);
        this.setPreferredSize(new Dimension(gameApp.getWidth(), gameApp.getHeight()));
    }

    private class ExitListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    private class StartListener implements ActionListener {

        private MainMenuPanel mainMenuPanel;
        private ControlPanel controlPanel;

        public StartListener(MainMenuPanel mmp, ControlPanel cp) {
            mainMenuPanel = mmp;
            controlPanel = cp;
        }

        public void actionPerformed(ActionEvent e) {
            mainMenuPanel.setVisible(false);
        }
    }

}
