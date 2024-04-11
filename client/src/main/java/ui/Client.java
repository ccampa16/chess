package ui;

import model.GameData;
import model.UserData;
import request.*;
import result.*;
import serverfacade.ServerFacade;


import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Client {
    private final ServerFacade serverFacade;
    private final String serverUrl;
    private boolean loggedIn = false;
    private UserData userData;
    private String authToken;
    private List<GameData> games;
    private int gameID;
    private String username;
    public Client(String serverUrl){
        this.serverUrl = serverUrl;
        this.serverFacade = new ServerFacade(serverUrl);
    }
    public static void main(String[] args) {
        var serverUrl = "http://localhost:8080";
        if (args.length == 1) {
            serverUrl = args[0];
        }
        new Client(serverUrl).run();
    }
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while(true){
            if(!loggedIn){
                preLoginUI(scanner);
            } else {
                postLoginUI(scanner);
            }
        }
    }

    private void preLoginUI(Scanner scanner){
        System.out.println("Welcome to Chess!");
        System.out.println("Choose an option:");
        System.out.println("1. Help");
        System.out.println("2. Quit");
        System.out.println("3. Login");
        System.out.println("4. Register");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice){
            case 1:
                displayHelpPreLogin();
                break;
            case 2:
                System.out.println("Exiting...");
                System.exit(0);
            case 3:
                login(scanner);
                break;
            case 4:
                register(scanner);
                break;
            default:
                System.out.println("Invalid choice, please try again:)");
        }
    }
    private void postLoginUI(Scanner scanner){
        System.out.println("Choose an option:");
        System.out.println("1. Help");
        System.out.println("2. Logout");
        System.out.println("3. Create Game");
        System.out.println("4. List Games");
        System.out.println("5. Join Game");
        System.out.println("6. Join Observer");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice){
            case 1:
                displayHelpPostLogin();
                break;
            case 2:
                logout();
                break;
            case 3:
                createGame(scanner);
                break;
            case 4:
                listGames();
                break;
            case 5:
                joinGame(scanner);
                break;
            case 6:
                joinObserver(scanner);
                break;
            default:
                System.out.println("Invalid choice, please try again:)");
        }
    }
    private void displayHelpPreLogin(){
        System.out.println("Before you can start, please login or register.");
        System.out.println("Here are your command options:");
        System.out.println("1. Help - Display this help message");
        System.out.println("2. Quit - Exit the program");
        System.out.println("3. Login - Login with your username and password");
        System.out.println("4. Register - Register a new account");
    }
    private void displayHelpPostLogin() {
        System.out.println("Here are your command options:");
        System.out.println("1. Help - Display this help message");
        System.out.println("2. Logout - Logout from your current account");
        System.out.println("3. Create Game - Create a new chess game");
        System.out.println("4. List Games - List all existing chess games");
        System.out.println("5. Join Game - Join an existing game");
        System.out.println("6. Join Observer - Observe an existing chess game");
    }

    private void login(Scanner scanner){
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        LoginRequest request = new LoginRequest(username, password);
        try{
            LoginResult result = serverFacade.login(request);
            System.out.println("You are logged in:)");
            serverFacade.setAuthtoken(result.getAuthToken());
            //authToken = result.getAuthToken();
            loggedIn = true;
            this.username = username;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private void register(Scanner scanner){
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        RegisterRequest request = new RegisterRequest(username, password, email);
        try {
            RegisterResult result = serverFacade.register(request);
            System.out.println("You are registered:)");
            serverFacade.setAuthtoken(result.getAuthToken());
            //authToken = result.getAuthToken();
            loggedIn = true;
            this.username = username;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void logout(){
        LogoutRequest request = new LogoutRequest(serverFacade.getAuthtoken()); //requires an authtoken
        try {
            LogoutResult result = serverFacade.logout(request);
            System.out.println("Goodbye");
            loggedIn = false;
            serverFacade.setAuthtoken(null);  //??
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void createGame(Scanner scanner){
        System.out.print("Enter the name of your new game: ");
        String gameName = scanner.nextLine();
        CreateGameRequest request = new CreateGameRequest(gameName);
        try {
            CreateGameResult result = serverFacade.createGame(request);
            System.out.println("Your game is created");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void listGames(){
        ListGamesRequest request = new ListGamesRequest(serverFacade.getAuthtoken()); //requires an authtoken
        try {
            ListGamesResult result = serverFacade.listGames();
            System.out.println("List of games: ");
            games = result.getGames();
            //List<GameData> games = result.getGames();
            if (games != null){
                for (int i = 0; i < games.size(); i++){
                    GameData game = games.get(i);
                    //print other info
                    System.out.println("Game " + (i+1) + ": " + game.gameName());
                    System.out.println("Players: " + game.blackUsername() + " & " + game.whiteUsername());
                }
            } else {
                System.out.println("No games available.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private void joinGame(Scanner scanner){
        listGames();
        System.out.print("Enter the number of the game you want to join: ");
        int gameNum = scanner.nextInt();
        scanner.nextLine();
        //
        String color = null;
        boolean validColor = false;
        while (!validColor){
            System.out.println("Please enter your color: 'BLACK' or 'WHITE'");
            color = scanner.nextLine().trim().toUpperCase();
            if (color.equals("BLACK") || color.equals("WHITE")){
                validColor = true;
            } else {
                System.out.println("Invalid color. Please enter 'BLACK' or 'WHITE'");
            }
        }
        if (games != null && gameNum >= 1 && gameNum <= games.size()){
            GameData selectedGame = games.get(gameNum - 1); //gameNum - 1??
            gameID = selectedGame.gameID();

        }


        JoinGameRequest request = new JoinGameRequest(color, gameID);
        try {
            JoinGameResult result = serverFacade.joinGame(request);
            System.out.println("You have joined");
            chess.ChessBoard board = new chess.ChessBoard();
            board.resetBoard();
            UiChessBoard.drawChessBoard(System.out, board);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private void joinObserver(Scanner scanner){
        listGames();
        System.out.print("Enter the number of the game you want to observe: ");
        int gameNum = scanner.nextInt();
        scanner.nextLine();
        String color = null;
        if (games != null && gameNum >= 1 && gameNum <= games.size()){
            GameData selectedGame = games.get(gameNum - 1); //gameNum - 1??
            gameID = selectedGame.gameID();
        }
        JoinGameRequest request = new JoinGameRequest(color, gameID);
        try {
            JoinGameResult result = serverFacade.joinGame(request);
            System.out.println("You have joined");
            chess.ChessBoard board = new chess.ChessBoard();
            board.resetBoard();
            UiChessBoard.drawChessBoard(System.out, board);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void displayGamePlayCommand(Scanner scanner){
        System.out.println("Choose an option:");
        System.out.println("1. Help");
        System.out.println("2. Redraw Chess Board");
        System.out.println("3. Leave");
        System.out.println("4. Make Move");
        System.out.println("5. Resign");
        System.out.println("6. Highlight Legal Moves");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice){
            case 1:
                displayHelpGamePlay();
                break;
            case 2:
                redrawChessBoard(scanner);
                break;
            case 3:
                leaveGame();
                break;
            case 4:
                makeMove(scanner);
                break;
            case 5:
                resign();
                break;
            case 6:
                highlightLegalMoves(scanner);
                break;
            default:
                System.out.println("Invalid choice, please try again:)");
        }
    }

    private void displayHelpGamePlay(){
        System.out.println("Here are your command options:");
        System.out.println("1. Help - Display this help message");
        System.out.println("2. Redraw Chess Board - Draw out the current chess board");
        System.out.println("3. Leave - Leave chess game");
        System.out.println("4. Make Move - Make a move on the board");
        System.out.println("5. Resign - Forfeit and end the game");
        System.out.println("6. Highlight Legal Moves - Highlight legal moves for your selected piece");
    }
    private void redrawChessBoard(Scanner scanner){}
    private void leaveGame(){}
    private void makeMove(Scanner scanner){}
    private void resign(){}
    private void highlightLegalMoves(Scanner scanner){}

}
