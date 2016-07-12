/**
 * @author Morten Beuchert
 */
package tictactoe.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import tictactoe.game.GameState;
import tictactoe.game.TicTacToe;
import tictactoe.gui.BoardPanel;
import tictactoe.gui.TicTacToeGUI;
import tictactoe.util.ObserverBase;

public class TicTacToeGUI extends JFrame
implements ObserverBase {
    private TicTacToe game;
    private GameState gameState;
    private JPanel topPanel;
    private JButton buttonStart;
    private BoardPanel board;

    public TicTacToeGUI(TicTacToe game) {
        super("Tic Tac Toe");
        this.game = game;
        gameState = game.getState();
        createComponents();
        initializeComponents();
        registerEventHandlers();
        addComponentsToFrame();
        pack();
        setVisible(true);
    }

    private void createComponents() {
        topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(400, 50));
        topPanel.setBackground(Color.BLACK);
        buttonStart = new JButton("Start game");
        buttonStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
    	        if (game.isGameOver()) {
    	            game.restart();
    	            board.repaint();
    	            buttonStart.setText("Start");
    	        }
				
			}
        });
        board = new BoardPanel(this);
        board.setPreferredSize(new Dimension(400, 400));
    }

    private void initializeComponents() {
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);
    }

    private void registerEventHandlers() {
        EventHandler eventHandler = new EventHandler();
        board.addMouseListener((MouseListener)eventHandler);
    }

    private void addComponentsToFrame() {
        topPanel.add(buttonStart);
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add((Component)topPanel, "North");
        mainPanel.add((Component)board, "Center");
        getContentPane().add(mainPanel);
    }

    public void update() {
        gameState = game.getState();
    }

    public GameState getGameState() {
        return gameState;
    }
    
    public class EventHandler implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent event) {
        }

        @Override
        public void mouseEntered(MouseEvent event) {
        }

        @Override
        public void mouseExited(MouseEvent event) {
        }

        @Override
        public void mousePressed(MouseEvent event) {
        }

        @Override
        public void mouseReleased(MouseEvent event) {
            if (!gameState.getGameOver() && gameState.getPlayerTurn()) {
            	// Human move
                int x = event.getX();
                int y = event.getY();
                int column = x / 133;
                int row = y / 133;
                int index = row * 3 + column;
                if (game.isLegalMove(index)) {
                    game.applyMove(index);
                    board.repaint();
                    if (gameState.getGameOver()) {
                        buttonStart.setText("Restart");
                    }
                }
                
                // AI move
                if (!gameState.getGameOver() && !gameState.getPlayerTurn()) {
                	game.moveAI();
                }
                board.repaint();
                if (gameState.getGameOver()) {
                    buttonStart.setText("Restart");
                }
            }
        }
    }
}