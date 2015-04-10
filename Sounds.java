package Spaceinvaders;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sounds
{
	private Clip mainclip = null;

	public Sounds()
	{

	}

	public void stopBG()
	{
		if (mainclip != null)
		{
			mainclip.stop();
		}

	}

	public void backgroundTrack()
	{
		try
		{
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("backgroundsound.wav"));
			mainclip = AudioSystem.getClip();
			mainclip.open(audioInputStream);
			mainclip.start();
		}
		catch (Exception ex)
		{
		}
	}

	public void explosion()
	{
		try
		{
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("explosion.wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		}
		catch (Exception ex)
		{

		}
	}

	public void playerFire()
	{
		try
		{
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("shoot.wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		}
		catch (Exception ex)
		{

		}
	}

	public void invaderDie()
	{
		try
		{
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("invaderkilled.wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		}
		catch (Exception ex)
		{

		}
	}

}
