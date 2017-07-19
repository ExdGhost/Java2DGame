package shourie.rpg.game;

//import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import shourie.rpg.game.DarkLord.BState;
import shourie.rpg.game.DarkLord.BatkState;
import shourie.rpg.game.MapLoader.MState;

@SuppressWarnings("unused")

public class Player {

	private int x;
	private int y;
	private int saveX;
	private int saveY;
	private int velX=0;
	private int velY=0;
	int LastX;
	int LastY;
	int attack =-1;
	 int sheild =0;//sheild
	 private int HP = 100;
	 private int MANA = 100;
	final int tileWidth  = 32;
	final int tileHeight = 64;
	private ImageLoader loader;
	private BufferedImage spriteSheet;
	private BufferedImage img;
	int gift = 0; //gift
	SpriteSheet ss;
	private int speed=6;
	private Animation playerWalkRight;
	private Animation playerWalkLeft;
	private Animation playerWalkUp;
	private Animation playerWalkDown;
	private Animation playerWalkTopRight;
	private Animation playerWalkTopLeft;
	private Animation playerWalkBottomRight;
	private Animation playerWalkBottomLeft;
	MapLoader CurrentMap;
	private MapCollision Pcollision;	
	private ArrayList<BufferedImage> playerTiles;
	private ResourceLoader resource;
	private Animation Pcombat;
	private Animation Pdead;
	private ArrayList<BufferedImage> playerCombatTiles;
	private ArrayList<BufferedImage> playerDeadTiles;
	private AttHoly holy;
	private AttDark dark;
	private DarkLord boss;
	int count =0;
	
	public enum LoadState
	{
		CAVE,
		WORLD,
		TOWN,
		NOTHING
	}
	
	LoadState Lstate;
	
	public enum Pstate {
		IDLE,
		WALKING,
		BATTLE
	}
	
	 Pstate state;
	 
	 public enum LiveState
	 {
		 ALIVE,
		 DEAD
	 }
	
	 LiveState PLstate;
	 
	Player(int x,int y,MapLoader map)
	{
		this.x=x;
		this.y=y;
		this.CurrentMap = map;
	    loader = new ImageLoader();
	    this.loadPlayerImg();
	    this.loadAnimation();	   
	    img= playerTiles.get(0);//initial
		
	}
	
	void loadAttack(AttHoly holy,AttDark dark)
	{
		this.holy = holy;
		this.dark  = dark;
	}
	
	public void tick()
	{
		x+=velX;
		y+=velY;
		
		    resource.chkResource();
		    
		    if(this.gift == 1)
		    {
		    	this.SetHP(this.HP+this.sheild);
		    	this.SetMANA(this.MANA+this.sheild);
		    	this.loadPlayerImg();
		    	this.loadAnimation();
		    	this.gift=0;
		    }

			 if(this.state == Pstate.BATTLE  && count ==1)
			 {
				     if(attack == 1 && this.MANA >= holy.Hitmana)
		        		holy.launchAtk(this.x+10,this.y,15,0,this);
				     
				     if(attack == 2 && this.MANA >= dark.Hitmana)
			        		dark.launchAtk(this.x+10,this.y,10,0,this);
		        }
						
		    
			if(!this.Pcollision.checkCollison(this.CurrentMap)) //no collision
			{
				saveX = this.x;
				saveY = this.y;
				
			}
			else
			{
				this.setX(saveX);
				this.setY(saveY);
			}
			

		if(x<0 )
		   x=0;
		if(x > Game.WIDTH*Game.SCALE-20)
			x= Game.WIDTH*Game.SCALE-20;
		if(y<0)
			y=0;		
		if(y>Game.HEIGHT*Game.SCALE-40)
			y= Game.HEIGHT*Game.SCALE-40;
		  

		playerWalkRight.runAnimation();
		playerWalkLeft.runAnimation();
		playerWalkUp.runAnimation();
		playerWalkDown.runAnimation();
		playerWalkTopRight.runAnimation();
		playerWalkTopLeft.runAnimation();
		playerWalkBottomRight.runAnimation();
		playerWalkBottomLeft.runAnimation();
		Pcombat.runAnimation();
		Pdead.runAnimation();
			
	}
	
	public void render(Graphics2D g)
	{
	  if(this.PLstate == LiveState.ALIVE) //if player is alive
	  {	  
		if(this.CurrentMap.state == MState.CAVE && boss.State == BState.ALIVE) //if current map is cave
		{
			//img = playerTiles.get(0);
		    
			String fontString = "MS Gothic";
			Font font = new Font(fontString, Font.BOLD, 16);
			g.setFont(font);
			g.setColor(Color.GREEN);
		    g.drawString("HP : "+HP+" "+"MP : "+MANA, 40, 50);
		    g.setColor(Color.YELLOW);
		    g.drawString("Space - Holy Attack ", 40, 70);
		    g.drawString("    X - Dark Attack ", 40, 90);
		    if(this.state == Pstate.BATTLE)
		    	Pcombat.drawAnimation(g, x, y);
		    
		    img = playerTiles.get(0);
		}
		else{
			
			
			if(velX>0&&velY==0)
			{
             playerWalkRight.drawAnimation(g, x, y);
             img= playerTiles.get(0);
			}
			else if(velX<0&&velY==0)
			{
				playerWalkLeft.drawAnimation(g, x, y);
				img=playerTiles.get(12);
			}
			
			else if(velY>0&&velX==0)
			{
             playerWalkDown.drawAnimation(g, x, y);
             img= playerTiles.get(18);
			}
			else if(velY<0&&velX==0)
			{
				playerWalkUp.drawAnimation(g, x, y);
				img=playerTiles.get(6);
			}
			
			else if(velX>0&&velY<0)
			{
             playerWalkTopRight.drawAnimation(g, x, y);
             img= playerTiles.get(3);
			}
			else if(velX<0&&velY<0)
			{
				playerWalkTopLeft.drawAnimation(g, x, y);
				img=playerTiles.get(9);
			}
			
			else if(velY>0&&velX<0)
			{
             playerWalkBottomLeft.drawAnimation(g, x, y);
             img= playerTiles.get(15);
			}
			else if(velY>0&&velX>0)
			{
				playerWalkBottomRight.drawAnimation(g, x, y);
				img=playerTiles.get(21);
			}
				
	   else
	    g.drawImage(img, x,y, null);	
		}
	  }
	  else
		  Pdead.drawAnimation(g, x, y);
	}
	
