package chess;

import java.util.Collection;
import java.util.Map;

/**
 * A class that can manage a chess game, making moves on a board
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
     * Sets which teams turn it is
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
     * Gets all valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece piece = currentBoard.getPiece(startPosition);
        Collection<ChessMove> pieceMoves = piece.pieceMoves(currentBoard, startPosition);
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
     * Makes a move in the chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition whiteKingPos = getKingPos(TeamColor.WHITE);
        ChessPosition blackKingPos = getKingPos(TeamColor.BLACK);
        ChessPiece piece = currentBoard.getPiece(move.getStartPosition());
        if (piece == null) {
            throw new InvalidMoveException("There is no piece to move in that spot");
        }
        if (piece.getTeamColor() == TeamColor.WHITE) {
            if (goodMove(TeamColor.WHITE, TeamColor.BLACK, move, whiteKingPos, whitePiecePos, blackPiecePos)) {
                setTeamTurn(TeamColor.BLACK);
            }
        }
        else {
            if (goodMove(TeamColor.BLACK, TeamColor.WHITE, move, blackKingPos, blackPiecePos, whitePiecePos)) {
                setTeamTurn(TeamColor.WHITE);
            }
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        if (teamColor == TeamColor.WHITE) {
            return whiteCheck;
        }
        else {
            return blackCheck;
        }
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if (teamColor == TeamColor.WHITE) {
            return whiteCheckmate;
        }
        else {
            return blackCheckmate;
        }
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if (teamColor == TeamColor.WHITE) {
            return whiteStale;
        }
        else {
            return blackStale;
        }
    }

    /**
     * Sets this game's chessboard to a given board
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
        return currentBoard;
    }


    private ChessPosition getKingPos(TeamColor color) {
        if (color == TeamColor.WHITE) {
            for (Map.Entry<ChessPosition, ChessPiece> pair : whitePiecePos.entrySet()) {
                if (pair.getValue().getPieceType() == ChessPiece.PieceType.KING) {
                    return pair.getKey();
                }
            }
        }
        else {
            for (Map.Entry<ChessPosition, ChessPiece> pair : blackPiecePos.entrySet()) {
                if (pair.getValue().getPieceType() == ChessPiece.PieceType.KING) {
                    return pair.getKey();
                }
            }
        }
        return null;
    }
}
