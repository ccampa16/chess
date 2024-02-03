package chess;

import java.util.ArrayList;
import java.util.Collection;

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
        //throw new RuntimeException("Not implemented");
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
        Collection<ChessMove> possibleMoves = new ArrayList<>();
        if (this.chessBoard.getPiece(startPosition) == null){
            return possibleMoves;
        } else {
            possibleMoves = this.chessBoard.getPiece(startPosition).pieceMoves(this.chessBoard, startPosition);
            Collection<ChessMove> validMovesOnly = new ArrayList<>();
            for (ChessMove move : validMovesOnly) {
//                ChessBoard clonedBoard = this.chessBoard.clone();
//                ChessPiece piece = clonedBoard.getPiece(move.getStartPosition());
//                ChessPiece attackedPiece = clonedBoard.getPiece(move.getEndPosition());
//                clonedBoard.addPiece(move.getEndPosition(), piece);
//                clonedBoard.deletePiece(move.getStartPosition());
//                if(!isInCheck(piece.getTeamColor()){
//                    validMovesOnly.add(move);
//                }
//            }
                ChessPiece piece = this.chessBoard.getPiece(move.getStartPosition());
                ChessPiece newPiece = this.chessBoard.getPiece(move.getEndPosition());
                this.chessBoard.addPiece(move.getEndPosition(), piece);
                this.chessBoard.deletePiece(move.getStartPosition());
                if(!this.isInCheck(piece.getTeamColor())){
                    validMovesOnly.add(move);
                }
                this.chessBoard.addPiece(move.getStartPosition(), piece);
                this.chessBoard.addPiece(move.getEndPosition(), newPiece);
            }
            return validMovesOnly;
        }
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        TeamColor turnColor = getTeamTurn();
        ChessPiece piece = this.chessBoard.getPiece(move.getStartPosition());
        TeamColor color = piece.getTeamColor();
        Collection<ChessMove> possibleMoves = piece.pieceMoves(this.chessBoard, move.getStartPosition());
        if(color != turnColor){
            throw new InvalidMoveException("Not your turn");
        }
        if(this.chessBoard.getPiece(move.getEndPosition()) != null){
            throw new InvalidMoveException("Same team");
        }
        if (!possibleMoves.contains(move)){
            throw new InvalidMoveException("Move not valid");
        } else {
            ChessPiece newPiece = piece;
            if (move.getPromotionPiece() != ChessPiece.PieceType.PAWN){
                int promotionRank = (color == TeamColor.WHITE) ? 7 :0;
                if (move.getEndPosition().getRow() == promotionRank) {
                    newPiece = new ChessPiece(color, move.getPromotionPiece());
                } else {
                    throw new InvalidMoveException("Invalid promotion attempt");
                }
            }
            ChessPiece attackedPiece = this.chessBoard.getPiece(move.getEndPosition());
            this.chessBoard.addPiece(move.getEndPosition(), newPiece);
            this.chessBoard.deletePiece(move.getStartPosition());
            if(this.isInCheck(color)){
                this.chessBoard.addPiece(move.getEndPosition(), attackedPiece);
                this.chessBoard.addPiece(move.getStartPosition(), piece);
                throw new InvalidMoveException("Will put king in check");
            }
            if (this.getTeamTurn() == TeamColor.WHITE){
                this.setTeamTurn(TeamColor.BLACK);
            } else {
                this.setTeamTurn(TeamColor.WHITE);
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
        ChessPosition kingPos = findKingPosition(teamColor);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPosition currPos = new ChessPosition(i, j);
                ChessPiece piece = chessBoard.getPiece(currPos);

                if(piece != null && piece.getTeamColor() != teamColor && piece.pieceMoves(chessBoard, currPos).contains(new ChessMove(currPos, kingPos, null))){
                    return true;
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
        if (!isInCheck(teamColor)){
            return false;
        }
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++) {
                ChessPosition currPos = new ChessPosition(i, j);
                ChessPiece piece = chessBoard.getPiece(currPos);
                if(piece != null && piece.getTeamColor() == teamColor && !validMoves(currPos).isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }
    private ChessPosition findKingPosition(TeamColor currTeamColor){
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                ChessPosition currPos = new ChessPosition(i,j);
                ChessPiece piece = chessBoard.getPiece(currPos);
                if (piece != null && piece.getTeamColor() == currTeamColor && piece.getPieceType() == ChessPiece.PieceType.KING) {
                    return currPos;
                }
            }
        }
        return null;
    }
    public Collection<ChessMove> piecesWithKingChecked(ChessPosition kingPos, TeamColor kingColor){
        Collection<ChessMove> kingAttackedMoves = new ArrayList<>();
        if (kingPos == null){
            return kingAttackedMoves;
        }
        //helper functions for each piece
        return kingAttackedMoves;
    }
    public void kingOnKing (Collection<ChessMove> kingAttackedMoves, ChessPosition kingPos, TeamColor kingColor, int newRow, int newCol){
        if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8){ //valid spot inbounds
            ChessPosition newPos = new ChessPosition(newRow, newCol);
            ChessPiece king = chessBoard.getPiece(newPos);
            if (king != null && king.getTeamColor() != kingColor && king.getPieceType() == ChessPiece.PieceType.KING){
                kingAttackedMoves.add(new ChessMove(newPos, kingPos, null));
            }
        }
    }
    public void pawnOnKing(Collection<ChessMove> kingAttackedMoves, ChessPosition kingPos, TeamColor kingColor){
        int direction = (kingColor == TeamColor.WHITE) ? 1 : -1;
        pawnMove(kingAttackedMoves, kingPos, kingColor, direction, 1);
        pawnMove(kingAttackedMoves, kingPos, kingColor, direction, -1);
    }
    public void knightOnKing(Collection<ChessMove> kingAttackedMoves, ChessPosition kingPos, TeamColor kingColor, int rowChange, int colChange){
        int newRow = kingPos.getRow() + rowChange;
        int newCol = kingPos.getColumn() + colChange;
        if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8){
            ChessPosition newPos = new ChessPosition(newRow, newCol);
            ChessPiece knight = chessBoard.getPiece(newPos);
            if (knight != null && knight.getPieceType() == ChessPiece.PieceType.KNIGHT && knight.getTeamColor() != kingColor){
                kingAttackedMoves.add(new ChessMove(newPos, kingPos, null));
            }
        }
    }
    //public void rookOrQueenOnKing()

    private void pawnMove(Collection<ChessMove> kingAttackedMoves, ChessPosition kingPos, TeamColor kingColor, int rowChange, int colChange){
        int newRow = kingPos.getRow() + rowChange;
        int newCol = kingPos.getColumn() + colChange;
        if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8){
            ChessPosition newPos = new ChessPosition(newRow, newCol);
            ChessPiece piece = chessBoard.getPiece(newPos);
            if(piece != null && piece.getPieceType() == ChessPiece.PieceType.PAWN && piece.getTeamColor() != kingColor){
                kingAttackedMoves.add(new ChessMove(newPos, kingPos, null));
            }
        }
    }




    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        //if king isnt in check
        //if there are no valid moves
            //then it is stalemate
        if (isInCheck(teamColor)){
            return false;
        } else {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    ChessPosition startPos = new ChessPosition(i, j);
                    ChessPiece piece = chessBoard.getPiece(startPos);

                    if (piece != null && piece.getTeamColor() == teamColor) {
                        Collection<ChessMove> validMoves = validMoves(startPos);
                        if (validMoves != null && !validMoves.isEmpty()) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }
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