	public void loadPlayerImg()
	{
	   if(gift == 0)
	   {
		try {
			spriteSheet= loader.loadImage("/Chars/charAim1.png");
		} catch (IOException e) {

			e.printStackTrace();
		}
		ss=new SpriteSheet(spriteSheet,8,3);
		playerTiles = ss.grabTileImage();
		
		try {
			spriteSheet= loader.loadImage("/Chars/Pcombat1.png");
		} catch (IOException e) {

			e.printStackTrace();
		}
		ss=new SpriteSheet(spriteSheet,1,9);
		this.playerCombatTiles = ss.grabTileImage();
	
	   }
	   else
	   {
			try {
				spriteSheet= loader.loadImage("/Chars/charAim2.png");
			} catch (IOException e) {

				e.printStackTrace();
			}
			ss=new SpriteSheet(spriteSheet,8,3);
			playerTiles = ss.grabTileImage();
		   
		   
		   
	    try {
			spriteSheet= loader.loadImage("/Chars/Pcombat.png");
		 } catch (IOException e) {

			e.printStackTrace();
		 }
		  ss=new SpriteSheet(spriteSheet,1,9);
		  this.playerCombatTiles = ss.grabTileImage();
	  }
	
		
		try {
			spriteSheet= loader.loadImage("/Chars/Pdead.png");
		} catch (IOException e) {

			e.printStackTrace();
		}
		ss=new SpriteSheet(spriteSheet,1,4);
		this.playerDeadTiles = ss.grabTileImage();
		
	}
	
	public void loadAnimation()
	{
		playerWalkRight = new Animation(speed,playerTiles.get(0),playerTiles.get(1),playerTiles.get(2));
		playerWalkLeft  = new Animation(speed,playerTiles.get(12),playerTiles.get(13),playerTiles.get(14));
		playerWalkUp    = new Animation(speed,playerTiles.get(6),playerTiles.get(7),playerTiles.get(8));
	    playerWalkDown  = new Animation(speed,playerTiles.get(18),playerTiles.get(19),playerTiles.get(20));
	    playerWalkTopRight = new Animation(speed,playerTiles.get(3),playerTiles.get(4),playerTiles.get(5));
	    playerWalkBottomRight = new Animation(speed,playerTiles.get(21),playerTiles.get(22),playerTiles.get(23));
	    playerWalkTopLeft = new Animation(speed,playerTiles.get(9),playerTiles.get(10),playerTiles.get(11));
	    playerWalkBottomLeft = new Animation(speed,playerTiles.get(15),playerTiles.get(16),playerTiles.get(17));
	    this.Pdead = new Animation(15,playerDeadTiles.get(0),playerDeadTiles.get(1),playerDeadTiles.get(2),playerDeadTiles.get(3));
	    this.Pcombat = new Animation(10,playerCombatTiles.get(0),playerCombatTiles.get(1),playerCombatTiles.get(2),playerCombatTiles.get(3),playerCombatTiles.get(4),playerCombatTiles.get(5),playerCombatTiles.get(6),playerCombatTiles.get(7),playerCombatTiles.get(8));
	}
	
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}

	public void setX(int x)
	{
		this.x=x;
	}
	

	public void setY(int y)
	{
		this.y=y;
	}
	
	public void setVelX(int velX)
	{
		this.velX=velX;
	}

	public void setVelY(int velY)
	{
		this.velY=velY;
	}
	
	void setBoss(DarkLord boss)
	{
		this.boss = boss;
	}
	
	Rectangle getBounds()
	{
		if(this.CurrentMap.state == MState.TOWN)
			return new Rectangle(this.x,this.y,this.tileWidth,this.tileHeight/2);
		else
		   return new Rectangle(this.x,this.y+32,this.tileWidth,this.tileHeight/2);
	}
	
	void setMapCollision(MapCollision collision,MapLoader map)
	{
		this.Pcollision = collision;
		this.CurrentMap = map;
	}
	 
	void takeResource(ResourceLoader resource)
	{
		this.resource = resource;
	}
	
	int getVelX()
	{
		return this.velX;
	}
	
	int getVelY()
	{
		return this.velY;
	}
	
	int getHP()
	{
		return HP;
		
	}

	int getMANA()
	{
		return MANA;
	}

	void SetHP(int hp)
	{
		this.HP=hp;
		
	}

	void SetMANA(int mana)
	{
		this.MANA=mana;
	}
}
