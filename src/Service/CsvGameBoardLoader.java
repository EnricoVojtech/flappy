/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import flappybird3.game.GameBoard;
import flappybird3.game.Tile;
import flappybird3.game.tiles.BonusTile;
import flappybird3.game.tiles.EmptyTile;
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

	public GameBoard loadLevel() {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
			String[] line = br.readLine().split(";");
			int typeCount = Integer.parseInt(line[0]);

			Map<String, Tile> tileTipes = new HashMap<>();
			// radky definice dlazdic
			BufferedImage imageOfTheBird= null;
			for (int i = 0; i < typeCount; i++) {
				line = br.readLine().split(";");
				String tileTipe = line[0];
				String clazz = line[1];
				int x = Integer.parseInt(line[2]);
				int y = Integer.parseInt(line[3]);
				int z = Integer.parseInt(line[4]);
				int w = Integer.parseInt(line[5]);
				String url = line[6];
				String referencedTileType =(line.length >=8)? line[7]: ""; // nepoviny odkaz pouzivame u bonusu
				Tile referencedTile = tileTipes.get(referencedTileType);
				if(clazz.equals("Bird")){
					//urcuje radek ptaka
					 imageOfTheBird = loadImage(x, y, z, w, url);
				}else{
					Tile tile = createTile(clazz, x, y, z, w, url,referencedTile);
					tileTipes.put(tileTipe, tile);		
				}
			
			}
			line = br.readLine().split(";");
			int rows = Integer.parseInt(line[0]);
			int columns = Integer.parseInt(line[1]);
			// vytvorime pole dlazdic odpovydajicich rozmeru
			Tile[][] tiles = new Tile[rows][columns];
			System.out.println(rows + "," + columns);
			for (int i = 0; i < rows; i++) {
				line = br.readLine().split(";");
				for (int j = 0; j < columns; j++) {
					String cell;
					if (j < line.length) {
						// bunka v csv existuje
						cell = line[j];
					} else {
						// bunka v csv chybi povazujeme ji za prazdnou
						cell = "";
					}
					// odpovidajici typ dlazdice hasMapy
					Tile t = tileTipes.get(cell);
					if(t instanceof BonusTile){
						//
						tiles[i][j] = new BonusTile((BonusTile)t); // klovani bonusu
					}else{
						tiles[i][j]=t;
						//
						
					}
					
						//tiles[i][j] = tileTipes.get(cell);
				}
			}
			GameBoard gb = new GameBoard(tiles, imageOfTheBird);
			return gb;
		} catch (IOException e) {
			throw new RuntimeException("Chyba pri cteni souboru", e);
		}
	}

	private Tile createTile(String clazz, int x, int y, int w, int h, String url, Tile referenceTile) {
		// stahnout obrazek z URL a ulozit do promene
		try {
			BufferedImage resizeImage = loadImage(x, y, w, h, url);
			switch (clazz) {
			case "Wall":
				return new WallTile(resizeImage);
			case "Empty":
				return new EmptyTile(resizeImage);
			case "Bonus":
				return new BonusTile(resizeImage,referenceTile);
			}
			//ani jedna vetev switch case nefungovala
			throw new RuntimeException("Neznami tip dlazdice" + clazz);

		} catch (MalformedURLException e) {
			throw new RuntimeException("Spatna url" + clazz + ": " + url, e);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private BufferedImage loadImage(int x, int y, int w, int h, String url) throws IOException, MalformedURLException {
		BufferedImage original = ImageIO.read(new URL(url));
		// vyrizneme sprite z obrazku

		BufferedImage cropedImg = original.getSubimage(x, y, w, h);
		// zvetsime dlazdice

		BufferedImage resizeImage = new BufferedImage(Tile.size, Tile.size, BufferedImage.TYPE_INT_ARGB);
		;
		Graphics2D g = resizeImage.createGraphics();
		g.drawImage(cropedImg, 0, 0, Tile.size, Tile.size, null);
		// vytvorime odpovidajici typ dlazdice
		return resizeImage;
	}

}
