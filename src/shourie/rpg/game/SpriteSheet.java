package shourie.rpg.game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SpriteSheet {
	
	ArrayList<BufferedImage> tiles = new ArrayList<BufferedImage>();
	private int Width ;
	private int Height;
	private int TileWidth ;
	private int TileHeight;
	private int rows;
	private int cols;
	BufferedImage image;
	
	SpriteSheet(BufferedImage image,int rows,int cols)
	{
		Width = image.getWidth();
		Height= image.getHeight();
		
	    TileWidth = Width/cols;  
		TileHeight= Height/rows;  
		this.rows=rows;
		this.cols=cols;
		this.image=image;
		
		//System.out.println(+TileWidth);
		}
	
	
	  
	public ArrayList<BufferedImage> grabTileImage()
	{
		
		
		if (TileWidth * cols != Width || TileHeight * rows != Height) {
            throw new IllegalArgumentException("Tile is not an even " +
                    "multiple of pixels of WxCols or HxRows!");
        }
		
		for(int x=0; x<rows;x++)
		{
			for(int y=0;y<cols;y++)
			{
				BufferedImage i = image.getSubimage(TileWidth*y, TileHeight*x,TileWidth ,TileHeight);
				tiles.add(i);
			}
	     }
           return tiles;
	}
           
	     /*  int getWidth()
           {
        	   return this.TileWidth;
           }
	       
	       int getHeight()
	       {
	    	   return this.TileHeight;
	       }*/
   }
