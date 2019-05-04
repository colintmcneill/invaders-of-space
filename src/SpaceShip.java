import java.awt.geom.Rectangle2D;
import javax.swing.ImageIcon;

public class SpaceShip {

    private ImageIcon shipImage;
    private Rectangle2D hitBox;
    private double xPos;
    private double yPos;
    private double colliderWidth = 50;
    private double colliderHeight = 80;

    public SpaceShip(ImageIcon img, int x, int y) {
        shipImage = img;
        xPos = x;
        yPos = y;
        hitBox = new Rectangle2D.Double();
        hitBox.setFrame(xPos - colliderWidth / 2, yPos - colliderHeight / 2,
                colliderWidth, colliderHeight);
    }

    public void setShipImage(ImageIcon img) {
        shipImage = img;
    }

    public ImageIcon getShipImage() {
        return shipImage;
    }

    public double getX() {
        return xPos;
    }

    public double getY() {
        return yPos;
    }

    public double getWidth() {
        return colliderWidth;
    }

    public double getHeight() {
        return colliderHeight;
    }

    public boolean isEmpty() {
        return false;
    }

    public void move(double dx, double dy) {
        setLocation(xPos + dx, yPos + dy);
    }

    public void setLocation(double x, double y) {
        xPos = x;
        yPos = y;
        hitBox.setFrame(xPos - colliderWidth / 2, yPos - colliderHeight / 2,
                colliderWidth, colliderHeight);
    }

    public Rectangle2D getHitBox() {
        return hitBox;
    }

    public boolean collide() {
        return true;
    }
}
