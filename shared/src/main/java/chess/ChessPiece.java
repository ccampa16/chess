package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> validMoves = new ArrayList<>();

        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        switch (type) {
            case BISHOP:
                validMoves.addAll(getBishopMoves(board, row, col));
                break;//
            //other cases for piece types
            case KING:
                validMoves.addAll(getKingMoves(board, row, col));
                break;
            //case QUEEN:
            //case PAWN:
            //case ROOK:
            //case KNIGHT:
            default:
                break;
        }


        return validMoves;
        //throw new RuntimeException("Not implemented");
    }

    private Collection<ChessMove> getBishopMoves(ChessBoard board, int row, int col) {
        Collection<ChessMove> validMoves = new ArrayList<>();
        for (int i = -1; i <= 1; i +=2){
            for (int j = -1; j <= 1; j += 2){
                for (int k = 1; k <= 7; k++){
                    int newRow = row + k * i;
                    int newCol = col + k * j;

                    //check if new pos is in boundaries
                    if (newRow >= 1 && newRow <= 8 && newCol >= 1 && newCol <= 8){
                        ChessPosition newPosition = new ChessPosition(newRow, newCol);
                        ChessPiece capturedPiece = board.getPiece(newPosition);

                        //capture enemy piece
                        if (capturedPiece != null && capturedPiece.getTeamColor() != pieceColor){
                            validMoves.add(new ChessMove(new ChessPosition(row, col), newPosition, null));
                            break;
                        }
                        //empty square
                        else if (capturedPiece == null){
                            validMoves.add(new ChessMove(new ChessPosition(row, col), newPosition, null));
                        }
                        //if theres an obstacle (cant jump over pieces)
                        else {
                            break;
                        }
                    } else {
                        break; // if out new pos is of boundaries
                    }
                }
            }
        }
        return validMoves;
    }
    private Collection<ChessMove> getKingMoves(ChessBoard board, int row, int col){
        Collection<ChessMove> validMoves = new ArrayList<>();
        //possible directions
        int[] directions = {-1, 0, 1};
        for (int i : directions){
            for (int j : directions) {
                //skip current spot
                if (i == 0 && j == 0){
                    continue;
                }
                int newRow = row + i;
                int newCol = col + j;
                //check boundaries
                if (newRow >= 1 && newRow <= 8 && newCol >= 1 && newCol <= 8){
                    ChessPosition newPosition = new ChessPosition(newRow, newCol);
                    ChessPiece capturedPiece = board.getPiece(newPosition);
                    //capture piece or move to empty spot
                    if (capturedPiece == null || capturedPiece.getTeamColor() != pieceColor){
                        validMoves.add(new ChessMove(new ChessPosition(row, col), newPosition, null));
                    }
                }
            }
        }
        return validMoves;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessPiece that)) return false;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }
}

//