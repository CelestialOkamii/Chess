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
    public Collection<ChessMove> checkValidity(ChessBoard board, Collection<ChessMove> moves,
                                               ChessPosition kingPos, Map<ChessPosition, ChessPiece> oppPiecePos, ChessPiece piece) {
        List<ChessMove> badMove = new ArrayList<>();
        boolean bad = false;
        if (piece.getPieceType() == ChessPiece.PieceType.KING) {
            board.addPiece(kingPos, null);
            badMove.addAll(kingBadMove(board, moves, oppPiecePos, piece));
            board.addPiece(kingPos, piece);
        }
        else {
            badMove.addAll(pieceBadMove(board, moves, kingPos, oppPiecePos, piece));
        }
        for (ChessMove move : badMove) {
            moves.remove(move);
        }
        return moves;
    }


    List<ChessMove> kingBadMove(ChessBoard board, Collection<ChessMove> moves,
                                Map<ChessPosition, ChessPiece> oppPiecePos, ChessPiece piece) {
        List<ChessMove> badMove = new ArrayList<>();
        boolean bad = false;
        for (ChessMove move : moves) {
            bad = false;
            ChessPiece oppPiece = board.getPiece(move.getEndPosition());
            board.addPiece(move.getEndPosition(), piece);
            ChessPosition kingEndPos = move.getEndPosition();
            for (Map.Entry<ChessPosition, ChessPiece> pair : oppPiecePos.entrySet()) {
                Collection<ChessMove> oppMoves = pair.getValue().pieceMoves(board, pair.getKey());
                if (pair.getKey().equals(move.getEndPosition())) {
                    continue;
                }
                for (ChessMove oppMove : oppMoves) {
                    if (oppMove.getEndPosition().equals(kingEndPos)) {
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
        }
        return badMove;
    }


    List<ChessMove> pieceBadMove(ChessBoard board, Collection<ChessMove> moves,
                                 ChessPosition kingPos, Map<ChessPosition, ChessPiece> oppPiecePos, ChessPiece piece) {
        List<ChessMove> badMove = new ArrayList<>();
        boolean bad = false;
        for (ChessMove move : moves) {
            bad = false;
            board.addPiece(move.getStartPosition(), null);
            ChessPiece oppPiece = board.getPiece(move.getEndPosition());
            board.addPiece(move.getEndPosition(), piece);
            for (Map.Entry<ChessPosition, ChessPiece> pair : oppPiecePos.entrySet()) {
                Collection<ChessMove> oppMoves = pair.getValue().pieceMoves(board, pair.getKey());
                for (ChessMove oppMove : oppMoves) {
                    if (oppMove.getStartPosition().equals(move.getEndPosition())) {
                        continue;
                    }
                    if (oppMove.getEndPosition().equals(kingPos) || (pair.getValue().getPieceType() == ChessPiece.PieceType.KING &&
                            move.getEndPosition().equals(pair.getKey()))) {
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
        return badMove;
    }


    /**
     * Determines if a move will put the teams king in check or fail to get it out of check and if either condition is true returns false
     */
    public boolean isValid(ChessBoard board, ChessMove move, ChessPiece piece, ChessPosition kingPos, Map<ChessPosition, ChessPiece> oppPiecePos) {
        board.addPiece(move.getStartPosition(), null);
        for (Map.Entry<ChessPosition, ChessPiece> pair : oppPiecePos.entrySet()) {
            Collection<ChessMove> oppMoves = pair.getValue().pieceMoves(board, pair.getKey());
            for (ChessMove oppMove : oppMoves) {
                if (oppMove.getStartPosition().equals(move.getEndPosition())) {
                    continue;
                }
                if (oppMove.getEndPosition().equals(kingPos)) {
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
    public boolean createsEnding(ChessBoard board, Map<ChessPosition, ChessPiece> ourTeam,
                                 Map<ChessPosition, ChessPiece> oppTeam, ChessPosition kingPos) {
        for (Map.Entry<ChessPosition, ChessPiece> pair : ourTeam.entrySet()) {
            Collection<ChessMove> posMoves = pair.getValue().pieceMoves(board, pair.getKey());
            Collection<ChessMove> movesLeft = checkValidity(board, posMoves, kingPos, oppTeam, pair.getValue());
            if (!movesLeft.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
