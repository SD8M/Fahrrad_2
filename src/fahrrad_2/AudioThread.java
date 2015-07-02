package fahrrad_2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class AudioThread implements Runnable {

	@Override
	public void run() {
		
		try {
			Player p = new Player(new FileInputStream("res/Track 01.mp3"));
					p.play();
					Player b = new Player(new FileInputStream("res/rock_-_i_am_rock.mp3"));
					b.play();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
