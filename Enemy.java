package Spaceinvaders;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Enemy implements Entity
{
	private BufferedImage currentSprite;
	private BufferedImage i1, i2;
	private Point2D position;
	private BufferedImage image;
	private SIGame main;
	private boolean reloading;
	private int n;
	public final int score;
	private int speedX;
	private boolean[] spedup;

	public Enemy(URL filename, Point2D position, SIGame main, int score)
	{
		try
		{
			image = ImageIO.read(filename);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		i1 = image.getSubimage(0, 0, image.getWidth()/2, image.getHeight());
		i2 = image.getSubimage(image.getWidth()/2, 0, image.getWidth()/2, image.getHeight());
		currentSprite = i1;
		this.position = position;
		this.main = main;
		reloading = false;
		n = 0;
		speedX = 10;
		spedup = new boolean[6];
		this.score = score;
		
	}

	public void draw(Graphics2D g)
	{
		g.drawImage(currentSprite, (int)position.getX(), (int)position.getY(),  null);
	}
	
	public void switchSprite()
	{
		if(currentSprite == i1)
		{
			currentSprite = i2;
		}
		else
		{
			currentSprite = i1;
		}
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

	@Override
	public void move(int direction)
	{
		switch (direction) {
			case 0:
				if (position.getX() >= 10)
				{
					position = new Point((int) position.getX() - speedX, (int) position.getY());
				}
				break;
			case 1:
				if (position.getX() < main.getWidth() - 10 - currentSprite.getWidth(null))
				{
					position = new Point((int) position.getX() + speedX, (int) position.getY());
				}
				break;
		}
		switchSprite();
	}
	
	public void speedup()
	{
		speedX += 10;
	}
	
	public void movedown()
	{
		this.position = new Point((int)position.getX(), (int)position.getY() + 40);
	}

	@Override
	public void fire()
	{
		if (!reloading)
		{
			Bullet bullet = new Bullet("bullet.png", new Point((int) position.getX() + currentSprite.getWidth(null) / 2, (int) position.getY() + currentSprite.getHeight(null) / 2), 1);
			bullet.setPosition(new Point((int)bullet.getPosition().getX() - bullet.getImage().getWidth(null)/2, (int)bullet.getPosition().getY() - bullet.getImage().getHeight(null)));
			main.addBullet(bullet);
			reloading = true;
		}
		
	}
	
	public void tick()
	{
		n++;
		if (n > 180)
		{
			reloading = false;
			n = 0;
		}
	}

	public boolean[] getSpedup()
	{
		return spedup;
	}

	public void setSpedup(boolean[] spedup)
	{
		this.spedup = spedup;
	}

	public BufferedImage getImage()
	{
		return currentSprite;
	}

	public void setImage(BufferedImage image)
	{
		this.image = currentSprite;
	}

}
