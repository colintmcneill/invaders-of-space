import java.awt.geom.Ellipse2D;
import javax.swing.ImageIcon;

public class Asteroid {

    private ImageIcon asteroidImage;
    private double currentRotation;
    private double rotationSpeed;
    private SpaceShip ship;
    private Ellipse2D hitBox;
    private double xPos;
    private double yPos;
    private double xSpeed;
    private double ySpeed;
    private double colliderWidth = 100;
    private double colliderHeight = 100;
    private PanelManager panelManager;
    private double speedBounds = 5;

    public Asteroid(PanelManager panelManager, SpaceShip ship, int x, int y) {
        this.panelManager = panelManager;
        this.ship = ship;
        hitBox = new Ellipse2D.Double();
        hitBox.setFrame(xPos - colliderWidth / 2, yPos - colliderHeight / 2,
                colliderWidth, colliderHeight);
        asteroidImage = new ImageIcon("./images/asteroid.png");
        currentRotation = randomRange(0, 359);
        rotationSpeed = randomRange(-0.05, 0.05);
        xSpeed = randomRange(-2, 2);
        ySpeed = 1;
        xPos = x;
        yPos = y;
    }

    public ImageIcon getAsteroidImage() {
        return asteroidImage;
    }

    public double getxSpeed() {
        return xSpeed;
    }

    public double getySpeed() {
        return ySpeed;
    }

    public void setySpeed(double ySpeed) {
        this.ySpeed = ySpeed;
    }

    public double getX() {
        return xPos;
    }

    public double getY() {
        return yPos;
    }

    public double getCurrentRotation() {
        return currentRotation;
    }

    public void setCurrentRotation(double currentRotation) {
        this.currentRotation = currentRotation;
    }

    public double getRotationSpeed() {
        return rotationSpeed;
    }

    public void move(double dx, double dy) {
        setLocation(xPos + dx, yPos + dy);
    }

    public void setLocation(double x, double y) {
        xPos = x;
        yPos = y;
        hitBox.setFrame(xPos, yPos, colliderWidth, colliderHeight);
    }

    public void bounce() {
        if (xPos <= 0) {
            xPos = 1;
            xSpeed = -xSpeed;
            rotationSpeed = randomRange(-0.05, 0.05);
        } else if (xPos >= GameApp.FRAME_WIDTH - asteroidImage.getIconWidth()) {
            xPos = GameApp.FRAME_WIDTH - asteroidImage.getIconWidth() - 1;
            xSpeed = -xSpeed;
            rotationSpeed = randomRange(-0.05, 0.05);
        }
    }

    public boolean collisionCheck() {
        if (hitBox.getBounds2D().intersects(ship.getHitBox().getBounds2D())) {
            return true;
        }
        return false;
    }

    private double randomRange(double min, double max) {
        double num = (double)(Math.random() * (max - min)) + min;
        if (num == 0) {
            randomRange(min, max);
        }
        return num;
    }

}
