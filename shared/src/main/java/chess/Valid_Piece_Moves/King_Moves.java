package chess.Valid_Piece_Moves;

import java.util.*;
import chess.*;

public class King_Moves {

    private final ChessPosition position;
    private final ChessGame.TeamColor color;
    private final ChessBoard board;

    public King_Moves(ChessPosition position, ChessGame.TeamColor color, ChessBoard board) {
        this.position = position;
        this.color = color;
        this.board = board;
    }

    public List<ChessMove> valid_moves() {
        List<ChessMove> moves = new ArrayList<>();
        int current_row = position.getRow();
        int current_column = position.getColumn();
        for (int row = current_row -1; row <= current_row + 1; row++) {
            for (int column = current_column - 1; column <= current_column + 1; column++) {
                if (row < 1 || row > 8 || column < 1 || column > 8) {
                    continue;
                }
                if (row == current_row && column == current_column) {
                    continue;
                }
                if (valid_move(row, column)) {
                    ChessPosition coordinate = new ChessPosition(row,column);
                    ChessMove move = new ChessMove(position, coordinate, null);
                    moves.add(move);
                }
            }
        }
        return moves;
    }

    public boolean valid_move(int row, int column) {
        ChessPosition new_position = new ChessPosition(row, column);
        ChessPiece piece = board.getPiece(new_position);
        if (piece == null) {
            return true;
        }
        return board.getPiece(new_position).getTeamColor() != color;
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
