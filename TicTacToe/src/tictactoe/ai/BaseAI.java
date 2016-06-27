/**
 * @author Morten Beuchert
 */
package tictactoe.ai;

import tictactoe.ai.TicTacToeAI;
import tictactoe.game.TicTacToe;

/**
 * All AI implementations must extend this class.
 */
public class BaseAI implements TicTacToeAI {
    protected TicTacToe game;

    public BaseAI(TicTacToe game) {
        this.game = game;
    }

    /**
     * All AI implementations must overwrite this method.
     */
    public int move() {
        return -1;
    }
}