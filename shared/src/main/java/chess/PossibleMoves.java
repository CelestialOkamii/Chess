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
        List<ChessPosition> positions = new ArrayList<>();
        if (type == ChessPiece.PieceType.KING) {
            moves = kingMoves();
        }
        else if (type == ChessPiece.PieceType.QUEEN) {
            positions = continuousMoves(rulerPath);
        }
        else if (type == ChessPiece.PieceType.BISHOP) {
            positions = continuousMoves(bishopPath);
        }
        else if (type == ChessPiece.PieceType.ROOK) {
            positions = continuousMoves(rookPath);
        }
        else if (type == ChessPiece.PieceType.KNIGHT) {
            positions = knightMoves(knightPath);
        }
        else {
            moves = pawnMoves();
        }
        for (ChessPosition move : positions) {
            ChessMove posMove = new ChessMove(pos, move, null);
            moves.add(posMove);
        }
        return moves;
    }


    private List<ChessMove> kingMoves() {
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


    private List<ChessPosition> continuousMoves(int[][] paths) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        ArrayList<ChessPosition> positions = new ArrayList<>();
        for (int[] direction : paths) {
            int row = pos.getRow();
            int col = pos.getColumn();
            while (true) {
                row = row + direction[0];
                col = col + direction[1];
                if (row < 1 || row > 8 || col < 1 || col > 8) {
                    break;
                }
                ChessPosition position = new ChessPosition(row, col);
                if (board.getPiece(position) == null) {
                    positions.add(position);
                }
                else {
                    if (board.getPiece(pos).getTeamColor() != board.getPiece(position).getTeamColor()) {
                        positions.add(position);
                    }
                    break;
                }
            }
        }
        return positions;
    }


    private List<ChessPosition> knightMoves(int[][] paths) {
        List<ChessPosition> positions = new ArrayList<>();
        for (int[] direction : paths) {
            int row = pos.getRow();
            int column = pos.getColumn();
            row = row + direction[0];
            column = column + direction[1];
            if (row < 1 || row > 8 || column < 1 || column > 8) {
                continue;
            }
            ChessPosition position = new ChessPosition(row, column);
            if (board.getPiece(position) == null) {
                positions.add(position);
            }
            else {
                if (board.getPiece(pos).getTeamColor() != board.getPiece(position).getTeamColor()) {
                    positions.add(position);
                }
            }
        }
        return positions;
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
