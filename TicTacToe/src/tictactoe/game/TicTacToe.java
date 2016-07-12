/**
 * @author Morten Beuchert
 * */

package tictactoe.game;

import java.util.Hashtable;
import tictactoe.ai.Node;
import tictactoe.ai.PerfectPlayAI;
import tictactoe.game.GameState;
import tictactoe.util.Observable;

/**
 * The board is represented by two bit patterns:
 * 1) Crosses, 'x' 2) Noughts, 'o'
 * 
 * Bit (decimal value):  256 128 64 32 16 8 4 2 1
 * Board index:            8   7  6  5  4 3 2 1 0
 * 
 * This LSB of x or o, respectively, corresponds the upper left square. The bit to the left of the LSB
 * corresponds the to the first row, second column
 * 0 1 2
 * 3 4 5
 * 6 7 8
 * 
 * Example:
 * x = 48; means that there are crosses at board indices 4 and 5:
 * - - -
 * - X X
 * - - -
 * 
 * This program uses bit operations to perform operations such as make a move, checking legal moves etc.
 *
 */
public class TicTacToe extends Observable {
    private GameState gameState;
    private Hashtable<String, Node> table;
    private PerfectPlayAI ai;
    private int x;
    private int o;
    private boolean player;
    private boolean gameOver;
    private int winner;

    public TicTacToe() {
    	gameState = new GameState();
    	restart();
        buildTranspositionTable();
    }

    private void buildTranspositionTable() {
    	table = new Hashtable<>();
        int depth = 0;
        Node n = ai.minimax(depth);
        n.player = true;
        put(toString(), n);
    }

    public void moveAI() {
        if (isGameOver()) {
            return;
        }
        int move = ai.move();
        applyMove(move);
        updateGameOver();
        updateGameState();
    }

    public void applyMove(int square) {
        if (isGameOver()) {
            return;
        }
        int mask = 1;
        mask <<= square;
        if (player) {
            if ((x & mask) == 0) {
                x |= mask;
                updateGameOver();
                player = !player;
            }
        } else {
        	if ((o & mask) == 0) {
	            o |= mask;
	            updateGameOver();
	            player = !player;
        	}
        }
        updateGameState();
    }

    public void undoMove(int square) {
        int mask = 1;
        if (!player) {
        	mask <<= square;
            if ((mask&x) > 0) {
                mask = 1;
                mask <<= square;
                // Invert bits
                mask = ~mask;
                // Unset bit
                x = x & mask;
                gameOver = false;
                winner = 0;
                player = !player;
            }
        } else {
        	mask <<= square;
            if ((mask&o) > 0) {
	            mask = 1;
	            mask <<= square;
                // Invert bits
                mask = ~mask;
                // Unset bit
                o = o & mask;
	            gameOver = false;
	            winner = 0;
	            player = !player;
            }
        }
        updateGameState();
    }

    public boolean isLegalMove(int square) {
        int mask = 1;
        mask <<= square;
	    return (mask &= x | o) == 0;
    }

    public void updateGameOver() {
        int row1 = 7;
        int row2 = 56;
        int row3 = 448;
        int col1 = 73;
        int col2 = 146;
        int col3 = 292;
        int diag1 = 273;
        int diag2 = 84;
        int fullboard = 511;
        if ((x | o) == fullboard) {
            gameOver = true;
        }
        if (player) {
            if ((x & row1) == row1 || (x & row2) == row2 || (x & row3) == row3 || (x & col1) == col1 || (x & col2) == col2 || (x & col3) == col3 || (x & diag1) == diag1 || (x & diag2) == diag2) {
                gameOver = true;
                winner = 1;
            }
        } else if ((o & row1) == row1 || (o & row2) == row2 || (o & row3) == row3 || (o & col1) == col1 || (o & col2) == col2 || (o & col3) == col3 || (o & diag1) == diag1 || (o & diag2) == diag2) {
            gameOver = true;
            winner = -1;
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getWinner() {
        return winner;
    }

    public boolean getTurn() {
        return player;
    }

    public void printBoard() {
        for (int i=0; i<9; i++) {
            int mask = 1;
            mask <<= i;
            if ((x & mask) == mask) {
                System.out.print("X ");
            } else if ((o & mask) == mask) {
                System.out.print("O ");
            } else {
                System.out.print("- ");
            }
            if ((i + 1) % 3 == 0) {
                System.out.println();
            }
        }
            System.out.println();
    }

    /**
     * @return an array representation of board. 0: no piece. 1: cross. 2: nought.
     */
    public int[] getBoard() {
        int[] result = new int[9];
        for (int i=0; i<9; i++) {
            int mask = 1;
            mask <<= i;
            if ((x & mask) == mask) {
                result[i] = 1;
            } else if ((o & mask) == mask) {
                result[i] = 2;
            }
        }
        return result;
    }

    public boolean[] getLegalMoves() {
        int board = x | o;
        boolean[] result = new boolean[9];
        int mask = 1;
        for (int i=0; i<9; i++) {
            mask = 1;
            mask <<= i;
            // Invert bits
            mask = ~mask;
            mask &= board;
            if (mask == 0) {
            	result[i] = true;
            }
        }
        return result;
    }

    public void restart() {
        ai = new PerfectPlayAI(this);
        player = true;
        winner = 0;
        gameOver = false;
        o = 0;
        x = 0;
        updateGameState();
    }

    public void put(String key, Node value) {
        table.put(key, value);
    }

    public Node get(String key) {
        return table.get(key);
    }

    private void updateGameState() {
        gameState.setBoard(getBoard());
        gameState.setWinner(getWinner());
        gameState.setPlayerTurn(player);
        gameState.setGameOver(isGameOver());
        notifyObservers();
    }

    public GameState getState() {
        return gameState;
    }
    
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (int i=0; i<9; i++) {
            int mask = 1;
            mask <<= i;
            if ((x & mask) == mask) {
                res.append("X");
            } else if ((o & mask) == mask) {
                res.append("O");
            } else {
                res.append("-");
            }
        }
        return res.toString();
    }
}