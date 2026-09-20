package chess;
import java.util.*;

import static java.util.Arrays.asList;

public class PossibleMoves {

    private final ChessBoard board;
    private final ChessPosition pos;
    private final ChessPiece.PieceType type;
    private final ChessGame.TeamColor color;
    private final ArrayList<ChessPosition> promotionPositions = new ArrayList<>();
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


    public Collection<ChessMove> pawnMoves() {
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
        int row = pos.getRow();
        int column = pos.getColumn();
        if (color == ChessGame.TeamColor.WHITE) {
            if (row < 9) {
                moves = whitePawnMoves(moves, row, column);
            }
        }
        else {
            if (row > 0) {
                moves = blackPawnMoves(moves, row, column);
            }
        }
        return moves;
    }


    Collection<ChessMove> whitePawnMoves(Collection<ChessMove> moves, int row, int column) {
        boolean oneAhead = false;
        for (int i = -1; i < 2; i++) {
            if (column + i > 0 && column + i < 9 && row + 1 < 9) {
                ChessPosition possPosition = new ChessPosition(row + 1, column + i);
                ChessPiece piece = board.getPiece(possPosition);
                if ((piece == null && i == 0) || (piece != null && i != 0 && color != piece.getTeamColor())) {
                    ChessMove move = new ChessMove(pos, possPosition, null);
                    moves.add(move);
                    if (row + 1 == 8) {
                        promotionPositions.add(possPosition);
                        moves.remove(move);
                    }
                    if (i == 0 && row + 2 < 8) {
                        oneAhead = true;
                        possPosition = new ChessPosition(row + 2, column + i);
                        piece = board.getPiece(possPosition);
                    }
                }
                if (row == 2 && oneAhead && i == 0 && piece == null) {
                    ChessMove move = new ChessMove(pos, possPosition, null);
                    moves.add(move);
                }
            }
        }
        return moves;
    }


    Collection<ChessMove> blackPawnMoves(Collection<ChessMove> moves, int row, int col) {
        boolean oneAhead = false;
        for (int i = -1; i < 2; i++) {
            if (col + i > 0 && col + i < 9 && row - 1 > 0) {
                ChessPosition possPosition = new ChessPosition(row - 1, col + i);
                ChessPiece piece = board.getPiece(possPosition);
                if ((piece == null && i == 0) || (piece != null && i != 0 && color != piece.getTeamColor())) {
                    ChessMove move = new ChessMove(pos, possPosition, null);
                    moves.add(move);
                    if (row - 1 == 1) {
                        promotionPositions.add(possPosition);
                        moves.remove(move);
                    }
                    if (i == 0 && row - 2 > 0) {
                        oneAhead = true;
                        possPosition = new ChessPosition(row - 2, col + i);
                        piece = board.getPiece(possPosition);
                    }
                }
                if (row == 7 && oneAhead && i == 0 && piece == null) {
                    ChessMove move = new ChessMove(pos, possPosition, null);
                    moves.add(move);
                }
            }
        }
        return moves;
    }


    private Collection<ChessMove> getPromoMoves(ChessPosition position) {
        Collection<ChessMove> moves = new ArrayList<>();
        ArrayList<ChessPiece.PieceType> pieces = new ArrayList<>(asList(ChessPiece.PieceType.ROOK, ChessPiece.PieceType.KNIGHT,
                ChessPiece.PieceType.BISHOP, ChessPiece.PieceType.QUEEN));
        for (ChessPiece.PieceType piece : pieces) {
            ChessMove move = new ChessMove(pos, position, piece);
            moves.add(move);
        }
        return moves;
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
        return Objects.equals(board, that.board) && Objects.equals(pos, that.pos) && type == that.type && color == that.color && Objects.equals(promotionPositions, that.promotionPositions) && Objects.deepEquals(rulerPath, that.rulerPath) && Objects.deepEquals(bishopPath, that.bishopPath) && Objects.deepEquals(rookPath, that.rookPath) && Objects.deepEquals(knightPath, that.knightPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, pos, type, color, promotionPositions, Arrays.deepHashCode(rulerPath), Arrays.deepHashCode(bishopPath), Arrays.deepHashCode(rookPath), Arrays.deepHashCode(knightPath));
    }
}
