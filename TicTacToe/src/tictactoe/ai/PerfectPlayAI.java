/**
 * @author Morten Beuchert
 */
package tictactoe.ai;

import tictactoe.ai.BaseAI;
import tictactoe.ai.Node;
import tictactoe.game.TicTacToe;

public class PerfectPlayAI extends BaseAI {
    public PerfectPlayAI(TicTacToe game) {
        super(game);
    }

    public int move() {
        Node n = game.get(game.toString());
        return n.bestMove;
    }

    public Node minimax(int depth) {
        if (game.isGameOver()) {
            Node n = new Node();
            n.depth = depth;
            n.score = game.getWinner();
            return n;
        }
        Node result = new Node();
        result.depth = Integer.MAX_VALUE;
        result.score = game.getTurn() ? -1 : 1;
        for (int i=0; i<9; i++) {
            if (game.isLegalMove(i)) {
                game.applyMove(i);
                Node tempEval = game.get(game.toString());
                if (tempEval == null) {
                    tempEval = minimax(depth + 1);
                    tempEval.player = game.getTurn();
                    game.put(game.toString(), tempEval);
                }
                if (!game.getTurn()) {
                    if (tempEval.score > result.score) {
                        result.bestMove = i;
                        result.depth = tempEval.depth;
                        result.score = tempEval.score;
                    } else if (tempEval.score == result.score && tempEval.depth < result.depth) {
                    	result.bestMove = i;
                        result.depth = tempEval.depth;
                        result.score = tempEval.score;
                    }
                } else {
                	if (tempEval.score < result.score) {
	                    result.bestMove = i;
	                    result.depth = tempEval.depth;
	                    result.score = tempEval.score;
                	} else if (tempEval.score == result.score && tempEval.depth < result.depth) {
	                    result.bestMove = i;
	                    result.depth = tempEval.depth;
	                    result.score = tempEval.score;
                	}
                }
                game.undoMove(i);
            }
        }
        return result;
    }
}