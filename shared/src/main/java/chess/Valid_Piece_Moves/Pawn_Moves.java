package chess.Valid_Piece_Moves;

import chess.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pawn_Moves {
    private final ChessPosition position;
    private final ChessGame.TeamColor color;
    private final ChessBoard board;
    private final List<Integer> promotion_indexes = new ArrayList<>();

    public Pawn_Moves(ChessPosition position, ChessGame.TeamColor color, ChessBoard board) {
        this.position = position;
        this.color = color;
        this.board = board;
    }

    public List<ChessMove> valid_moves() {
        List<ChessMove> moves = new ArrayList<>();
        List<ChessPosition> valid_moves = valid_positions();
        int position_index = 0;
        for (ChessPosition move : valid_moves) {
            if (!promotion_indexes.isEmpty() && promotion_indexes.size() > position_index) {
                if (valid_moves.indexOf(move) == promotion_indexes.get(position_index)) {
                    List<ChessMove> promotion_moves = promotion_pieces(move);
                    moves.addAll(promotion_moves);
                    position_index++;
                    continue;
                }
            }
            ChessMove pos_move = new ChessMove(position, move, null);
            moves.add(pos_move);
        }
        return moves;
    }

    private List<ChessPosition> valid_positions() {
        List<ChessPosition> positions = new ArrayList<>();
        int current_row = position.getRow();
        int current_column = position.getColumn();
        if (color == ChessGame.TeamColor.WHITE) {
            boolean ahead_clear = false;
            for (int i  = -1; i < 2; i++) {
                if (current_row + 1 <= 8 && current_column + i >= 1 && current_column + i <= 8) {
                    ChessPosition ahead = new ChessPosition(current_row + 1, current_column + i);
                    ChessPiece piece = board.getPiece(ahead);
                    if (piece == null && i == 0) {
                        positions.add(ahead);
                        ahead_clear = true;
                        if (current_row + 1 == 8) {
                            promotion_indexes.add(positions.indexOf(ahead));
                        }
                    }
                    else if (piece != null && i != 0 && piece.getTeamColor() != color) {
                        positions.add(ahead);
                        if (current_row + 1 == 8) {
                            promotion_indexes.add(positions.indexOf(ahead));
                        }
                    }
                }
            }
            if (current_row == 2 && ahead_clear) {
                ChessPosition two_ahead = new ChessPosition(current_row + 2, current_column);
                if (board.getPiece(two_ahead) == null) {
                    positions.add(two_ahead);
                }
            }
        }
        else {
            boolean ahead_clear = false;
            for (int i = - 1; i < 2; i++) {
                if (current_row - 1 >= 1 && current_column - i >= 1 && current_column + i <= 8 && current_column + i >= 1) {
                    ChessPosition ahead = new ChessPosition(current_row - 1, current_column + i);
                    ChessPiece piece = board.getPiece(ahead);
                    if (piece == null && i == 0) {
                        positions.add(ahead);
                        ahead_clear = true;
                        if (current_row - 1 == 1) {
                            promotion_indexes.add(positions.indexOf(ahead));
                        }
                    }
                    else if (piece != null && i != 0 && piece.getTeamColor() != color) {
                        positions.add(ahead);
                        if (current_row - 1 == 1) {
                            promotion_indexes.add(positions.indexOf(ahead));
                        }
                    }
                }
            }
            if (current_row == 7 && ahead_clear) {
                ChessPosition two_ahead = new ChessPosition(current_row - 2, current_column);
                if (board.getPiece(two_ahead) == null) {
                    positions.add(two_ahead);
                }
            }
        }
        return positions;
    }

    private List<ChessMove> promotion_pieces(ChessPosition pos) {
        List<ChessMove> promotion_options = new ArrayList<>();
        List<ChessPiece.PieceType> pieces = new ArrayList<>(Arrays.asList(ChessPiece.PieceType.QUEEN, ChessPiece.PieceType.BISHOP, ChessPiece.PieceType.ROOK, ChessPiece.PieceType.KNIGHT));
        for (ChessPiece.PieceType piece : pieces) {
            ChessMove move = new ChessMove(position, pos, piece);
            promotion_options.add(move);
        }
        return promotion_options;
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;
        Pawn_Moves pos = (Pawn_Moves) obj;
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
