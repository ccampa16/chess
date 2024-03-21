package ui;

import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static ui.EscapeSequences.*;


public class UiChessBoard {
    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final String EMPTY = "   ";
    private static Random rand = new Random();


    public static void main(String[] args) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        drawHeaders(out);

        chess.ChessBoard board = new chess.ChessBoard();

        drawChessBoard(out, board);
        drawHeaders(out);

        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private static void drawHeaders(PrintStream out) {
        setBlack(out);
        out.print(EMPTY + " ");
        String[] headers = {"a", "b", "c", "d", "e", "f", "g", "h"};
        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
            drawHeader(out, headers[boardCol]);
            if (boardCol < BOARD_SIZE_IN_SQUARES - 1) {
                out.print("  ");
            }
        }
        out.println();
    }

    private static void drawHeader(PrintStream out, String headerText) {
        printHeaderText(out, headerText);
    }

    private static void printHeaderText(PrintStream out, String header) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_GREEN);

        out.print(header);

        setBlack(out);
    }

    private static void drawChessBoard(PrintStream out, chess.ChessBoard board) {
        String[] rowHeader = {"8", "7", "6", "5", "4", "3", "2", "1"};
        for (int boardRow = 0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow) {
            out.print(" " + rowHeader[boardRow] + " "); // row header
            for (int col = 0; col < BOARD_SIZE_IN_SQUARES; ++col) {
                var position = new ChessPosition(boardRow + 1, col + 1);
                var piece = board.getPiece(position);
                drawSquare(out, piece, boardRow, col);
            }
            out.print(" " + rowHeader[boardRow] + " "); //row header
            out.println();
        }
    }


    private static void drawSquare(PrintStream out, ChessPiece piece, int row, int col) {
        if ((row + col) % 2 == 0) {
            out.print(SET_BG_COLOR_WHITE);
        } else {
            out.print(SET_BG_COLOR_BLACK);
        }
        if (piece != null) {
            out.print(SET_TEXT_COLOR_RED);
            String pieceSymbol = getPieceSymbol(piece);
            out.print(pieceSymbol);
        } else {
            out.print(EMPTY);
        }
        out.print(SET_BG_COLOR_BLACK);
    }

    private static String getPieceSymbol(ChessPiece piece) {
        String symbol = switch (piece.getPieceType()) {
            case PAWN -> "P";
            case ROOK -> "R";
            case KNIGHT -> "N";
            case BISHOP -> "B";
            case QUEEN -> "Q";
            case KING -> "K";
        };
        return (piece.getTeamColor() == ChessGame.TeamColor.WHITE) ? symbol : symbol.toUpperCase();
    }

    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
    }
}