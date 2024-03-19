package ui;

import model.GameData;
import model.UserData;
import request.*;
import result.*;
import serverfacade.ServerFacade;


import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Client {
    private final ServerFacade serverFacade;
    private final String serverUrl;
    private boolean loggedIn = false;
    private UserData userData;
    private String authToken;
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
        //System.out.println("\uD83D\uDC36 Welcome to the pet store. Sign in to start.");
        //System.out.print(client.help());

        Scanner scanner = new Scanner(System.in);
        while(true){
            if(!loggedIn){
                preLoginUI(scanner);
            } else {
                postLoginUI(scanner);
            }
        }
//        var result = "";
//        while (!result.equals("quit")) {
//            printPrompt();
//            String line = scanner.nextLine();
//
//            try {
//                result = eval(line);
//                System.out.print(BLUE + result);
//            } catch (Throwable e) {
//                var msg = e.toString();
//                System.out.print(msg);
//            }
//        }
//        System.out.println();
    }

//    pet shop stuff
//    public void notify(Notification notification) {
//        System.out.println(RED + notification.message());
//        printPrompt();
//    }
//
//    private void printPrompt() {
//        System.out.print("\n" + RESET + ">>> " + GREEN);
//    }
//
//    public String eval(String input){
//        try {
//            var tokens = input.toLowerCase().split(" ");
//            var cmd = (tokens.length > 0) ? tokens[0] : "help";
//            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
//            return switch (cmd){
//                case "register" -> register(params);
//                case "login" -> login(params);
//                case "logout" -> logout(params);
//                case ""
//            }
//        }
//    }
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
        //System.out.println("Welcome to Chess!");
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
        //create login request object from username and password
        //call server facade method with the request
        LoginRequest request = new LoginRequest(username, password);
        try{
            LoginResult result = serverFacade.login(request);
            System.out.println("You are logged in:)");
            loggedIn = true;
            authToken = result.getAuthToken();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //imp
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
            loggedIn = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //imp
    }
    private void logout(){
        LogoutRequest request = new LogoutRequest(authToken); //requires an authtoken
        try {
            LogoutResult result = serverFacade.logout(request);
            System.out.println("Goodbye");
            loggedIn = false;
            authToken = null; //??
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //imp
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
        //imp
    }
    private void listGames(){
        ListGamesRequest request = new ListGamesRequest(authToken); //requires an authtoken
        try {
            ListGamesResult result = serverFacade.listGames(request);
            System.out.println("List of games: ");
            List<GameData> games = result.getGames();
            if (games != null){
                for (int i = 0; i < games.size(); i++){
                    GameData game = games.get(i);
                    System.out.println("Game " + (i+1) + ": " + game.gameName());
                }
            } else {
                System.out.println("No games available.");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void joinGame(Scanner scanner){
        listGames();
        System.out.print("Enter the number of the game you want to join: ");
        int gameNum = scanner.nextInt();
        scanner.nextLine();
//        System.out.println("Please enter your color: 'BLACK' or 'WHITE'");
//        String color = scanner.nextLine().trim().toUpperCase(); //do i need to make sure they enter an accepted string??
        //scanner.nextLine();
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

        JoinGameRequest request = new JoinGameRequest(color, gameNum);
        try {
            JoinGameResult result = serverFacade.joinGame(request);
            System.out.println("You have joined");
            //ChessBoard.drawChessBoard();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //call ui.ChessBoard !!!
        //imp
    }
    private void joinObserver(Scanner scanner){
        listGames();
        System.out.print("Enter the number of the game you want to observe: ");
        int gameNum = scanner.nextInt();
        scanner.nextLine();
        String color = null;
        JoinGameRequest request = new JoinGameRequest(color, gameNum); //can I create another constructor?
        try {
            JoinGameResult result = serverFacade.joinGame(request);
            System.out.println("You have joined");
            //ChessBoard.drawChessBoard();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //c
        //call ui.ChessBoard
        //imp
    }
}
