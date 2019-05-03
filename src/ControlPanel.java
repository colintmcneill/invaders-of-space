import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

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
	private GameplayPanel gameplayPanel;
	private AvatarHolder avatarHolder;
	private int testInt = 0;
	
	public ControlPanel(GameplayPanel gp) {
		//this.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 25));
		gameplayPanel = gp;
		this.add(new AvatarPanel());
		this.add(new EnemyCountPanel());
		this.add(new DifficultyPanel());
		beginGameButton = new JButton("Begin");
		beginGameButton.addActionListener(new BeginListener(this, gameplayPanel)); //FIXME shouldn't this take gp? the parameter?

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

		ControlPanel controlPanel;
		GameplayPanel gameplayPanel;
		
		public BeginListener(ControlPanel cp, GameplayPanel gp) {
			controlPanel = cp;
			gameplayPanel = gp;
		}
		
		public void actionPerformed(ActionEvent e) {
			if (avatarButtons[0].isSelected()) {
				avatarHolder.setImageIcon(avatarImages[0]);				
			} else if (avatarButtons[1].isSelected()) {
				avatarHolder.setImageIcon(avatarImages[1]);
			} else {
				avatarHolder.setImageIcon(avatarImages[2]);
			}
			gameplayPanel.setAvatarImage(avatarHolder.getAvatarImage());
			gameplayPanel.setPause(false);
			controlPanel.setVisible(false);
		}
		
	}


	private class AvatarPanel extends JPanel {
		public AvatarPanel() {
			this.setPreferredSize(new Dimension(GameApp.FRAME_WIDTH, GameApp.FRAME_HEIGHT/4));
			avatarHolder = new AvatarHolder();

			//label
			avatarLabel = new JLabel("AVATAR SELECTION", SwingConstants.CENTER);
			avatarLabel.setPreferredSize(new Dimension(GameApp.FRAME_WIDTH, 50));
			avatarLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 30));
			this.add(avatarLabel);

			//images
			avatarImages = new ImageIcon[3];
			avatarImages[0] = new ImageIcon("./images/rocket_red.png");
			avatarImages[1] = new ImageIcon("./images/rocket_green.png");
			avatarImages[2] = new ImageIcon("./images/rocket_blue.png");
			for (int i = 0; i < avatarImages.length; i ++) {
				JLabel label = new JLabel(" ", avatarImages[i], JLabel.RIGHT);
				this.add(label);

			}

			//buttons
			avatarButtonGroup = new ButtonGroup();
			avatarButtons = new JRadioButton[3];
			for (int i = 0; i < 3; i ++) {
				avatarButtons[i] = new JRadioButton("", false);
				avatarButtonGroup.add(avatarButtons[i]);
				avatarButtons[i].setIcon(new ImageIcon("./images/button_false.png"));
				avatarButtons[i].setSelectedIcon(new ImageIcon("./images/button_true.png"));
				this.add(avatarButtons[i]);
			}
			avatarButtons[0].setSelected(true);
		}
	}

	private class EnemyCountPanel extends JPanel {
		public EnemyCountPanel() {
			//this.setPreferredSize(new Dimension(GameApp.FRAME_WIDTH, GameApp.FRAME_HEIGHT/5));
			totalEnemyCount = 30;
			enemyCountLabel = new JLabel("TOTAL NUMBER OF ENEMIES", SwingConstants.CENTER);
			enemyCountNumber = new JLabel(Integer.toString(totalEnemyCount), SwingConstants.CENTER);
			//enemyCountLabel.setPreferredSize(new Dimension(GameApp.FRAME_WIDTH, GameApp.FRAME_HEIGHT/10));
			//enemyCountNumber.setPreferredSize(new Dimension(GameApp.FRAME_WIDTH / 8, GameApp.FRAME_HEIGHT/10));
			enemyCountLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 30));
			enemyCountNumber.setFont(new Font("Sans-Serif", Font.PLAIN, 30));
			increaseEnemyCountButton = new JButton(">");
			increaseEnemyCountButton.addActionListener(new EnemyCountListener());
			decreaseEnemyCountButton = new JButton("<");
			decreaseEnemyCountButton.addActionListener(new EnemyCountListener());
			this.add(enemyCountLabel);
			this.add(decreaseEnemyCountButton);
			this.add(enemyCountNumber);
			this.add(increaseEnemyCountButton);
		}
	}

	private class DifficultyPanel extends JPanel {
		public DifficultyPanel() {
			//this.setPreferredSize(new Dimension(GameApp.FRAME_WIDTH, GameApp.FRAME_HEIGHT/3));
			difficultyLabel = new JLabel("GAME DIFFICULTY", SwingConstants.CENTER);
			JLabel difficultyTextLabel = new JLabel("Easy          Medium          Hard", SwingConstants.CENTER);
			//difficultyLabel.setPreferredSize(new Dimension(GameApp.FRAME_WIDTH, GameApp.FRAME_HEIGHT/10));
			//difficultyTextLabel.setPreferredSize(new Dimension(GameApp.FRAME_WIDTH, GameApp.FRAME_HEIGHT/10));
			difficultyLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 30));
			difficultyTextLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
			difficultyButtonGroup = new ButtonGroup();
			difficultyButtons = new JRadioButton[3];
			this.add(difficultyLabel);
			this.add(difficultyTextLabel);
			for (int i = 0; i < 3; i ++) {
				difficultyButtons[i] = new JRadioButton("", false);
				difficultyButtonGroup.add(difficultyButtons[i]);
				difficultyButtons[i].setIcon(new ImageIcon("./images/button_false.png"));
				difficultyButtons[i].setSelectedIcon(new ImageIcon("./images/button_true.png"));
				this.add(difficultyButtons[i]);
			}
			difficultyButtons[0].setSelected(true);
		}
	}
}
