package chess;

import java.util.*;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private ChessBoard currentBoard = new ChessBoard();
    private TeamColor currentColor = TeamColor.WHITE;
    private Map<ChessPiece, ChessPosition> whitePiecePos = currentBoard.getStartPositions(TeamColor.WHITE);
    private Map<ChessPiece, ChessPosition> blackPiecePos = currentBoard.getStartPositions(TeamColor.BLACK);
    private final ChessRules rules = new ChessRules();
    private boolean whiteStale = false;
    private boolean whiteCheck = false;
    private boolean whiteCheckmate = false;
    private boolean blackStale = false;
    private boolean blackCheck = false;
    private boolean blackCheckmate = false;

    public ChessGame() {

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
        Collection<ChessMove> piece_moves = piece.pieceMoves(currentBoard, startPosition);
        if (piece_moves == null) {
            return null;
        }
        if (piece.getTeamColor() == TeamColor.WHITE) {
            ChessPosition whiteKingPos = null;
            for (ChessPiece whitePiece : whitePiecePos.keySet()) {
                if (whitePiece.getPieceType() == ChessPiece.PieceType.KING) {
                    whiteKingPos = whitePiecePos.get(whitePiece);
                    break;
                }
            }
            return rules.checkValidity(currentBoard, piece_moves, whiteKingPos, blackPiecePos, piece);
        }
        else {
            ChessPosition blackKingPos = null;
            for (ChessPiece blackPiece : blackPiecePos.keySet()) {
                if (blackPiece.getPieceType() == ChessPiece.PieceType.KING) {
                    blackKingPos = blackPiecePos.get(blackPiece);
                    break;
                }
            }
            return rules.checkValidity(currentBoard, piece_moves, blackKingPos, whitePiecePos, piece);
        }
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition whiteKingPos = null;
        ChessPosition blackKingPos = null;
        ChessPiece piece = currentBoard.getPiece(move.getStartPosition());
        for (ChessPiece whitePiece : whitePiecePos.keySet()) {
            if (whitePiece.getPieceType() == ChessPiece.PieceType.KING) {
                whiteKingPos = whitePiecePos.get(whitePiece);
                break;
            }
        }
        for (ChessPiece blackPiece : blackPiecePos.keySet()) {
            if (blackPiece.getPieceType() == ChessPiece.PieceType.KING) {
                blackKingPos = blackPiecePos.get(blackPiece);
                break;
            }
        }
        if (piece.getTeamColor() == TeamColor.WHITE) {
            if (goodMove(TeamColor.WHITE, TeamColor.BLACK, move, whiteKingPos, blackKingPos, whitePiecePos, blackPiecePos)) {
                setTeamTurn(TeamColor.BLACK);
            }
        }
        else {
            if (goodMove(TeamColor.BLACK, TeamColor.WHITE, move, blackKingPos, whiteKingPos, blackPiecePos, whitePiecePos)) {
                setTeamTurn(TeamColor.WHITE);
            }
        }
    }

    private boolean goodMove(TeamColor teamColor, TeamColor oppColor, ChessMove move, ChessPosition myKingPos, ChessPosition oppKingPos, Map<ChessPiece, ChessPosition> sameTeam, Map<ChessPiece, ChessPosition> oppTeam) throws InvalidMoveException {
        ChessPiece piece = currentBoard.getPiece(move.getStartPosition());
        if (piece == null) {
            throw new InvalidMoveException("There is no piece to move in that spot");
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
        if (!rules.isValid(currentBoard, move, piece, myKingPos, oppTeam)) {
            throw new InvalidMoveException("You are in check. This move will not get you out of check and in therefore invalid");
        }
        ChessPiece possOppPiece = currentBoard.getPiece(move.getEndPosition());
        if (possOppPiece != null) {
            oppTeam.remove(possOppPiece);
        }
        if (move.getPromotionPiece() == null) {
            currentBoard.addPiece(move.getEndPosition(), piece);
            sameTeam.put(piece, move.getEndPosition());
        }
        else {
            ChessPiece newPiece = new ChessPiece(teamColor, move.getPromotionPiece());
            currentBoard.addPiece(move.getEndPosition(), newPiece);
            sameTeam.remove(piece);
            sameTeam.put(newPiece, move.getEndPosition());
        }
        boolean check = rules.createsCheck(currentBoard, move.getEndPosition(), oppKingPos);
        changeCheck(oppColor, check);
        if (isInCheck(oppColor)) {
            changeCheckmate(oppColor, rules.createsEnding(currentBoard, sameTeam, oppTeam, oppKingPos));
        }
        else {
            changeStalemate(oppColor, rules.createsEnding(currentBoard, sameTeam, oppTeam, oppKingPos));
        }
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


    /**
     * Changes if white or black is in stalemate after a move is made
     */
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
        currentBoard = board;
        whitePiecePos = new HashMap<>();
        blackPiecePos = new HashMap<>();
        int row = 1;
        int column = 1;
        while (row < 9) {
            ChessPosition pos = new ChessPosition(row, column);
            ChessPiece piece = board.getPiece(pos);
            if (piece != null) {
                if (piece.getTeamColor() == TeamColor.WHITE) {
                    whitePiecePos.put(piece, pos);
                }
                else {
                    blackPiecePos.put(piece, pos);
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
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return currentBoard;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return whiteStale == chessGame.whiteStale && whiteCheck == chessGame.whiteCheck && whiteCheckmate == chessGame.whiteCheckmate && blackStale == chessGame.blackStale &&
                blackCheck == chessGame.blackCheck && blackCheckmate == chessGame.blackCheckmate && Objects.equals(currentBoard, chessGame.currentBoard) && currentColor == chessGame.currentColor &&
                Objects.equals(whitePiecePos, chessGame.whitePiecePos) && Objects.equals(blackPiecePos, chessGame.blackPiecePos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentBoard, currentColor, whitePiecePos, blackPiecePos, whiteStale, whiteCheck, whiteCheckmate, blackStale, blackCheck, blackCheckmate);
    }
}
