/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import flappybird3.game.GameBoard;
import flappybird3.game.Tile;
import flappybird3.game.tiles.WallTile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

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

            for (int i = 0; i < typeCount; i++) {
                br.readLine();
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
                    if (!"".equals(cell)) {
                   tiles[i][j]=new WallTile();
                    }
                }
            }
            GameBoard gb = new GameBoard(tiles);
            return gb;
        } catch (IOException e) {
            throw new RuntimeException("Chyba pri cteni souboru", e);
        }

    }

}
