package shourie.rpg.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import shourie.rpg.game.DarkLord.BState;
import shourie.rpg.game.MapLoader.MState;

public class npcMain extends NPC{
		
	private ArrayList<BufferedImage> potrait;
	private DarkLord boss;
	private int Sindex =0;
	private int Pindex =1;
	boolean accept = false;
	private BufferedImage img1;
	private BufferedImage dbox;
	private BufferedImage dbox1;
	private String name = "Town Witch: Shiori";
	private String Tname="";
	private String say="";
	private int DX =120;
	private int DY =540;
	private int sayX = DX;
	private int sayY = DY;
	private String dialogue1[] = {	
	"Please don't bother me, I need to think.On second thought hold on a moment.I sense a power hidden deep within you, maybe you can help after all." ,//0
	"Let me introduce my self first. I am the witch of this samll town called Shiverspike. As you can see everyone in this town has fallen into a deep sleep.",//1
	"This is because an evil entity has taken up residence in the cave to the north-west of this town. It's dark aura is responsible for this situation.",//2
	"I think you can help this town and it's citizens. Please, we really need all the help we can get. Please take this holy sheild, it will help you a lot.",//3
	"With this sheild and your power you should be able to defeat this dark entity.Press F to accept this sheild.",//4
	"Go now, and defeat the Dark Lord of the north-west cave.",//5
	"You haven't defeated the dark entity yet? Please hurry,we are running out of time."//6
	};
	
	private String dialogue2[]={
	"You did it, you really did it, I am at a loss of words. There is nothing in the world with which I can repay you for this great deed.",//0
	"The people in this town can now live peacefully and happily.",//1
	"But I know, that you seek something, I can sense it from within you.Go to the village of Holders Peak, east of here and you may find a lead to what you seek.",//2
	 "Do you want to venture forth on your journey? Press Q to venture forth"//3
	};
		
	
    npcMain(int x, int y,MapLoader map,Game game,Player player,DarkLord boss)
    {
    	super(map, game, player);
    	this.x = x;
    	this.y = y;
    	this.boss = boss;
    	this.loadNpcImg("/NPC/npc_Main.png");
    	this.loadAnimation();
    	this.updateLastXY();
    	this.img = this.npcTiles.get(0);
    	this.loadImg();
    	this.property ="Main";
    	this.delay = 3000;
    	//this.potrait = new ArrayList<BufferedImage>();
    	
    }
    
  
    
	@Override
	public void tick() {
		
		this.map = game.getActiveMap();
		
		if(map.state == MState.TOWN)
		{
		  super.tick();
		  if(!this.getBounds().intersects(player.getBounds()))
		  {
			  this.LastX =x;
			  this.LastY =y;
		  }
		}
	}

	@SuppressWarnings("static-access")
	@Override
	public void render(Graphics2D g)
	{		
		if(map.state == MState.TOWN )
		{
			super.render(g);
			
			//String fontString = "MS Gothic";
			Font font = new Font(Font.SANS_SERIF, Font.BOLD, 12);
			//Font font = new Font(fontString, Font.BOLD, 12);
			g.setFont(font);
			g.setColor(Color.WHITE);
			
			if(!this.NoCollision)
			{
				if(trigger)
				{
				   if(boss.State == BState.ALIVE)
				   {
					  game.enableTrigger = true; 
					img1 = potrait.get(Pindex);
					dbox1 = dbox;
					Tname = this.name;	
					if(Sindex ==2)
					   Pindex++;
					if(Sindex ==3)
						Pindex++;
					say = dialogue1[Sindex];
					if(Sindex < 4)
						Sindex++;
					if(trigger2 && Sindex >=4 && Sindex < 6)
						{
						 Sindex++;
						 Pindex=2;
						   if(Sindex==6)
						   {
						   trigger2 = false;
						   game.enableTrigger = false;
						   }
						}
					
				   }
				   else
				   {
					   Pindex=0;
					   img1 = potrait.get(Pindex);
						dbox1 = dbox;
						Tname = this.name;	
						say = dialogue2[Sindex];
						if(Sindex < 4)
						{	Sindex++;							
							if(Sindex == 4)
							{
								Sindex = 3;
								game.enableTrigger = true;
							}
						}
				   }
				   
			    }
				
				 g.drawImage(dbox1,0,500, null);
				 g.drawImage(img1,8, 528, null);
				 g.drawString(Tname, 6, 520);
				    font = new Font(Font.SANS_SERIF, Font.BOLD, 14);
					g.setFont(font);
					g.setColor(Color.WHITE);
				// g.drawString(dialogue1[0], 100, 550);
				// g.drawString(dialogue1[1], 100, 570);
				
				if(say.length() > 0)
				{
				 for(int i = 0;i <say.length();i++)
				 {
					 String s = Character.toString(say.charAt(i));
					 
					 g.drawString(s, sayX, sayY);
					 
					 sayX+=10;
					 
					 if(sayX > dbox1.getWidth()-15)
					 {
						 if(say.charAt(i+1) == ' ')
							 i++;
						 
						 sayX = 120;
						 sayY = sayY + 20;
					 }
						
				  }
				 
				 sayX = DX;
				 sayY = DY;
				
				}					 
		   }
			else
			{
				img1=null;
				dbox1=null;
				Tname="";
				say="";
			}
			
		}		
		this.trigger = false;
		
	}
	
	 void loadImg()
	    {
	    	try {
	    		dbox = loader.loadImage("/Generic/Dbox.png");
				spriteSheet= loader.loadImage("/NPC/Main_potrait.png");
			} catch (IOException e) {

				e.printStackTrace();
			}
			ss=new SpriteSheet(spriteSheet,2,4);
			potrait = ss.grabTileImage();
	    				
	    }
	 
	 public int getSindex()
	 {
		 return this.Sindex;
	 }
	 
	 public void SetSindex(int x)
	 {
		 this.Sindex = x;
	 }
	
}
