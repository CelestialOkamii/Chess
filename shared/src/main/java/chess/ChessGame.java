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
        ChessPiece piece = currentBoard.getPiece(move.getStartPosition());
        ChessPosition whiteKingPos = null;
        ChessPosition blackKingPos = null;
        if (currentColor == TeamColor.WHITE) {
            if (whiteStale) {
                throw new InvalidMoveException("Can not move when in Stalemate");
            }
            else if (whiteCheckmate) {
                throw new InvalidMoveException("You have lost and cannot make further moves");
            }
            else if (whiteCheck) {
                for (ChessPiece whitePiece : whitePiecePos.keySet()) {
                    if (whitePiece.getPieceType() == ChessPiece.PieceType.KING) {
                        whiteKingPos = whitePiecePos.get(whitePiece);
                        break;
                    }
                }
                if (!rules.isValid(currentBoard, move, piece, whiteKingPos, blackPiecePos)) {
                    throw new InvalidMoveException("You are in check. This move will not get you out of check and in therefore invalid");
                }
            }
            ChessPiece possOppPiece = currentBoard.getPiece(move.getEndPosition());
            if (possOppPiece != null) {
                blackPiecePos.remove(possOppPiece);
            }
            if (move.getPromotionPiece() == null) {
                currentBoard.addPiece(move.getEndPosition(), piece);
                blackPiecePos.put(piece, move.getEndPosition());
            }
            else {
                ChessPiece newPiece = new ChessPiece(TeamColor.WHITE, move.getPromotionPiece());
                currentBoard.addPiece(move.getEndPosition(), newPiece);
                blackPiecePos.put(newPiece, move.getEndPosition());
            }
            setTeamTurn(TeamColor.BLACK);
            boolean check = rules.createsCheck(currentBoard, move, blackKingPos);
            changeCheck(TeamColor.BLACK, check);
            if (isInCheck(TeamColor.BLACK)) {
                changeCheckmate(TeamColor.BLACK, rules.createsEnding(currentBoard, whitePiecePos, blackPiecePos, blackKingPos));
            }
            else {
                changeStalemate(TeamColor.BLACK, rules.createsEnding(currentBoard, whitePiecePos, blackPiecePos, blackKingPos));
            }
        }
        else {
            if (blackStale) {
                throw new InvalidMoveException("Can not move when in Stalemate");
            }
            else if (blackCheckmate) {
                throw new InvalidMoveException("You have lost and cannot make further moves");
            }
            else if (blackCheck) {
                for (ChessPiece blackPiece : blackPiecePos.keySet()) {
                    if (blackPiece.getPieceType() == ChessPiece.PieceType.KING) {
                        blackKingPos = blackPiecePos.get(blackPiece);
                        break;
                    }
                }
                if (!rules.isValid(currentBoard, move, piece, blackKingPos, whitePiecePos)) {
                    throw new InvalidMoveException("You are in check. This move will not get you out of check and in therefore invalid");
                }
            }
            ChessPiece possOppPiece = currentBoard.getPiece(move.getEndPosition());
            if (possOppPiece != null) {
                whitePiecePos.remove(possOppPiece);
            }
            if (move.getPromotionPiece() == null) {
                currentBoard.addPiece(move.getEndPosition(), piece);
                blackPiecePos.put(piece, move.getEndPosition());
            }
            else {
                ChessPiece newPiece = new ChessPiece(TeamColor.BLACK, move.getPromotionPiece());
                currentBoard.addPiece(move.getEndPosition(), newPiece);
                blackPiecePos.put(newPiece, move.getEndPosition());
            }
            setTeamTurn(TeamColor.WHITE);
            boolean check = rules.createsCheck(currentBoard, move, whiteKingPos);
            changeCheck(TeamColor.WHITE, check);
            if (isInCheck(TeamColor.WHITE)) {
                changeCheckmate(TeamColor.WHITE, rules.createsEnding(currentBoard, blackPiecePos, whitePiecePos, whiteKingPos));
            }
            else {
                changeStalemate(TeamColor.WHITE, rules.createsEnding(currentBoard, blackPiecePos, whitePiecePos, whiteKingPos));
            }
        }
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
