package chess;

import java.util.*;

public class ChessRules {

    public ChessRules() {
    }

    public Collection<ChessMove> checkValidity(ChessBoard board, Collection<ChessMove> moves, ChessPosition kingPos, Map<ChessPiece, ChessPosition> oppPiecePos, ChessPiece piece) {
        List<ChessMove> badMove = new ArrayList<>();
        if (piece.getPieceType() == ChessPiece.PieceType.KING) {
            for (ChessMove move : moves) {
                ChessPosition kingEndPos = move.getEndPosition();
                for (Map.Entry<ChessPiece, ChessPosition> pair : oppPiecePos.entrySet()) {
                    Collection<ChessMove> oppMoves = pair.getKey().pieceMoves(board, pair.getValue());
                    for (ChessMove oppMove : oppMoves) {
                        if (oppMove.getEndPosition() == kingEndPos) {
                            badMove.add(move);
                        }
                    }
                }
            }
        }
        else {
            for (ChessMove move : moves) {
                board.addPiece(move.getStartPosition(), null);
                for (Map.Entry<ChessPiece, ChessPosition> pair : oppPiecePos.entrySet()) {
                    Collection<ChessMove> oppMoves = pair.getKey().pieceMoves(board, pair.getValue());
                    for (ChessMove oppMove : oppMoves) {
                        if (oppMove.getStartPosition() == move.getEndPosition()) {
                            continue;
                        }
                        if (oppMove.getEndPosition() == kingPos) {
                            badMove.add(move);
                        }
                    }
                }
                board.addPiece(move.getStartPosition(), piece);
            }
        }
        for (ChessMove move : badMove) {
            moves.remove(move);
        }
        return moves;
    }

    public boolean isValid(ChessBoard board, ChessMove move, ChessPiece piece, ChessPosition kingPos, Map<ChessPiece, ChessPosition> oppPiecePos) {
        board.addPiece(move.getStartPosition(), null);
        for (Map.Entry<ChessPiece, ChessPosition> pair : oppPiecePos.entrySet()) {
            Collection<ChessMove> oppMoves = pair.getKey().pieceMoves(board, pair.getValue());
            for (ChessMove oppMove : oppMoves) {
                if (oppMove.getStartPosition() == move.getEndPosition()) {
                    continue;
                }
                if (oppMove.getEndPosition() == kingPos) {
                    board.addPiece(move.getStartPosition(), piece);
                    return false;
                }
            }
        }
        board.addPiece(move.getStartPosition(), piece);
        return true;
    }


}
