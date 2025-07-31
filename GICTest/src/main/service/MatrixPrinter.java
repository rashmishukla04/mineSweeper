package main.service;

import main.model.Cell;

public class MatrixPrinter {
    private final int size;

    MatrixPrinter(int size) {
        this.size = size;
    }

    void render(Cell[][] grid) {
        System.out.print("  ");
        for (int j = 1; j <= size; j++) {
            System.out.print(j + " ");
        }
        System.out.println();

        for (int i = 0; i < size; i++) {
            System.out.print((char) ('A' + i) + " ");
            for (int j = 0; j < size; j++) {
                Cell cell = grid[i][j];
                if (!cell.isRevealed()) System.out.print("_ ");
                else if (cell.hasMine()) System.out.print("* ");
                else System.out.print(cell.getAdjacentMines() + " ");
            }
            System.out.println();
        }
    }
}