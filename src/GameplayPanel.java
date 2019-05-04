import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.*;

public class GameplayPanel extends JPanel {

    private SpaceShip ship;
    private final int INIT_X = GameApp.FRAME_WIDTH / 2;
    private final int INIT_Y = GameApp.FRAME_HEIGHT * 4 / 5;
    private final int SPEED = 50;
    private Image backgroundImage;
    private JButton pauseButton;
    private boolean pause;
    private ArrayList<Asteroid> asteroidList;
    private int currentEnemyLimit = 5;
    private int totalEnemyCount = 30;
    private int currentEnemyCount = 0;
    private int enemySpawnRate = 100;
    private GameApp gameApp;
    private Timer[] allTimers = new Timer[2];
    private Point clickLoc = null;
    private int score = 0;
    private JLabel scoreLabel;
    private JButton quitButton;
    private MainMenuPanel mainMenuPanel;

    public GameplayPanel(GameApp ga) {
        super();
        gameApp = ga;
        totalEnemyCount = ControlPanel.ENEMY_MIN;
        currentEnemyCount = 0;
        asteroidList = new ArrayList<Asteroid>(currentEnemyLimit);
        backgroundImage = new ImageIcon("./images/space.png").getImage();
        this.setPreferredSize(new Dimension(gameApp.getWidth(), gameApp.getHeight()));
        ship = new SpaceShip(new ImageIcon("./images/rocket_blue.png"), INIT_X, INIT_Y);
        pause = false;
        pauseButton = new JButton("Pause");
        pauseButton.setFocusable(false);
        pauseButton.addActionListener(new PauseListener());
        this.setBackground(Color.BLACK);
        this.add(pauseButton);
        this.addMouseListener(new MListener());
        this.addMouseMotionListener(new MListener());
        this.addKeyListener(new KeyBoardListener());
        this.setFocusable(true);
        AsteroidCreationTimer act = new AsteroidCreationTimer(1000, null);
        AsteroidAnimationTimer aat = new AsteroidAnimationTimer(10, null);
        allTimers[0] = act;
        allTimers[1] = aat;
        allTimers[0].start();
        allTimers[1].start();

        scoreLabel = new JLabel(Integer.toString(score), SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 50));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setPreferredSize(new Dimension(600, 60));
        this.add(scoreLabel);

        quitButton = new JButton("End");
        quitButton.addActionListener(new QuitListener(this));
        this.add((quitButton));
    }

    public void paintComponent(java.awt.Graphics aBrush) {
        super.paintComponent(aBrush);
        java.awt.Graphics2D g = (java.awt.Graphics2D)(aBrush);
        aBrush.drawImage(backgroundImage, 0, 0, GameApp.FRAME_WIDTH, GameApp.FRAME_HEIGHT, null);
        aBrush.drawImage(ship.getShipImage().getImage(),
                (int)ship.getX() - ship.getShipImage().getIconWidth() / 2,
                (int)ship.getY() - ship.getShipImage().getIconHeight() / 2,
                100, 100, null);
        for (Asteroid ast: asteroidList) {
            g.rotate(ast.getCurrentRotation(),
                    ast.getX() + ast.getAsteroidImage().getIconWidth() / 2
                    , ast.getY() + ast.getAsteroidImage().getIconHeight() / 2);
            aBrush.drawImage(ast.getAsteroidImage().getImage(), (int)ast.getX(), (int)ast.getY(), null);
            g.rotate(-ast.getCurrentRotation(),
                    ast.getX() + ast.getAsteroidImage().getIconWidth() / 2
                    , ast.getY() + ast.getAsteroidImage().getIconHeight() / 2);
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
            if (!pause) {
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
            if (!pause) {
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
            if (!pause) {
                pause = true;
                pauseButton.setText("Play");
                for (Timer timer: allTimers) {
                    timer.stop();
                }
            } else if (pause) {
                pause = false;
                pauseButton.setText("Pause");
                for (Timer timer: allTimers) {
                    timer.start();
                }
            }
        }

    }

    private class AsteroidAnimationTimer extends Timer {

        public AsteroidAnimationTimer(int delay, ActionListener listener) {
            super(delay, null);
            this.addActionListener(new AsteroidListener());
        }

        private class AsteroidListener implements ActionListener {

            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < asteroidList.size(); i ++) {
                    Asteroid currentAsteroid = asteroidList.get(i);
                    currentAsteroid.updateBounds();
                    if (currentAsteroid.collisionCheck()) {
                        asteroidList.remove(i);
                        i --;
                        currentEnemyCount --;
                    }
                    currentAsteroid.bounce();
                    currentAsteroid.move(currentAsteroid.getxSpeed(), currentAsteroid.getySpeed());
                    currentAsteroid.setCurrentRotation(
                            currentAsteroid.getCurrentRotation() +
                                    currentAsteroid.getRotationSpeed());
                    repaint();
                    if (currentAsteroid.getY() > GameApp.FRAME_HEIGHT) {
                        asteroidList.remove((i));
                        i --;
                        currentEnemyCount --;
                        score ++;
                        scoreLabel.setText(Integer.toString(score));
                    }
                }
            }
        }

    }

    private class QuitListener implements ActionListener {

        private GameplayPanel gameplayPanel;

        public QuitListener(GameplayPanel gp) {
            gameplayPanel = gp;
        }

        public void actionPerformed(ActionEvent e) {
            pause = true;
            gameplayPanel.setVisible(false);
            mainMenuPanel.setVisible(true);
        }
    }

    private class AsteroidCreationTimer extends Timer {

        public AsteroidCreationTimer(int delay, ActionListener listener) {
            super(delay, null);
            this.addActionListener(new AsteroidListener());
        }

        private class AsteroidListener implements ActionListener {

            public void actionPerformed(ActionEvent e) {
                if (currentEnemyCount < currentEnemyLimit) {
                    int xLoc = (int) (Math.random() * GameApp.FRAME_WIDTH) - 100;
                    Asteroid newAsteroid = new Asteroid(ship, xLoc, -100);
                    asteroidList.add(newAsteroid);
                    currentEnemyCount ++;
                    repaint();
                }
            }
        }

    }

}
