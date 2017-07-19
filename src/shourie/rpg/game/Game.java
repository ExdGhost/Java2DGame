package shourie.rpg.game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import shourie.rpg.game.DarkLord.BState;
import shourie.rpg.game.DarkLord.BatkState;
import shourie.rpg.game.MapLoader.MState;
import shourie.rpg.game.NPC.npcPos;
import shourie.rpg.game.Player.LiveState;
import shourie.rpg.game.Player.LoadState;
import shourie.rpg.game.Player.Pstate;

@SuppressWarnings("unused")
public class Game extends Canvas implements Runnable{
	private BufferedImage image1 = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB); 
		//private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData(); 
	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 315;
	public static final int HEIGHT = WIDTH/9*9;
	public static final int SCALE =2;
	public static final String NAME="Game";
	public static final int adjust = -3;
	private boolean running = false;
	private int tickCount = 0; 
	private JFrame frame;
	private Player player;
	private MapLoader map;
	private MapLoader mapList[];
	private ResourceLoader rL; 
	SoundLoader sLoader;
	private DarkLord boss;
	private BattleMonitor bMonitor;
	private AttThunder thunder;
	private AttIce ice;
	private AttFire fire;
	private AttHoly holy;
	private AttDark dark;
	private  NPCollision npcCollision;
	private npcMain shiori;
	private GiftEffect gift;
	boolean enableTrigger = false;
	private boolean control = true;
	private Objects tree1,tree2;
	private npcGeneric ryu,kotaro,isuka,leeila,kyoko,kyouske;
	
	Game()
	{ 
		setMinimumSize(new Dimension(WIDTH*SCALE+adjust,HEIGHT*SCALE+adjust));
		setMaximumSize(new Dimension(WIDTH*SCALE+adjust,HEIGHT*SCALE+adjust));
		setPreferredSize(new Dimension(WIDTH*SCALE+adjust,HEIGHT*SCALE+adjust));
		
		frame = new JFrame(NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(this,BorderLayout.CENTER);
		frame.pack();
		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void init() 
	{
		requestFocus();
		addKeyListener(new KeyInput(this));
		
		mapList = new MapLoader[4];
		mapList[0] = new MapLoader(0,0);
		mapList[1] = new MapLoader(0,0);
		mapList[2] = new MapLoader(0,0);
		mapList[3] = new MapLoader(0,0);
		mapList[0].loadMap("resources/Maps/map1.ini");
		mapList[1].loadMap("resources/Maps/cave.ini");
		mapList[2].loadMap("resources/Maps/town.ini");
		mapList[3].loadMap("resources/Maps/GameOver.ini");
		mapList[0].state = MState.GENERAL;
		mapList[1].state = MState.CAVE;
		mapList[2].state = MState.TOWN;	
		mapList[3].state = MState.GAME_OVER;	
		this.map = mapList[0];
		
		 sLoader = new SoundLoader("resources/sounds/world.wav");
		 player = new Player(0,640/2,map);
		 player.state = Pstate.IDLE;
		 player.PLstate = LiveState.ALIVE;
		 player.Lstate = LoadState.WORLD;
		 
		 thunder = new AttThunder(player);
		 ice = new AttIce(player);
		 fire = new AttFire(player);
		  
		 boss = new DarkLord(450,500,this,player,this.map,thunder,ice,fire);
		 boss.State = BState.ALIVE;
		 bMonitor = new BattleMonitor(this.player,this.boss);
		 
		 holy = new AttHoly(boss);
		 dark= new AttDark(boss);
		 
		 player.loadAttack(holy,dark);
		 rL = new ResourceLoader(this,player,mapList,sLoader,boss);
		 player.takeResource(rL);
		 player.setBoss(boss);
		
		 //adding gift effect
		 gift  = new GiftEffect(player,map,this);		 
		 npcCollision = new NPCollision(this,map,player,gift);
		
/*-----------------adding npc's-----------------------------------------------------------------*/	
		shiori = new npcMain(200,290,map,this,player,boss);	
		shiori.position = npcPos.UP;
		ryu = new npcGeneric(32,250,map,this,player,boss,"CITIZEN : RYU","/NPC/npcRyu.png","/NPC/Pryu.png");
		ryu.position = npcPos.LEFT;	
		isuka = new npcGeneric(10,544,map,this,player,boss,"CITIZEN : ISUKA","/NPC/Isuka.png","/NPC/Pisuka.png");
		isuka.position = npcPos.DOWN;
		kyoko = new npcGeneric(256,15,map,this,player,boss,"CITIZEN : KYOKO","/NPC/kyoko.png","/NPC/Pkyoko.png");
		kyoko.position = npcPos.RIGHT;
		kotaro = new npcGeneric(416,384,map,this,player,boss,"CITIZEN : KOTARO","/NPC/kotaro.png","/NPC/Pryu.png");
		kotaro.position = npcPos.LEFT;
		leeila = new npcGeneric(576,500,map,this,player,boss,"CITIZEN : LEEILA","/NPC/leeila.png","/NPC/Pleeila.png");
		leeila.position = npcPos.UP;
		kyouske = new npcGeneric(576,192,map,this,player,boss,"CITIZEN : KYOUSKE","/NPC/kyouske.png","/NPC/Pkyoske.png");
		kyouske.position = npcPos.DOWN;
		
		npcCollision.loadNPC(shiori);
		npcCollision.loadGenNPC(ryu);
		npcCollision.loadGenNPC(isuka);
		npcCollision.loadGenNPC(kyoko);
		npcCollision.loadGenNPC(kotaro);
		npcCollision.loadGenNPC(leeila);
		npcCollision.loadGenNPC(kyouske);
/*------------------layer-2 objects---------------------------------------------------------------*/		
		tree1 = new Objects(64,512,"/Generic/tree1.png");
		tree2= new Objects(224,256,"/Generic/tree2.png");
		
		//boss.State = BState.DEAD;
	}
	public synchronized void start() {
		if(running)
			return;
		
		running = true;
		new Thread(this).start();
		
	}
    
    public synchronized void stop() {
    	if(!running)
    		return;
	
    	running = false;
	
	}

    public void run() {
    	long lastTime = System.nanoTime();
    	double nsPerTick = 1000000000D/60D;
    	
    	int ticks=0;
    	int frames = 0;
    	long lastTimer = System.currentTimeMillis();
    	double delta =0;
    	
	    while(running)
	    {
	    	long now = System.nanoTime();
	    	delta+= (now - lastTime)/nsPerTick;
	    	lastTime=now;
	    	boolean shouldRender=true;
	    	
	    	while(delta>=1)
	    	{
	    	  ticks++;
	          tick();
	          delta-=1;
	          shouldRender=true;
	    	}
	    	
	    	try{
	    		Thread.sleep(2);
	    		
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    	if(shouldRender)
	    	{
	    		frames++;
	    		try {
					render();
				} catch (IOException | InterruptedException e) {
					
					e.printStackTrace();
				}
	    	}
	    	
	    	if(System.currentTimeMillis()-lastTimer >=1000)
	    	{
	    		lastTimer+=1000;
	    		//System.out.println(ticks+" ticks, "+frames+" frames");
	    		frames=0;
	    		ticks=0;
	    	}
	    
	    }
	    
   }
		  
    public void tick() // updates the game variable and logic
    {   	 
    	  
    	//if(player.PLstate == LiveState.ALIVE)
    	  //   player.tick();
    	
    	  shiori.tick();
    	  ryu.tick();
    	  isuka.tick();
    	  kyoko.tick();
    	  kotaro.tick();
    	  leeila.tick();
    	  kyouske.tick();
    	  
    	if(map.state == MState.TOWN)
    	{ 
    		npcCollision.tick();
    		gift.tick();
    	}
    	
    	  
    	if(player.PLstate == LiveState.ALIVE && boss.State == BState.ALIVE)
    	{
    	  player.tick();   	  
    	   //shiori.tick();
    	
    	if(map.state == MState.CAVE)
    	    bMonitor.tick();
    	
    	boss.tick();
    	holy.tick();
    	dark.tick();
    	ice.tick();
    	fire.tick();
    	thunder.tick();
    	}
    	else
    		player.tick();
    	
    	if(boss.State == BState.DEAD && control)
    	{
    		shiori.SetSindex(0);
    		control = false;
    	}
    }
	
    public void render() throws IOException//prints out the game logic
, InterruptedException
    {
    	BufferStrategy bs = getBufferStrategy(); 
    	if (bs == null)
    	{ createBufferStrategy(3);
    	    return; 
    	    } 
    	Graphics2D g = (Graphics2D) bs.getDrawGraphics(); 
    	
    	map.render(g);
    	player.render(g);   	
    	if(map.state == MState.TOWN)
    	{ tree1.render(g);
    	  tree2.render(g);
    	}
    	isuka.render(g);
    	leeila.render(g);
        kotaro.render(g);
    	shiori.render(g);
    	kyouske.render(g);
    	ryu.render(g);
    	kyoko.render(g);
   	
    	if(player.PLstate == LiveState.ALIVE && boss.State == BState.ALIVE)
    	{    
    	gift.render(g);
    	boss.render(g);
    	holy.render(g);
    	dark.render(g);
    	ice.render(g);
    	fire.render(g);
    	thunder.render(g);
    	}
    	else
    	{ 
        	if(map.state == MState.CAVE && player.PLstate == LiveState.ALIVE)
    		{
    			String fontString = "MS Gothic";
    			Font font = new Font(fontString, Font.BOLD, 24);
    			g.setFont(font);
    			g.setColor(Color.ORANGE);
    			g.drawString("You have defeated The Dark Lord.", 70, 30);
    			g.drawString("Return back to town and report your triumph.", 70, 50);
    		}
    	}
    	g.dispose(); 
    	bs.show();
    }
    
    public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		int velocity = 1;
		if(map.state == MState.CAVE && boss.State == BState.ALIVE)
	    {
			if(key == KeyEvent.VK_SPACE && bMonitor.playerCanAttack)
			{
				player.count++;
				 bMonitor.playerAtk = 1;
				 bMonitor.start = System.currentTimeMillis();
				 bMonitor.playerCanAttack = false;
				
			}
			
			if(key == KeyEvent.VK_X && bMonitor.playerCanAttack)
			{
				player.count++;
				 bMonitor.playerAtk = 2;
				 bMonitor.start = System.currentTimeMillis();
				 bMonitor.playerCanAttack = false;
											
			}
		
	  }//end if
		else if(map.state == MState.CAVE && boss.State == BState.DEAD)
		{
			player.state = Pstate.WALKING;
			if (key == KeyEvent.VK_RIGHT)
			{
				
				player.setVelX(velocity);
			}
			else if (key == KeyEvent.VK_LEFT)
			{
				player.setVelX(-velocity);	
			}
			else if (key == KeyEvent.VK_UP)
			{
				player.setVelY(-velocity);	
			}
			else if (key == KeyEvent.VK_DOWN)
			{
				player.setVelY(velocity);	
			}
			
		}//end else if 1
		
		else
		{
			player.state = Pstate.WALKING;
			if (key == KeyEvent.VK_RIGHT)
			{
				
				player.setVelX(velocity);
			}
			else if (key == KeyEvent.VK_LEFT)
			{
				player.setVelX(-velocity);	
			}
			else if (key == KeyEvent.VK_UP)
			{
				player.setVelY(-velocity);	
			}
			else if (key == KeyEvent.VK_DOWN)
			{
				player.setVelY(velocity);	
			}
		}
	}
    
	public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();
		int velocity = 0;
		//if(map.state != MState.CAVE || boss.State == BState.DEAD);
	    	//player.state = Pstate.IDLE;
		
		if (key == KeyEvent.VK_RIGHT)
		{

			player.setVelX(velocity);
		}
		else if (key == KeyEvent.VK_LEFT)
		{
			player.setVelX(velocity);	
		}
		else if (key == KeyEvent.VK_UP)
		{
			player.setVelY(velocity);	
		}
		else if (key == KeyEvent.VK_DOWN)
		{
			player.setVelY(velocity);	
		}
	
		if (map.state == MState.CAVE)
		{
			if(key == KeyEvent.VK_SPACE )
			{
				bMonitor.playerAtk = 0;
				bMonitor.turn = false;
				player.count = 0;
				
			}
			
			if(key == KeyEvent.VK_X )
			{
				bMonitor.playerAtk = 0;
				bMonitor.turn = false;
				player.count = 0;
				
			}
		}
		
		if(map.state == MState.TOWN)
		{
		  if (key == KeyEvent.VK_T)
		  {
			  NPC.trigger = true;
	          ryu.npcGenTrigger =true;
	          isuka.npcGenTrigger = true;
	          kyoko.npcGenTrigger = true;
	          kotaro.npcGenTrigger = true;
	          leeila.npcGenTrigger = true;
	          kyouske.npcGenTrigger = true;
		  }
		  if (key == KeyEvent.VK_F)
		  {   if(this.enableTrigger)
			   NPC.trigger2 = true;
		  }
		  if (key == KeyEvent.VK_Q)
		  {   if(this.enableTrigger)
			    this.quit();
		  }
		}
	}
	
	public void quit()
	{
		try {
			Desktop.getDesktop().open(new File("resources/sounds/Brave Shine.mp3"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.exit(0);
	}
 
	
	public void SetActiveMap(int index,MState mstate)
	{
		this.map = this.mapList[index];
		map.state = mstate;
	}
	public MapLoader getActiveMap()
	{
		return this.map;
	}
	public static void main(String args[]) 
	{
		Game game = new Game();
		game.init();
		game.start();
	}
	
}
