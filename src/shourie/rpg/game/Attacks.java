package shourie.rpg.game;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public interface Attacks {

	void tick();
	void render(Graphics2D g);
	void SetX(int x);
	void SetY(int y);
	int getX();
	int getY();
	void setVelX(int velX);
	void setVelY(int velY);	
	Rectangle getBounds();
}
