package Obj;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import BALL.GamePanel;
import BALL.KeyHandler;

public class Player extends obj {
	GamePanel gP;
	KeyHandler keyH;
	private int startx;
	private int starty;
	
	public Player (GamePanel gP, KeyHandler keyH,int startx,int starty) {
		this.gP = gP;
		this.keyH = keyH;
		this.startx=startx;
		this.starty= starty;
		setDefaultValues();
		getPlayerImage();
	}
	public void setDefaultValues() { //시작지점설정
		x = startx;
		y = starty;
		speed = 10;
		direction = "up";
	}
	public void getPlayerImage() {
		try{
			image = ImageIO.read(getClass().getResourceAsStream("/player/성대사랑.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void resetpos(int startx, int starty) {
		this.startx=startx;
		this.starty=starty;
		setDefaultValues();
	}
	public void update() {
		if(keyH.upPressed == true || keyH.downPressed ==true || keyH.leftPressed == true || keyH.rightPressed == true) {
			if(keyH.upPressed==true) {
				direction = "up";
			}
			else if(keyH.downPressed==true) {
				direction = "down";
			}
			else if (keyH.leftPressed==true) {
				direction = "left";
			}
			else if (keyH.rightPressed==true) {
				direction = "right";
			}
		
			collisionOn = false;
			gP.cDetector.checkTile(this);
		
			if(collisionOn==false) {
				switch(direction){
				case "up":
					y -= speed;
					break;
				case "down":
					y += speed;
					break;
				case "left":
					x -= speed;
					break;
				case "right":
					x += speed;
					break;
				}	
			}
		}
	}
	public void draw(Graphics2D g2) {
		BufferedImage im = image;
		g2.drawImage(im, x, y, gP.tileSize*2/3, gP.tileSize*2/3, null);
		
	}
}
