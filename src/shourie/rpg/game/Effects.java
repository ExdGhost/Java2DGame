package shourie.rpg.game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Effects{
	protected int x;
	protected int y;
	protected ImageLoader loader;
	protected BufferedImage spriteSheet;
	SpriteSheet ss;
	protected int speed=6;
	protected ArrayList<BufferedImage> ETiles;
	protected Player player;
	int launch=0;
	protected Animation effect;
	protected SoundLoader effectSound;
	protected MapLoader map;
	protected Game game;
	protected String SoundPath="";
	
	public enum EState
	{
		LAUNCHED,
		STOP
	}
	
	protected EState tstate;
	
	Effects(MapLoader map,Game game)
	{
		this.map = map;
		this.game = game;
		loader = new ImageLoader();
		effectSound = new SoundLoader();
	}


	public void tick() {		   
		    map = game.getActiveMap();
		     effect.runAnimation();
	}

	public void render(Graphics2D g) {	
			effect.drawAnimation(g,x,y);			  					
	}
	
	public void launchEffect(int x,int y)
	{
		this.x = x;
		this.y = y;
		this.tstate = EState.LAUNCHED;
		effectSound.play(SoundPath);		
	}

	public void loadEImg(String path)
	{
		try {
			spriteSheet= loader.loadImage(path);
		} catch (IOException e) {

			e.printStackTrace();
		}
		ss=new SpriteSheet(spriteSheet,1,3);
		ETiles = ss.grabTileImage();		
	}
	
	public void loadAnimation()
	{
		effect = new Animation(speed,ETiles.get(0),ETiles.get(1),ETiles.get(2));		
	}
	
}
