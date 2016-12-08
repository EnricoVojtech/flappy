package flappybird3.game;

import flappybird3.game.tiles.WallTile;
import java.awt.Graphics;



public class GameBoard implements TickAware {
		Tile[][] tiles;
		int shiftX;
		int viewportWidth = 200;
                Bird bird;
                
		
		public GameBoard(){
			tiles = new Tile[20][20];
			//tiles[2][1] = new WallTile();
               //         tiles[2][0] = new WallTile();
                        bird= new Bird(viewportWidth/2,(tiles.length*Tile.size/2)); //umisteni ptaka
		}
                
                public GameBoard( Tile[][] tiles){
                this.tiles=tiles;
                bird= new Bird(viewportWidth/2,(tiles.length*Tile.size/2));
                }
		/**
		 * kresl� cely herni sv�t(zdi,bonusy,ptaka) na platno g.
		 * @param g
		 */
		public void  draw(Graphics g){
			//spocitame prvni j-index bunky, kterou ma smysl kreslit (je videt je viewportu)
			int minJ = shiftX/Tile.size;
			int maxJ = minJ + viewportWidth/Tile.size +2;
			// proto�e celociselne delime jak shift X, tak viewPort size 
			for (int i=0;i<tiles.length;i++){	
                            for (int j=minJ; j< maxJ;j++){
				int j2 =j%tiles[0].length;
                                //chceme aby svet "tocil dokola" j2 pohybuje 0... pocet sloupu sloupcu-1
                                
                                Tile t = tiles[i][j2];
					if( t!= null){  //je na souradnich nejaka dlazdice
					int screenX =j*Tile.size - shiftX;
					int screenY =i*Tile.size;
					t.draw(g,screenX,screenY);
					}
				}
			}
                        bird.draw(g);
			// TODO ptak 
		}
		@Override
		public void tick(long ticksSinceStart) {
			// s kazdym tickem ve h�e posuneme hru o jeden pixel 
			// po�et ticku a pixelu posunu se rovnaj�.
			
			shiftX = (int)ticksSinceStart;
			// TODO dame vedet je�t� ptakovy, ze jsme klikli 
			bird.tick(ticksSinceStart);
			
			
		}
                public void kickTheBird(){
        bird.kick();
        }
                
                

    public int getWidthPix() {
      return tiles.length*Tile.size;
    }
}
