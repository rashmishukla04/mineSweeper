package main.service;

import main.model.Cell;

import java.util.Random;
import java.util.Scanner;

public class MineSweeperGame {
    private final int size;
    private final int mineCount;
    private final Cell[][] grid;
    private final MatrixPrinter matrixPrinter;
    private int revealedCount;
    private boolean gameOver;

    public MineSweeperGame(int size, int mineCount) {
        this.size = size;
        this.mineCount = mineCount;
        this.grid = new Cell[size][size];
        this.matrixPrinter = new MatrixPrinter(size);
        initialize();
    }

    public void startGame(Scanner scanner) {
        while (!gameOver) {
            matrixPrinter.render(grid);
            System.out.print("Select a square (e.g. A1): ");
            String input = scanner.next().toUpperCase();

            if (!InputValidator.isValid(input, size)) {
                System.out.println("Invalid input.Try again.");
                continue;
            }

            int row = input.charAt(0) - 'A';
            int col = Integer.parseInt(input.substring(1)) - 1;

            Cell selected = grid[row][col];
            if (selected.isRevealed()) {
                System.out.println("Already revealed.Choose another.");
                continue;
            }

            if (selected.hasMine()) {
                revealAllMines();
                matrixPrinter.render(grid);
                System.out.println("Oh no, you detonated a mine! Game over");
                gameOver = true;
            } else {
                reveal(row, col);
                if (revealedCount == size * size - mineCount) {
                    matrixPrinter.render(grid);
                    System.out.println("Congratulations, you have won the game!");
                    gameOver = true;
                }
            }
        }
    }

    private void initialize() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = new Cell();
            }
        }

        placeMines();
        calculateAdjacents();
    }

    private void placeMines() {
        Random rand = new Random();
        int placed = 0;
        while (placed < mineCount) {
            int r = rand.nextInt(size);
            int c = rand.nextInt(size);
            if (!grid[r][c].hasMine()) {
                grid[r][c].placeMine();
                placed++;
            }
        }
      }

    private void calculateAdjacents() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!grid[i][j].hasMine()) {
                    int count = 0;
                    for (int x = -1; x <= 1; x++) {
                        for (int y = -1; y <= 1; y++) {
                            int ni = i + x;
                            int nj = j + y;
                            if (isValid(ni, nj) && grid[ni][nj].hasMine()) count++;
                        }
                    }
                    grid[i][j].setAdjacentMines(count);
                }
            }
        }
    }

    private void reveal(int row, int col) {
        if (!isValid(row, col) || grid[row][col].isRevealed()) return;

        grid[row][col].reveal();
        revealedCount++;

        if (grid[row][col].getAdjacentMines() == 0) {
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    if (dx != 0 || dy != 0) reveal(row + dx, col + dy);
                }
            }
        }
    }

    private void revealAllMines() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j].hasMine()) grid[i][j].reveal();
            }
        }
    }

    private boolean isValid(int x, int y) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }

}
