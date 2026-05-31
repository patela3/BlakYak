import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

public class Sounds {

	private Clip clip;
	private String songName;

	public Sounds(String s) {
		songName = s;
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File(s).getAbsoluteFile());
			AudioFormat baseFormat = ais.getFormat();
			clip = AudioSystem.getClip();
			clip.open(ais);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playMusic() {
		try {
			File musicPath = new File(songName);
			if (musicPath.exists()) {
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
				clip = AudioSystem.getClip();
				clip.setFramePosition(0);
				clip.open(audioInput);
				clip.start();
			} else {
				JOptionPane.showMessageDialog(null, "Error");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void play() {
		if (clip == null)
			return;
		stop();
		clip.setFramePosition(0);
		clip.start();

	}

	public void stop() {
		if (clip.isRunning())
			clip.stop();
	}

	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public void close() {
		stop();
		clip.close();
	}

	public Clip getClip() {
		return clip;
	}

	public void setClip(Clip clip) {
		this.clip = clip;
	}

	public String getSongName() {
		return songName;
	}

	public void setSongName(String songName) {
		this.songName = songName;
	}
	
	
}
