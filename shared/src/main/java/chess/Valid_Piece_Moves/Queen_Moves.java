package chess.Valid_Piece_Moves;

import chess.*;

import java.util.*;

public class Queen_Moves {

    private ChessPosition position;
    private ChessGame.TeamColor color;
    private ChessBoard board;

    public Queen_Moves(ChessPosition position, ChessGame.TeamColor color, ChessBoard board) {
        this.position = position;
        this.color = color;
        this.board = board;
    }

    public List<ChessMove> valid_moves() {
        List<ChessMove> moves = new ArrayList<>();
        List<ChessPosition> valid_moves = valid_positions();
        for (ChessPosition move : valid_moves) {
            ChessMove pos_move = new ChessMove(position, move, null);
            moves.add(pos_move);
        }
        return moves;
    }

    private List<ChessPosition> valid_positions() {
        int[][] paths = {{1,1}, {-1,1}, {0,-1}, {-1,0}, {1,0}, {1,-1}, {-1,-1}, {0,1}};
        List<ChessPosition> positions = new ArrayList<>();
        for (int[] course : paths) {
            int row = position.getRow();
            int column = position.getColumn();
            while (true) {
                row = row + course[0];
                column = column + course[1];
                if (row < 1 || row > 8 || column < 1 || column > 8) {
                    break;
                }
                ChessPosition pos = new ChessPosition(row, column);
                if (board.getPiece(pos) == null) {
                    positions.add(pos);
                }
                else {
                    if (board.getPiece(pos).getTeamColor() != board.getPiece(position).getTeamColor()) {
                        positions.add(pos);
                    }
                    break;
                }
            }
        }
        return positions;
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;
        Queen_Moves pos = (Queen_Moves) obj;
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
