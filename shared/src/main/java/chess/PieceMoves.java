package chess;

import java.util.*;
import static java.util.Arrays.asList;

public class PieceMoves {

    private final ChessPosition position;
    private final ChessGame.TeamColor color;
    private final ChessBoard board;
    private final ChessPiece.PieceType piece;
    private final ArrayList<ChessPosition> promotionPositions = new ArrayList<>();
    private final int[][] rulerPath = {{1,1}, {-1,1}, {0,-1}, {-1,0}, {1,0}, {1,-1}, {-1,-1}, {0,1}};
    private final int[][] bishopPath = {{1,1}, {-1,1}, {1,-1}, {-1,-1}};
    private final int[][] rookPath = {{0,-1}, {-1,0}, {1,0}, {0,1}};
    private final int[][] knightPath = {{2,1}, {2,-1}, {-2,1}, {-2,-1}, {1,2}, {-1,2}, {1,-2}, {-1,-2}};


    public PieceMoves(ChessPosition position, ChessGame.TeamColor color, ChessBoard board, ChessPiece.PieceType piece) {
        this.position = position;
        this.color = color;
        this.board = board;
        this.piece = piece;
    }

    public Collection<ChessMove> validMoves() {
        List<ChessPosition> goodMoves = new ArrayList<>();
        Collection<ChessMove> moves = new ArrayList<>();
        if (piece == ChessPiece.PieceType.KING) {
            moves = kingPositions();
        }
        else if (piece == ChessPiece.PieceType.QUEEN) {
            goodMoves = continuousPositions(rulerPath);
        }
        else if (piece == ChessPiece.PieceType.BISHOP) {
            goodMoves = continuousPositions(bishopPath);
        }
        else if (piece == ChessPiece.PieceType.ROOK) {
            goodMoves = continuousPositions(rookPath);
        }
        else if (piece == ChessPiece.PieceType.KNIGHT) {
            goodMoves = knightPositions(knightPath);
        }
        else {
            moves = pawnPositions();
        }
        for (ChessPosition move : goodMoves) {
            ChessMove posMove = new ChessMove(position, move, null);
            moves.add(posMove);
        }
        return moves;
    }


    public List<ChessMove> kingPositions() {
        List<ChessMove> moves = new ArrayList<>();
        int currRow = position.getRow();
        int currCol = position.getColumn();
        for (int row = currRow -1; row <= currRow + 1; row++) {
            for (int column = currCol - 1; column <= currCol + 1; column++) {
                if (row < 1 || row > 8 || column < 1 || column > 8) {
                    continue;
                }
                if (row == currRow && column == currCol) {
                    continue;
                }
                if (validMove(row, column)) {
                    ChessPosition coordinate = new ChessPosition(row,column);
                    ChessMove move = new ChessMove(position, coordinate, null);
                    moves.add(move);
                }
            }
        }
        return moves;
    }

    public boolean validMove(int row, int column) {
        ChessPosition newPos = new ChessPosition(row, column);
        ChessPiece piece = board.getPiece(newPos);
        if (piece == null) {
            return true;
        }
        return board.getPiece(newPos).getTeamColor() != color;
    }


    private List<ChessPosition> continuousPositions(int[][] paths) {
        List<ChessPosition> positions = new ArrayList<>();
        for (int[] course : paths) {
            int row = position.getRow();
            int column = position.getColumn();
            while (true) {
                row = row + course[0];
                column = column + course[1];
                if (row < 1 || row > 8 || column < 1 || column > 8) {
                    break;
                }
                ChessPosition pos = new ChessPosition(row, column);
                if (board.getPiece(pos) == null) {
                    positions.add(pos);
                }
                else {
                    if (board.getPiece(pos).getTeamColor() != board.getPiece(position).getTeamColor()) {
                        positions.add(pos);
                    }
                    break;
                }
            }
        }
        return positions;
    }


    private List<ChessPosition> knightPositions(int[][]paths) {
        List<ChessPosition> positions = new ArrayList<>();
        for (int[] course : paths) {
            int row = position.getRow();
            int column = position.getColumn();
            row = row + course[0];
            column = column + course[1];
            if (row < 1 || row > 8 || column < 1 || column > 8) {
                continue;
            }
            ChessPosition pos = new ChessPosition(row, column);
            if (board.getPiece(pos) == null) {
                positions.add(pos);
            }
            else {
                if (board.getPiece(pos).getTeamColor() != board.getPiece(position).getTeamColor()) {
                    positions.add(pos);
                }
            }
        }
        return positions;
    }


    public Collection<ChessMove> pawnPositions() {
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
        int row = position.getRow();
        int column = position.getColumn();
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
        return moves;
    }


    Collection<ChessMove> blackPawnMoves(Collection<ChessMove> moves, int row, int column) {
        boolean oneAhead = false;
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
        return moves;
    }


    private Collection<ChessMove> getPromoMoves(ChessPosition pos) {
        Collection<ChessMove> moves = new ArrayList<>();
        ArrayList<ChessPiece.PieceType> pieces = new ArrayList<>(asList(ChessPiece.PieceType.ROOK, ChessPiece.PieceType.KNIGHT,
                ChessPiece.PieceType.BISHOP, ChessPiece.PieceType.QUEEN));
        for (ChessPiece.PieceType piece : pieces) {
            ChessMove move = new ChessMove(position, pos, piece);
            moves.add(move);
        }
        return moves;
    }



    @Override
    public boolean equals(Object o) {
        if (o == null || o == this || getClass() != o.getClass()) {
            return false;
        }
        PieceMoves pieceMoves = (PieceMoves) o;
        return Objects.equals(board, pieceMoves.board) && color == pieceMoves.color &&
                Objects.equals(position, pieceMoves.position) && Objects.equals(promotionPositions, pieceMoves.promotionPositions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, color, position, promotionPositions);
    }
}
