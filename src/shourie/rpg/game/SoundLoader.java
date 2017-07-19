package shourie.rpg.game;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;


public class SoundLoader {
	private AudioInputStream audioInputStream;
	private Clip clip;
	private int set=0;
	
	SoundLoader(String path)
	{
		this.set =0;
		this.play(path);
	}
	SoundLoader()
	{
		this.set=1;
	}
	
	public void play(String path){
		  try {
		    audioInputStream=AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile());
		    playAudioStream(audioInputStream);
		  }
		 catch (  Exception e) {
			 System.out.println("Error while playing sound");
		    e.printStackTrace();
		  }
		}

	@SuppressWarnings("static-access")
	private void playAudioStream(AudioInputStream audioInputStream) throws LineUnavailableException, IOException 
	{
		
		clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
        if(set==0)
        clip.loop(clip.LOOP_CONTINUOUSLY);
		
	}
		 
     void stop()
     {
    	 clip.close();
    	 clip.flush();
    	try {
			this.audioInputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }
}
