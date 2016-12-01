/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import flappybird3.game.GameBoard;

/**
 *
 * @author enrico
 */

//spolecni rozhrani pro tridi umoznujici nacitat levely.
public interface GameBoardLoader {
   GameBoard loadLevel();
}
