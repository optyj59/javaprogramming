package BALL;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


import Obj.Enemy;
import Obj.Player;
import Obj.obj;
import tile.Tile;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
	
	final int originalTileSize = 16; //16X16 pixel
	final int scale = 3;
	
	private int deathcount;
	
	public final int tileSize = originalTileSize * scale; //48 pixel
	public final int maxScreenCol = 28;
	public final int maxScreenRow = 16;
	final int screenWidth = tileSize * maxScreenCol; //768
	final int screenHeight = tileSize * maxScreenRow; //576 pixels
	public int gameState;
	
	public final int titleState = 1;
	public final int leaderboard = 2;
	public final int playState = 3;
	public final int endState = 4;
	
	public JButton startButton,leaderboardButton,enlistButton;
	public JTextField nameField;
	
	int FPS = 60;
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler(); 
	public CollisionDetector cDetector = new CollisionDetector(this);
	Thread gameThread;
	
	//
	Player player = new Player(this, keyH,100,300);
	Enemy enemy1 = new Enemy(this,"C",0,3);
	Enemy enemy2 = new Enemy(this,"C#",0,6);
	Enemy enemy3 = new Enemy(this,"C++",0,9);
	Enemy enemy4 = new Enemy(this,"docker",0,12);
	Enemy enemy5 = new Enemy(this,"git",7,0);
	Enemy enemy6 = new Enemy(this,"happycat",14,0);
	Enemy enemy7 = new Enemy(this,"java",21,0);
	Enemy enemy8 = new Enemy(this,"py",0,15);
	
	public boolean gpsafety ;
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		this.setDoubleBuffered(true);
		this.setLayout(null);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		gameState= titleState;
		
		deathcount = 0;
		
		startButton = new JButton("Start");
		leaderboardButton = new JButton("Leaderboard");
		enlistButton = new JButton("Enlist");
		startButton.setBounds(600,450,130,40);
		leaderboardButton.setBounds(600,500,130,40);
		enlistButton.setBounds(730,600,130,40);
		
		ActionListener enlistListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tmp = nameField.getText();
				JOptionPane.showMessageDialog(null, tmp+" enlisted!");
				nameField.setVisible(false);
				enlistButton.setVisible(false);
				nameField.setText("");
				gameState = leaderboard;
			}
		};
		
		nameField = new JTextField();
		nameField.setBounds(600,600,130,40);
		ActionListener buttonListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==startButton) {
					gameState = playState;
					resetplayerposition();
					startButton.setVisible(false);
					leaderboardButton.setVisible(false);
				}
				else if (e.getSource()==leaderboardButton) {
					gameState = leaderboard;
					startButton.setBounds(600,600,130,40);
					leaderboardButton.setVisible(false);
				}
			}
		};
		startButton.addActionListener(buttonListener);
		leaderboardButton.addActionListener(buttonListener);
		enlistButton.addActionListener(enlistListener);
		
		this.add(startButton);
		this.add(leaderboardButton);
		this.add(nameField);
		this.add(enlistButton);
		nameField.setVisible(false);
		enlistButton.setVisible(false);
		startButton.setVisible(false);
		leaderboardButton.setVisible(false);
	}
	
	public void startGameThread() {
		gameThread = new Thread(this); // gamepanel to 
		gameThread.start();
	}
	
	public boolean playerissafe() {
		if(gpsafety) {
			return true;
		}
		return false;
	}
	
	public void resetplayerposition() {
		int level = tileM.getcurrentnum();
		gpsafety=true;
		switch(level) {
			case 1:
				player.resetpos(48*4, 24*15);
				break;
			case 2:
				player.resetpos(48*3, 24*14);
				break;
			case 3:
				player.resetpos(48*4, 48*3 );
				break;
			case 4:
				player.resetpos(48*5, 48*4);
				break;
			case 5:
				player.resetpos(24*27, 24*22);
				break;
			case 6:
				player.resetpos(48*2, 24*9);
				break;
			case 7:
				player.resetpos(48*4, 48*2);
				break;
			case 8:
				player.resetpos(24*3, 24*3);
				break;
			case 9:
				player.resetpos(48*3, 24*27);
				break;
			case 10:
				gameState = endState;
				deathcount =0;
				tileM.initcurrentnum();
				break;
		}
		
	}

	
	public void run() {
		double drawInterval = 1000000000/FPS;
		double nextDrawTime = System.nanoTime()+drawInterval;
		
		while(gameThread!=null) {
			 
			//update the info as character position
			update();
			
			// draw the screen with updated thing
			repaint();
			
	         
			
			try {
				
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime /= 1000000;
				
				if(remainingTime<0) remainingTime = 0;
				
				Thread.sleep((long)remainingTime);
				
				nextDrawTime += drawInterval;
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(EnemyCollision(player,enemy1,enemy2,enemy3,enemy4,enemy5,enemy6,enemy7,enemy8)==false) {
	        	 if(playerissafe()) {
	        		 continue;
	        	 }
	        	 resetplayerposition();
	        	 deathcount++;
	         }
		}
	}
	public void update() { //왼쪽 위가 0,0
		player.update();
	    enemy1.Xupdate();
	    enemy2.Xupdate();
	    enemy3.Xupdate();
	    enemy4.Xupdate();
	    enemy5.Yupdate();
	    enemy6.Yupdate();
	    enemy7.Yupdate();
	    enemy8.Xupdate();
		int mapnumber = tileM.getcurrentnum();
		if(mapnumber==10) {
			gameState=endState;
		}
	}
	public Boolean EnemyCollision(Player pl,Enemy em1,Enemy em2,Enemy em3,Enemy em4,Enemy em5,Enemy em6,Enemy em7,Enemy em8) {
			
			if(Math.abs(pl.x-em1.x)<32 && Math.abs(pl.y-em1.y)<32)
				return false;
			
			if(Math.abs(pl.x-em2.x)<32 && Math.abs(pl.y-em2.y)<32)
				return false;
			
			if(Math.abs(pl.x-em3.x)<32 && Math.abs(pl.y-em3.y)<32)
				return false;
			
			if(Math.abs(pl.x-em4.x)<32 && Math.abs(pl.y-em4.y)<32)
				return false;
			
			if(Math.abs(pl.x-em5.x)<32 && Math.abs(pl.y-em5.y)<32)
				return false;
			
			if(Math.abs(pl.x-em6.x)<32 && Math.abs(pl.y-em6.y)<32)
				return false;
			
			if(Math.abs(pl.x-em7.x)<32 && Math.abs(pl.y-em7.y)<32)
				return false;
			
			if(Math.abs(pl.x-em8.x)<32 && Math.abs(pl.y-em8.y)<32)
				return false;
			
			return true;
			
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if(gameState==titleState) {
			startButton.setVisible(true);
			leaderboardButton.setVisible(true);
			Graphics2D g2= (Graphics2D)g;
			g2.setFont(new Font("궁서",Font.BOLD,30));
			g2.drawString("World's modest difficult block avoidance", 360, 180);
		}
		else if(gameState==leaderboard) {
			startButton.setVisible(true);
		}
		else if(gameState==playState){
			Graphics2D g2 = (Graphics2D)g;
			tileM.draw(g2);
			player.draw(g2);
	         enemy1.draw(g2);
	         enemy2.draw(g2);
	         enemy3.draw(g2);
	         enemy4.draw(g2);
	         enemy5.draw(g2);
	         enemy6.draw(g2);
	         enemy7.draw(g2);
	         enemy8.draw(g2);
			g2.setFont(new Font("궁서",Font.BOLD,30));
			g2.drawString("Deaths: "+deathcount, 1100, 700);
			g2.dispose();
		}
		else if(gameState==endState) {
			Graphics2D g2= (Graphics2D)g;
			nameField.setVisible(true);
			enlistButton.setVisible(true);
			g2.setFont(new Font("궁서",Font.BOLD,28));
			g2.drawString("Congratulations!                    You made it!", 360, 160);
			// 리더보드에 추가하는 코드
			
			
			BufferedImage im = null;
			try {
				im = ImageIO.read(getClass().getResourceAsStream("/map/game_clear.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g2.drawImage(im,350,300,700,200,null);
		}
	}
	
	public void openClient() {
		
	}
	
	

}