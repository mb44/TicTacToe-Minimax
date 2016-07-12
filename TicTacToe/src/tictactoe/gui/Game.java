/**
 * @author Morten Beuchert
 */
package tictactoe.gui;

import tictactoe.game.TicTacToe;
import tictactoe.gui.TicTacToeGUI;
import tictactoe.util.ObserverBase;

public class Game {
    public static void main(String[] args) {
    	TicTacToe game = new TicTacToe();
        ObserverBase gui = new TicTacToeGUI(game);
        game.add(gui);
    }
}