package shourie.rpg.game;

import java.util.ArrayList;

import shourie.rpg.game.MapLoader.MState;

public class NPCollision {
	
	private MapLoader map;
	private Player player;
	private Game game;
	private int set = 0;
	private int PlastX;
	private int PlastY;
	//private int NpclastX;
	//private int NpclastY;
	private ArrayList<NPC> npc;
	private npcMain shiori;
	private Effects effect;
	private npcGeneric np;

	NPCollision(Game game,MapLoader map,Player player,Effects effect)
	{
		this.map = map;
		this.player = player;
		this.game = game;
		this.effect = effect;
		npc = new ArrayList<NPC>();
	}
	
	void loadNPC(NPC npc)
	{
		this.shiori = (npcMain)npc;
		this.npc.add(npc);
	}
	
	void loadGenNPC(NPC npc)
	{
		this.npc.add(npc);
	}
	
	
	
	void tick()
	{
	   
		for(NPC i : npc)
		{
			if( (Math.abs(i.getX()- player.getX()) + Math.abs(i.getY()-player.getY()-20)) < 60 )
			{
				i.NoCollision = false;
			}
			else
			{
				i.NoCollision = true;
			}
			
			if(i.getBounds().intersects(player.getBounds()))
			{				
				i.SetX(i.getLastX());
				i.SetY(i.getLastY());
				i.startTime();
				
				if(i.getProperty().equals("Generic"))
				{
					np = (npcGeneric)i;
					player.setX(np.PLx);
					player.setY(np.PLy);
					
				}
				else
				{
				  player.setX(this.PlastX);
				  player.setY(this.PlastY);
				}
			}
			else
			{				
				this.PlastX = player.getX();
				this.PlastY = player.getY();
			}
		
		}
		  if(set == 0)
		  {
		   this.map = game.getActiveMap();
		
		  if(map.state == MState.TOWN && NPC.trigger2 && shiori.getSindex() >= 4)
			{
			  player.gift = 1;
			  player.sheild = 5000;
			  set = 1;
			  effect.launchEffect(player.getX(),player.getY());
			}
		}
		
	}//end tick
	
}//end class
