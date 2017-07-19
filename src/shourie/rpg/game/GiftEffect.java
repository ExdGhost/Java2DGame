package shourie.rpg.game;

import java.awt.Graphics2D;

import shourie.rpg.game.Effects.EState;

public class GiftEffect extends Effects{

	private Player player;
	
	GiftEffect(Player player,MapLoader map, Game game) {
		super(map, game);
		this.player = player;
		this.loadEImg("/Effects/Gift.png");
		this.loadAnimation();	
		this.SoundPath="resources/sounds/Gift.wav";
	}
	
	public void tick()
	{
		this.x = player.getX()-20;
		this.y = player.getY()-10;
		super.tick();
		
		if(player.getX()<2)
		{
			this.tstate = EState.STOP;
		}
	}
	
	public void render(Graphics2D g)
	{
		if(this.tstate == EState.LAUNCHED)
		{	
		    super.render(g);
		}
	}


}
