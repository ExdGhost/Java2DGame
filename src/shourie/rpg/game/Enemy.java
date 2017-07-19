package shourie.rpg.game;


import java.awt.Graphics2D;
import java.awt.Rectangle;

public interface Enemy {	
	void tick();
	void SetX(int x);
	void SetY(int y);
	void SetVelX(int velX);
	void SetVelY(int velY);
	int getX();
	int getY();
	int getVelX();
	int getVelY();
	Rectangle getBounds();
	void render(Graphics2D g);
	
}
