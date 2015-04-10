package Spaceinvaders;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SIGame extends JPanel implements ActionListener
{

	private Background bg;
	private ArrayList<Enemy> enemies;
	private ArrayList<Bullet> bullets;
	private ArrayList<Barrier> barriers;
	private Player player;
	private Spaceinvaders si;
	private Timer t;
	private Sounds sound;
	private int totalScore;
	private int counter;
	private int moveCount;
	private int enemyDir;
	private int moveDownCount;
	private int moveDownMargin;
	private URL[] sURLs = new URL[3];
	private int bulletSpriteCount;
	private int randomDomain;
	private int initialEnemies;

	public SIGame(Spaceinvaders si, Sounds s)
	{
		bg = new Background(new Point(0, 0));
		enemies = new ArrayList<Enemy>();
		bullets = new ArrayList<Bullet>();
		barriers = new ArrayList<Barrier>();
		player = new Player("sprite.png", new Point(si.getWidth() / 2 - 35, si.getHeight() - 100), this);
		totalScore = 0;
		initialEnemies = 42;
		this.si = si;
		enemyDir = 1;
		moveCount = 0;
		counter = 0;
		moveDownCount = 0;
		moveDownMargin = 1;
		bulletSpriteCount = 0;
		randomDomain = 5000;
		sound = s;
		sURLs[0] = getClass().getResource("enemy1.png");
		sURLs[1] = getClass().getResource("enemy2.png");
		sURLs[2] = getClass().getResource("enemy3.png");
		initBarriers();
		initEnemies();

		sound.stopBG();
		sound.backgroundTrack();
		t = new Timer(1000 / 60, this);
		t.start();
	}

	public void addBullet(Bullet bullet)
	{
		bullets.add(bullet);
	}

	public void initBarriers()
	{
		int xval = 100;
		for (int i = 0; i < 4; i++)
		{
			barriers.add(new Barrier("barrier1.png", new Point(xval, 430)));
			xval += 180;
		}
	}

	public void initEnemies()
	{
		try
		{
			int x = 100;
			int y = 20;
			int count = 0;
			int tempCount = 1;
			//initialenemies
			for (int i = 0; i < initialEnemies ; i++)
			{
				int toAddX = 35 + ImageIO.read(sURLs[count]).getWidth() / 2;
				if (x + toAddX < si.getWidth() - 100)
				{
					enemies.add(createEnemy(count, new Point(x, y), this));
					x += toAddX;
				}
				else
				{
					y += 10 + ImageIO.read(sURLs[count]).getHeight();
					x = 100;
					i--;
					tempCount++;
					if (tempCount % 3 == 0)
					{
						count++;
						tempCount = 1;
					}
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public Enemy createEnemy(int count, Point point, SIGame game)
	{
		switch (count)
		{
			case 0:
				return (Enemy) new Enemy1(sURLs[count], point, game, 40);
			case 1:
				return (Enemy) new Enemy2(sURLs[count], point, game, 20);
			case 2:
				return (Enemy) new Enemy3(sURLs[count], point, game, 10);
			default:
				return new Enemy(null, null, null, 0);
		}
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bg.getBackground(), (int) bg.getPoint().getX(), (int) bg.getPoint().getY(), null);
		for (Enemy enemy : enemies)
		{
			enemy.draw(g2d);
		}

		player.draw(g2d);
		for (Bullet bullet : bullets)
		{
			bullet.draw(g2d);
		}
		for (Barrier barrier : barriers)
		{
			barrier.draw(g2d);
		}
		g2d.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
		g2d.setColor(Color.YELLOW);
		g2d.drawString("Score: " + totalScore, 10, 20);
		g2d.drawString("Lives: " + player.getLives(), getWidth() - 100, 20);
		g2d.drawString("Enemies: " + enemies.size() + "/" + (initialEnemies), getWidth() / 2 - 50, 20);

	}

	public void garbagecollection()
	{
		Iterator<Bullet> it = bullets.iterator();
		while (it.hasNext())
		{
			Bullet bul = it.next();
			if (bul.getPosition().getY() < 0 && bul.getDir() == 0)
			{
				it.remove();
				player.setFired(false);
			}
			if (bul.getPosition().getY() > getHeight() && bul.getDir() == 1)
			{
				it.remove();
			}
		}
	}

	public void collisionCheck()
	{
		Iterator<Bullet> it = bullets.iterator();
		while (it.hasNext())
		{
			Bullet bullet = it.next();
			Iterator<Enemy> it2 = enemies.iterator();
			while (it2.hasNext())
			{
				Enemy enemy = it2.next();
				if (enemy.getRect().intersects(bullet.getRect()) && bullet.getDir() == 0)
				{
					it2.remove();
					sound.invaderDie();
					it.remove();
					totalScore += enemy.score;
					player.setFired(false);
					break;
				}
			}
			Iterator<Barrier> it3 = barriers.iterator();
			while (it3.hasNext())
			{
				Barrier barrier = it3.next();
				if (barrier.getRect().intersects(bullet.getRect()))
				{
					it.remove();
					barrier.switchImage();
					sound.explosion();
					if (barrier.getHp() < 0)
					{
						it3.remove();
					}
					if (bullet.getDir() == 0)
					{
						player.setFired(false);
					}
					break;
				}
			}
			if (player.getRect().intersects(bullet.getRect()) && bullet.getDir() == 1)
			{
				it.remove();
				player.setLives(player.getLives() - 1);
				sound.explosion();

			}
		}
	}

	public void moveDownEnemies()
	{
		boolean gameover = false;
		for (Enemy enemy : enemies)
		{
			enemy.movedown();
			if (enemy.getPosition().getY() > 500)
			{
				gameover = true;
			}
		}
		if (gameover)
		{
			repaint();
			dead();
		}
	}

	public void writeHighscore(int score)
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
					if (score > Integer.valueOf(next))
					{
						PrintWriter output = new PrintWriter(outputFile);
						output.write("" + score);
						output.close();
					}
				}
				scan.close();

			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			try
			{
				PrintWriter output = new PrintWriter(outputFile);
				output.write("-1");
				output.close();
			}
			catch (IOException e)
			{
			}
		}
	}

	public void dead()
	{
		t.stop();
		writeHighscore(totalScore);
		JOptionPane.showMessageDialog(si, "Game over");
		sound.stopBG();
		si.newMenu();
	}

	public void update()
	{
		if (player.getLives() == 0)
		{
			dead();
		}
		collisionCheck();
		garbagecollection();
		speedupEnemies();
		if (enemies.size() == 0)
		{
			enemyDir = 1;
			moveCount = 0;
			counter = 0;
			moveDownCount = 0;
			moveDownMargin = 1;
			bulletSpriteCount = 0;
			randomDomain = 5000;
			initEnemies();
		}
		Enemy mostLeft = getMostLeftEnemy();
		for (Bullet bullet : bullets)
		{
			bullet.move();
			if (bulletSpriteCount > 10)
			{
				bulletSpriteCount = 0;
				bullet.updateSprite();
			}
		}
		for (Enemy enemy : enemies)
		{
			double random = Math.random() * randomDomain;
			if (Math.round(random) == 100)
			{
				enemy.fire();
			}
			enemy.tick();
			if (counter > 60)
			{
				enemy.move(enemyDir);
			}
		}
		if (getMostRightEnemyX() >= getWidth() - 50)
		{
			enemyDir = 0;
			moveCount = 0;
		}
		else if (mostLeft.getPosition().getX() <= 50)
		{
			enemyDir = 1;
			moveCount = 0;
		}
		if (counter > 60)
		{
			counter = 0;
			moveCount++;
		}
		if (moveCount > 10)
		{
			moveCount = 0;
			if (enemyDir == 0)
			{
				enemyDir = 1;
			}
			else
			{
				enemyDir = 0;
			}
		}
		if (moveDownCount > 600 / moveDownMargin + 200)
		{
			moveDownCount = 0;
			moveDownMargin++;
			moveDownEnemies();
		}
		moveDownCount++;
		counter++;
		bulletSpriteCount++;
		player.tick();
		repaint();
	}

	public void speedupEnemies()
	{
		for (Enemy enemy : enemies)
		{
			switch (enemies.size())
			{
				case 36:
					if (enemy.getSpedup()[0] == false)
					{
						enemy.speedup();
						enemy.getSpedup()[0] = true;
						randomDomain = 4000;
					}
					break;
				case 24:
					if (enemy.getSpedup()[1] == false)
					{
						enemy.speedup();
						enemy.getSpedup()[1] = true;
						randomDomain = 2000;
					}
					break;
				case 18:
					if (enemy.getSpedup()[2] == false)
					{
						enemy.speedup();
						enemy.getSpedup()[2] = true;
						randomDomain = 1000;
					}
					break;
				case 12:
					if (enemy.getSpedup()[3] == false)
					{
						enemy.speedup();
						enemy.getSpedup()[3] = true;
						randomDomain = 500;
					}
					break;
				case 6:
					if (enemy.getSpedup()[4] == false)
					{
						enemy.speedup();
						enemy.getSpedup()[4] = true;
						randomDomain = 200;
					}
					break;
				case 3:
					if (enemy.getSpedup()[5] == false)
					{
						enemy.speedup();
						enemy.getSpedup()[5] = true;
						randomDomain = 50;
					}
					break;
			}
		}
	}

	public int getMostRightEnemyX()
	{
		int mostRight = 0;
		for (Enemy enemy : enemies)
		{
			if (enemy.getPosition().getX() + enemy.getImage().getWidth() > mostRight)
			{
				mostRight = (int) enemy.getPosition().getX() + enemy.getImage().getWidth();
			}
		}
		return mostRight;
	}

	public Enemy getMostRightEnemy()
	{
		Enemy lastEnemy = null;
		for (Enemy enemy : enemies)
		{
			if (lastEnemy == null)
			{
				lastEnemy = enemy;
				continue;
			}
			if (enemy.getPosition().getX() > lastEnemy.getPosition().getX())
			{
				lastEnemy = enemy;
			}
		}
		return lastEnemy;
	}

	public Enemy getMostLeftEnemy()
	{
		Enemy lastEnemy = null;
		for (Enemy enemy : enemies)
		{
			if (lastEnemy == null)
			{
				lastEnemy = enemy;
				continue;
			}
			if (enemy.getPosition().getX() < lastEnemy.getPosition().getX())
			{
				lastEnemy = enemy;
			}
		}
		return lastEnemy;
	}

	public Player getPlayer()
	{
		return player;
	}

	public void setPlayer(Player player)
	{
		this.player = player;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		update();
		// Workaround for bug in JVM causing lots of stutter in openjdk-8
		Toolkit.getDefaultToolkit().sync();
	}

	public Sounds getSound()
	{
		return sound;
	}

	public void setSound(Sounds sound)
	{
		this.sound = sound;
	}

}
