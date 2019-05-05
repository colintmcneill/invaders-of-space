import com.sun.tools.javac.Main;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameApp extends JFrame {

    public static final int FRAME_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().height - Toolkit.getDefaultToolkit().getScreenSize().height / 10;
    public static final int FRAME_HEIGHT = FRAME_WIDTH;
    private JPanel panelHolder;

    private PanelManager panelManager;

    public GameApp() {
        super("INVADERS OF SPACE");
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBackground(Color.BLACK);

        panelHolder = new JPanel();
        panelHolder.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        panelHolder.setBackground(Color.BLACK);

        panelManager = new PanelManager(this, panelHolder);
        this.add(panelHolder);
        this.setVisible(true);
    }



    public PanelManager getPanelManager() {
        return panelManager;
    }

    public JPanel getPanelHolder() {
        return panelHolder;
    }

//    public void newGame() {
//        panelHolder.remove(gameplayPanel);
//        gameplayPanel = new GameplayPanel(this);
//        //allPanels[2] = gameplayPanel;
//        panelHolder.add(gameplayPanel);
//    }

    public static void main(String[] args) {
        new GameApp();
    }

}
