package chess;

import java.util.*;

public class ChessRules {

    public ChessRules() {
    }

    public Collection<ChessMove> checkValidity(ChessBoard board, Collection<ChessMove> moves, ChessPosition kingPos, Map<ChessPiece, ChessPosition> oppPiecePos, ChessPiece.PieceType type) {
        List<ChessMove> badMove = new ArrayList<>();
        if (type == ChessPiece.PieceType.KING) {
            for (ChessMove move : moves) {
                ChessPosition kingEndPos = move.getEndPosition();
                board.addPiece(move.getStartPosition(), null);
                for (Map.Entry<ChessPiece, ChessPosition> pair : oppPiecePos.entrySet()) {
                    Collection<ChessMove> oppMoves = pair.getKey().pieceMoves(board, pair.getValue());
                    for (ChessMove oppMove : oppMoves) {
                        if (oppMove.getEndPosition() == kingEndPos) {
                            badMove.add(oppMove);
                        }
                    }
                }
            }
        }
        else {

        }
        for (ChessMove move : badMove) {
            moves.remove(move);
        }
        return moves;
    }


}
