package chess;

import java.util.Collection;
import java.util.HashMap;
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

    private boolean goodMove(TeamColor teamColor, TeamColor oppColor, ChessMove move,
                             ChessPosition myKingPos, Map<ChessPosition, ChessPiece> sameTeam,
                             Map<ChessPosition, ChessPiece> oppTeam) throws InvalidMoveException {
        ChessPiece piece = currentBoard.getPiece(move.getStartPosition());
        Collection<ChessMove> validMoves = validMoves(move.getStartPosition());
        boolean valid = false;
        for (ChessMove posMove : validMoves) {
            if (move.equals(posMove))  {
                valid = true;
                break;
            }
        }
        if (!valid) {
            throw new InvalidMoveException("This is not a valid move");
        }
        if (currentColor != teamColor) {
            throw new InvalidMoveException("It is not your turn yet, be patient");
        }
        if (isInStalemate(teamColor)) {
            throw new InvalidMoveException("Can not move when in Stalemate");
        }
        else if (isInCheckmate(teamColor)) {
            throw new InvalidMoveException("You have lost and cannot make further moves");
        }
        else if (isInCheck(teamColor)) {
            if (!rules.isValid(currentBoard, move, piece, myKingPos, oppTeam)) {
                throw new InvalidMoveException("You are in check. This move will not get you out of check and in therefore invalid");
            }
        }
        ChessPiece possOppPiece = currentBoard.getPiece(move.getEndPosition());
        if (possOppPiece != null) {
            oppTeam.remove(move.getEndPosition());
        }
        if (move.getPromotionPiece() == null) {
            currentBoard.addPiece(move.getEndPosition(), piece);
            sameTeam.put(move.getEndPosition(), piece);
            sameTeam.remove(move.getStartPosition());
        }
        else {
            ChessPiece newPiece = new ChessPiece(teamColor, move.getPromotionPiece());
            currentBoard.addPiece(move.getEndPosition(), newPiece);
            sameTeam.put(move.getEndPosition(), newPiece);
            sameTeam.remove(move.getStartPosition());
        }
        currentBoard.addPiece(move.getStartPosition(), null);
        checkChange(oppColor, move.getEndPosition());
        endingChange(teamColor, getKingPos(teamColor), sameTeam, oppTeam);
        endingChange(oppColor, getKingPos(oppColor), oppTeam, sameTeam);
        return true;
    }

    /**
     * Changes if white or black is in check after a move is made
     */
    public void changeCheck(TeamColor color, boolean tOf) {
        if (color == TeamColor.WHITE) {
            whiteCheck = tOf;
        }
        else {
            blackCheck = tOf;
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
     * Changes if white or black is in checkmate after a move is made
     */
    public void changeCheckmate(TeamColor color, boolean tOf) {
        if (color == TeamColor.WHITE) {
            whiteCheckmate = tOf;
        }
        else {
            blackCheckmate = tOf;
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

    public void changeStalemate(TeamColor color, boolean tOf) {
        if (color == TeamColor.WHITE) {
            whiteStale = tOf;
        }
        else {
            blackStale = tOf;
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
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.currentBoard = board;
        whitePiecePos = new HashMap<>();
        blackPiecePos = new HashMap<>();
        ChessPosition whiteKingPos = getKingPos(TeamColor.WHITE);
        ChessPosition blackKingPos = getKingPos(TeamColor.BLACK);
        int row = 1;
        int column = 1;
        while (row < 9) {
            ChessPosition pos = new ChessPosition(row, column);
            ChessPiece piece = board.getPiece(pos);
            if (piece != null) {
                if (piece.getTeamColor() == TeamColor.WHITE) {
                    whitePiecePos.put(pos, piece);
                }
                else {
                    blackPiecePos.put(pos, piece);
                }
            }
            if (column == 8) {
                column = 1;
                row++;
            }
            else {
                column++;
            }
        }
        for (Map.Entry<ChessPosition, ChessPiece> pair : whitePiecePos.entrySet()) {
            if (isInCheck(TeamColor.BLACK)){
                break;
            }
            checkChange(TeamColor.BLACK, pair.getKey());
        }
        for (Map.Entry<ChessPosition, ChessPiece> pair : blackPiecePos.entrySet()) {
            if (isInCheck(TeamColor.WHITE)){
                break;
            }
            checkChange(TeamColor.WHITE, pair.getKey());
        }
        endingChange(TeamColor.WHITE, getKingPos(TeamColor.WHITE), whitePiecePos, blackPiecePos);
        endingChange(TeamColor.BLACK, getKingPos(TeamColor.BLACK), blackPiecePos, whitePiecePos);
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return currentBoard;
    }


}
