import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MainMenuPanel extends JPanel {

    private JLabel title;
    private JButton startButton, quitButton;
    private PanelManager panelManager;

    public MainMenuPanel(PanelManager panelManager) {
        this.panelManager = panelManager;
        title = new JLabel("INVADERS OF SPACE", SwingConstants.CENTER);
        startButton = new JButton("Start");
        quitButton = new JButton("Exit");

        title.setPreferredSize(new Dimension(GameApp.FRAME_WIDTH, GameApp.FRAME_HEIGHT / 2));
        title.setFont(new Font("Sans-Serif", Font.PLAIN, 60));

        quitButton.addActionListener(new ExitListener());
        startButton.addActionListener(new StartListener());

        this.add(title);
        this.add(startButton);
        this.add(quitButton);
        this.setPreferredSize(new Dimension(GameApp.FRAME_WIDTH, GameApp.FRAME_HEIGHT));
    }


    private class ExitListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    private class StartListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            panelManager.changeCurrentState(PanelManager.PanelState.CONTROL);
        }
    }

}
