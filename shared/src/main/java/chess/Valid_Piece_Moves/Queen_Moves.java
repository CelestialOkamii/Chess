package chess.Valid_Piece_Moves;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

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
        int current_row = position.getRow();
        int current_column = position.getColumn();
        int row = 0;
        int column = 0;
        List<ChessPosition> possible_positions = new ArrayList<>();
        //no_go_column tells the column of where a piece is so the queen knows where it has to stop diagonally or vertically from its position
        //no_go_row tells me where the queen needs to stop horizontally from its position
        int no_go_up = 0;
        int no_go_down = 0;
        int no_go_right = 0;
        int no_go_left = 0;
        int ng_upper_left = 0;
        int ng_bottom_left = 0;
        int ng_upper_right = 0;
        int ng_bottom_right = 0;
        while (row <= 8) {
            List<ChessPosition> valid_boundaries = valid_move(row, column);
            //if piece is same color as Queen will return position of [1,1]
            //if opposing color will return position of [0,0] for valid_boundaries.get(1)
            //if there si no piece will return position [2,2]
            //if position isn't valid will return [-1,-1] for valid_boundaries.get(0)
            int valid_row = valid_boundaries.get(0).getRow();
            int piece_type = valid_boundaries.get(1).getRow();
            if (valid_row > 0 && valid_row < 7) {
                possible_positions.add(valid_boundaries.get(0))
            }
            if (piece_type == 1 || piece_type == 0) {
                if (current_row != row && current_column != column) {
                    if (column < current_column) {

                        Iterator<ChessPosition> it = possible_positions.iterator();
                        while (it.hasNext()) {
                            ChessPosition pos = it.next();
                            if (pos.getColumn() < column) {
                                it.remove();
                            }
                        }
                    }
                    else {
                        Iterator<ChessPosition> it = possible_positions.iterator();
                        while (it.hasNext()) {
                            ChessPosition pos = it.next();
                            if (pos.getColumn() > column) {
                                it.remove();
                            }
                        }
                    }
                }
                else if (current_row == row) {
                    if (column < current_column) {
                        Iterator<ChessPosition> it = possible_positions.iterator();
                        while (it.hasNext()) {
                            ChessPosition pos = it.next();
                            if (pos.getColumn() < column) {
                                it.remove();
                            }
                        }
                    }
                    else {
                        Iterator<ChessPosition> it = possible_positions.iterator();
                        while (it.hasNext()) {
                            ChessPosition pos = it.next();
                            if (pos.getColumn() > column) {
                                it.remove();
                            }
                        }
                    }
                }
                else if (current_column == column) {
                    if (row < current_row) {
                        Iterator<ChessPosition> it = possible_positions.iterator();
                        while (it.hasNext()) {
                            ChessPosition pos = it.next();
                            if (pos.getRow() < row) {
                                it.remove();
                            }
                        }
                    }
                    else {
                        Iterator<ChessPosition> it = possible_positions.iterator();
                        while (it.hasNext()) {
                            ChessPosition pos = it.next();
                            if (pos.getRow() > row) {
                                it.remove();
                            }
                        }
                    }
                }
            }
            column++;
            if (column > 8 && row < 7) {
                column = 0;
                row++;
            }
        }
        return moves;
    }

    public List<ChessPosition> valid_move(int row, int column) {

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
