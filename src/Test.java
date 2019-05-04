import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

public class Test extends RectangularShape {

    public double getX() {
        return 0;
    }

    public double getY() {
        return 0;
    }

    public double getWidth() {
        return 0;
    }

    public double getHeight() {
        return 0;
    }

    public boolean isEmpty() {
        return false;
    }

    public void setFrame(double x, double y, double w, double h) {

    }

    public Rectangle2D getBounds2D() {
        return null;
    }

    public boolean contains(double x, double y) {
        return false;
    }

    public boolean intersects(double x, double y, double w, double h) {
        return false;
    }

    public boolean contains(double x, double y, double w, double h) {
        return false;
    }

    public PathIterator getPathIterator(AffineTransform at) {
        return null;
    }
}
