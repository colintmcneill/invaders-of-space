import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameplayPanel extends JPanel {

    private SpaceShip ship;
    private final int INIT_X = GameApp.FRAME_WIDTH / 2;
    private final int INIT_Y = GameApp.FRAME_HEIGHT * 4 / 5;
    private final int SPEED = 50;
    private Image backgroundImage;
    private JButton pauseButton;
    private boolean pause = true;
    private ArrayList<Asteroid> asteroidList;
    private int currentEnemyLimit = 5;
    private int totalEnemyCount;
    private int currentEnemyCount = 0;
    private int score;
    private int enemySpawnRate = 100;
    private GameApp gameApp;

    public GameplayPanel(GameApp ga) {
        super();
        gameApp = ga;
        totalEnemyCount = ControlPanel.ENEMY_MIN;
        currentEnemyCount = 0;
        asteroidList = new ArrayList<Asteroid>(currentEnemyLimit);
        backgroundImage = new ImageIcon("./images/space.png").getImage();
        this.setPreferredSize(new Dimension(gameApp.getWidth(), gameApp.getHeight()));
        ship = new SpaceShip(new ImageIcon("./images/rocket_blue.png"), new Point(INIT_X, INIT_Y));
        pauseButton = new JButton("Pause");
        pauseButton.addActionListener(new PauseListener());
        this.add(pauseButton);
        this.addMouseListener(new MListener());
        this.addMouseMotionListener(new MListener());
        this.addKeyListener(new KeyBoardListener());
        this.setFocusable(true);
        AsteroidCreationTimer act = new AsteroidCreationTimer(100, null);
        AsteroidAnimationTimer aat = new AsteroidAnimationTimer(10, null);
        act.start();
        aat.start();
    }

    public void paintComponent(java.awt.Graphics aBrush) {
        super.paintComponent(aBrush);
        aBrush.drawImage(backgroundImage, 0, 0, GameApp.FRAME_WIDTH, GameApp.FRAME_HEIGHT, null);
        aBrush.drawImage(ship.getShipImage().getImage(),
                (int)ship.getX() - ship.getShipImage().getIconWidth() / 2,
                (int)ship.getY() - ship.getShipImage().getIconHeight() / 2,
                100, 100, null);
        for (Asteroid ast: asteroidList) {
            aBrush.drawImage(ast.getAsteroidImage().getImage(), ast.getX(), ast.getY(), null);
        }
    }

    public void setAvatarImage(ImageIcon img) {
        ship.setShipImage(img);
        repaint();
    }

    public void setPause(boolean bool) {
        pause = bool;
    }

    public int getCurrentEnemyLimit() {
        return currentEnemyLimit;
    }

    public void setCurrentEnemyLimit(int el) {
        currentEnemyLimit = el;
    }

    private class MListener implements MouseListener, MouseMotionListener {

        public void mouseDragged(MouseEvent e) {
            if (pause == false) {
                ship.setLocation(e.getX(), ship.getY());
                repaint();
            }
        }

        public void mouseMoved(MouseEvent e) {}

        public void mouseClicked(MouseEvent e) {}

        public void mousePressed(MouseEvent e) {}

        public void mouseReleased(MouseEvent e) {}

        public void mouseEntered(MouseEvent e) {}

        public void mouseExited(MouseEvent e) {}

    }

    private class KeyBoardListener implements KeyListener {

        public void keyTyped(KeyEvent e) {}

        public void keyPressed(KeyEvent e) {
            if (pause == false) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyChar() == 'a') {
                    ship.move(-SPEED, 0);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyChar() == 'd') {
                    ship.move(SPEED, 0);
                }
                repaint();
            }
        }

        public void keyReleased(KeyEvent e) {}

    }

    private class PauseListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            pause = !pause;
        }

    }

    private class AsteroidAnimationTimer extends Timer {

        public AsteroidAnimationTimer(int delay, ActionListener listener) {
            super(delay, listener);
            this.addActionListener(new AsteroidListener());
        }

        private class AsteroidListener implements ActionListener {

            public void actionPerformed(ActionEvent e) {
                for (Asteroid ast: asteroidList) {
                    ast.move(0, ast.getSpeed());
                    repaint();
                }
            }
        }

    }

    private class AsteroidCreationTimer extends Timer {

        public AsteroidCreationTimer(int delay, ActionListener listener) {
            super(delay, listener);
            this.addActionListener(new AsteroidListener());
        }

        private class AsteroidListener implements ActionListener {

            public void actionPerformed(ActionEvent e) {
                if (currentEnemyCount < currentEnemyLimit) {
                    int xLoc = (int) (Math.random() * GameApp.FRAME_WIDTH) - 100;
                    Asteroid newAsteroid = new Asteroid(xLoc, 100);
                    asteroidList.add(newAsteroid);
                    currentEnemyCount ++;
                    repaint();
                }
            }
        }

    }

}
