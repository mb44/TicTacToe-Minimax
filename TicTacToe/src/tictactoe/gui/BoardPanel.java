/**
 * @author Morten Beuchert
 */
package tictactoe.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import tictactoe.game.GameState;
import tictactoe.gui.TicTacToeGUI;

public class BoardPanel
extends JPanel {
    private TicTacToeGUI gui;
    private BufferedImage cross;
    private BufferedImage nought;

    public BoardPanel(TicTacToeGUI gui) {
        this.gui = gui;
        cross = null;
        nought = null;
        try {
        	// Use this to load the images from the file system
        	//cross = ImageIO.read(new File("C:\\src\\MyProjects\\TicTacToe\\cross.png"));
            //nought = ImageIO.read(new File("C:\\src\\MyProjects\\TicTacToe\\nought.png"));
        	
            // Use this to load the images from a JAR file
            cross = ImageIO.read(this.getClass().getResource("/ressources/cross.png"));
            nought = ImageIO.read(this.getClass().getResource("/ressources/nought.png"));
        }
        catch (IOException e) {
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.paintBackground(g);
        this.paintBoard(g);
        this.paintText(g);
    }

    private void paintBackground(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 400, 400);
        g.setColor(Color.WHITE);
        g.drawLine(0, 0, 400, 0);
        g.drawLine(0, 133, 400, 133);
        g.drawLine(0, 266, 400, 266);
        g.drawLine(133, 0, 133, 400);
        g.drawLine(266, 0, 266, 400);
    }

    private void paintBoard(Graphics g) {
        int[] board = gui.getGameState().getBoard();
        for (int i=0; i<board.length; i++) {
            int y;
            int x;
            if (board[i] == 1) {
                x = i % 3 * 133 + 33;
                y = i / 3 * 133 + 33;
                g.drawImage(cross, x, y, null);
            } else if (board[i] == 2) {
                x = i % 3 * 133 + 33;
                y = i / 3 * 133 + 33;
                g.drawImage(nought, x, y, null);
            }
        }
    }

    private void paintText(Graphics g) {
        g.setColor(Color.GREEN);
        g.setFont(new Font("Serif", 1, 44));
        int x = 80;
        int y = 150;
        if (gui.getGameState().getGameOver()) {
            if (gui.getGameState().getWinner() == -1) {
                g.drawString("Computer Won!", x - 20, y);
            } else if (gui.getGameState().getWinner() == 1) {
                g.drawString("You won!", x + 30, y);
            } else if (gui.getGameState().getWinner() == 0) {
                g.drawString("No winner!", x + 20, y);
            }
        }
    }
}