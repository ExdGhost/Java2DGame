package shourie.rpg.game;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import shourie.rpg.game.MapLoader.MState;

public class DarkLord implements Enemy{

 private int x;
 private int y;
 private int velX;
 private int velY;
 private String dialogue[];
 private SoundLoader sound;
 private Game game;
 private Player player;
 private MapLoader map;
 private int HP = 1000;
 private int MANA = 5000; 
 private ImageLoader loader;
 private Animation Battack1;
 private Animation Battack2;
 private Animation Battack3;
 int attack =-1;
 private BufferedImage spriteSheet;
 private BufferedImage img;
 private int speed=10;
 private AttThunder thunder;
 private AttIce ice;
 private AttFire fire;
 private ArrayList<BufferedImage> bossTiles;

 DarkLord(int x,int y,Game game,Player player,MapLoader map, AttThunder thunder,AttIce ice,AttFire fire)
 {
	 this.x = x;
	 this.y = y;
	 this.loader = new ImageLoader();
	 this.game = game;
	 this.player = player;
	 this.map = map;
	 this.thunder = thunder;
	 this.ice = ice;
	 this.fire = fire;
	 bossTiles = new ArrayList<BufferedImage>();
	 loadBossImg();
	 this.loadAnimation();
 }
 public enum BatkState
 {
	 IDLE,
	 ATTACK
 }
 BatkState bossAtk;
 
 public enum BState{
	 ALIVE,
	 DEAD,
	 
 }
 
 BState State;
 void loadIntro()
 {
	 
 }
 @Override
public void tick() {
	 
	 if(this.State == BState.ALIVE)
	 {
	      this.map = game.getActiveMap();
	      
	        Battack1.runAnimation();
	        Battack2.runAnimation();
	        Battack3.runAnimation();
	        
	        if(this.bossAtk == BatkState.ATTACK)
	        {
	        	 if(attack == 1 && this.MANA >= ice.Hitmana)
	        		ice.launchAtk(this.x-10,this.y,-15,0,this);
	        	
	        	 else if(attack == 2 && this.MANA >= fire.Hitmana)
		        		fire.launchAtk(this.x-10,this.y,-5,0,this);
	        	
	        	 else if(attack==3 && this.MANA >= thunder.Hitmana)
	        	    thunder.launchAtk(player.getX(), 10, 0, 15,this);	        	
	        
	        }
	      
	 }
	 
}
@Override
public void render(Graphics2D g) {
	
	if(this.map.state == MState.CAVE && this.State == BState.ALIVE)
	{
		String fontString = "MS Gothic";
		Font font = new Font(fontString, Font.BOLD, 16);
		g.setFont(font);
		g.setColor(Color.RED);
	    g.drawString("HP : "+HP+" "+"MP : "+MANA, 450, 50);
	    
	    img = bossTiles.get(0);
	    if(attack == 1)
	    	Battack1.drawAnimation(g,x,y);
	    else if (attack==2)
	    	Battack2.drawAnimation(g,x,y);
	    else if(attack == 3)
	    	Battack3.drawAnimation(g, x, y);
	     else
	    	g.drawImage(img,x,y,null);
	}
	
}

public void loadBossImg()
{
	
	for(int i=1;i<=19;i++)
	{
		try {
			spriteSheet= loader.loadImage("/Chars/boss/"+i+".png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bossTiles.add(spriteSheet);
	}
	
			
}

public void loadAnimation()
{
	Battack1 =  new Animation(speed,bossTiles.get(0),bossTiles.get(1),bossTiles.get(2),bossTiles.get(3),bossTiles.get(4));
	Battack2  =  new Animation(speed,bossTiles.get(5),bossTiles.get(6),bossTiles.get(7),bossTiles.get(8),bossTiles.get(9),bossTiles.get(10),bossTiles.get(11));
	Battack3   = new Animation(speed,bossTiles.get(12),bossTiles.get(13),bossTiles.get(14),bossTiles.get(15),bossTiles.get(16),bossTiles.get(17),bossTiles.get(18));
}
   
void launchAtk1(Graphics2D g)
{
   	
}

void launchAtk2(Graphics2D g)
{
   	
}

void launchAtk3(Graphics2D g)
{
	
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
public void SetVelX(int velX) {
	// TODO Auto-generated method stub
	
}
@Override
public void SetVelY(int velY) {
	// TODO Auto-generated method stub
	
}
@Override
public int getX() {
	return this.x;
}
@Override
public int getY() {
	
	return this.y;
	
}
@Override
public int getVelX() {
	// TODO Auto-generated method stub
	return 0;
}
@Override
public int getVelY() {
	// TODO Auto-generated method stub
	return 0;
}
@Override
public Rectangle getBounds() {
	
	return (new Rectangle(this.x,this.y,72,84));
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

DarkLord getBoss()
{
	return this;
}
	
}
