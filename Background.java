package Spaceinvaders;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Background
{

	private BufferedImage background;
	private Point2D point;
	private double scale;

	public Background(Point2D point)
	{
		//"src/Spaceinvaders/background.png"
		try
		{
			background = ImageIO.read(getClass().getResource("background.png"));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		this.point = point;
	}

	public BufferedImage getBackground()
	{
		return background;
	}

	public void setBackground(BufferedImage background)
	{
		this.background = background;
	}

	public Point2D getPoint()
	{
		return point;
	}

	public void setPoint(Point2D point)
	{
		this.point = point;
	}

	public double getScale()
	{
		return scale;
	}

	public void setScale(double scale)
	{
		this.scale = scale;
	}

}
