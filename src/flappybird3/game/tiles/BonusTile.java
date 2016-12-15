package flappybird3.game.tiles;

import java.awt.Graphics;
import java.awt.Image;

import flappybird3.game.Tile;

public class BonusTile extends AbstractTile {

	private boolean visible = true;
    Tile emptyTile;
	public BonusTile(Image image, Tile emptyTile) {
		super(image);
		this.emptyTile=emptyTile;
	}

	public void setVisible(boolean value) {
		visible = value;
	}

	@Override
	public void draw(Graphics g, int x, int y) {
		if (visible) {
			super.draw(g, x, y);
		}else
		emptyTile.draw(g, x, y);
	}
}
