package flappybird3.game;

import java.awt.Graphics;

/**
 * 
 * Reprezentuje hern� objekt um�st�n� do matice hern� plochy.
 * @author lavican1
 *
 */
public interface Tile {
	/**
	 *  sirka a vyska dlazdice v pixelech 
	 */
	static final int size =20;
	/**
	 * kresl� hern� objekt na pl�tno g.
	 * @param x x-ova souradnice v pixelech na obrazovce, kam se dlazdice vykresli 
	 * @param y y-ova souradnice v pixelech na obrazovce, kam se dlazdice vykresli 
	 * @param g
	 */
		void draw(Graphics g,int x,int y);
}
