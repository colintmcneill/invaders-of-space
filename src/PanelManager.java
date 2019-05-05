import javax.swing.*;

public class PanelManager {

    public static enum PanelState {MAIN_MENU, CONTROL, GAME, WIN_LOSE};
    private PanelState currentState;
    private JPanel currentPanel;
    private GameApp gameApp;

    private JPanel panelHolder;
    private MainMenuPanel mainMenuPanel;
    private ControlPanel controlPanel;
    private GameplayPanel gameplayPanel;
    private WinLosePanel winLosePanel;

    public PanelManager(GameApp gameApp, JPanel paneHolder) {
        this.gameApp = gameApp;
        this.panelHolder = paneHolder;
        mainMenuPanel = new MainMenuPanel(this);
        controlPanel = new ControlPanel(this);
        gameplayPanel = new GameplayPanel(this);
        winLosePanel = new WinLosePanel(this);

        mainMenuPanel.setVisible(false);
        controlPanel.setVisible(false);
        //gameplayPanel.setVisible(false);
        winLosePanel.setVisible(false);

        paneHolder.add(mainMenuPanel);
        paneHolder.add(controlPanel);
        paneHolder.add(winLosePanel);
        paneHolder.add(gameplayPanel);

        currentState = PanelState.MAIN_MENU;
        currentPanel = mainMenuPanel;
        currentPanel.setVisible(true);
    }

    public GameplayPanel getGameplayPanel() {
        return gameplayPanel;
    }

    public ControlPanel getControlPanel() {
        return controlPanel;
    }

    public MainMenuPanel getMainMenuPanel() {
        return mainMenuPanel;
    }

    public WinLosePanel getWinLosePanel() {
        return winLosePanel;
    }

    public void changeCurrentState(PanelState panelState) {
        currentPanel.setEnabled(false);
        if (currentPanel != gameplayPanel) {
            currentPanel.setVisible(false);
        }
        currentState = panelState;
        if (currentState == PanelState.MAIN_MENU) {
            currentPanel = mainMenuPanel;
        } else if (currentState == PanelState.CONTROL) {
            currentPanel = controlPanel;
        } else if (currentState == PanelState.GAME) {
            currentPanel = gameplayPanel;
        } else if (currentState == PanelState.WIN_LOSE) {
            currentPanel = winLosePanel;
        }
        currentPanel.setEnabled(true);
        currentPanel.setVisible(true);
    }

}
