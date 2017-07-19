package shourie.rpg.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import shourie.rpg.game.DarkLord.BState;
import shourie.rpg.game.MapLoader.MState;

public class npcGeneric extends NPC {
	private ArrayList<BufferedImage> potrait;
	private DarkLord boss;
	private int Sindex =0;
	private int Pindex =0;
	boolean accept = false;
	private BufferedImage img1;
	private BufferedImage dbox;
	private BufferedImage dbox1;
	private String name = "";
	private String Tname="";
	private String say="";
	private int DX =120;
	private int DY =540;
	private int sayX = DX;
	private int sayY = DY;
	boolean npcGenTrigger = false;
	int PLx;
    int PLy;
	
	private String dialogue1[] = {		
	"It's a bright and sunny day.Finally I can take a bath now, all that sleeping on the ground, woosh!!",
	"We are really thankful that you saved this town, all the people can now live without fear.",
	 "Oh,thank you so much for saving me and my daughter,now we can finally sail to the shores of Yearnsdale.",
	 "Oh,thank you so much for saving me and my son,now we can finally hunt monsters again.",	 
	 "In the end we shall all die at the hands of Balthemor.",
	 "If you ever visit the town of Elder-Hollow,be sure to meet the town ghost,it might sound at the moment,but just do it.",
	 "It's getting colder by the moment.",
	 "Wanna have some cake,you will find lots of cake in the INN.",
	 "Always keep a HP and MANA potions, difficult battles require great strategy."
	};
	
	private String dialogue2[]={
			"I guess I should take a walk now, see you.",
			"On your way to Holders Peak? Be aware about the swamp monster over there,been driving the people crazy lately.",
			"Have you seen cave trolls? They are quite dangerous.",
			"Oh silent night, starry night -- err what was it again?",
			"If you keep a pet, be sure to feed it every day.",
			"There are so many lost ruins in Ettrania, the northern continent.Who knows how much treasures they hold!",
			"As a token of thanks,let me feed you my most famous recepie.",
			"If a Phoenix could talk,I wonder what would it say?",
			"Hello -- Hello--Hello ..err how may times did I say it just now?"
	};
		
	private int arr[] = {5000,3000,4000,2000,6000,1000,7000,9000,3500,2500,5500,6500,6700,3300,4760,8510,3900};
	
    npcGeneric(int x, int y,MapLoader map,Game game,Player player,DarkLord boss,String name,String image,String potrait)
    {
    	super(map, game, player);
    	this.name = name;
    	this.x = x;
    	this.y = y;
    	this.boss = boss;
    	this.loadNpcImg(image);
    	this.loadAnimation();
    	this.updateLastXY();
    	this.img = this.npcTiles.get(0);
    	this.potrait = new ArrayList<BufferedImage>();
    	this.loadImg(potrait);
    	this.property ="Generic";
    	this.delay = this.arr[this.randomWithRange(0, 16)]; 	
    }
          
	@Override
	public void tick() {
		
		
		this.map = game.getActiveMap();
		
		if(map.state == MState.TOWN && boss.State == BState.DEAD)
		{
			 if(!this.getBounds().intersects(player.getBounds()))
			  {
				  this.LastX =x;
				  this.LastY =y;
				  this.PLx = player.getX();
				  this.PLy = player.getY();
			  }
		  super.tick();
		  //Sindex = 0;
		}
	}

	@Override
	public void render(Graphics2D g)
	{		
		if(map.state == MState.TOWN && boss.State == BState.DEAD )
		{
			super.render(g);
			
			//String fontString = "MS Gothic";
			Font font = new Font(Font.SANS_SERIF, Font.BOLD, 12);
			//Font font = new Font(fontString, Font.BOLD, 12);
			g.setFont(font);
			g.setColor(Color.WHITE);
			
		//	System.out.println("NoCollision "+this.NoCollision+" trigger = "+trigger);
			
			if(!this.NoCollision)
			{
				if(this.npcGenTrigger)
				{
					img1 = potrait.get(0);
					dbox1 = dbox;
					Tname = this.name;						
				
					int choice = this.randomWithRange(0, 1);
					
					//System.out.println("choice = "+choice);
					if(choice == 0)
					{
						int length = dialogue1.length;	
						Sindex = this.randomWithRange(0, length-1);
						say = dialogue1[Sindex];						  
						  
					}
					else
					{
						int length = dialogue2.length;	
						Sindex = this.randomWithRange(0, length-1);
						say = dialogue2[Sindex];						  
						  
					}	
				   
			    }
				
				 g.drawImage(dbox1,0,500, null);
				 g.drawImage(img1,8, 528, null);
				 g.drawString(Tname, 6, 520);
				    font = new Font(Font.SANS_SERIF, Font.BOLD, 14);
					g.setFont(font);
					g.setColor(Color.WHITE);
				
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
		this.npcGenTrigger = false; 
		
	}
	
	  int randomWithRange(int min, int max)
	  {
	   int range = (max - min) + 1;     
	   return (int)(Math.random() * range) + min;
	  }
	
	 void loadImg(String path)
	    {
	    	try {
	    		dbox = loader.loadImage("/Generic/Dbox.png");
				spriteSheet= loader.loadImage(path);
			} catch (IOException e) {

				e.printStackTrace();
			}
			potrait.add(spriteSheet);
	    				
	    }
	 

}
