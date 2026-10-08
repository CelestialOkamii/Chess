package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class PossibleMoves {

    ChessBoard board;
    ChessGame.TeamColor color;
    ChessPiece.PieceType piece;
    ChessPosition position;
    int row;
    int col;
    ArrayList<ChessPosition> promoPositions = new ArrayList<>();
    int[][] rulerPath = {{1,-1}, {1,0}, {1,1}, {0,-1}, {0,1}, {-1,-1}, {-1,0}, {-1,1}};
    int[][] bishopPath = {{1,-1}, {1,1}, {-1,-1}, {-1,1}};
    int[][] rookPath = {{0,-1}, {1,0}, {0,1}, {-1,0}};
    int[][] knightPath = {{2,-1}, {2,1}, {1,-2}, {1,2}, {-1,-2}, {-1,2}, {-2,-1}, {-2,1}};
    ArrayList<ChessPiece.PieceType> promoPieces = new ArrayList<>(Arrays.asList(ChessPiece.PieceType.QUEEN, ChessPiece.PieceType.BISHOP, ChessPiece.PieceType.ROOK, ChessPiece.PieceType.KNIGHT));


    public PossibleMoves(ChessBoard board, ChessGame.TeamColor color, ChessPiece.PieceType piece, ChessPosition position, int row, int col) {
        this.board = board;
        this.color = color;
        this.piece = piece;
        this.position = position;
        this.row = row;
        this.col = col;
    }


    public ArrayList<ChessMove> getPieceMoves() {
        ArrayList<ChessMove> moves = new ArrayList<>();
        ArrayList<ChessPosition> positions = new ArrayList<>();
        if (piece == ChessPiece.PieceType.KING) {
            positions = getKPos(rulerPath);
        }
        else if (piece == ChessPiece.PieceType.QUEEN) {
            positions = getContinuousPos(rulerPath);
        } else if (piece == ChessPiece.PieceType.BISHOP) {
            positions = getContinuousPos(bishopPath);
        }
        else if (piece == ChessPiece.PieceType.ROOK) {
            positions = getContinuousPos(rookPath);
        }
        else if (piece == ChessPiece.PieceType.KNIGHT) {
            positions = getKPos(knightPath);
        }
        else {
            positions = getPawnPos();
        }
        if (!positions.isEmpty()) {
            for (ChessPosition endPos : positions) {
                ChessMove move = new ChessMove(position, endPos, null);
                moves.add(move);
            }
        }
        if (!promoPositions.isEmpty()) {
            for (ChessPosition endPos : promoPositions) {
                for (ChessPiece.PieceType promoPiece : promoPieces) {
                    ChessMove move = new ChessMove(position, endPos, promoPiece);
                    moves.add(move);
                }
            }
        }
        return moves;
    }


    private ArrayList<ChessPosition> getKPos(int[][] paths) {
        ArrayList<ChessPosition> positions = new ArrayList<>();
        for (int[] path : paths) {
            ChessPosition possPos = new ChessPosition(row + path[0], col + path[1]);
            if (isSafe(possPos)) {
                positions.add(possPos);
            }
        }
        return positions;
    }


    private ArrayList<ChessPosition> getContinuousPos(int[][] paths) {
        ArrayList<ChessPosition> positions = new ArrayList<>();
        for (int[] path : paths) {
            int currRow = row;
            int currCol = col;
            while (true) {
                ChessPosition pos = new ChessPosition(currRow + path[0], currCol + path[1]);
                if (isSafe(pos)) {
                    positions.add(pos);
                    if (board.getPiece(pos) != null) {
                        break;
                    }
                    currRow += path[0];
                    currCol += path[1];
                }
                else {
                    break;
                }
            }
        }
        return positions;
    }


    private ArrayList<ChessPosition> getPawnPos() {
        ArrayList<ChessPosition> positions = new ArrayList<>();
        int vDirection = 1;
        if (color == ChessGame.TeamColor.BLACK) {
            vDirection = -1;
        }
        for (int hDirection = -1; hDirection < 2; hDirection++) {
            int currRow = row + vDirection;
            int currCol = col + hDirection;
            ChessPosition pos = new ChessPosition(currRow, currCol);
            if (isSafe(pos)) {
                boolean isPromoPiece = currRow == 1 || currRow == 8;
                ChessPiece pieceAhead = board.getPiece(pos);
                if (hDirection == 0 && pieceAhead == null) {
                    if (isPromoPiece) {
                        promoPositions.add(pos);
                    }
                    else {
                        positions.add(pos);
                        if (row == 2 && color == ChessGame.TeamColor.WHITE || row == 7 && color == ChessGame.TeamColor.BLACK) {
                            ChessPosition aheadPos = new ChessPosition(currRow + vDirection, currCol);
                            ChessPiece aheadPiece = board.getPiece(aheadPos);
                            if (aheadPiece == null) {
                                positions.add(aheadPos);
                            }
                        }
                    }
                }
                else if (hDirection != 0 && pieceAhead != null) {
                    if (isPromoPiece) {
                        promoPositions.add(pos);
                    }
                    else {
                        positions.add(pos);
                    }
                }
            }
        }
        return positions;
    }


    private boolean isSafe(ChessPosition pos) {
        if (pos.getRow() > 0 && pos.getRow() < 9 && pos.getColumn() > 0 && pos.getColumn() < 9) {
            ChessPiece type = board.getPiece(pos);
            if (type == null || type.getTeamColor() != color) {
                return  true;
            }
        }
        return false;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PossibleMoves that = (PossibleMoves) o;
        return row == that.row && col == that.col && Objects.equals(board, that.board) && color == that.color && piece == that.piece && Objects.equals(position, that.position) && Objects.equals(promoPositions, that.promoPositions) && Objects.deepEquals(rulerPath, that.rulerPath) && Objects.deepEquals(bishopPath, that.bishopPath) && Objects.deepEquals(rookPath, that.rookPath) && Objects.deepEquals(knightPath, that.knightPath) && Objects.equals(promoPieces, that.promoPieces);
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, color, piece, position, row, col, promoPositions, Arrays.deepHashCode(rulerPath), Arrays.deepHashCode(bishopPath), Arrays.deepHashCode(rookPath), Arrays.deepHashCode(knightPath), promoPieces);
    }
}