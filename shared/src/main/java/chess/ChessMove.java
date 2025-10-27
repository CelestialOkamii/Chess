package chess;
import java.util.*;

/**
 * Represents moving a chess piece on a chessboard
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessMove {

    private final ChessPosition startPosition;
    private final ChessPosition endPosition;
    private final ChessPiece.PieceType promotionPiece;

    public ChessMove(ChessPosition startPosition, ChessPosition endPosition,
                     ChessPiece.PieceType promotionPiece) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.promotionPiece = promotionPiece;
    }

    /**
     * @return ChessPosition of starting location
     */
    public ChessPosition getStartPosition() {
        return startPosition;
    }

    /**
     * @return ChessPosition of ending location
     */
    public ChessPosition getEndPosition() {
        return endPosition;
    }

    /**
     * Gets the type of piece to promote a pawn to if pawn promotion is part of this
     * chess move
     *
     * @return Type of piece to promote a pawn to, or null if no promotion
     */
    public ChessPiece.PieceType getPromotionPiece() {
        return promotionPiece;
    }

    @Override
    public String toString() {
        return String.format("Start Position: %s, End Position: %s", startPosition, endPosition);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        ChessMove pos = (ChessMove) obj;
        boolean startAndEnd = Objects.equals(startPosition, pos.startPosition) && Objects.equals(endPosition, pos.endPosition);
        return startAndEnd && Objects.equals(promotionPiece, pos.promotionPiece);
    }

    @Override
    public int hashCode() {
        int startInt = startPosition.getRow() * 8 + startPosition.getColumn();
        int endInt   = endPosition.getRow() * 8 + endPosition.getColumn();
        int promoInt = (promotionPiece != null) ? promotionPiece.ordinal() : -1;
        int hash = 17;
        hash = 31 * hash + startInt;
        hash = 31 * hash + endInt;
        hash = 31 * hash + promoInt;
        return hash;
    }
}
