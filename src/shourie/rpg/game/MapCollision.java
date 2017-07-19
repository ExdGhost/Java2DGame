package shourie.rpg.game;

import java.awt.Rectangle;
import java.util.ArrayList;

import shourie.rpg.game.MapLoader.MState;
import shourie.rpg.game.Player.LoadState;

public class MapCollision {
	
	int Width;
	int Height;
	int LastX;
	int LastY;
	final int MapTileW = 32;
	final int MaptileH = 32;
	Player P;
	private ArrayList<Rectangle> MapC;
	private ArrayList<Rectangle> Cave;
	private ArrayList<Rectangle> Town;
	
	int MapMatrix[][];
	MapCollision (Player p)
	{
		this.Width = p.tileWidth;
		this.Height = p.tileHeight;
		this.P = p; 
		MapC = new ArrayList<Rectangle>();
		Cave = new ArrayList<Rectangle>();
		Town = new ArrayList<Rectangle>();
			
	}
	
	void loadMapMatrix(int MapMatrix[][])
	{
		this.MapMatrix = MapMatrix;
		                             // i is index for tile Height -Y
		for(int i=0;i<20;i++)
		{                           // j is index for tile Width - X
			for(int j=0; j<20;j++)
			{
				
				if(MapMatrix[i][j]==0)
					MapC.add(new Rectangle(j*this.MapTileW,i*this.MaptileH,this.MapTileW,this.MaptileH));
				
				else if(MapMatrix[i][j]==2)
					Cave.add(new Rectangle(j*this.MapTileW,i*this.MaptileH,this.MapTileW,this.MaptileH)); 
				
				else if(MapMatrix[i][j]==3)
					Town.add(new Rectangle(j*this.MapTileW,i*this.MaptileH,this.MapTileW,this.MaptileH)); 
				
			}
		}
		
	}
      
	boolean checkCollison(MapLoader map)
	{
	
		for(Rectangle i : MapC)
		{
			if(P.getBounds().intersects(i.getBounds()))
		      return true;
		}
		
		if(map.state == MState.GENERAL)
		{
			for (Rectangle i : Cave)
			{
		      if(P.getBounds().intersects(i.getBounds()))
		     {
			  P.LastX= P.getX();
			  P.LastY= P.getY()+3;
		    
			  P.Lstate = LoadState.CAVE;
			   return false; //collision with cave
		
	    	}
		}
			for (Rectangle i : Town)
			{
				int Xadd=0;
		      if(P.getBounds().intersects(i.getBounds()))
		     {
		      if(P.getVelX() >=0)  
		    	  Xadd = -10;
		      else if(P.getVelX() < 0)
		    	  Xadd = 30;
		      
			  P.LastX= P.getX()+Xadd;
			  P.LastY= P.getY()+50;
		    
			  P.Lstate = LoadState.TOWN;
			   return false; //collision with town
		
	    	}
		}
	 }
		
		return false; // no collision
		
	
	}
}
