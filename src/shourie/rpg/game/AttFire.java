package shourie.rpg.game;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class AttFire implements Attacks{
	private int Hitpoint = 100;
	int Hitmana = 150;
	private int x;
	private int y;
	private int VelX=0;
	private int VelY=0;
	private ImageLoader loader;
	private BufferedImage spriteSheet;
	SpriteSheet ss;
	private int speed=5;
	private ArrayList<BufferedImage> AtkTiles;
	private Player player;
	private Animation Fattack1;
	private Animation Fattack2;
	private SoundLoader atkSound;
	private DarkLord boss;
	
	
	public enum FState{
		LAUNCHED,
		STOP,
	}
	
	FState tstate; 
	AttFire(Player player)
	{
		this.player = player;
		loader = new ImageLoader();
		loadAtkImg();
		this.loadAnimation();
		atkSound = new SoundLoader();
	}

	@Override
	public void tick() {
		
		
		   x+=VelX;
		   y+=VelY;
		
		   
		     Fattack1.runAnimation();
		     Fattack2.runAnimation();
		    
		  if(this.tstate == FState.LAUNCHED)
		   {
		    
		  if(this.getBounds().intersects(player.getBounds()))
		  {
			  player.SetHP(player.getHP()-this.Hitpoint);
			  this.tstate = FState.STOP;
		  }
		}
		
	}

	@Override
	public void render(Graphics2D g) {
		
		if(this.tstate == FState.LAUNCHED)
		{
			Fattack1.drawAnimation(g, x, y);		
			Fattack2.drawAnimation(g, player.getX(),player.getY());
			  			
		}
		
		
	}
	
	public void launchAtk(int x,int y ,int VelX,int VelY,DarkLord boss)
	{
	
		this.boss = boss;
		this.x = x;
		this.y = y;
		this.VelX = VelX;
		this.VelY = VelY;
		this.tstate = FState.LAUNCHED;
        boss.SetMANA(boss.getMANA()-this.Hitmana);
		atkSound.play("resources/sounds/Fire.wav");
		
	}

	public void loadAtkImg()
	{
		try {
			spriteSheet= loader.loadImage("/attacks/Fire.png");
		} catch (IOException e) {

			e.printStackTrace();
		}
		ss=new SpriteSheet(spriteSheet,1,5);
		AtkTiles = ss.grabTileImage();
		
	}
	
	public void loadAnimation()
	{
		Fattack1 = new Animation(speed,AtkTiles.get(0),AtkTiles.get(1),AtkTiles.get(2));
		Fattack2 = new Animation(10,AtkTiles.get(4),AtkTiles.get(3));
		
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
		return (new Rectangle(this.x,this.y,48,48));
	}

}
