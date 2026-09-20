package chess;
import java.util.*;

public class PossibleMoves {

    ChessBoard board;
    ChessPosition pos;
    ChessPiece.PieceType type;
    ChessGame.TeamColor color;


    public PossibleMoves(ChessBoard board, ChessPosition pos, ChessPiece.PieceType type, ChessGame.TeamColor color) {
        this.board = board;
        this.pos = pos;
        this.type = type;
        this.color = color;
    }


    public Collection<ChessMove> getMoves() {
        Collection<ChessMove> moves = new ArrayList<>();
        if (type == ChessPiece.PieceType.KING) {
            moves = rulerMoves();
        }
        else if (type == ChessPiece.PieceType.QUEEN) {
            moves = rulerMoves();
        }
        else if (type == ChessPiece.PieceType.BISHOP) {
            moves = bishopMoves();
        }
        else if (type == ChessPiece.PieceType.ROOK) {
            moves = rookMoves();
        }
        else if (type == ChessPiece.PieceType.KNIGHT) {
            moves = knightMoves();
        }
        else {
            moves = pawnMoves();
        }
        return moves;
    }


    private List<ChessMove> rulerMoves() {

    }


    private List<ChessMove> bishopMoves() {

    }


    private List<ChessMove> rookMoves() {

    }


    private List<ChessMove> knightMoves() {

    }


    private List<ChessMove> pawnMoves() {

    }
}
