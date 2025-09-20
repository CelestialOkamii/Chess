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
        int[][] paths = {{0,1}, {0,-1}, {-1,0}, {1,0}, {1,-1}, {1,1}, {-1,-1}, {-1,1}};
        List<ChessPosition> positions = new ArrayList<>();
        for (int[] course : paths) {
            int row = position.getRow();
            int column = position.getColumn();
            while (true) {
                row = row + course[0];
                column = column + course[1];
                if (row < 0 || row > 7 || column < 0 || column > 7) {
                    break;
                }
                ChessPosition pos = new ChessPosition(row, column);
                if (board.getPiece(pos) == null) {
                    positions.add(pos);
                }
                else {
                    if (board.getPiece(pos).getTeamColor() == board.getPiece(position).getTeamColor()) {
                        positions.add(pos);
                    }
                    break;
                }
            }
        }
        return positions;
    }

//    private List<Conditions> valid_move(int row, int column, List<ChessPosition> no_go_past) {
//        int current_row = position.getRow();
//        int current_column = position.getColumn();
//        List<Conditions> move_piece_color = new ArrayList<>();
//        ChessPosition pos = new ChessPosition(row,column);
//        if (no_go_past.size() > 0) {
//            for (ChessPosition bad_spot : no_go_past) {
//                int bad_row = bad_spot.getRow();
//                int bad_column = bad_spot.getColumn();
//                if ((row > bad_row && row != current_row) && (column != current_column && (column > bad_column  && column > current_column|| column < bad_column && column < current_column))) {
//                    move_piece_color.add(Conditions.INVALID);
//                    if (board.getPiece(pos) != null) {
//                        ChessGame.TeamColor color = board.getPiece(pos).getTeamColor();
//                        if (color == board.getPiece(position).getTeamColor()) {
//                            move_piece_color.add(Conditions.SAME_COLOR);
//                            return move_piece_color;
//                        }
//                        else {
//                            move_piece_color.add(Conditions.DIFF_COLOR);
//                            return move_piece_color;
//                        }
//                    }
//                    else {
//                        move_piece_color.add(Conditions.EMPTY);
//                        return move_piece_color;
//                    }
//                }
//                else if ((bad_row == current_row && bad_column < column) || (bad_column == current_column && row > bad_row)) {
//                    move_piece_color.add(Conditions.INVALID);
//                    move_piece_color.add(Conditions.EMPTY);
//                    return move_piece_color;
//                }
//            }
//        }
//        move_piece_color.add(Conditions.VALID);
//        if (board.getPiece(pos) != null) {
//            ChessGame.TeamColor color = board.getPiece(pos).getTeamColor();
//            if (color == board.getPiece(position).getTeamColor()) {
//                move_piece_color.add(Conditions.SAME_COLOR);
//                return move_piece_color;
//            }
//            else {
//                move_piece_color.add(Conditions.DIFF_COLOR);
//                return move_piece_color;
//            }
//        }
//        else {
//            move_piece_color.add(Conditions.EMPTY);
//            return move_piece_color;
//        }
//    }
//
//    private List<ChessPosition> valid_positions() {
//        List<ChessPosition> positions = new ArrayList<>();
//        int curr_col = position.getColumn();
//        int curr_row = position.getRow();
//        for (int i = curr_row + 1; i < 8; i++) {
//            int column_down = i - curr_row;
//            if (curr_col + column_down < 8) {
//                ChessPosition pos = new ChessPosition(i, curr_col + column_down);
//                positions.add(pos);
//            }
//            if (curr_col - column_down > -1) {
//                ChessPosition pos = new ChessPosition(i, curr_col - column_down);
//                positions.add(pos);
//            }
//        }
//        for (int i = curr_row - 1; i > -1; i--) {
//            int column_up = curr_row - i;
//            if (curr_col + column_up < 8) {
//                ChessPosition pos = new ChessPosition(i, curr_col + column_up);
//                positions.add(pos);
//            }
//            if (curr_col - column_up > -1) {
//                ChessPosition pos = new ChessPosition(i, curr_col - column_up);
//                positions.add(pos);
//            }
//        }
//        for (int i = 0; i < 8; i++) {
//            if (i != curr_row) {
//                ChessPosition col = new ChessPosition(i, curr_col);
//                positions.add(col);
//                ChessPosition row = new ChessPosition(curr_row, i);
//                positions.add(row);
//            }
//            else {
//
//            }
//        }
//        return positions;
//    }


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
