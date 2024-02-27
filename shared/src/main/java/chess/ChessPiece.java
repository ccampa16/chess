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
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
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
            case KNIGHT:
                validMoves.addAll(getKnightMoves(board, row, col));
                break;
            case QUEEN:
                validMoves.addAll(getQueenMoves(board, row, col));
                break;
            case ROOK:
                validMoves.addAll(getRookMoves(board, row, col));
                break;
            case PAWN:
                validMoves.addAll(getPawnMoves(board, row, col));
                break;
            default:
                break;
        }

        return validMoves;
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
                        else if (capturedPiece == null){ //empty square
                            validMoves.add(new ChessMove(new ChessPosition(row, col), newPosition, null));
                        }
                        else { //obstacle (cant jump over pieces)
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
        int[] directionChoices = {-1, 0, 1};
        for (int i : directionChoices){
            for (int j : directionChoices) {
                if (i == 0 && j == 0){ //skip current spot
                    continue;
                }
                int newRow = row + i;
                int newCol = col + j;

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

    private Collection<ChessMove> getKnightMoves(ChessBoard board, int row, int col){
        Collection<ChessMove> validMoves = new ArrayList<>();

        int[][] directionChoices = {
                {-2, -1}, {-2, 1},
                {-1, -2}, {-1, 2},
                {1, -2}, {1, 2},
                {2, -1}, {2, 1}
        };
        for (int[] direction : directionChoices){
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            if (newRow >= 1 && newRow <= 8 && newCol >= 1 && newCol <= 8) {
                ChessPosition newPosition = new ChessPosition(newRow, newCol);
                ChessPiece capturedPiece = board.getPiece(newPosition);

                if (capturedPiece == null || capturedPiece.getTeamColor() != pieceColor){
                    validMoves.add(new ChessMove(new ChessPosition(row, col), newPosition, null));
                }
            }
        }
        return validMoves;
    }

    private Collection<ChessMove> getQueenMoves(ChessBoard board, int row, int col){
        Collection<ChessMove> validMoves = new ArrayList<>();
        int [][] directionChoices = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1}, //horizontal moves
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1} //diagonal moves
        };
        return getValidMovesQueenAndRook(board, row, col, directionChoices);
    }
    private Collection<ChessMove> getRookMoves(ChessBoard board, int row, int col) {
        Collection<ChessMove> validMoves = new ArrayList<>();
        int [][] directionChoices = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1} //{row change, column change}
        };
        return getValidMovesQueenAndRook(board, row, col, directionChoices);
    }
    private Collection<ChessMove> getValidMovesQueenAndRook(ChessBoard board, int row, int col, int[][] directionChoices){
        Collection<ChessMove> validMoves = new ArrayList<>();
        for (int[] direction : directionChoices) {
            int i = direction[0]; // initialize i to equal each of the row change options
            int j = direction[1]; // initialize j respectively

            int newRow = row + i; // add that spacing to the current row number
            int newCol = col + j; // ^^

            while (newRow >= 1 && newRow <= 8 && newCol >= 1 && newCol <= 8){ // check if new spot is in bounds
                ChessPosition newPosition = new ChessPosition(newRow, newCol);
                ChessPiece capturedPiece = board.getPiece(newPosition); //retrieves the piece currently in the new spot (if there is one)

                if (capturedPiece == null) {
                    validMoves.add(new ChessMove(new ChessPosition(row, col), newPosition, null)); // no piece, so move there
                } else if (capturedPiece.getTeamColor() != pieceColor){ // if there is an opp
                    validMoves.add(new ChessMove(new ChessPosition(row, col), newPosition, null));
                    break; //opp color
                } else{
                    break; //obstacle
                }
                newRow += i; //keep the loop going
                newCol += j;
            }
        }
        return validMoves;
    }

  private Collection<ChessMove> getPawnMoves(ChessBoard board, int row, int col){
      Collection<ChessMove> validMoves = new ArrayList<>();
      int direction;
      if (pieceColor == ChessGame.TeamColor.WHITE){
          direction = 1;
      } else {
          direction = -1;
      }
      int newRow = row + direction;
      if (newRow <= 8 && newRow >= 1 && col <= 8 && col >=1
              && board.getPiece(new ChessPosition(newRow, col)) == null){ //in bounds & empty spot
          //single move
          if ((pieceColor == ChessGame.TeamColor.WHITE && newRow == 8) || // 8 & 1??????? 7 2
                  (pieceColor == ChessGame.TeamColor.BLACK && newRow == 1)){ //promotion available
              validMoves.add(new ChessMove(new ChessPosition(row, col), new ChessPosition(newRow, col), PieceType.QUEEN));
              validMoves.add(new ChessMove(new ChessPosition(row, col), new ChessPosition(newRow, col), PieceType.ROOK));
              validMoves.add(new ChessMove(new ChessPosition(row, col), new ChessPosition(newRow, col), PieceType.KNIGHT));
              validMoves.add(new ChessMove(new ChessPosition(row, col), new ChessPosition(newRow, col), PieceType.BISHOP));
          } else {
              validMoves.add(new ChessMove(new ChessPosition(row, col), new ChessPosition(newRow, col), null));
          }
          //double move
          if ((pieceColor == ChessGame.TeamColor.WHITE && row == 2) || (pieceColor == ChessGame.TeamColor.BLACK && row == 7)){
              int doubRow = newRow + direction;
              if (doubRow <= 8 && doubRow >= 1 && col <= 8 && col >=1
                      && board.getPiece(new ChessPosition(doubRow, col)) == null){
                  validMoves.add(new ChessMove(new ChessPosition(row, col), new ChessPosition(doubRow, col), null));
              }
          }
      }
      //captures
      int[] colCaptureDir = {col - 1, col + 1};
      for (int captureCol : colCaptureDir){
          int captureRow = row + direction;
          ChessPiece pieceInSpot = board.getPiece(new ChessPosition(captureRow, captureCol));
          if (captureRow <= 8 && captureRow >= 1 && captureCol <= 8 && captureCol >=1 //in bounds
                  && pieceInSpot != null && pieceInSpot.getTeamColor() != pieceColor){  //piece in spot is opp
              if ((pieceColor == ChessGame.TeamColor.WHITE && newRow == 8) || // 8 & 1???????
                      (pieceColor == ChessGame.TeamColor.BLACK && newRow == 1)){ //promotion available
                  validMoves.add(new ChessMove(new ChessPosition(row, col), new ChessPosition(captureRow, captureCol), PieceType.QUEEN));
                  validMoves.add(new ChessMove(new ChessPosition(row, col), new ChessPosition(captureRow, captureCol), PieceType.ROOK));
                  validMoves.add(new ChessMove(new ChessPosition(row, col), new ChessPosition(captureRow, captureCol), PieceType.KNIGHT));
                  validMoves.add(new ChessMove(new ChessPosition(row, col), new ChessPosition(captureRow, captureCol), PieceType.BISHOP));
              } else {
                  validMoves.add(new ChessMove(new ChessPosition(row, col), new ChessPosition(captureRow, captureCol), null));
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