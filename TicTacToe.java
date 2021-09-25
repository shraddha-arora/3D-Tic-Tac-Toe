import java.util.ArrayList;
import java.util.Scanner;

public class TicTacToe {
    static int[][][] reference = MagicCube.generate(3); // Cube to reference magic cube number positions
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Integer> human = new ArrayList<>(), computer = new ArrayList<>(); // List of positions occupied by human/computer
    static int humanLines = 0, computerLines = 0; // Count of collinear 42-sum combinations made

    public static void main(String[] args) {
        display(reference);
        // 27 moves for 27 blocks in the cube
        while (human.size() + computer.size() < 27) {
            humanMove();
            System.out.println("Human moves: " + human);
            computerMove();
            System.out.println("Computer moves: " + computer);
        }

        // In case 10 lines are not made, the side with greater lines wins
        if (computerLines > humanLines)
            System.out.println("Computer won with " + computerLines + " lines to " + humanLines);
        else if (humanLines > computerLines)
            System.out.println("Computer won with " + computerLines + " lines to " + humanLines);
        else System.out.println("It's a tie! Both sides have " + computerLines + " lines.");
    }

    // Method to check for co-linearity
    static boolean areCollinear(int a, int b, int c) {
        int r1 = -1, r2 = -1, r3 = -1, c1 = -1, c2 = -1, c3 = -1, h1 = -1, h2 = -1, h3 = -1;
        // Find the magic cube indices of all three points
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                for (int k = 0; k < 3; k++) {
                    if (reference[i][j][k] == a) {
                        r1 = i;
                        c1 = j;
                        h1 = k;
                    } else if (reference[i][j][k] == b) {
                        r2 = i;
                        c2 = j;
                        h2 = k;
                    } else if (reference[i][j][k] == c) {
                        r3 = i;
                        c3 = j;
                        h3 = k;
                    }
                }

        // Co-linearity checks
        boolean rowSame = r1 == r2 && r1 == r3, columnSame = c1 == c2 && c1 == c3, heightSame = h1 == h2 && h1 == h3;
        // To avoid division by zero due to the same value of two variables
        if (rowSame && columnSame || rowSame && heightSame || heightSame && columnSame) return true;
        // Math formula
        double xLambda = (double) (r2 - r1) / (r3 - r1);
        double yLambda = (double) (c2 - c1) / (c3 - c1);
        double zLambda = (double) (h2 - h1) / (h3 - h1);

        return yLambda == xLambda && zLambda == xLambda;
    }

    // Method controlling each computer move
    /*
        Move-priorities:
            - Winning move
            - Blocking move
            - Center block
            - Cube corner blocks
            - Edge center blocks
     */
    static void computerMove() {

        // Find and play winning move
        for (int i = 0; i < computer.size() - 1; i++) {
            for (int j = i + 1; j < computer.size(); j++) {
                int potentialMove = 42 - computer.get(i) - computer.get(j);
                if (!human.contains(potentialMove) && !computer.contains(potentialMove) && potentialMove > 0 && potentialMove < 28 && areCollinear(potentialMove, computer.get(i), computer.get(j))) {
                    computer.add(potentialMove);
                    System.out.println("Computer played O at magic cube position " + potentialMove);
                    displayGame(reference);
                    computerLines++;
                    if (computerLines == 10) {
                        System.out.println("Computer won!");
                        System.exit(0);
                    }
                }
            }
        }

        // Find opponent's winning move and block it
        for (int i = 0; i < human.size() - 1; i++) {
            for (int j = i + 1; j < human.size(); j++) {
                int potentialMove = 42 - human.get(i) - human.get(j);
                if (!computer.contains(potentialMove) && potentialMove > 0 && potentialMove < 28 && areCollinear(potentialMove, human.get(i), human.get(j))) {
                    computer.add(potentialMove);
                    System.out.println("Computer played O at magic cube position " + potentialMove);
                    displayGame(reference);
                    return;
                }
            }
        }

        // Play at the center if possible
        if (!human.contains(reference[1][1][1]) && !computer.contains(reference[1][1][1])) {
            computer.add(reference[1][1][1]);
            System.out.println("Computer played O at magic cube position " + reference[1][1][1]);
            displayGame(reference);
            return;
        }

        // Play at cube corners if possible
        for (int i = 0; i < 3; i += 2)
            for (int j = 0; j < 3; j += 2) {
                int corner1 = reference[i][j][0], corner2 = reference[i][j][2];
                if (!human.contains(corner1) && !computer.contains(corner1)) {
                    computer.add(corner1);
                    System.out.println("Computer played O at magic cube position " + corner1);
                    displayGame(reference);
                    return;
                } else if (!human.contains(corner2) && !computer.contains(corner2)) {
                    computer.add(corner2);
                    System.out.println("Computer played O at magic cube position " + corner2);
                    displayGame(reference);
                    return;
                }
            }

        // Play at edge centers if possible
        for (int i = 0; i < 3; i += 2)
            for (int j = 0; j < 3; j++) {
                int edgeCenter1 = reference[i][1][j], edgeCenter2 = reference[1][i][j];
                if (!human.contains(edgeCenter1) && !computer.contains(edgeCenter1)) {
                    computer.add(edgeCenter1);
                    System.out.println("Computer played O at magic cube position " + edgeCenter1);
                    displayGame(reference);
                    return;
                } else if (!human.contains(edgeCenter2) && !computer.contains(edgeCenter2)) {
                    computer.add(edgeCenter2);
                    System.out.println("Computer played O at magic cube position " + edgeCenter2);
                    displayGame(reference);
                    return;
                }
            }
    }

    static void humanMove() {
        int move;
        while (true) {
            System.out.println("Where to move? (-1 to quit, -2 for reference)");
            move = scanner.nextInt();
            if (move == -1) System.exit(0);
            if (move == -2) {
                display(reference);
                continue;
            }
            // Check for invalid input
            if (human.contains(move) || computer.contains(move) || move < -2 || move > 27)
                System.out.println("Enter a valid spot.");
            else break;
        }
        human.add(move);
        System.out.println("Human played X at magic cube position " + move);
        displayGame(reference);
        for (int i = 0; i < human.size() - 1; i++)
            for (int j = i + 1; j < human.size(); j++)
                if (42 - human.get(i) - human.get(j) - move == 0 && areCollinear(human.get(i), human.get(j), move))
                    humanLines++;
        if (humanLines == 10) {
            System.out.println("Human won!");
            System.exit(0);
        }
    }

    // Display current state of game
    static void displayGame(int[][][] cube) {
        for (int height = 2; height >= 0; height--) {
            for (int row = 0; row < 3; row++) {
                for (int column = 0; column < 3; column++) {
                    if (human.contains(cube[row][column][height]))
                        System.out.print("X ");
                    else if (computer.contains(cube[row][column][height]))
                        System.out.print("O ");
                    else System.out.print("- ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    // Method to display the reference cube
    static void display(int[][][] magicCube) {
        for (int height = 2; height >= 0; height--) {
            for (int row = 0; row < 3; row++) {
                for (int column = 0; column < 3; column++)
                    System.out.print(magicCube[row][column][height] + " ");
                System.out.println();
            }
            System.out.println();
        }
    }
}