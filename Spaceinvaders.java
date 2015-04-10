package Spaceinvaders;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class Spaceinvaders extends JFrame
{
	private SIGame game;
	private Sounds sound;
	private MenuPanel menu;
	private String currentPanel;

	public Spaceinvaders()
	{
		super("Spaceinvaders");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(800, 600);
		this.setVisible(true);
		this.setResizable(false);
		sound = new Sounds();
		// newGame();
		newMenu();
		currentPanel = "menu";

		// game = new SIGame(this, sound);
		// this.add(game);
		// game.repaint();
		// game.revalidate();
		this.addKeyListener(new KeyListener()
		{
			@Override
			public void keyTyped(KeyEvent e)
			{

			}

			@Override
			public void keyReleased(KeyEvent e)
			{
				if (currentPanel == "game")
				{
					switch (e.getKeyCode())
					{
						case KeyEvent.VK_SPACE:
							game.getPlayer().fire();
							break;
						case KeyEvent.VK_LEFT:
							game.getPlayer().setMoveLeft(false);
							game.getPlayer().stop();
							break;
						case KeyEvent.VK_RIGHT:
							game.getPlayer().setMoveRight(false);
							game.getPlayer().stop();
							break;
						case KeyEvent.VK_Q:
							System.exit(0);
							break;
					}
				}
				else if (currentPanel == "menu")
				{
					switch (e.getKeyCode())
					{
						case KeyEvent.VK_ENTER:
							newGame();
							break;
						case KeyEvent.VK_Q:
							System.exit(0);
							break;
					}
				}
			}

			@Override
			public void keyPressed(KeyEvent e)
			{
				if (currentPanel == "game")
				{
					switch (e.getKeyCode())
					{
						case KeyEvent.VK_LEFT:
							game.getPlayer().move(0);
							game.getPlayer().setMoveLeft(true);
							break;
						case KeyEvent.VK_RIGHT:
							game.getPlayer().move(1);
							game.getPlayer().setMoveRight(true);
							break;
						case KeyEvent.VK_SPACE:
							game.getPlayer().fire();
							break;

					}
				}
			}
		});
	}

	public void newMenu()
	{
		currentPanel = "menu";
		menu = new MenuPanel(sound);
		this.add(menu);
		this.repaint();
		this.revalidate();
		menu.repaint();
		menu.revalidate();
	}

	public void newGame()
	{
		currentPanel = "game";
		game = new SIGame(this, sound);
		this.add(game);
		this.repaint();
		this.revalidate();
		game.repaint();
		game.revalidate();
	}

	public void removeCurrentGame()
	{
		this.remove(game);
	}

	public static void main(String[] args)
	{
		new Spaceinvaders();
	}

}
