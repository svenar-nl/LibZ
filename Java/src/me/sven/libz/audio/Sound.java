package me.sven.libz.audio;

import java.io.BufferedInputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class Sound {
	private AudioInputStream audioInputStream;
	private Clip clip;
	private String path;
	
	public Sound(String path) {
		this.path = path;
		try {
			BufferedInputStream myStream = new BufferedInputStream(getClass().getResourceAsStream(path));
			audioInputStream = AudioSystem.getAudioInputStream(myStream);
			
			DataLine.Info info = new DataLine.Info(Clip.class, audioInputStream.getFormat());
			clip = (Clip)AudioSystem.getLine(info);
			clip.open(audioInputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void play() {
		clip.setFramePosition(0);
		clip.start();
	}
	
	public void resume() {
		clip.start();
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void loop(int count) {
		clip.loop(count);
	}
	
	public void stop() {
		clip.stop();
	}
	
	public boolean isPlaying() {
		return clip.isRunning();
	}
	
	public String getPath() {
		return path;
	}
}