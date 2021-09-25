import java.util.Random;
import java.util.Scanner;

public class MagicCube {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Enter size of cube: ");
        generate(scanner.nextInt());
    }

    static int[][][] generate(int n) {
        int[][][] magicCube = new int[n][n][n];
        // Initialization of indices for '1'
        int row = 0, column = n / 2, height = n / 2;
        magicCube[row][column][height] = 1;
        for (int index = 2; index <= n * n * n; index++) {
            row--;
            column--;
            if (row == -1) row = n - 1; // In case the row goes out of bounds
            if (column == -1) column = n - 1; // In case the column goes out of bounds
            // In case the position is occupied
            if (magicCube[row][column][height] != 0) {
                row = row == 0 ? n - 1 : row - 1;
                height = height == 0 ? n - 1 : height - 1;
                // If even that position is occupied
                if (magicCube[row][column][height] != 0) {
                    column = column == n - 1 ? 0 : column + 1;
                    height = height == n - 1 ? 0 : height + 1;
                }
            }
            magicCube[row][column][height] = index;
        }
        // Randomly create a valid magic cube
        int random = new Random().nextInt(8);
        switch (random) {
            case 0: // Original
                return magicCube;
            case 1: {    // Height
                swapHeight(magicCube);
                return magicCube;
            }
            case 2: {    // Row
                swapRow(magicCube);
                return magicCube;
            }
            case 3: {    // Column
                swapColumn(magicCube);
                return magicCube;
            }
            case 4: {    // Row, Column
                swapRow(magicCube);
                swapColumn(magicCube);
                return magicCube;
            }
            case 5: {    // Height, Column
                swapHeight(magicCube);
                swapColumn(magicCube);
                return magicCube;
            }
            case 6: {    // Row, Height
                swapRow(magicCube);
                swapHeight(magicCube);
                return magicCube;
            }
            case 7: {   // Row, Column, Height
                swapRow(magicCube);
                swapColumn(magicCube);
                swapHeight(magicCube);
                return magicCube;
            }
        }

//        display(magicCube, n);
        return magicCube;
    }

    // Swap top and bottom layers
    static void swapHeight(int[][][] cube) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int temp = cube[i][j][2];
                cube[i][j][2] = cube[i][j][0];
                cube[i][j][0] = temp;
            }
        }
    }

    // Swap front and back faces
    static void swapRow(int[][][] cube) {
        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 3; k++) {
                int temp = cube[0][j][k];
                cube[0][j][k] = cube[2][j][k];
                cube[2][j][k] = temp;
            }
        }
    }

    // Swap side faces
    static void swapColumn(int[][][] cube) {
        for (int i = 0; i < 3; i++) {
            for (int k = 0; k < 3; k++) {
                int temp = cube[i][0][k];
                cube[i][0][k] = cube[i][2][k];
                cube[i][2][k] = temp;
            }
        }
    }

    // Display a 3D cube layer wise
    static void display(int[][][] magicCube, int n) {
        for (int height = n - 1; height >= 0; height--) {
            for (int row = 0; row < n; row++) {
                for (int column = 0; column < n; column++)
                    System.out.print(magicCube[row][column][height] + " ");
                System.out.println();
            }
            System.out.println();
        }
    }
}