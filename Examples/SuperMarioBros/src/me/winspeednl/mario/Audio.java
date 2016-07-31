package me.winspeednl.mario;

import me.winspeednl.libz.audio.Sound;

public class Audio {
	/*
	 * NOTE: Only .WAV is supported!
	 * Work around for .MP3 is not yet available!
	 */
	
	// Music
	public Sound ground = new Sound("/audio/music/ground.wav");
	public Sound groundHurry = new Sound("/audio/music/ground-hurry.wav");
	
	//SFX
	public Sound die = new Sound("/audio/sfx/die.wav");
	public Sound jumpSmall = new Sound("/audio/sfx/jump-small.wav");
	public Sound jumpSuper = new Sound("/audio/sfx/jump-super.wav");
	public Sound powerup = new Sound("/audio/sfx/powerup.wav");
	public Sound powerupApears = new Sound("/audio/sfx/powerup-appears.wav");
	public Sound coin = new Sound("/audio/sfx/coin.wav");
	public Sound blockBreak = new Sound("/audio/sfx/breakBlock.wav");
	public Sound powerdown = new Sound("/audio/sfx/powerdown.wav");
	public Sound pipe = new Sound("/audio/sfx/pipe.wav");
	public Sound stomp = new Sound("/audio/sfx/stomp.wav");
	public Sound life1up = new Sound("/audio/sfx/1-up.wav");
}
