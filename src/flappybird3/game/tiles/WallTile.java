package flappybird3.game.tiles;

import java.awt.Graphics;

import flappybird3.game.Tile;

public class WallTile implements Tile {

	@Override
	public void draw(Graphics g,int x, int y ) {
		g.drawRect(x, y, Tile.size, Tile.size);
		
	}

}
