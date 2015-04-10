package Spaceinvaders;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Bullet implements Entity{

	private Point2D position;
	private BufferedImage image;
	private int dir;
	private int imageX, imageY, imageXCount, imageYCount;
	private BufferedImage currentSprite;

	public Bullet(String filename, Point2D position, int dir)
	{
		try
		{
			image = ImageIO.read(new File(ResourceGet.resourceGet(getClass().getResource(filename).getPath())));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		imageX = image.getWidth()/4;
		imageY = image.getHeight();
		imageXCount = 0;
		imageYCount = 0;
		currentSprite = image.getSubimage(imageXCount, imageYCount, imageX, imageY);
		this.dir = dir;
		this.position = position;
	}
	
	public void draw(Graphics2D g)
	{
		currentSprite = image.getSubimage(imageXCount,imageYCount,imageX,imageY);
		AffineTransform oldtrans = g.getTransform();
		if(dir == 1)
		{
			g.rotate(Math.toRadians(180),position.getX(),position.getY());
		}
		g.drawImage(currentSprite, (int)position.getX(), (int)position.getY(), null);
		g.setTransform(oldtrans);
		
	}
	
	public void updateSprite()
	{
		imageXCount += imageX;
		if(imageXCount > 3*imageX)
		{
			imageXCount = 0;
		}
	}
	
	public Rectangle2D.Double getRect()
	{
		return new Rectangle2D.Double(position.getX(), position.getY(), currentSprite.getWidth(null), currentSprite.getHeight(null));
	}

	@Override
	public void fire()
	{
		System.out.println("FIRED BULLET" );
	}

	public void move()
	{
		move(dir);
	}
	
	@Override
	public void move(int direction)
	{
		switch(direction)
		{
			case 0:
				position = new Point((int)position.getX(), (int)position.getY() - 10);
				break;
			case 1:
				position = new Point((int)position.getX(), (int)position.getY() + 10);
				break;
		}
	}

	public int getDir()
	{
		return dir;
	}

	public void setDir(int dir)
	{
		this.dir = dir;
	}

	public Point2D getPosition()
	{
		return position;
	}

	public void setPosition(Point2D position)
	{
		this.position = position;
	}

	public BufferedImage getImage()
	{
		return currentSprite;
	}

}