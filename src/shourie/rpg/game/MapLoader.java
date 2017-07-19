package shourie.rpg.game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class MapLoader {

	private BufferedImage map;
	private ImageLoader loader;
	private File file;
   //  Graphics2D g;
	private int x=0;
	private int y=0;
	String soundpath;
	private int MapMatrix[][];
	
	enum MState {
		GENERAL,
		CAVE,
		TOWN,
		GAME_OVER
	}
	
	MState state;
	
	MapLoader(int x,int y)
 	{
		this.x = x;
		this.y = y;
		loader = new ImageLoader();
		
	}
	
	@SuppressWarnings("resource")
	public void loadMap(String path)
	{
		file = new File(path);
		if(!file.exists())
		{
            System.out.println(path);
            System.exit(0);
		}
		String str="";
		
		try {
			Scanner sc  = new Scanner(file);
			
		       if(sc.hasNextLine())
				str = sc.nextLine();
		       
		       try {
				map = loader.loadImage(str);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   
		    if(sc.hasNextLine())
			soundpath=sc.nextLine(); //get sound file path
		    
		    if(sc.hasNextLine())
				sc.nextLine();
		    MapMatrix = new int[20][20];
		    int row =0;
			while(sc.hasNextLine())
			{
				str = sc.nextLine();
				//System.out.println(str);
				for(int j=0;j<str.length();j++)
					MapMatrix[row][j]= Character.getNumericValue(str.charAt(j));
				row++;
				
			}
		       
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
	}
	
	int [][] getMapMatrix()
	{
		return this.MapMatrix;
	}
	
	public void render(Graphics2D g)
	{
		g.drawImage(map,x,y,null);
	}
}
 