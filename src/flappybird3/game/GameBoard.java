package flappybird3.game;

import flappybird3.game.tiles.BonusTile;
import flappybird3.game.tiles.WallTile;
import java.awt.Graphics;
import java.awt.Image;

public class GameBoard implements TickAware {
	Tile[][] tiles;
	int shiftX;
	int viewportWidth = 200;
	Bird bird;
	boolean gameOver = false;



	public GameBoard(Tile[][] tiles, Image imageOfTheBird) {
		this.tiles = tiles;
		bird = new Bird(viewportWidth / 2, (tiles.length * Tile.size / 2),imageOfTheBird);
	}

	/**
	 * kresl� cely herni sv�t(zdi,bonusy,ptaka) na platno g.
	 * 
	 * @param g
	 */
	public void drawAndTestCollision(Graphics g) {
		// spocitame prvni j-index bunky, kterou ma smysl kreslit (je videt je
		// viewportu)
		int minJ = shiftX / Tile.size;
		int maxJ = minJ + viewportWidth / Tile.size + 2;
		// proto�e celociselne delime jak shift X, tak viewPort size
		for (int i = 0; i < tiles.length; i++) {
			for (int j = minJ; j < maxJ; j++) {
				int j2 = j % tiles[0].length;
				// chceme aby svet "tocil dokola" j2 pohybuje 0... pocet sloupu
				// sloupcu-1
				
				

				Tile t = tiles[i][j2];
				if (t != null) { // je na souradnich nejaka dlazdice
					int screenX = j * Tile.size - shiftX;
					int screenY = i * Tile.size;
					t.draw(g, screenX, screenY);
					// testovani moynost kolize s ptakem
					if (t instanceof WallTile) {
						if (bird.collidesWithRactangles(screenX, screenY, Tile.size, Tile.size)) {
							System.out.println("narazil");
							gameOver = true;
						}
					}
					
				

					if (t instanceof BonusTile) {
						if (bird.collidesWithRactangles(screenX, screenY, Tile.size, Tile.size)) {
							((BonusTile) t).setVisible(false);
							System.out.println("safsa");
							
						
							
							
						}if(shiftX % 150==0){
							((BonusTile) t).setVisible(true);
						}

					}

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

		if (!gameOver) {
			shiftX = (int) ticksSinceStart;
			// TODO dame vedet je�t� ptakovy, ze jsme klikli
			bird.tick(ticksSinceStart);
		}

	}

	public void kickTheBird() {
		bird.kick();
	}

	public int getWidthPix() {
		return tiles.length * Tile.size;
	}
}
