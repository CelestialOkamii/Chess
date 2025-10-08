package chess;

import java.util.*;

import static java.util.Arrays.asList;

public class ChessRules {

    public ChessRules() {
    }

    /**
     * Takes a Collection(ArrayList) of moves and removes any moves that would put the king in check or that
     * wouldn't get the king out of check if it's currently in check
     */
    public Collection<ChessMove> checkValidity(ChessBoard board, Collection<ChessMove> moves, ChessPosition kingPos, Map<ChessPiece, ChessPosition> oppPiecePos, ChessPiece piece) {
        List<ChessMove> badMove = new ArrayList<>();
        boolean bad = false;
        if (piece.getPieceType() == ChessPiece.PieceType.KING) {
            for (ChessMove move : moves) {
                bad = false;
                ChessPosition kingEndPos = move.getEndPosition();
                for (Map.Entry<ChessPiece, ChessPosition> pair : oppPiecePos.entrySet()) {
                    Collection<ChessMove> oppMoves = pair.getKey().pieceMoves(board, pair.getValue());
                    for (ChessMove oppMove : oppMoves) {
                        if (oppMove.getEndPosition().equals(kingEndPos) || (pair.getKey().getPieceType() == ChessPiece.PieceType.KING && move.getEndPosition().equals(pair.getValue()))) {
                            badMove.add(move);
                            bad = true;
                            break;
                        }
                    }
                    if (bad) {
                        break;
                    }
                }
            }
        }
        else {
            for (ChessMove move : moves) {
                bad = false;
                board.addPiece(move.getStartPosition(), null);
                ChessPiece oppPiece = board.getPiece(move.getEndPosition());
                board.addPiece(move.getEndPosition(), piece);
                for (Map.Entry<ChessPiece, ChessPosition> pair : oppPiecePos.entrySet()) {
                    Collection<ChessMove> oppMoves = pair.getKey().pieceMoves(board, pair.getValue());
                    for (ChessMove oppMove : oppMoves) {
                        if (oppMove.getStartPosition() == move.getEndPosition()) {
                            continue;
                        }
                        if (oppMove.getEndPosition().equals(kingPos) || (pair.getKey().getPieceType() == ChessPiece.PieceType.KING && move.getEndPosition().equals(pair.getValue()))) {
                            badMove.add(move);
                            bad = true;
                            break;
                        }
                    }
                    if (bad) {
                        break;
                    }
                }
                board.addPiece(move.getEndPosition(), oppPiece);
                board.addPiece(move.getStartPosition(), piece);
            }
        }
        for (ChessMove move : badMove) {
            moves.remove(move);
        }
        return moves;
    }


    /**
     * Determines if a move will put the teams king in check or fail to get it out of check and if either condition is true returns false
     */
    public boolean isValid(ChessBoard board, ChessMove move, ChessPiece piece, ChessPosition kingPos, Map<ChessPiece, ChessPosition> oppPiecePos) {
        board.addPiece(move.getStartPosition(), null);
        for (Map.Entry<ChessPiece, ChessPosition> pair : oppPiecePos.entrySet()) {
            Collection<ChessMove> oppMoves = pair.getKey().pieceMoves(board, pair.getValue());
            for (ChessMove oppMove : oppMoves) {
                if (oppMove.getStartPosition() == move.getEndPosition()) {
                    continue;
                }
                if (oppMove.getEndPosition() == kingPos || (pair.getKey().getPieceType() == ChessPiece.PieceType.KING && move.getEndPosition() == pair.getValue())) {
                    board.addPiece(move.getStartPosition(), piece);
                    return false;
                }
            }
        }
        board.addPiece(move.getStartPosition(), piece);
        return true;
    }


    /**
     * Determines if this move will put the opposing team in check
     */
    public boolean createsCheck(ChessBoard board, ChessPosition pos, ChessPosition oppKingPos) {
        ChessPiece currPiece = board.getPiece(pos);
        Collection<ChessMove> newMoves = currPiece.pieceMoves(board, pos);
        for (ChessMove possMove : newMoves) {
            if (possMove.getEndPosition().equals(oppKingPos)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Determines if this move will cause the other team to be in checkmate or stalemate
     */
    public boolean createsEnding(ChessBoard board, Map<ChessPiece, ChessPosition> ourTeam, Map<ChessPiece, ChessPosition> oppTeam, ChessPosition oppKingPos) {
        for (Map.Entry<ChessPiece, ChessPosition> pair : oppTeam.entrySet()) {
            Collection<ChessMove> posMoves = pair.getKey().pieceMoves(board, pair.getValue());
            Collection<ChessMove> movesLeft = checkValidity(board, posMoves, oppKingPos, ourTeam, pair.getKey());
            if (!movesLeft.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
