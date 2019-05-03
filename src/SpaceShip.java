import java.awt.Point;

import javax.swing.ImageIcon;

public class SpaceShip  {

	private ImageIcon shipImage;
	private Point location;
	
	public SpaceShip(ImageIcon img, Point loc) {
		shipImage = img;
		location = loc;
	}
	
	public void setShipImage(ImageIcon img) {
		shipImage = img;
	}
	
	public ImageIcon getShipImage() {
		return shipImage;
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
