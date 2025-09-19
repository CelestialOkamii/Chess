package chess.Valid_Piece_Moves;

import java.util.*;
import chess.*;

public class King_Moves {

    private ChessPosition position;
    private ChessGame.TeamColor color;
    private ChessBoard board;

    public King_Moves(ChessPosition position, ChessGame.TeamColor color, ChessBoard board) {
        this.position = position;
        this.color = color;
        this.board = board;
    }

    public List<ChessMove> valid_moves() {
        List<ChessMove> moves = new ArrayList<>();
        int current_row = position.getRow();
        int current_column = position.getColumn();
        int row = current_row - 1;
        int column = current_column - 1;
        while (row <= current_row + 1) {
            if (column > current_column + 1 && row < current_row + 1) {
                column = current_column - 1;
            }
            if (row > 7 || row < 0 || column > 7 || column < 0) {
                continue;
            }
            else if ( valid_move(row, column) == true) {
                ChessPosition coordinate = new ChessPosition(row,column);
                ChessMove move = new ChessMove(position, coordinate, null);
                moves.add(move);
            }

        }
        return moves;
    }

    public boolean valid_move(int row, int column) {
        ChessPosition new_position = new ChessPosition(row, column);
        if (board.getPiece(new_position).getTeamColor() == color) {
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;
        King_Moves pos = (King_Moves) obj;
        return (position.equals(pos.position) && color.equals(pos.color) && board.equals(pos.board));
    }

    @Override
    public int hashCode() {
        int position_int = position.hashCode();
        int color_int   = color.hashCode();
        int board_int = board.hashCode();
        int hash = 17;
        hash = 31 * hash + position_int;
        hash = 31 * hash + color_int;
        hash = 31 * hash + board_int;
        return hash;
    }
}
