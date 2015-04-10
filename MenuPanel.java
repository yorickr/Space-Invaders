package Spaceinvaders;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MenuPanel extends JPanel
{
	private Background bg;
	private BufferedImage logo;
	private Sounds sound;

	public MenuPanel(Sounds sound)
	{
		bg = new Background(new Point(0, 0));
		this.sound = sound;
		sound.stopBG();
		sound.backgroundTrack();
		try
		{
			logo = ImageIO.read(getClass().getResource("logo.png"));
		}
		catch (IOException e)
		{
		}
	}

	public int getHighscore()
	{
		File outputFile = new File(ResourceGet.resourceGet(getClass().getResource("scores.txt").getPath()));
		if (outputFile.isFile())
		{

			try
			{
				Scanner scan = new Scanner(outputFile);
				while (scan.hasNext())
				{
					String next = scan.next();
					return Integer.valueOf(next);
				}
				scan.close();

			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
		}
		return -1;
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bg.getBackground(), (int) bg.getPoint().getX(), (int) bg.getPoint().getY(), null);

		g2d.drawImage(logo, (getWidth() / 2 - logo.getWidth() / 2), 100, null);

		g2d.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
		g2d.setColor(Color.YELLOW);
		g2d.drawString("Highscore: " + getHighscore(), this.getWidth() / 2 - 75, this.getHeight() / 2);
		g2d.drawString("Press enter to start game,", this.getWidth() / 2 - 150, this.getHeight() - 50);
		g2d.drawString("or press q to quit.", this.getWidth() / 2 - 150, this.getHeight() - 32);

	}

}
