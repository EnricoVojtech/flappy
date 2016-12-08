/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import flappybird3.game.GameBoard;
import flappybird3.game.Tile;
import flappybird3.game.tiles.WallTile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

/**
 *
 * @author enrico
 */
public class CsvGameBoardLoader implements GameBoardLoader {

    InputStream is;

    public CsvGameBoardLoader(InputStream is) {
        this.is = is;
    }

    @Override
    public GameBoard loadLevel() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            // String line = br.readLine();
            String[] line = br.readLine().split(";");
            int typeCount = Integer.parseInt(line[0]);

            Map<String,Tile> tilesTypes = new HashMap<>();
            for (int i = 0; i < typeCount; i++) {
                line = br.readLine().split(";");
                String tileType = line[0];
                String clazz = line[0];
                int x = Integer.parseInt(line[2]);
                int y = Integer.parseInt(line[3]);
                int w = Integer.parseInt(line[4]);
                int h = Integer.parseInt(line[5]);
                String url = line[6];
                Tile tile = creatTile(clazz, x ,y,w,h,url);
                tilesTypes.put(tileType,tile);
            }
            

            line = br.readLine().split(";");
            int rows = Integer.parseInt(line[0]);
            int colums = Integer.parseInt(line[1]);
            System.out.println(rows + "   " + colums);
           Tile[][] tiles = new Tile[rows][colums];
            for (int i = 0; i < rows; i++) {
                line = br.readLine().split(";");

               
                
                for (int j = 0; j < colums; j++) {
                    String cell;
                    if (j < line.length) {
                        cell = line[j];
                    } //bunka existuje v csv
                    else {
                        cell = "";
                    } //povazujeme ji prazdnou
                    
                    
      
                   
                   tiles[i][j]=tilesTypes.get(cell);
                    
                }
            }
            GameBoard gb = new GameBoard(tiles);
            return gb;
        } catch (IOException e) {
            throw new RuntimeException("Chyba pri cteni souboru", e);
        }

    }

	private Tile creatTile(String clazz, int x, int y, int w, int h, String url) {
		
		
		
		try{
			// stahnout URL do promene
			BufferedImage originalImage = ImageIO.read(new URL(url));
			//vyriznout Spirite z URL
			BufferedImage croppedImage = originalImage.getSubimage(x, y, w, h);
			//vytvorime typ dlazdice
			BufferedImage resizeImage= new BufferedImage(Tile.size,Tile.size,BufferedImage.TYPE_INT_ARGB);
			Graphics2D g =resizeImage.createGraphics();
			g.drawImage(croppedImage, 0, 0, Tile.size, Tile.size, null);
			//zvetsime obrazek na velikost dlazdice
			switch(clazz){
			default:
			return new WallTile(resizeImage);
			}
			
		}catch(MalformedURLException e){
			throw new RuntimeException("ds");
		}catch(IOException e){
			throw new RuntimeException("ds");
		}
		
	}

}
