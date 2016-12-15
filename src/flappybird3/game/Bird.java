package flappybird3.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Ellipse2D;

public class Bird implements TickAware {
		//fyzika
		static final double koefUp = -5.0;
		static final double koefDown = 2;
		static final int ticksFlyingUp = 4;
		
		Image image;
           
		// souradnice stredu ptaka
		int viewportX;
		double viewportY;
		// rychlost pad�n�(pozitivn�) nebo vzletu (negativn�)
		double velocityY = koefDown;
		//kolik ticku jeste zbyva, nez ptak po nakopnuti zacne padat
		int ticksToFall =0;
		public Bird(int initialX, int initialY, Image image) {
			this.viewportX = initialX;
			this.viewportY = initialY;	
			this.image = image;
		}
		public void kick(){
			velocityY = koefUp;
			
                        ticksToFall = ticksFlyingUp;
		}
		public void draw(Graphics g){
			g.setColor(Color.green);
			//g.fillOval(viewportX-Tile.size/2, (int) (viewportY-Tile.size/2), Tile.size, Tile.size);
			
			g.drawImage(image, viewportX-Tile.size/2, (int) (viewportY-Tile.size/2),null);
			g.setColor(Color.BLACK);
			g.drawString(viewportX+", "+viewportY, viewportX, (int) viewportY);
		}
		
	@Override
	public void tick(long ticksSinceStart) {
		// TODO Auto-generated method stub
            if(ticksToFall>0){ // ptak lezi nahoru "cekame"
            ticksToFall--;
            }else{
            //ptak pada nebo ma zacit padat
                velocityY=koefDown;
            
            }
            viewportY=(viewportY+velocityY);
		
	}
	
	public boolean  collidesWithRactangles(int x, int y, int w, int h){
	Ellipse2D.Float birdBoundary = new  Ellipse2D.Float(viewportX-Tile.size/2, (int) (viewportY-Tile.size/2), Tile.size, Tile.size);
	return birdBoundary.intersects(x, y, w, h);
	}
	
	
       

}
