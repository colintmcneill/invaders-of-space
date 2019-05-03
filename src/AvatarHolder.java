import javax.swing.ImageIcon;

public class AvatarHolder {
	
	private ImageIcon avatarImage;
	
	public AvatarHolder() {
		avatarImage = new ImageIcon("./images/rocket_red.png");
	}
	
	public ImageIcon getAvatarImage() {
		return avatarImage;
	}
	
	public void setImageIcon(ImageIcon img) {
		avatarImage = img;
	}

}
