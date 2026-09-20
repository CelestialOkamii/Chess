package chess;
import java.util.*;

public class PossibleMoves {

    private final ChessBoard board;
    private final ChessPosition pos;
    private final ChessPiece.PieceType type;
    private final ChessGame.TeamColor color;
    private final int[][] rulerPath = {{1,1}, {-1,1}, {0,-1}, {-1,0}, {1,0}, {1,-1}, {-1,-1}, {0,1}};
    private final int[][] bishopPath = {{1,1}, {-1,1}, {1,-1}, {-1,-1}};
    private final int[][] rookPath = {{0,-1}, {-1,0}, {1,0}, {0,1}};
    private final int[][] knightPath = {{2,1}, {2,-1}, {-2,1}, {-2,-1}, {1,2}, {-1,2}, {1,-2}, {-1,-2}};


    public PossibleMoves(ChessBoard board, ChessPosition pos, ChessPiece.PieceType type, ChessGame.TeamColor color) {
        this.board = board;
        this.pos = pos;
        this.type = type;
        this.color = color;
    }


    public Collection<ChessMove> getMoves() {
        Collection<ChessMove> moves = new ArrayList<>();
        if (type == ChessPiece.PieceType.KING) {
            moves = kingMoves(rulerPath);
        }
        else if (type == ChessPiece.PieceType.QUEEN) {
            moves = continuousMoves(rulerPath);
        }
        else if (type == ChessPiece.PieceType.BISHOP) {
            moves = continuousMoves(bishopPath);
        }
        else if (type == ChessPiece.PieceType.ROOK) {
            moves = continuousMoves(rookPath);
        }
        else if (type == ChessPiece.PieceType.KNIGHT) {
            moves = knightMoves(knightPath);
        }
        else {
            moves = pawnMoves();
        }
        return moves;
    }


    private List<ChessMove> kingMoves(int[][] path) {
        List<ChessMove> moves = new ArrayList<>();
        int currRow = pos.getRow();
        int currCol = pos.getColumn();
        for (int row = currRow - 1 ; row <= currRow + 1; row++) {
            for (int col = currCol - 1; col <= currCol + 1; col++) {
                if (row < 1 || row > 8 || col < 1 || col > 8) {
                    continue;
                }
                if (row == currRow && col == currCol) {
                    continue;
                }
                if (validMove(row, col)) {
                    ChessPosition position = new ChessPosition(row, col);
                    ChessMove move = new ChessMove(pos, position, null);
                    moves.add(move);
                }
            }
        }
        return moves;
    }


    private List<ChessMove> continuousMoves(int[][] path) {
        return new ArrayList<>();
    }


    private List<ChessMove> knightMoves(int[][] path) {
        return new ArrayList<>();
    }


    private List<ChessMove> pawnMoves() {
        return new ArrayList<>();
    }


    private boolean validMove(int row, int col) {
        ChessPosition newPos = new ChessPosition(row, col);
        ChessPiece piece = board.getPiece(newPos);
        if (piece == null) {
            return true;
        }
        return board.getPiece(newPos).getTeamColor() != color;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PossibleMoves that = (PossibleMoves) o;
        return Objects.equals(board, that.board) && Objects.equals(pos, that.pos) && type == that.type && color == that.color && Objects.deepEquals(rulerPath, that.rulerPath) && Objects.deepEquals(bishopPath, that.bishopPath) && Objects.deepEquals(rookPath, that.rookPath) && Objects.deepEquals(knightPath, that.knightPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, pos, type, color, Arrays.deepHashCode(rulerPath), Arrays.deepHashCode(bishopPath), Arrays.deepHashCode(rookPath), Arrays.deepHashCode(knightPath));
    }
}
