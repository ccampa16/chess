package ui;

import serverfacade.ServerFacade;

import java.util.Arrays;
import java.util.Scanner;

public class Client {
    private final ServerFacade serverFacade;
    private final String serverUrl;
    private boolean loggedIn = false;
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
        System.out.println("Welcome to Chess! Before you can start, please login or register.");
        System.out.println("Here are your command options:");
        System.out.println("1. Help - Display this help message");
        System.out.println("2. Quit - Exit the program");
        System.out.println("3. Login - Login with your username and password");
        System.out.println("4. Register - Register a new account");
    }
    private void displayHelpPostLogin() {
        System.out.println("Welcome to Chess!");
        System.out.println("Here are your command options:");
        System.out.println("1. Help - Display this help message");
        System.out.println("2. Logout - Logout from your current account");
        System.out.println("3. Create Game - Create a new chess game");
        System.out.println("4. List Games - List all existing chess games");
        System.out.println("5. Join Game - Join an existing game");
        System.out.println("6. Join Observer - Observe an existing chess game");
    }

    private void login(Scanner scanner){
        System.out.println("Enter username: ");
        String username = scanner.nextLine();
        System.out.println("Enter password: ");
        String password = scanner.nextLine();

        //imp
        loggedIn = true;
    }
    private void register(Scanner scanner){
        System.out.println("Enter username: ");
        String username = scanner.nextLine();
        System.out.println("Enter password: ");
        String password = scanner.nextLine();

        //imp
        loggedIn = true;
    }
    private void logout(){
        //imp
        loggedIn = false;
    }
    private void createGame(Scanner scanner){
        System.out.println("Enter the name of your new game: ");
        String gameName = scanner.nextLine();

        //imp
    }
    private void listGames(){}
    private void joinGame(Scanner scanner){
        //listGames();
        System.out.println("Enter the number of the game you want to join: ");
        int gameNum = scanner.nextInt();
        scanner.nextLine();

        //imp
    }
    private void joinObserver(Scanner scanner){
        //listGames();
        System.out.println("Enter the number of the game you want to observe: ");
        int gameNum = scanner.nextInt();
        scanner.nextLine();

        //imp
    }
}
