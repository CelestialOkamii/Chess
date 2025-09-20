package chess;

import java.util.*;
import chess.Valid_Piece_Moves.*;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {


    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        PieceType piece = board.getPiece(myPosition).getPieceType();
        ChessGame.TeamColor piece_color = board.getPiece(myPosition).getTeamColor();
        if (piece == PieceType.KING) {
            List<ChessMove> my_moves = new King_Moves(myPosition, piece_color, board).valid_moves();
            return my_moves;
        }
        else if (piece == PieceType.QUEEN) {
            List<ChessMove> my_moves = new Queen_Moves(myPosition, piece_color, board).valid_moves();
            return my_moves;
        }
        else if (piece == PieceType.BISHOP) {
            List<ChessMove> my_moves = new Bishop_Moves(myPosition, piece_color, board).valid_moves();
            return my_moves;
        }
        else if (piece == PieceType.ROOK) {
            List<ChessMove> my_moves = new Rook_Moves(myPosition, piece_color, board).valid_moves();
            return my_moves;
        }
        else if (piece == PieceType.KNIGHT) {
            List<ChessMove> my_moves = new Knight_Moves(myPosition, piece_color, board).valid_moves();
            return my_moves;
        }
        else if (piece == PieceType.PAWN) {
            List<ChessMove> my_moves = new Pawn_Moves(myPosition, piece_color, board).valid_moves();
            return my_moves;
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;
        ChessPiece pos = (ChessPiece) obj;
        return (pieceColor.equals(pos.pieceColor) && type.equals(pos.type));
    }

    @Override
    public int hashCode() {
        int color_int = pieceColor.hashCode();
        int type_int   = type.hashCode();
        int hash = 17;
        hash = 31 * hash + color_int;
        hash = 31 * hash + type_int;
        return hash;
    }
}
