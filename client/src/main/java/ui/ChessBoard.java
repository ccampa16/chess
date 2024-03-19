package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static ui.EscapeSequences.*;


public class ChessBoard {
    private static final int BOARD_SIZE = 8;
    private static final int LINE_SIZE = 2;
    private static final int SQUARE_SIZE = 2;
    private static final int WHITE_SQUARE_SIZE = 2;
    private static final int BLACK_SQUARE_SIZE = 2;

    private static final String EMPTY = "   ";
    private static final String HORIZONTAL_LINE = "----";
    private final Random rand = new Random();
    public static void main(String[] args){
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);
        ChessBoard chessBoard = new ChessBoard();
        char[][] initialBoard = {
                {'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R'},
                {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'},
                {'r', 'n', 'b', 'q', 'k', 'b', 'n', 'r'}
        };
        drawChessBoard(out, initialBoard);

    }
    public static void drawChessBoard(PrintStream out, char[][] board){
        out.print(ERASE_SCREEN);
        drawHeaders(out, 'a', 'h');
        for (int boardRow = 0; boardRow < BOARD_SIZE; ++boardRow){
//            if ((boardRow % 2) == 0){
//                drawRow(out, boardRow, board[boardRow]);
//            } else {
//                drawRow(out, boardRow, board[boardRow]);
//            }
            drawRow(out, boardRow, board[boardRow]);
//            if (boardRow < BOARD_SIZE - 1){
//                drawHorizontalLine(out);
//            }
        }
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
    }
    private static void drawHeaders(PrintStream out, char startCol, char endCol){
        setBlack(out);
        out.print(EMPTY);
        for (char col = startCol; col <= endCol; col++){
            drawHeader(out, col);
            if(col != endCol){
                out.print(EMPTY.repeat(LINE_SIZE));
            }
        }
        out.println();
    }
    private static void drawHeader(PrintStream out, char header){
        out.print(EMPTY.repeat(SQUARE_SIZE));
        printHeader(out, String.valueOf(header));
        out.print(EMPTY.repeat(SQUARE_SIZE - SQUARE_SIZE/2 -1));
    }
    private static void printHeader(PrintStream out, String headerText){
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_MAGENTA);
        out.print(headerText);
        setBlack(out);
    }
    private static void  drawRow(PrintStream out, int rowNum, char[] rowData){
        for (int i = 0; i < SQUARE_SIZE; i++){
            for (int squareCOl = 0; squareCOl<BOARD_SIZE; ++squareCOl){
                drawSquare(out, rowData[squareCOl]);
                if (squareCOl < BOARD_SIZE - 1){
                    out.print(EMPTY.repeat(LINE_SIZE));
                }
            }
            if (i == SQUARE_SIZE/2){
                printHeader(out, String.valueOf(rowNum+1));
            } else{
                out.print(EMPTY);
            }
            out.println();
        }
    }
    private static void drawSquare(PrintStream out, char content){
        setWhite(out);
        out.print(content);
        out.print(EMPTY.repeat(SQUARE_SIZE));
        setBlack(out);
    }
    private static void drawHorizontalLine(PrintStream out){
        //int boardSize = BOARD_SIZE * SQUARE_SIZE + (BOARD_SIZE - 1) * LINE_SIZE;
        for (int lineRow = 0; lineRow < LINE_SIZE; ++lineRow){
            for (int i = 0; i < LINE_SIZE; i++){
                //setRed(out);
                out.print(EMPTY.repeat(LINE_SIZE));
                setBlack(out);
                out.println();
            }
        }
    }
    private static void printRowHeader(PrintStream out, int rowNum){
        out.print(EMPTY.repeat(SQUARE_SIZE / 2));
        printHeader(out, String.valueOf(rowNum));
        out.print(EMPTY.repeat(SQUARE_SIZE - SQUARE_SIZE / 2 -1));
    }
    private static void setBlack(PrintStream out){
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
    }
    private static void setWhite(PrintStream out){
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_BLACK);
    }
    private static void setRed(PrintStream out){
        out.print(SET_BG_COLOR_RED);
        out.print(SET_TEXT_COLOR_RED);
    }
}