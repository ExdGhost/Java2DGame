package shourie.rpg.game;

import shourie.rpg.game.DarkLord.BState;
import shourie.rpg.game.MapLoader.MState;
import shourie.rpg.game.Player.LiveState;
import shourie.rpg.game.Player.LoadState;
import shourie.rpg.game.Player.Pstate;

public class ResourceLoader {
	
	private Game game;
	private Player player;
	private MapLoader mapList[];
	private MapCollision collision1;
	private MapCollision collision2;
	private MapCollision collision3;
	private MapLoader Curmap;
	private SoundLoader loadSound;
	private DarkLord boss;
	ResourceLoader(Game game,Player player,MapLoader mapList[],SoundLoader s,DarkLord boss) 
	{
		this.game = game;
		this.player = player;
		this.mapList = mapList;
		this.loadSound = s;
		this.boss = boss;
		this.Init();
	}

	void Init()
	{
		collision1 = new MapCollision(player);
		collision2 = new MapCollision(player);
		collision3 = new MapCollision(player);
		
		collision1.loadMapMatrix(mapList[0].getMapMatrix());
		collision2.loadMapMatrix(mapList[1].getMapMatrix());
		collision3.loadMapMatrix(mapList[2].getMapMatrix());
		
		if(player.Lstate == LoadState.WORLD)
		{
			player.setMapCollision(collision1, mapList[0]);
			player.Lstate = LoadState.NOTHING;
			Curmap = mapList[0];
		}
		
	}
	
	void chkResource()
	{
	   if(player.PLstate == LiveState.ALIVE)
	   {
		if(player.Lstate == LoadState.WORLD)
		{
			player.setMapCollision(collision1, mapList[0]);
			player.Lstate = LoadState.NOTHING;
			player.setX(player.LastX);
			player.setY(player.LastY);
			game.SetActiveMap(0,mapList[0].state);
			Curmap = mapList[0];
			player.state = Pstate.IDLE;
			loadSound.stop();
			loadSound.play(Curmap.soundpath);
		}
		
		if(player.Lstate == LoadState.CAVE)
		{
			player.setMapCollision(collision2, mapList[1]);
			player.Lstate = LoadState.NOTHING;
			player.setX(60);
			player.setY(500);
			game.SetActiveMap(1,mapList[1].state);
			Curmap = mapList[1];
			loadSound.stop();
			loadSound.play(Curmap.soundpath);
			player.state = Pstate.BATTLE;
		}
		
		if(player.Lstate == LoadState.TOWN)
		{
			player.setMapCollision(collision3, mapList[2]);
			player.Lstate = LoadState.NOTHING;
			player.setX(50);
			player.setY(375);
			game.SetActiveMap(2,mapList[2].state);
			Curmap = mapList[2];
			loadSound.stop();
			if(boss.State == BState.ALIVE)
				loadSound.play("resources/sounds/Town2.wav");
			else
			    loadSound.play(Curmap.soundpath);
		}
		
		if((Curmap.state==MState.CAVE || Curmap.state==MState.TOWN) && player.getX() < 0)
		{
			player.Lstate = LoadState.WORLD;
			
		}
		
		
	}
	   else if(player.PLstate == LiveState.DEAD && player.state == Pstate.BATTLE)
	   {
		   player.setMapCollision(collision3, mapList[3]);
			player.Lstate = LoadState.NOTHING;
			player.state = Pstate.IDLE;
			player.setX(640/2-30);
			player.setY(640/2+50);
			game.SetActiveMap(3,mapList[3].state);
			Curmap = mapList[3];
			loadSound.stop();
			loadSound.play(Curmap.soundpath);
			
		   
	   }
  }
}
