import java.util.Scanner;
import java.util.Random;

public class TicTacToe {


    // Returns "PLAYER", "COMPUTER", "DRAW", or "NONE"
    public static String winCheck(int size, char[][] board, String mark, char computerMark) {
        // Check for a win in rows and columns
        for(int i = 0; i < size; i++) {
            if(board[i][0] == mark.charAt(0) && board[i][1] == mark.charAt(0) && board[i][2] == mark.charAt(0)) {
                System.out.println("Player wins!");
                return "PLAYER";
            }
            if(board[0][i] == mark.charAt(0) && board[1][i] == mark.charAt(0) && board[2][i] == mark.charAt(0)) {
                System.out.println("Player wins!");
                return "PLAYER";
            }
        }
        for(int i = 0 ; i < size; i++){
            if(board[i][0] == computerMark && board[i][1] == computerMark && board[i][2] == computerMark) {
                System.out.println("Computer wins!");
                return "COMPUTER";
            }
            if(board[0][i] == computerMark && board[1][i] == computerMark && board[2][i] == computerMark) {
                System.out.println("Computer wins!");
                return "COMPUTER";
            }
        }
        // Diagonal checks
        if((board[0][0] == mark.charAt(0) && board[1][1] == mark.charAt(0) && board[2][2] == mark.charAt(0)) ||
           (board[0][2] == mark.charAt(0) && board[1][1] == mark.charAt(0) && board[2][0] == mark.charAt(0))) {
            System.out.println("Player wins!");
            return "PLAYER";
        }
        if((board[0][0] == computerMark && board[1][1] == computerMark && board[2][2] == computerMark) ||
           (board[0][2] == computerMark && board[1][1] == computerMark && board[2][0] == computerMark)) {
            System.out.println("Computer wins!");
            return "COMPUTER";
        }
        // Check for a draw
        boolean isDraw = true;
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if(board[i][j] == '_') {
                    isDraw = false;
                    break;
                }
            }
            if(!isDraw) break;
        }
        if(isDraw) {
            System.out.println("It's a draw!");
            return "DRAW";
        }
        return "NONE";
    }

    public void initialize() {
        // Initialize the Tic Tac Toe board
        System.out.println("Initializing Tic Tac Toe board...");
        System.out.println("Board is a 3x3 grid, each cell can be marked with 'X' or 'O'.");
        System.out.println("............................................................");
        int size = 3;
        int winsNeeded = 3; // Number of marks needed to win
        int row,col;
        int playerWins = 0, computerWins = 0; // Initialize player and computer wins
        boolean isGameOver = false; // Flag to check if the game is over
        boolean isPlayerTurn = true; // Player starts first
        Scanner scan = new Scanner(System.in);
        Random rand = new Random();
        System.out.println("Choose your mark (X or O): ");
        String mark = "";
        while (true) {
            String input = scan.nextLine().trim().toUpperCase();
            if (input.length() > 0 && (input.charAt(0) == 'X' || input.charAt(0) == 'O')) {
                mark = input.charAt(0) + "";
                break;
            } else {
                System.out.println("Invalid mark. Please choose X or O:");
            }
        }
        char computerMark = (mark.equals("X")) ? 'O' : 'X';
        System.out.println("Your mark is: " + mark);
        System.out.println("Computer's mark is: " + computerMark);

        do{
            char[][] board = {
            {'_', '_', '_'},
            {'_', '_', '_'},
            {'_', '_', '_'} };
            String Round = ((playerWins + computerWins + 1) == 5) ? "Final Round" : "Round " + (playerWins + computerWins + 1);
            System.out.println(Round);
            System.out.println("Current Score -- Player: " + playerWins + ", Computer: " + computerWins);
            int choice = rand.nextInt(2);
            boolean turn = (choice == 0)? true : false;
            isPlayerTurn = turn;
            isGameOver = false; // Reset for each round
            do{    
                if(isPlayerTurn){
                    System.out.println("Player's Turn. Enter row and column (1-3): ");
                    // Input validation for integers
                    while (true) {
                        if (scan.hasNextInt()) {
                            row = scan.nextInt() - 1;
                        } else {
                            System.out.println("Invalid input. Please enter a number for row (1-3): ");
                            scan.next();
                            continue;
                        }
                        if (scan.hasNextInt()) {
                            col = scan.nextInt() - 1;
                        } else {
                            System.out.println("Invalid input. Please enter a number for column (1-3): ");
                            scan.next();
                            continue;
                        }
                        if(row<0 || row>=size || col<0 || col>=size || board[row][col] != '_') {
                            System.out.println("Invalid move. Try again.");
                            continue;
                        }
                        break;
                    }
                    board[row][col] = mark.charAt(0);
                    isPlayerTurn = false; // Switch to computer turn
                }
                else{
                    System.out.println("Computer's Turn.");
                    row = rand.nextInt(size);
                    col = rand.nextInt(size);
                    while(board[row][col] != '_') {
                        row = rand.nextInt(size);
                        col = rand.nextInt(size);
                    }
                    board[row][col] = computerMark;
                    isPlayerTurn = true; // Switch back to player turn
                }
                // Print the board
                for(int i = 0; i < size; i++) { 
                    for(int j = 0; j < size; j++) {
                        System.out.print(board[i][j] + " ");
                    }
                    System.out.println();
                }
                // Check for a win
                String result = winCheck(size, board, mark, computerMark);
                if(result.equals("PLAYER")) {
                    playerWins++;
                    isGameOver = true;
                } else if(result.equals("COMPUTER")) {
                    computerWins++;
                    isGameOver = true;
                } else if(result.equals("DRAW")) {
                    isGameOver = true;
                }
                if(playerWins >= winsNeeded || computerWins >= winsNeeded) {
                    System.out.println("Game Over! Final Score -- Player: " + playerWins + ", Computer: " + computerWins);
                    break;
                }
            }while(!isGameOver);
        }while(playerWins<winsNeeded && computerWins<winsNeeded);

        // Announce overall winner
        if (playerWins > computerWins) {
            System.out.println("Congratulations! You are the overall winner!");
        } else if (computerWins > playerWins) {
            System.out.println("Computer is the overall winner. Better luck next time!");
        } else {
            System.out.println("It's a tie overall!");
        }
        scan.close();
    }
    public static void main(String[] args) {
        System.out.println("Welcome to Tic Tac Toe!");
        TicTacToe game = new TicTacToe();
        game.initialize();
    }
}
