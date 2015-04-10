package Spaceinvaders;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Player implements Entity
{

	private Point2D position;
	private BufferedImage image;
	private SIGame main;
	private boolean fired, canfire = true;
	private int n;
	private final int MOVESPEED = 5;
	private boolean moveLeft, moveRight;
	private int speed;
	private int lives = 3;

	public Player(String filename, Point2D position, SIGame main)
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
		this.main = main;
		fired = false;
		n = 0;
		moveLeft = false;
		moveRight = false;
		speed = 0;
	}

	public void draw(Graphics2D g)
	{
		g.drawImage(image, (int) position.getX(), (int) position.getY(), null);
	}

	public Rectangle2D.Double getRect()
	{
		return new Rectangle2D.Double(position.getX(), position.getY(), image.getWidth(null), image.getHeight(null));
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
		return image;
	}

	public void tick()
	{
		if (position.getX() + speed <= main.getWidth() - 10 - image.getWidth(null) && position.getX() + speed >= 10)
		{
			position = new Point((int) position.getX() + speed, (int) position.getY());
		}
		n++;
		if (n > 30 && !fired)
		{
			canfire = true;
		}
	}

	@Override
	public void fire()
	{
		if (canfire)
		{
			Bullet bullet = new Bullet("bullet.png", new Point((int) position.getX() + image.getWidth(null) / 2, (int) position.getY() + image.getHeight(null) / 2), 0);
			bullet.setPosition(new Point((int) bullet.getPosition().getX() - bullet.getImage().getWidth(null) / 2, (int) bullet.getPosition().getY() - bullet.getImage().getHeight(null)));
			main.addBullet(bullet);
			main.getSound().playerFire();
			fired = true;
			canfire = false;
			n = 0;
		}

	}

	public void stop()
	{
		if (moveRight == false && moveLeft == false)
		{
			speed = 0;
		}
		if (moveRight == false && moveLeft == true)
		{
			move(0);
		}

		if (moveRight == true && moveLeft == false)
		{
			move(1);
		}

	}

	@Override
	public void move(int direction)
	{
		switch (direction)
		{
			case 0:
				speed = -MOVESPEED;
				break;
			case 1:
				speed = MOVESPEED;
				break;
		}
	}

	public boolean getMoveLeft()
	{
		return moveLeft;
	}

	public void setMoveLeft(boolean moveLeft)
	{
		this.moveLeft = moveLeft;
	}

	public boolean getMoveRight()
	{
		return moveRight;
	}

	public void setMoveRight(boolean moveRight)
	{
		this.moveRight = moveRight;
	}

	public int getSpeed()
	{
		return speed;
	}

	public void setSpeed(int speed)
	{
		this.speed = speed;
	}

	public boolean getFired()
	{
		return fired;
	}

	public void setFired(boolean fired)
	{
		this.fired = fired;
	}

	public int getLives()
	{
		return lives;
	}

	public void setLives(int lives)
	{
		this.lives = lives;
	}

	public boolean isCanfire()
	{
		return canfire;
	}

	public void setCanfire(boolean canfire)
	{
		this.canfire = canfire;
	}

}
