package BALL;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

import Obj.Player;
import Obj.obj;

public class CollisionDetector {
	
	GamePanel gp;
	
	
	public void Loadmap() {
		gp.tileM.getmap();
	}
	
	public CollisionDetector(GamePanel gp) {
		this.gp = gp;
	}
	public void checkTile(obj OBJ) {
		int objleftX= OBJ.x;
		int objrightX = OBJ.x+32;
		int objtopY = OBJ.y;
		int objbottomY = OBJ.y+32;
		
		
		
		int objleftCOL = objleftX/gp.tileSize;
		int objrightCOL = objrightX/gp.tileSize;
		int objtopROW = objtopY/gp.tileSize;
		int objbottomROW = objbottomY/gp.tileSize;
		
		switch(OBJ.direction){
		case "up":
			objtopROW = (objtopY-OBJ.speed)/gp.tileSize;
			if(gp.tileM.tile[objtopROW][objleftCOL].endgame==true || gp.tileM.tile[objtopROW][objrightCOL].endgame==true) {
				Loadmap();
				gp.resetplayerposition();
				
			}
			if(gp.tileM.tile[objtopROW][objleftCOL].collision==true || gp.tileM.tile[objtopROW][objrightCOL].collision==true) {
				
				OBJ.collisionOn = true;
			}
			break;
		case "down":
			objbottomROW = (objbottomY+OBJ.speed)/gp.tileSize;
			if(gp.tileM.tile[objbottomROW][objleftCOL].endgame==true || gp.tileM.tile[objbottomROW][objrightCOL].endgame==true) {
			
				Loadmap();
				gp.resetplayerposition();
			}
			if(gp.tileM.tile[objbottomROW][objleftCOL].collision==true || gp.tileM.tile[objbottomROW][objrightCOL].collision==true) {
			
				OBJ.collisionOn = true;
			}
			break;
		case "left":
			objleftCOL = (objleftX-OBJ.speed)/gp.tileSize;
			if(gp.tileM.tile[objtopROW][objleftCOL].endgame==true || gp.tileM.tile[objbottomROW][objleftCOL].endgame==true) {
				
				Loadmap();
				gp.resetplayerposition();
				
			}
			if(gp.tileM.tile[objtopROW][objleftCOL].collision==true || gp.tileM.tile[objbottomROW][objleftCOL].collision==true) {
				
				OBJ.collisionOn = true;
			}
			break;
		case "right":
			objrightCOL = (objrightX+OBJ.speed)/gp.tileSize;
			if(gp.tileM.tile[objtopROW][objrightCOL].endgame==true || gp.tileM.tile[objbottomROW][objrightCOL].endgame==true) {
			
				Loadmap();
				gp.resetplayerposition();
			}
			if(gp.tileM.tile[objtopROW][objrightCOL].collision==true || gp.tileM.tile[objbottomROW][objrightCOL].collision==true) {
				
				OBJ.collisionOn = true;
				break;
			}
			break;
		}
		//if(ended) {
			//Loadmap();
			//ended=false;
		//}
	}
}
