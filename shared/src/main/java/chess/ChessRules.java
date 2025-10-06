package chess;

import java.util.*;

public class ChessRules {

    public ChessRules() {
    }

    public Collection<ChessMove> checkValidity(ChessBoard board, Collection<ChessMove> moves, Map<ChessPiece, ChessPosition> teamPiecePos, Map<ChessPiece, ChessPosition> oppPiecePos, ChessPiece.PieceType type) {
        List<ChessMove> badMove = new ArrayList<>();
        for (ChessMove move : moves) {
            ChessPosition kingEndPos = move.getEndPosition();
            ChessPosition kingPos = teamPiecePos.get();
            for (Map.Entry<ChessPiece, ChessPosition> pair : oppPiecePos.entrySet()) {
                Collection<ChessMove> oppMoves = pair.getKey().pieceMoves(board, pair.getValue());
                for (ChessMove oppMove : oppMoves) {
                    if (type == ChessPiece.PieceType.KING) {
                        if (oppMove.getEndPosition() == kingEndPos) {
                            badMove.add(oppMove);
                        }
                    }
                    else {

                    }
                }
            }
        }
        for (ChessMove move : badMove) {
            moves.remove(move);
        }
        return moves;
    }


}
