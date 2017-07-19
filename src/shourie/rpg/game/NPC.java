package shourie.rpg.game;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public abstract class NPC {
	
    protected int x;
	protected int y;
	protected int VelX=0;
	protected int VelY=0;
	protected MapLoader map;
	protected Game game;
	protected ImageLoader loader;
	protected BufferedImage spriteSheet;
	SpriteSheet ss;
	protected int speed=8;
	protected BufferedImage img;
	protected ArrayList<BufferedImage> npcTiles;
	protected Player player;
	protected SoundLoader npcSound;
	protected DarkLord boss;
	protected Animation WalkLeft;
	protected Animation WalkRight;
	protected Animation WalkUp;
	protected Animation WalkDown;
	protected String name ;
	boolean NoCollision = true;
	private int lastx;
	private int lasty;
	private boolean control = true;
	private long start;
	private long  end;
	protected String property ="";
	static boolean trigger = false;
	static boolean trigger2 = false;
	protected int LastX;
	protected int LastY; // to keep track of last non collision state diff from lastx or lasty
	protected int delay;
	
    public enum npcPos
    {
    	UP,
    	DOWN,
    	LEFT,
    	RIGHT
    }
	
    npcPos position;
	
    NPC(MapLoader map,Game game,Player player)
    {
    	this.map = map;
    	this.game = game;
    	this.player = player;
    	loader = new ImageLoader();
    
    }
    
    void updateLastXY()
    {
    	this.lastx = x;
    	this.lasty = y;
    }
   
    
	public void tick() {
		
		this.x += this.VelX;
		this.y += this.VelY;
		
		this.WalkDown.runAnimation();
		this.WalkLeft.runAnimation();
		this.WalkRight.runAnimation();
		this.WalkUp.runAnimation();
		
	}

	void startTime()
	{
		start = System.currentTimeMillis();
		
	}
	
	void endTime()
	{
		end = System.currentTimeMillis();
		
	}
	
	public void render(Graphics2D g) {
		
		 // if(this.NoCollision) 
		 // {
			  if(this.position == npcPos.UP)
			  {
				  this.setVelY(0);
				  if(control)
				  { 
					  startTime();
					  control = false;
				  }
				  endTime();
				  
				  if(end - start > delay)
					  this.setVelY(1);
					  
				     this.WalkDown.drawAnimation(g, this.x, this.y);
				  this.img = this.npcTiles.get(0);
				  
				  if(this.y >= lasty+100 )
				  {
					  this.updateLastXY();
					  this.position = npcPos.DOWN;
					  control = true;
					 
				  }
				  					  
			  }
			  else if(this.position == npcPos.DOWN)
			  {
				  this.setVelY(0);
				  if(control)
				  { 
					  startTime();
					  control = false;
				  }
				  endTime();
				  
				  if(end - start > delay)
					  this.setVelY(-1);
				  
				  this.WalkUp.drawAnimation(g, this.x, this.y);
				  this.img = this.npcTiles.get(9);
				  
				  if(this.y <= lasty-100 )
				  {
					  this.updateLastXY();
					  this.position = npcPos.UP;		
					  control = true;
				  }
				  					  
			  }
			  else if(this.position == npcPos.LEFT)
			  {
				  this.setVelX(0);
				  if(control)
				  { 
					  startTime();
					  control = false;
				  }
				  endTime();
				  
				  if(end - start > delay)
					  this.setVelX(1);
				  
				  this.WalkRight.drawAnimation(g, this.x, this.y);
				  this.img = this.npcTiles.get(3);
				  
				  if(this.x >= lastx+100 )
				  {
					  this.updateLastXY();
					  this.position = npcPos.RIGHT;		
					  control = true;
				  }				  					  
			  }
			  
			  else if(this.position == npcPos.RIGHT)
			  {
				  this.setVelX(0);
				  if(control)
				  { 
					  startTime();
					  control = false;
				  }
				  endTime();
				  
				  if(end - start > delay)
					  this.setVelX(-1);
				  
				  this.WalkLeft.drawAnimation(g, this.x, this.y);
				  this.img = this.npcTiles.get(6);
				  
				  if(this.x <= lastx-100 )
				  {
					  this.updateLastXY();
					  this.position = npcPos.LEFT;		
					  control = true;
				  }				  					  
			  }			  
				  
		//  }
		  else
			  g.drawImage(img,x,y,null);	
		
	}
	
	 void loadNpcImg(String path)
	    {
	    	try {
				spriteSheet= loader.loadImage(path);
			} catch (IOException e) {

				e.printStackTrace();
			}
			ss=new SpriteSheet(spriteSheet,4,3);
			npcTiles = ss.grabTileImage();
	    				
	    }

void loadAnimation()
{
	 WalkLeft = new Animation(speed,npcTiles.get(3),npcTiles.get(4),npcTiles.get(5));
	 WalkRight= new Animation(speed,npcTiles.get(6),npcTiles.get(7),npcTiles.get(8));
	    WalkUp= new Animation(speed,npcTiles.get(9),npcTiles.get(10),npcTiles.get(11));
	 WalkDown = new Animation(speed,npcTiles.get(0),npcTiles.get(1),npcTiles.get(2));
}
	public void SetX(int x) {
		this.x = x;
		
	}

	public void SetY(int y) {
		this.y = y;
		
	}

	public int getX() {
		
		return this.x;
	}

	public int getY() {
		return this.y;
	}
	
	public int getLastX() {
		
		return this.LastX;
	}

	public int getLastY() {
		return this.LastY;
	}

	public void setVelX(int velX) {
		this.VelX = velX;
		
	}

	public void setVelY(int velY) {
		this.VelY = velY;
		
	}

	public Rectangle getBounds() {
		return (new Rectangle(this.x,this.y,npcTiles.get(0).getWidth(),npcTiles.get(0).getHeight()));
	}

	String getProperty()
	{
		return this.property;
	}
	
}
