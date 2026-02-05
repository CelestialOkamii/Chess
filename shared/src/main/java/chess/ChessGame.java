package chess;

import java.util.Collection;
import java.util.Map;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private ChessBoard currentBoard = new ChessBoard();
    private TeamColor currentColor = TeamColor.WHITE;
    private Map<ChessPosition, ChessPiece> whitePiecePos = currentBoard.getStartPositions(TeamColor.WHITE);
    private Map<ChessPosition, ChessPiece> blackPiecePos = currentBoard.getStartPositions(TeamColor.BLACK);
    private final ChessRules rules = new ChessRules();
    private boolean whiteStale = false;
    private boolean whiteCheck = false;
    private boolean whiteCheckmate = false;
    private boolean blackStale = false;
    private boolean blackCheck = false;
    private boolean blackCheckmate = false;

    public ChessGame() {
        currentBoard.resetBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return currentColor;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        currentColor = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece piece = currentBoard.getPiece(startPosition);
        Collection<ChessMove> pieceMoves = piece.pieceMoves(currentBoard, startPosition);
        if (pieceMoves == null) {
            return null;
        }
        if (piece.getTeamColor() == TeamColor.WHITE) {
            ChessPosition whiteKingPos = null;
            for (Map.Entry<ChessPosition, ChessPiece> pair : whitePiecePos.entrySet()) {
                if (pair.getValue().getPieceType() == ChessPiece.PieceType.KING) {
                    whiteKingPos = pair.getKey();
                    break;
                }
            }
            return rules.checkValidity(currentBoard, pieceMoves, whiteKingPos, blackPiecePos, piece);
        }
        else {
            ChessPosition blackKingPos = null;
            for (Map.Entry<ChessPosition, ChessPiece> pair : blackPiecePos.entrySet()) {
                if (pair.getValue().getPieceType() == ChessPiece.PieceType.KING) {
                    blackKingPos = pair.getKey();
                    break;
                }
            }
            return rules.checkValidity(currentBoard, pieceMoves, blackKingPos, whitePiecePos, piece);
        }
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        throw new RuntimeException("Not implemented");
    }
}
