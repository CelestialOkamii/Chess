package chess;

import java.util.*;

import chess.moves.*;

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
        ChessGame.TeamColor piecesColor = board.getPiece(myPosition).getTeamColor();
        if (piece == PieceType.KING) {
            return new KingMoves(myPosition, piecesColor, board).validMoves();
        }
        else if (piece == PieceType.QUEEN) {
            return new QueenMoves(myPosition, piecesColor, board).validMoves();
        }
        else if (piece == PieceType.BISHOP) {
            return new BishopMoves(myPosition, piecesColor, board).validMoves();
        }
        else if (piece == PieceType.ROOK) {
            return new RookMoves(myPosition, piecesColor, board).validMoves();
        }
        else if (piece == PieceType.KNIGHT) {
            return new KnightMoves(myPosition, piecesColor, board).validMoves();
        }
        else if (piece == PieceType.PAWN) {
            return new PawnMoves(myPosition, piecesColor, board).validMoves();
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
        int colorInt = pieceColor.hashCode();
        int typeInt  = type.hashCode();
        int hash = 17;
        hash = 31 * hash + colorInt;
        hash = 31 * hash + typeInt;
        return hash;
    }
}
