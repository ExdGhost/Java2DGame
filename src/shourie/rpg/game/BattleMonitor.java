package shourie.rpg.game;

import shourie.rpg.game.DarkLord.BState;
import shourie.rpg.game.DarkLord.BatkState;
import shourie.rpg.game.Player.LiveState;
import shourie.rpg.game.Player.Pstate;



public class BattleMonitor {
	
	//private Game game;
	private Player player;
	private DarkLord boss;
	//private MapLoader map;
	boolean turn=true;
	int playerAtk;
	long start;
	private long end;
	boolean playerCanAttack = true;
	SoundLoader sloader;

	 BattleMonitor(Player player,DarkLord boss)
	 {
		 this.player = player;	
		 this.boss = boss;
		 sloader = new SoundLoader();
		
	 }
	 
	 void tick()
	 {
		 
		 if(player.getHP() <=0)
			 player.PLstate = LiveState.DEAD;
		
		 if(boss.getHP() <=0)
		 {
			 boss.State = BState.DEAD;
			 player.state = Pstate.IDLE;
			 sloader.play("resources/sounds/victory.wav");
		 }
		 
	       if(turn)
		 {  
	    	 boss.bossAtk  = BatkState.IDLE;
		     player.attack = this.playerAtk;
		     
		 }
		 else 
		 {  end = System.currentTimeMillis();
		 
		   if((end - start) > 2000)
		   {
			 boss.bossAtk = BatkState.ATTACK;
			 
			 if(boss.getHP()>1000)
			 {
				 int x = this.randomWithRange(0, 2);
				 
				 if(x==0)
				   boss.attack=1;
				 else if(x==1)
				   boss.attack=2;
				 else if(x==2)
				   boss.attack=3;
			 }
			
		   else
			   boss.attack = 3;
			 
			 this.turn = true;
			 this.playerCanAttack = true;
		   }

		 }
		
	 }
	 

	  int randomWithRange(int min, int max)
	  {
	   int range = (max - min) + 1;     
	   return (int)(Math.random() * range) + min;
	  }
	 
}
