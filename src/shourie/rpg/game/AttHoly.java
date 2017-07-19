package shourie.rpg.game;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class AttHoly implements Attacks{
	private int Hitpoint = 100;
	int Hitmana = 50;
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
	private Animation Hattack1;
	private Animation Hattack2;
	private SoundLoader atkSound;
	private DarkLord boss;

	
	
	public enum HState{
		LAUNCHED,
		STOP,
	}
	
	HState tstate; 
	AttHoly(DarkLord boss)
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
		
		   
		     Hattack1.runAnimation();
		     Hattack2.runAnimation();
		    
		  if(this.tstate == HState.LAUNCHED)
		   {
		    
		  if(this.getBounds().intersects(boss.getBounds()))
		  {
			  boss.SetHP(boss.getHP()-this.Hitpoint);
			  player.SetMANA(player.getMANA()-this.Hitmana);
			  this.tstate = HState.STOP;
		  }
		}
		
	}

	@Override
	public void render(Graphics2D g) {
		
		if(this.tstate == HState.LAUNCHED)
		{
			Hattack1.drawAnimation(g, x, y);		
			Hattack2.drawAnimation(g, boss.getX(),boss.getY());
			  			
		}
		
		
	}
	
	public void launchAtk(int x,int y ,int VelX,int VelY,Player player)
	{
	
		this.player = player;
		this.x = x;
		this.y = y;
		this.VelX = VelX;
		this.VelY = VelY;
		this.tstate = HState.LAUNCHED;
       // player.SetMANA(player.getMANA()-this.Hitmana);
		atkSound.play("resources/sounds/Holy.wav");
		
	}

	public void loadAtkImg()
	{
		try {
			spriteSheet= loader.loadImage("/attacks/Holy.png");
		} catch (IOException e) {

			e.printStackTrace();
		}
		ss=new SpriteSheet(spriteSheet,1,8);
		AtkTiles = ss.grabTileImage();
		
	}
	
	public void loadAnimation()
	{
		Hattack1 = new Animation(speed,AtkTiles.get(0));
		Hattack2 = new Animation(10,AtkTiles.get(2),AtkTiles.get(3),AtkTiles.get(4),AtkTiles.get(5),AtkTiles.get(6),AtkTiles.get(7));
		
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
