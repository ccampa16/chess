package chess;

import java.util.*;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor currTeamColor;
    private ChessBoard chessBoard;
    public ChessGame() {
        this.currTeamColor = TeamColor.WHITE;
        this.chessBoard = new ChessBoard();
        this.chessBoard.resetBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return currTeamColor;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        currTeamColor = team;
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
        ChessPiece currPiece = chessBoard.getPiece(startPosition);
        if (currPiece == null){
            return Collections.emptyList();
        }
        Collection<ChessMove> possibleMoves = new ArrayList<>(currPiece.pieceMoves(chessBoard, startPosition));
        ArrayList<ChessMove> invalidMoves = new ArrayList<>();
        for (ChessMove move : possibleMoves) {
            ChessPiece piece = chessBoard.getPiece(move.getStartPosition());
            ChessPiece newPiece = chessBoard.getPiece(move.getEndPosition());
            chessBoard.addPiece(move.getEndPosition(), piece);
            chessBoard.deletePiece(move.getStartPosition());
            if(isInCheck(piece.getTeamColor())){
                invalidMoves.add(move);
            }
            chessBoard.addPiece(move.getStartPosition(), piece);
            chessBoard.addPiece(move.getEndPosition(), newPiece);
        }
        possibleMoves.removeAll(invalidMoves);
        return possibleMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPiece piece = chessBoard.getPiece(move.getStartPosition());
        TeamColor turnColor = getTeamTurn();
        TeamColor color = piece.getTeamColor();
        if (color != turnColor || !piece.pieceMoves(chessBoard, move.getStartPosition()).contains(move)) {
            throw new InvalidMoveException();
        }
        ChessPiece newPiece = piece;
        int promotionSide = (color == TeamColor.WHITE) ? 8 :1;
        if (move.getEndPosition().getRow() == promotionSide && piece.getPieceType() == ChessPiece.PieceType.PAWN && move.getPromotionPiece() != null) {
            newPiece = new ChessPiece(color, move.getPromotionPiece());
        }
        ChessPiece attackedPiece = chessBoard.getPiece(move.getEndPosition());
        chessBoard.addPiece(move.getEndPosition(), newPiece);
        chessBoard.deletePiece(move.getStartPosition());
        if (isInCheck(color)) {
            chessBoard.addPiece(move.getEndPosition(), attackedPiece);
            chessBoard.addPiece(move.getStartPosition(), piece);
            throw new InvalidMoveException();
        }

        if (getTeamTurn() == TeamColor.WHITE){
            setTeamTurn(TeamColor.BLACK);
        } else {
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
        ChessPosition kingPos = findKingPosition(teamColor);
        for (int i = 1; i < 9; i++){
            for (int j = 1; j < 9; j++) {
                ChessPosition currPos = new ChessPosition(i, j);
                ChessPiece piece = chessBoard.getPiece(currPos);

                if(piece != null && piece.getTeamColor() != teamColor){
                    Collection<ChessMove> moves = piece.pieceMoves(chessBoard, currPos);
                    ArrayList<ChessMove> movesList = new ArrayList<>(moves);
                    if (movesList.contains(new ChessMove(currPos, kingPos, null)) ||
                            movesList.contains(new ChessMove(currPos, kingPos, ChessPiece.PieceType.QUEEN)) ||
                            movesList.contains(new ChessMove(currPos, kingPos, ChessPiece.PieceType.ROOK)) ||
                            movesList.contains(new ChessMove(currPos, kingPos, ChessPiece.PieceType.BISHOP)) ||
                            movesList.contains(new ChessMove(currPos, kingPos, ChessPiece.PieceType.KNIGHT))){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if (!isInCheck(teamColor)) {
            return false;
        }
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                ChessPosition currPos = new ChessPosition(i, j);
                ChessPiece piece = chessBoard.getPiece(currPos);
                if (piece != null && piece.getTeamColor() == teamColor && !validMoves(currPos).isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private ChessPosition findKingPosition(TeamColor currTeamColor) {
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                ChessPosition currPos = new ChessPosition(i, j);
                ChessPiece piece = chessBoard.getPiece(currPos);
                if (piece != null && piece.getTeamColor() == currTeamColor && piece.getPieceType() == ChessPiece.PieceType.KING) {
                    return currPos;
                }
            }
        }
        return null;
    }
    
    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        ArrayList<ChessMove> validMoves = new ArrayList<>();
        for (int i = 1; i < 9; i++){
            for (int j = 1; j < 9; j++) {
                ChessPosition currPos = new ChessPosition(i, j);
                ChessPiece currPiece = chessBoard.getPiece(currPos);
                if (currPiece == null){
                    continue;
                }
                if (currPiece.getTeamColor() == teamColor){
                    validMoves.addAll(validMoves(currPos));
                }
            }
        }
        return !isInCheck(teamColor) && validMoves.isEmpty();
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.chessBoard = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return chessBoard;
    }


}
