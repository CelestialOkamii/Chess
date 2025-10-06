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
        if (currentColor == TeamColor.WHITE) {
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
                    blackKingPos = whitePiecePos.get(blackPiece);
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
        //add call to function in chess rules or here see if move puts opposing team in check, stalemate, or checkmate
        if (currentColor == TeamColor.WHITE) {
            if (whiteStale) {
                throw new InvalidMoveException("Can not move when in Stalemate");
            }
            else if (whiteCheckmate) {
                throw new InvalidMoveException("You have lost and cannot make further moves")
            }
            else if (whiteCheck) {
                ChessPosition whiteKingPos = null;
                for (ChessPiece whitePiece : whitePiecePos.keySet()) {
                    if (whitePiece.getPieceType() == ChessPiece.PieceType.KING) {
                        whiteKingPos = whitePiecePos.get(whitePiece);
                        break;
                    }
                }
                if (!rules.isValid(currentBoard, move, whiteKingPos, blackPiecePos)) {
                    throw new InvalidMoveException("You are in check. This move will not get you out of check and in therefore invalid");
                }
            }
            ChessPiece piece = currentBoard.getPiece(move.getStartPosition());
            ChessPiece possOppPiece = currentBoard.getPiece(move.getEndPosition());
            if (possOppPiece != null) {
                blackPiecePos.remove(possOppPiece);
            }
            currentBoard.addPiece(move.getEndPosition(), piece);
            whitePiecePos.put(piece, move.getEndPosition());
            setTeamTurn(TeamColor.BLACK);





        }
        else {
            if (blackStale) {
                throw new InvalidMoveException("Can not move when in Stalemate");
            }
            else if (blackCheckmate) {
                throw new InvalidMoveException("You have lost and cannot make further moves")
            }
            else if (blackCheck) {
                ChessPosition blackKingPos = null;
                for (ChessPiece blackPiece : blackPiecePos.keySet()) {
                    if (blackPiece.getPieceType() == ChessPiece.PieceType.KING) {
                        blackKingPos = blackPiecePos.get(blackPiece);
                        break;
                    }
                }
                if (!rules.isValid(currentBoard, move, blackKingPos, whitePiecePos)) {
                    throw new InvalidMoveException("You are in check. This move will not get you out of check and in therefore invalid");
                }
            }
            ChessPiece piece = currentBoard.getPiece(move.getStartPosition());
            ChessPiece possOppPiece = currentBoard.getPiece(move.getEndPosition());
            if (possOppPiece != null) {
                whitePiecePos.remove(possOppPiece);
            }
            currentBoard.addPiece(move.getEndPosition(), piece);
            blackPiecePos.put(piece, move.getEndPosition());
            setTeamTurn(TeamColor.WHITE);





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
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        currentBoard = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return currentBoard;
    }


    //PUT OVERRIDES IN WHEN DONE WITH REST
}
