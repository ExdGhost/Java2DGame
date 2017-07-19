package shourie.rpg.game;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class AttDark implements Attacks{
	private int Hitpoint = 500;
	int Hitmana = 500;
	private int x;
	private int y;
	private int VelX=0;
	private int VelY=0;
	private ImageLoader loader;
	private BufferedImage spriteSheet;
	SpriteSheet ss;
	private int speed=3;
	private ArrayList<BufferedImage> AtkTiles;
	private Player player;
	private Animation Dattack1;
	private Animation Dattack2;
	private SoundLoader atkSound;
	private DarkLord boss;

	
	
	public enum DState{
		LAUNCHED,
		STOP,
	}
	
	DState tstate; 
	AttDark(DarkLord boss)
	{
		this.boss=boss;
		loader = new ImageLoader();
		loadAtkImg();
		this.loadAnimation();
		atkSound = new SoundLoader();
	}

	@Override
	public void tick() {
		
		
		   x+=VelX;
		   y+=VelY;
		
		   
		     Dattack1.runAnimation();
		     Dattack2.runAnimation();
		    
		  if(this.tstate == DState.LAUNCHED)
		   {
		    
		  if(this.getBounds().intersects(boss.getBounds()))
		  {
			  boss.SetHP(boss.getHP()-this.Hitpoint);
			  player.SetMANA(player.getMANA()-this.Hitmana);
			  this.tstate = DState.STOP;
		  }
		}
		
	}

	@Override
	public void render(Graphics2D g) {
		
		if(this.tstate == DState.LAUNCHED)
		{
			Dattack1.drawAnimation(g, x, y);		
			Dattack2.drawAnimation(g, boss.getX()-10,boss.getY());
			  			
		}
		
		
	}
	
	public void launchAtk(int x,int y ,int VelX,int VelY,Player player)
	{
	
		this.player = player;
		this.x = x;
		this.y = y;
		this.VelX = VelX;
		this.VelY = VelY;
		this.tstate = DState.LAUNCHED;
        //player.SetMANA(player.getMANA()-this.Hitmana);
		atkSound.play("resources/sounds/Dark.wav");
		
	}

	public void loadAtkImg()
	{
		try {
			spriteSheet= loader.loadImage("/attacks/Dark.png");
		} catch (IOException e) {

			e.printStackTrace();
		}
		ss=new SpriteSheet(spriteSheet,1,5);
		AtkTiles = ss.grabTileImage();
		
	}
	
	public void loadAnimation()
	{
		Dattack1 = new Animation(speed,AtkTiles.get(0));
		Dattack2 = new Animation(5,AtkTiles.get(1),AtkTiles.get(2),AtkTiles.get(3));
		
	}
	@Override
	public void SetX(int x) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void SetY(int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setVelX(int velX) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setVelY(int velY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Rectangle getBounds() {
		return (new Rectangle(this.x,this.y,64,64));
	}

}
