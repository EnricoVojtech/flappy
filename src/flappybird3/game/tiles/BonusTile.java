package flappybird3.game.tiles;

import java.awt.Graphics;
import java.awt.Image;

public class BonusTile extends AbstractTile {

	private boolean visible = true;

	public BonusTile(Image image) {
		super(image);
	}

	public void setVisible(boolean value) {
		visible = value;
	}

	@Override
	public void draw(Graphics g, int x, int y) {
		if (visible) {
			super.draw(g, x, y);
		}
	}
}
