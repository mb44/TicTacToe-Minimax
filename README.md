# Tic-Tac-Toe-Minimax
Tic Tac Toe with a perfect play AI (using Minimax). The AI evaluates all possible positions once, and then later uses a hash table to look up score/best move for a certain position. The game uses two bitboards to represent the board and bit operations to perform operations such as move, undo move, get legal moves etc (see: https://en.wikipedia.org/wiki/Bitboard). The game has a graphical user interface. Everything is coded in Java. To make you own AI you can extend BaseAI (or PerfectPlayAI) class and overwrite the move method. Also, if you AI class extends BaseAI, you must also update the TicTacToe constructor.

See the image below for a UML class diagram (click the image for larger version):


![Alt text](https://github.com/mb44/TicTacToe-Minimax/blob/master/TicTacToe-ClassDiagram.png?raw=true "Optional Title")
