import java.awt.geom.Rectangle2D;
import javax.swing.ImageIcon;

public class SpaceShip {

    private ImageIcon shipImage;
    private Rectangle2D hitBox;
    private double xPos;
    private double yPos;
    private double colliderWidth;
    private double colliderHeight;

    public SpaceShip(ImageIcon img, int x, int y) {
        shipImage = img;
        xPos = x;
        yPos = y;
        hitBox = new Rectangle2D.Double();
        hitBox.setRect(xPos - colliderWidth / 2, yPos - colliderHeight / 2,
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
    }

    public void setRect(double x, double y, double w, double h) {

    }

    public int outcode(double x, double y) {
        return 0;
    }

    public Rectangle2D createIntersection(Rectangle2D r) {
        return null;
    }

    public Rectangle2D createUnion(Rectangle2D r) {
        return null;
    }

    public boolean collide() {
        return true;
    }
}
