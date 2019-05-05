import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.*;

public class GameplayPanel extends JPanel {

    private SpaceShip ship;
    private final int INIT_X = GameApp.FRAME_WIDTH / 2;
    private final int INIT_Y = GameApp.FRAME_HEIGHT * 4 / 5;
    private final int XCLAMP = 35;
    private final int SPEED = 50;
    private Image backgroundImage;
    private JButton pauseButton;
    private boolean pause;
    private ArrayList<Asteroid> asteroidList;
    private int currentEnemyLimit = 5;
    private int totalEnemyCount = 30;
    private int currentEnemyCount = 0;
    private int score = 0;
    private JLabel scoreLabel;
    private JButton endButton;
    private PanelManager panelManager;
    private MainGameTimer mainGameTimer;
    private AsteroidCreationTimer asteroidCreationTimer;
    private int asteroidSpawnRate = 500;

    public GameplayPanel(PanelManager panelManager) {
        super();
        this.panelManager = panelManager;
        asteroidList = new ArrayList<Asteroid>(currentEnemyLimit);
        asteroidCreationTimer = new AsteroidCreationTimer(0, null);
        mainGameTimer = new MainGameTimer(10, null);
        scoreLabel = new JLabel(Integer.toString(0), SwingConstants.CENTER);
        pauseButton = new JButton("Pause");
        endButton = new JButton("End");
        backgroundImage = new ImageIcon("./images/space.png").getImage();
        ship = new SpaceShip(new ImageIcon("./images/rocket_blue.png"), INIT_X, INIT_Y);
        constructGame();
        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!pause) {
                    pause = true;
                    pauseButton.setText("Play");
                    stopTimers();
                } else if (pause) {
                    pause = false;
                    pauseButton.setText("Pause");
                    startTimers();
                }
            }
        });
        this.add(pauseButton);
        endButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panelManager.changeCurrentState(PanelManager.PanelState.MAIN_MENU);
                constructGame();
                restartTimers();
                stopTimers();
                pause = true;
            }
        });
        this.add(endButton);

        this.addMouseMotionListener(new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {
                if (!pause) {
                    if (e.getX() > XCLAMP && e.getX() < GameApp.FRAME_WIDTH - XCLAMP) {
                        ship.setLocation(e.getX(), ship.getY());
                    }
                    repaint();
                }
            }
            public void mouseMoved(MouseEvent e) {}
        });
        this.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {}

            public void keyPressed(KeyEvent e) {
                if (!pause) {
                    if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyChar() == 'a') {
                        if (ship.getX() - SPEED > XCLAMP) {
                            ship.move(-SPEED, 0);
                        }
                    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyChar() == 'd') {
                        if (ship.getX() + SPEED < GameApp.FRAME_WIDTH - XCLAMP) {
                            ship.move(SPEED, 0);
                        }
                    }
                    repaint();
                }
            }

            public void keyReleased(KeyEvent e) {}
        });
        this.setFocusable(true);
        this.add(scoreLabel);
    }

    public void constructGame() {
        totalEnemyCount = ControlPanel.ENEMY_MIN;
        currentEnemyCount = 0;
        score = 0;
        pause = false;
        ship.setLocation(INIT_X, INIT_Y);

        this.setBackground(Color.BLACK);
        this.setPreferredSize(new Dimension(GameApp.FRAME_WIDTH, GameApp.FRAME_HEIGHT));
        asteroidCreationTimer.setDelay(asteroidSpawnRate);

        pause = false;
        scoreLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 50));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setText(Integer.toString(score));
        scoreLabel.setPreferredSize(new Dimension(600, 60));

        for (int i = 0; i < asteroidList.size(); i ++) {
            asteroidList.remove(i);
            i --;
        }
    }

    public void startTimers() {
        mainGameTimer.start();
        asteroidCreationTimer.start();
    }

    public void stopTimers() {
        mainGameTimer.stop();
        asteroidCreationTimer.stop();
    }

    public void restartTimers() {
        mainGameTimer.restart();
        asteroidCreationTimer.restart();
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

    public int getTotalEnemyCount() {
        return totalEnemyCount;
    }

    public void setAsteroidSpawnRate(int asteroidSpawnRate) {
        this.asteroidSpawnRate = asteroidSpawnRate;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public AsteroidCreationTimer getAsteroidCreationTimer() {
        return asteroidCreationTimer;
    }

    public void setTotalEnemyCount(int totalEnemyCount) {
        this.totalEnemyCount = totalEnemyCount;
    }

    private class MainGameTimer extends Timer {

        public MainGameTimer(int delay, ActionListener listener) {
            super(delay, null);
            this.addActionListener(new AsteroidListener());
        }

        private class AsteroidListener implements ActionListener {

            public void actionPerformed(ActionEvent e) {
                if (!pause) {
                    for (int i = 0; i < asteroidList.size(); i++) {
                        Asteroid currentAsteroid = asteroidList.get(i);
                        if (currentAsteroid.collisionCheck()) {
                            panelManager.getWinLosePanel().updateScoreLabel();
                            panelManager.getWinLosePanel().updateWinLoseLabel(false);
                            panelManager.changeCurrentState(PanelManager.PanelState.WIN_LOSE);
                            constructGame();
                            restartTimers();
                            stopTimers();
                            pause = true;
                            repaint();
                        }
                        currentAsteroid.bounce();
                        currentAsteroid.move(currentAsteroid.getxSpeed(), currentAsteroid.getySpeed());
                        currentAsteroid.setCurrentRotation(
                                currentAsteroid.getCurrentRotation() + currentAsteroid.getRotationSpeed());
                        repaint();
                        if (currentAsteroid.getY() > GameApp.FRAME_HEIGHT) {
                            asteroidList.remove(i);
                            i --;
                            score++;
                            scoreLabel.setText(Integer.toString(score));
                            if (score >= totalEnemyCount) {
                                panelManager.getWinLosePanel().updateScoreLabel();
                                panelManager.getWinLosePanel().updateWinLoseLabel(true);
                                panelManager.changeCurrentState(PanelManager.PanelState.WIN_LOSE);
                                constructGame();
                                restartTimers();
                                stopTimers();
                                pause = true;
                                repaint();
                            }
                        }
                    }
                }
            }
        }
    }

    private class AsteroidCreationTimer extends Timer {

        public AsteroidCreationTimer(int delay, ActionListener listener) {
            super(delay, null);
            this.addActionListener(new AsteroidListener());
        }

        private class AsteroidListener implements ActionListener {

            public void actionPerformed(ActionEvent e) {
                if (!pause && asteroidList.size() < currentEnemyLimit) {
                    asteroidCreationTimer.setDelay(asteroidSpawnRate);
                    int xLoc = (int) (Math.random() * GameApp.FRAME_WIDTH) - 100;
                    Asteroid newAsteroid = new Asteroid(panelManager, ship, xLoc, 0);
                    asteroidList.add(newAsteroid);
                    newAsteroid.setySpeed(panelManager.getControlPanel().getDifficultySpeed());
                    currentEnemyCount ++;
                    repaint();
                }
            }
        }
    }

}
