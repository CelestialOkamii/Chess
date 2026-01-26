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
     * Returns the current chess board
     */
    public ChessBoard getBoard() {
        return this;
    }


    /**
     * Returns the starting positions for either white or black pieces
     */
    public Map<ChessPosition, ChessPiece> getStartPositions(ChessGame.TeamColor color) {
        if (color == ChessGame.TeamColor.WHITE) {
            return whitePiecePos;
        }
        else {
            return blackPiecePos;
        }
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
        for (int i = 1; i < 9; i++) {
            ChessPosition newPosition = new ChessPosition(row,i);
            positions.add(newPosition);
        }
        return positions;
    }

    private List<ChessPiece> rowPieces(int row) {
        List<ChessPiece> pieces = new ArrayList<>();
        List<ChessPiece.PieceType> backRows = new ArrayList<>(Arrays.asList(ChessPiece.PieceType.ROOK,
                ChessPiece.PieceType.KNIGHT, ChessPiece.PieceType.BISHOP, ChessPiece.PieceType.QUEEN,
                ChessPiece.PieceType.KING, ChessPiece.PieceType.BISHOP, ChessPiece.PieceType.KNIGHT, ChessPiece.PieceType.ROOK));
        List<ChessPiece.PieceType> frontRows = new ArrayList<>(Arrays.asList(ChessPiece.PieceType.PAWN,
                ChessPiece.PieceType.PAWN, ChessPiece.PieceType.PAWN, ChessPiece.PieceType.PAWN, ChessPiece.PieceType.PAWN,
                ChessPiece.PieceType.PAWN, ChessPiece.PieceType.PAWN, ChessPiece.PieceType.PAWN));
        if (row == 1 || row == 2) {
            ChessGame.TeamColor color = ChessGame.TeamColor.WHITE;
            if (row == 1) {
                for (ChessPiece.PieceType piece : backRows) {
                    ChessPiece newPiece = new ChessPiece(color, piece);
                    pieces.add(newPiece);
                }
            }
            else {
                for (ChessPiece.PieceType piece : frontRows) {
                    ChessPiece newPiece = new ChessPiece(color, piece);
                    pieces.add(newPiece);
                }
            }
        }
        else {
            ChessGame.TeamColor color = ChessGame.TeamColor.BLACK;
            if (row == 7) {
                for (ChessPiece.PieceType piece : frontRows) {
                    ChessPiece newPiece = new ChessPiece(color, piece);
                    pieces.add(newPiece);
                }
            }
            else {
                for (ChessPiece.PieceType piece : backRows) {
                    ChessPiece newPiece = new ChessPiece(color, piece);
                    pieces.add(newPiece);
                }
            }
        }
        return pieces;
    }

    @Override
    public String toString() {
        int column = 0;
        int row = 0;
        String boardRow = "";
        while (row < 8) {
            ChessPiece piece = board[row][column];
            if (piece == null) {
                boardRow = String.format("[%d][%d]: empty\n", row, column);
            }
            else {
                boardRow = boardRow + String.format("[%d][%d]: color = %s, type = %s\n", row, column,
                        piece.getTeamColor().toString(), piece.getPieceType().toString());
            }
            if (column == 7) {
                column = 0;
                row++;
                continue;
            }
            column++;
        }
        return boardRow;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ChessBoard that = (ChessBoard) obj;
        return Arrays.deepEquals(board, that.board);
    }


    @Override
    public int hashCode() {
        int boardHash = Arrays.deepHashCode(board);
        int hash = 17;
        hash = 31 * hash + boardHash;
        return hash;
    }
}