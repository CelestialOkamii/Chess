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
        int current_row = position.getRow();
        int current_column = position.getColumn();
        List<ChessPosition> moves_to_try = valid_positions();
        List<ChessPosition> possible_positions = new ArrayList<>();
        List<ChessPosition> no_go_past = new ArrayList<>();
        for (ChessPosition item : moves_to_try) {
            int row = item.getRow();
            int column = item.getColumn();
            List<ChessPosition> valid_boundaries = valid_move(row, column, no_go_past);
            //if piece is same color as Queen will return position of [1,1]
            //if opposing color will return position of [0,0] for valid_boundaries.get(1)
            //if there is no piece will return position [2,2]
            //if position isn't valid will return [-1,-1] for valid_boundaries.get(0)
            int valid_row = valid_boundaries.get(0).getRow();
            int piece_type = valid_boundaries.get(1).getRow();
            if (valid_row > 0 && valid_row < 7 && piece_type != 1) {
                ChessMove valid = new ChessMove(position, valid_boundaries.get(0), null);
                moves.add(valid);
            }
            if (piece_type == 1 || piece_type == 0) {
                if (current_row >= row && current_column != column) {
                    Iterator<ChessPosition> it = possible_positions.iterator();
                    while (it.hasNext()) {
                        ChessPosition pos = it.next();
                        if (piece_type == 1) {
                            if (pos.getColumn() < column || pos.getColumn() > column) {
                                it.remove();
                            }
                        }
                        else {
                            if (pos.getColumn() <= column || pos.getColumn() >= column) {
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
                            if (piece_type == 1 && pos.getRow() == current_row) {
                                if (pos.getColumn() < column) {
                                    it.remove();
                                }
                            }
                            else {
                                if (pos.getColumn() <= column) {
                                    it.remove();
                                }
                            }
                        }
                    }
                }
                else if (current_column == column) {
                    if (row < current_row) {
                        Iterator<ChessPosition> it = possible_positions.iterator();
                        while (it.hasNext()) {
                            ChessPosition pos = it.next();
                            if (piece_type == 1 && pos.getColumn() == current_column) {
                                if (pos.getRow() < row) {
                                    it.remove();
                                }
                            }
                            else {
                                if (pos.getRow() <= row) {
                                    it.remove();
                                }
                            }
                        }
                    }
                }
                else {
                    no_go_past.add(valid_boundaries.get(0));
                }
            }
        }
        return moves;
    }

    private List<ChessPosition> valid_move(int row, int column, List<ChessPosition> no_go_past) {
        int current_row = position.getRow();
        int current_column = position.getColumn();
        List<ChessPosition> move_piece_color = new ArrayList<>();
        ChessPosition pos = new ChessPosition(row,column);
        for (ChessPosition bad_spot : no_go_past) {
            int bad_row = bad_spot.getRow();
            int bad_column = bad_spot.getColumn();
            ChessPosition bad_move = new ChessPosition(-1,-1);
            if ((row > bad_row && row != current_row) && (column != current_column && (column > bad_column  && column > current_column|| column < bad_column && column < current_column))) {
                move_piece_color.add(bad_move);
                if (board.getPiece(pos) != null) {
                    ChessGame.TeamColor color = board.getPiece(pos).getTeamColor();
                    if (color == board.getPiece(position).getTeamColor()) {
                        ChessPosition piece = new ChessPosition(1,1);
                        move_piece_color.add(piece);
                        return move_piece_color;
                    }
                    else {
                        ChessPosition piece = new ChessPosition(0,0);
                        move_piece_color.add(piece);
                        return move_piece_color;
                    }
                }
                else {
                    ChessPosition empty = new ChessPosition(2,2);
                    move_piece_color.add(empty);
                    return move_piece_color;
                }
            }
            else if ((bad_row == current_row && bad_column < column) || (bad_column == current_column && row > bad_row)) {
                move_piece_color.add(bad_move);
                move_piece_color.add(bad_move);
                return move_piece_color;
            }
            move_piece_color.add(pos);
            if (board.getPiece(pos) != null) {
                ChessGame.TeamColor color = board.getPiece(pos).getTeamColor();
                if (color == board.getPiece(position).getTeamColor()) {
                    ChessPosition piece = new ChessPosition(1,1);
                    move_piece_color.add(piece);
                    return move_piece_color;
                }
                else {
                    ChessPosition piece = new ChessPosition(0,0);
                    move_piece_color.add(piece);
                    return move_piece_color;
                }
            }
            else {
                ChessPosition empty = new ChessPosition(2,2);
                move_piece_color.add(empty);
                return move_piece_color;
            }
        }
        return move_piece_color;
    }

    private List<ChessPosition> valid_positions() {
        List<ChessPosition> positions = new ArrayList<>();
        int column = position.getColumn();
        for (int i = position.getRow() + 1; i < 8; i++) {
            if (i + 1 < 8) {
                ChessPosition pos = new ChessPosition(i, i + 1);
                positions.add(pos);
            }
            if (column - 1 > -1) {
                ChessPosition pos = new ChessPosition(i, i - 1);
                positions.add(pos);
                column--;
            }
        }
        column = position.getColumn();
        for (int i = position.getRow() - 1; i > -1; i--) {
            if (column + 1 < 8) {
                ChessPosition pos = new ChessPosition(i, i + 1);
                positions.add(pos);
            }
            if (column - 1 > -1) {
                ChessPosition pos = new ChessPosition(i, i - 1);
                positions.add(pos);
            }
        }
        for (int i = 0; i < 8; i++) {
            ChessPosition col = new ChessPosition(i, position.getColumn());
            positions.add(col);
            ChessPosition row = new ChessPosition(position.getRow(), i);
            positions.add(row);
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
