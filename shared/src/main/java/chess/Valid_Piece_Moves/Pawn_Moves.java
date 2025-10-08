package chess.Valid_Piece_Moves;

import chess.*;

import java.util.*;
import static java.util.Arrays.asList;

public class Pawn_Moves {
    private final ChessPosition position;
    private final ChessGame.TeamColor color;
    private final ChessBoard board;
    private final ArrayList<ChessPosition> promotionPositions = new ArrayList<>();

    public Pawn_Moves(ChessPosition position, ChessGame.TeamColor color, ChessBoard board) {
        this.position = position;
        this.color = color;
        this.board = board;
    }

    public Collection<ChessMove> valid_moves() {
        Collection<ChessMove> mainMoves = getMainMoves();
        if (!promotionPositions.isEmpty()) {
            for (ChessPosition position : promotionPositions) {
                Collection<ChessMove> promoMoves = getPromoMoves(position);
                mainMoves.addAll(promoMoves);
            }
        }
        return mainMoves;
    }


    private Collection<ChessMove> getMainMoves() {
        Collection<ChessMove> moves = new ArrayList<>();
        boolean oneAhead = false;
        int row = position.getRow();
        int column = position.getColumn();
        if (color == ChessGame.TeamColor.WHITE) {
            if (row < 9) {
                for (int i = -1; i < 2; i++) {
                    if (column + i > 0 && column + i < 9 && row + 1 < 9) {
                        ChessPosition posPosition = new ChessPosition(row + 1, column + i);
                        ChessPiece piece = board.getPiece(posPosition);
                        if ((piece == null && i == 0) || (piece != null && i != 0 && color != piece.getTeamColor())) {
                            ChessMove move = new ChessMove(position, posPosition, null);
                            moves.add(move);
                            if (row + 1 == 8) {
                                promotionPositions.add(posPosition);
                                moves.remove(move);
                            }
                            if (i == 0 && row + 2 < 8) {
                                oneAhead = true;
                                posPosition = new ChessPosition(row + 2, column + i);
                                piece = board.getPiece(posPosition);
                            }
                        }
                        if (row == 2 && oneAhead && i == 0 && piece == null) {
                            ChessMove move = new ChessMove(position, posPosition, null);
                            moves.add(move);
                        }
                    }
                }
            }
        }
        else {
            if (row > 0) {
                for (int i = -1; i < 2; i++) {
                    if (column + i > 0 && column + i < 9 && row - 1 > 0) {
                        ChessPosition posPosition = new ChessPosition(row - 1, column + i);
                        ChessPiece piece = board.getPiece(posPosition);
                        if ((piece == null && i == 0) || (piece != null && i != 0 && color != piece.getTeamColor())) {
                            ChessMove move = new ChessMove(position, posPosition, null);
                            moves.add(move);
                            if (row - 1 == 1) {
                                promotionPositions.add(posPosition);
                                moves.remove(move);
                            }
                            if (i == 0 && row - 2 > 0) {
                                oneAhead = true;
                                posPosition = new ChessPosition(row - 2, column + i);
                                piece = board.getPiece(posPosition);
                            }
                        }
                        if (row == 7 && oneAhead && i == 0 && piece == null) {
                            ChessMove move = new ChessMove(position, posPosition, null);
                            moves.add(move);
                        }
                    }
                }
            }
        }
        return moves;
    }


    private Collection<ChessMove> getPromoMoves(ChessPosition pos) {
        Collection<ChessMove> moves = new ArrayList<>();
        ArrayList<ChessPiece.PieceType> pieces = new ArrayList<>(asList(ChessPiece.PieceType.ROOK, ChessPiece.PieceType.KNIGHT, ChessPiece.PieceType.BISHOP, ChessPiece.PieceType.QUEEN));
        for (ChessPiece.PieceType piece : pieces) {
            ChessMove move = new ChessMove(position, pos, piece);
            moves.add(move);
        }
        return moves;
    }



    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pawn_Moves pawnMoves = (Pawn_Moves) o;
        return Objects.equals(board, pawnMoves.board) && color == pawnMoves.color && Objects.equals(position, pawnMoves.position) && Objects.equals(promotionPositions, pawnMoves.promotionPositions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, color, position, promotionPositions);
    }
}
