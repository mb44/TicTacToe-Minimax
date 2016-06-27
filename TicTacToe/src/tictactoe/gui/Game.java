/**
 * @author Morten Beuchert
 */
package tictactoe.gui;

import tictactoe.game.TicTacToe;
import tictactoe.gui.TicTacToeGUI;

public class Game {
    public static void main(String[] args) {
    	TicTacToe game = new TicTacToe();
        TicTacToeGUI gui = new TicTacToeGUI(game);
        gui.setVisible(true);
    }
}