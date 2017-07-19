package shourie.rpg.game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Objects {
private int x;
private int y;
private BufferedImage img;
private ImageLoader loader;
	
	Objects(int x,int y,String path)
	{
		this.x=x;
		this.y=y;
		loader = new ImageLoader();
		try {
			img = loader.loadImage(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void render(Graphics2D g)
	{
		g.drawImage(img,x,y,null);
	}

}
