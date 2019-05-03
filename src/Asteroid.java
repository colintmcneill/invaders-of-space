import java.awt.Point;

import javax.swing.ImageIcon;

public class Asteroid {

    private ImageIcon asteroidImage;
    private Point location;
    private int speed = 2;

    public Asteroid(int x, int y) {
        asteroidImage = new ImageIcon("./images/asteroid.png");
        location = new Point(x, y);
    }

    public ImageIcon getAsteroidImage() {
        return asteroidImage;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int spd) {
        speed = spd;
    }

    public int getX() {
        return (int)location.getX();
    }

    public int getY() {
        return (int)location.getY();
    }

    public void move(int dx, int dy) {
        location.move(location.x + dx, location.y + dy);
    }

    public void setLocation(double x, double y) {
        location.setLocation(x, y);
    }



}
