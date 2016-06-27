/**
 * Author: Morten Beuchert
 */
package tictactoe.game;

public class GameState {
    private int[] board;
    private boolean gameOver;
    private int winner;

    public GameState() {
    	board = new int[9];
        for (int i=0; i<board.length; i++) {
            this.board[i] = 0;
        }
        this.gameOver = false;
        this.winner = 0;
    }

    public int[] getBoard() {
        return board;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public int getWinner() {
        return winner;
    }

    public void setBoard(int[] board) {
        this.board = board;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }
}