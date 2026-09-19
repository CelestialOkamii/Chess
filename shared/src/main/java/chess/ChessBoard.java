package chess;

import java.util.*;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {

    ChessPiece[][] board = new ChessPiece[8][8];
    private Map<ChessPosition, ChessPiece> whitePiecePos = new HashMap<>();
    private Map<ChessPosition, ChessPiece> blackPiecePos = new HashMap<>();


    public ChessBoard() {
        
    }


    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        board[position.getRow() - 1][position.getColumn() - 1] = piece;
    }


    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return board[position.getRow() - 1][position.getColumn() - 1];
    }


    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        List<Integer> startRows = new ArrayList<>(Arrays.asList(1, 2, 7, 8));
        for (int row : startRows) {
            List<ChessPosition> positions = startRows(row);
            List<ChessPiece> pieces = rowPieces(row);
            for (ChessPosition position : positions) {
                addPiece(position, pieces.get(positions.indexOf(position)));
                if (row == 1 || row == 2) {
                    whitePiecePos.put(position, pieces.get(positions.indexOf(position)));
                }
                else {
                    blackPiecePos.put(position, pieces.get(positions.indexOf(position)));
                }
            }
        }

    }


    private List<ChessPosition> startRows(int row) {
        List<ChessPosition> positions = new ArrayList<>();
        for (int col = 1 ; col < 9; col++) {
            ChessPosition newPos = new ChessPosition(row, col);
            positions.add(newPos);
        }
        return positions;
    }


    private List<ChessPiece> rowPieces(int row) {
        List<ChessPiece> endRow = new ArrayList<>();
        List<ChessPiece.PieceType> pawns = new ArrayList<>(Arrays.asList(
                ChessPiece.PieceType.PAWN, ChessPiece.PieceType.PAWN, ChessPiece.PieceType.PAWN, ChessPiece.PieceType.PAWN, ChessPiece.PieceType.PAWN, ChessPiece.PieceType.PAWN, ChessPiece.PieceType.PAWN, ChessPiece.PieceType.PAWN
        ));
        List<ChessPiece.PieceType> specialized = new ArrayList<>(Arrays.asList(
                ChessPiece.PieceType.ROOK, ChessPiece.PieceType.KNIGHT, ChessPiece.PieceType.BISHOP, ChessPiece.PieceType.QUEEN, ChessPiece.PieceType.KING, ChessPiece.PieceType.BISHOP, ChessPiece.PieceType.KNIGHT, ChessPiece.PieceType.ROOK
        ));
        if (row == 1) {
            for (ChessPiece.PieceType type : specialized) {
                ChessPiece piece = new ChessPiece(ChessGame.TeamColor.WHITE, type);
                endRow.add(piece);
            }
        }
        else if (row == 2) {
            for (ChessPiece.PieceType type : pawns) {
                ChessPiece piece = new ChessPiece(ChessGame.TeamColor.WHITE, type);
                endRow.add(piece);
            }
        }
        else if (row == 7) {
            for (ChessPiece.PieceType type : pawns) {
                ChessPiece piece = new ChessPiece(ChessGame.TeamColor.BLACK, type);
                endRow.add(piece);
            }
        }
        else {
            for (ChessPiece.PieceType type : specialized) {
                ChessPiece piece = new ChessPiece(ChessGame.TeamColor.BLACK, type);
                endRow.add(piece);
            }
        }
        return endRow;
    }
}


