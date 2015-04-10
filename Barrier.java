package Spaceinvaders;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Barrier
{
	private BufferedImage image,currentSprite;
	private Point2D position;
	private int hp;
	private int imagePosX, imagePosY;

	public Barrier(String filename, Point2D position)
	{
		try
		{
			image = ImageIO.read(getClass().getResource(filename));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		this.position = position;
		hp = 3;
		imagePosX = 0;
		imagePosY = 0;
		currentSprite = image.getSubimage(imagePosX, imagePosY, image.getWidth()/4, image.getHeight());
		
	}

	public void switchImage()
	{
		imagePosX += image.getWidth()/4;
		hp--;
		if(imagePosX >= image.getWidth()/4 * 4)
		{
			return;
		}
		currentSprite = image.getSubimage(imagePosX, imagePosY, image.getWidth()/4, image.getHeight());
	}
	
	public void draw(Graphics2D g)
	{
		g.drawImage(currentSprite, (int)position.getX(), (int)position.getY(),  null);
	}

	public Rectangle2D.Double getRect()
	{
		return new Rectangle2D.Double(position.getX(), position.getY(), currentSprite.getWidth(null), currentSprite.getHeight(null));
	}
	
	public Point2D getPosition()
	{
		return position;
	}

	public void setPosition(Point2D position)
	{
		this.position = position;
	}

	public int getHp()
	{
		return hp;
	}

	public void setHp(int hp)
	{
		this.hp = hp;
	}

}
