package chess;
import java.util.*;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    ChessPiece[][] board = new ChessPiece[8][8];

    public ChessBoard() {
        
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        board[position.getRow() - 1][position.getColumn() - 1] = piece;
    }
    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return board[position.getRow() - 1][position.getColumn() - 1];
    }
    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        board = new ChessPiece[8][8];
    }

    @Override
    public String toString() {
        int column = 0;
        int row = 0;
        String board_row = null;
        while (row < 8) {
            board_row = board_row + String.format("[%d][%d]: color = s%, type = %s", row, column, board[row][column].getTeamColor().toString(),board[row][column].getPieceType().toString());
            if (column == 7) {
                board_row = board_row + "%n";
                column = 0;
                row++;
                continue;
            }
            column++;
        }
        return board_row;
    }
}
