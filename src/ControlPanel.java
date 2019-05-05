import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ControlPanel extends JPanel {

    private JLabel avatarLabel, difficultyLabel, enemyCountLabel, enemyCountNumber;
    private ButtonGroup avatarButtonGroup, difficultyButtonGroup;
    private JButton increaseEnemyCountButton, decreaseEnemyCountButton, beginGameButton;
    private JRadioButton[] avatarButtons, difficultyButtons;
    private ImageIcon[] avatarImages;
    private int totalEnemyCount;
    public static final int ENEMY_MIN = 10;
    public static final int ENEMY_MAX = 50;
    private AvatarHolder avatarHolder;
    private PanelManager panelManager;

    public ControlPanel(PanelManager panelManager) {
        this.panelManager = panelManager;
        this.add(new AvatarPanel());
        this.add(new EnemyCountPanel());
        this.add(new DifficultyPanel());
        beginGameButton = new JButton("Begin");
        beginGameButton.addActionListener(new BeginListener());
        this.setPreferredSize(new Dimension(GameApp.FRAME_WIDTH, GameApp.FRAME_HEIGHT));
        this.add(beginGameButton);
    }

    private class EnemyCountListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(decreaseEnemyCountButton) && totalEnemyCount > ENEMY_MIN) {
                totalEnemyCount --;
            }
            if (e.getSource().equals(increaseEnemyCountButton) && totalEnemyCount < ENEMY_MAX) {
                totalEnemyCount ++;
            }
            enemyCountNumber.setText(Integer.toString(totalEnemyCount));
        }
    }

    private class BeginListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (avatarButtons[0].isSelected()) {
                avatarHolder.setImageIcon(avatarImages[0]);
            } else if (avatarButtons[1].isSelected()) {
                avatarHolder.setImageIcon(avatarImages[1]);
            } else {
                avatarHolder.setImageIcon(avatarImages[2]);
            }
            panelManager.getGameplayPanel().setAvatarImage(avatarHolder.getAvatarImage());


            panelManager.changeCurrentState(PanelManager.PanelState.GAME);
            panelManager.getGameplayPanel().startTimers();
            panelManager.getGameplayPanel().setPause(false);
            panelManager.getGameplayPanel().setTotalEnemyCount(totalEnemyCount);
            setGamePanelDifficulty();
            panelManager.getGameplayPanel().setAsteroidSpawnRate(getDifficultySpawnRate());
        }
    }

    private void setGamePanelDifficulty() {
        if (difficultyButtons[0].isSelected()) {
            panelManager.getGameplayPanel().setCurrentEnemyLimit(3);
        } else if (difficultyButtons[1].isSelected()) {
            panelManager.getGameplayPanel().setCurrentEnemyLimit(6);
        } else if (difficultyButtons[2].isSelected()) {
            panelManager.getGameplayPanel().setCurrentEnemyLimit(12);
        }
    }

    public int getDifficultySpeed() {
        if (difficultyButtons[0].isSelected()) {
            return 3;
        } else if (difficultyButtons[1].isSelected()) {
            return 5;
        } else {
            return 7;
        }
    }

    public int getDifficultySpawnRate() {
        if (difficultyButtons[0].isSelected()) {
            return 1500;
        } else if (difficultyButtons[1].isSelected()) {
            return 750;
        } else if (difficultyButtons[2].isSelected()) {
            return 250;
        }
        return 0;
    }

    private class AvatarPanel extends JPanel {
        public AvatarPanel() {
            this.setPreferredSize(new Dimension(GameApp.FRAME_WIDTH, GameApp.FRAME_HEIGHT/3));
            avatarHolder = new AvatarHolder();
            GridBagConstraints gbc = new GridBagConstraints();
            this.setLayout(new GridBagLayout());

            avatarLabel = new JLabel("AVATAR SELECTION", SwingConstants.CENTER);
            avatarLabel.setPreferredSize(new Dimension(GameApp.FRAME_WIDTH, 50));
            avatarLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 30));
            gbc.ipady = 10;
            gbc.gridx = 1;
            gbc.gridy = 0;
            this.add(avatarLabel, gbc);

            avatarImages = new ImageIcon[3];
            avatarImages[0] = new ImageIcon("./images/rocket_red.png");
            avatarImages[1] = new ImageIcon("./images/rocket_green.png");
            avatarImages[2] = new ImageIcon("./images/rocket_blue.png");
            for (int i = 0; i < avatarImages.length; i ++) {
                gbc.ipady = 0;
                gbc.gridx = i;
                gbc.gridy = 1;
                JLabel label = new JLabel(" ", avatarImages[i], JLabel.RIGHT);
                this.add(label, gbc);

            }

            avatarButtonGroup = new ButtonGroup();
            avatarButtons = new JRadioButton[3];
            for (int i = 0; i < 3; i ++) {
                avatarButtons[i] = new JRadioButton("", false);
                avatarButtonGroup.add(avatarButtons[i]);
                avatarButtons[i].setIcon(new ImageIcon("./images/button_false.png"));
                avatarButtons[i].setSelectedIcon(new ImageIcon("./images/button_true.png"));
                gbc.ipady = 0;
                gbc.gridx = i;
                gbc.gridy = 2;
                this.add(avatarButtons[i], gbc);
            }
            avatarButtons[0].setSelected(true);
        }
    }

    private class EnemyCountPanel extends JPanel {
        public EnemyCountPanel() {
            this.setPreferredSize(new Dimension(GameApp.FRAME_WIDTH, GameApp.FRAME_HEIGHT/4));
            GridBagConstraints gbc = new GridBagConstraints();
            this.setLayout(new GridBagLayout());

            JPanel labelPanel = new JPanel();
            labelPanel.setLayout(new GridBagLayout());
            JPanel enemyCountPanel = new JPanel();
            enemyCountPanel.setLayout(new GridBagLayout());

            enemyCountLabel = new JLabel("TOTAL NUMBER OF ENEMIES", SwingConstants.CENTER);
            enemyCountLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 30));
            totalEnemyCount = 30;
            enemyCountNumber = new JLabel(Integer.toString(totalEnemyCount), SwingConstants.CENTER);
            enemyCountNumber.setFont(new Font("Sans-Serif", Font.PLAIN, 30));
            decreaseEnemyCountButton = new JButton("<");
            decreaseEnemyCountButton.addActionListener(new EnemyCountListener());
            increaseEnemyCountButton = new JButton(">");
            increaseEnemyCountButton.addActionListener(new EnemyCountListener());

            gbc.fill = GridBagConstraints.HORIZONTAL;
            labelPanel.add(enemyCountLabel, gbc);

            gbc.fill = 0;
            gbc.weightx = 0;
            gbc.gridx = 0;
            gbc.gridy = 1;
            enemyCountPanel.add(decreaseEnemyCountButton, gbc);

            gbc.gridx = 1;
            gbc.gridy = 1;
            gbc.ipadx = 50;
            enemyCountPanel.add(enemyCountNumber, gbc);

            gbc.gridx = 2;
            gbc.gridy = 1;
            gbc.ipadx = 0;
            enemyCountPanel.add(increaseEnemyCountButton, gbc);

            gbc.gridx = 0;
            gbc.gridy = 0;
            this.add(labelPanel, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            this.add(enemyCountPanel, gbc);

        }
    }

    private class DifficultyPanel extends JPanel {
        public DifficultyPanel() {
            this.setPreferredSize(new Dimension(GameApp.FRAME_WIDTH, GameApp.FRAME_HEIGHT/4));
            GridBagConstraints gbc = new GridBagConstraints();
            this.setLayout(new GridBagLayout());

            JPanel difficultyLabelPanel = new JPanel();
            difficultyLabelPanel.setLayout(new GridBagLayout());
            JPanel difficultyButtonPanel = new JPanel();
            difficultyButtonPanel.setLayout(new GridBagLayout());

            difficultyLabel = new JLabel("GAME DIFFICULTY", SwingConstants.CENTER);
            JLabel difficultyTextLabel = new JLabel("Easy          Medium          Hard", SwingConstants.CENTER);
            difficultyLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 30));
            difficultyTextLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
            difficultyButtonGroup = new ButtonGroup();
            difficultyButtons = new JRadioButton[3];

            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridy = 0;
            difficultyLabelPanel.add(difficultyLabel, gbc);

            gbc.gridy = 1;
            difficultyLabelPanel.add(difficultyTextLabel, gbc);


            for (int i = 0; i < 3; i ++) {
                difficultyButtons[i] = new JRadioButton("", false);
                difficultyButtonGroup.add(difficultyButtons[i]);
                difficultyButtons[i].setIcon(new ImageIcon("./images/button_false.png"));
                difficultyButtons[i].setSelectedIcon(new ImageIcon("./images/button_true.png"));
                gbc.fill = 0;
                gbc.weightx = 0;
                gbc.gridx = i;
                gbc.gridy = 0;
                if (i != 2) {
                    gbc.ipadx = 80;
                } else {
                    gbc.ipadx = 0;
                }
                difficultyButtonPanel.add(difficultyButtons[i], gbc);
            }

            difficultyButtons[0].setSelected(true);
            gbc.fill = GridBagConstraints.CENTER;
            gbc.gridy = 0;
            this.add(difficultyLabelPanel, gbc);

            gbc.gridy = 1;
            this.add(difficultyButtonPanel, gbc);
        }
    }
}
