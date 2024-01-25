package chess;

import java.util.Arrays;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private ChessPiece[][] squares = new ChessPiece[8][8];
    public ChessBoard() {
        
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        squares[position.getRow()-1][position.getColumn()-1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        int row = position.getRow();
        int col = position.getColumn();
        if (row >= 1 && row <= 8 && col >= 1 && col <= 8){
            return squares[row-1][col-1];
        } else {
            return null;
        }
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        //pawns
        for (int col = 1; col <= 8; col++){
            addPiece(new ChessPosition(2, col), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN));
            addPiece(new ChessPosition(7, col), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN));
        }
        //set order of back row
        ChessPiece.PieceType[] backRowOrder = {
                ChessPiece.PieceType.ROOK, ChessPiece.PieceType.KNIGHT, ChessPiece.PieceType.BISHOP, ChessPiece.PieceType.QUEEN,
                ChessPiece.PieceType.KING, ChessPiece.PieceType.BISHOP, ChessPiece.PieceType.KNIGHT, ChessPiece.PieceType.ROOK };

        for (int i = 1; i <= 8; i++){ //loop through rows
            for (int j = 1; j <= 8; j++){ //loop through columns
                ChessGame.TeamColor teamColor;
                if (i <= 2){ // if playing on bottom half of board
                    teamColor = ChessGame.TeamColor.WHITE;
                } else { //on top half of board
                    teamColor = ChessGame.TeamColor.BLACK;
                }

                if (i == 1||i == 8){
                    addPiece(new ChessPosition(i, j), new ChessPiece(teamColor, backRowOrder[j -1]));
                } else if (i == 2 || i == 7) {
                    continue;
                } else {
                    addPiece(new ChessPosition(i, j), null);
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessBoard that)) return false;
        return Arrays.deepEquals(squares, that.squares);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(squares);
    }
}
