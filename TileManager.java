package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import BALL.CollisionDetector;
import BALL.GamePanel;

public class TileManager {
	GamePanel gp;
	public Tile[][] tile;
	private int currentMapnum =0;
	
	
	
	public int getcurrentnum() {
		return currentMapnum;
	}
	
	
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[gp.maxScreenRow][gp.maxScreenCol];
		getmap();
	}
	
	
	public void getmap() {
		currentMapnum++;
		if(currentMapnum>=10) {
			return;
			
			}
		try { //tilemanger로 맵을 만듭니다. 
			  //map 요소는 res tiles a~q 이고 
			  //메모장 형식으로 넣어주면 대충은 일단 완성!
				String mapString = "/map/map0"+currentMapnum+".txt";
				InputStream is= getClass().getResourceAsStream(mapString);
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				
				String frag;
				int cnt = 0;
				while((frag=br.readLine())!=null){
					for(int i=0;i<frag.length();i++) {
						tile[cnt][i] = new Tile();
						tile[cnt][i].endgame=false;
						tile[cnt][i].safety=false;
						tile[cnt][i].image = ImageIO.read(getClass().getResource("/tiles/"+frag.charAt(i)+".png"));
						if(frag.charAt(i)=='z') {
							tile[cnt][i].endgame = true;
							tile[cnt][i].collision = false;
						}
						else if(frag.charAt(i)=='q' || frag.charAt(i)=='r') {
							if(frag.charAt(i)=='r') {
								tile[cnt][i].safety =true;
							}
							tile[cnt][i].collision = false;
						}else {
							tile[cnt][i].collision = true;
						}
					}
					cnt++;
				}
			
			
			
			
		} catch (IOException e) {
			// TODO: handle exception
		}
	}
	
	public void initcurrentnum() {
		currentMapnum=0;
		return;
	}
	
	public void draw(Graphics2D g2) {
		for(int i=0;i<gp.maxScreenRow;i++) {	
			for(int j=0;j<gp.maxScreenCol;j++) {
				g2.drawImage(tile[i][j].image, 0+48*j, 0+48*i, gp.tileSize, gp.tileSize, null);
			}
		}
		
	}
}