package main;

import main.service.MineSweeperGame;

import java.util.Scanner;

public class Main{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Minesweeper!");
        System.out.println("Enter the size of the grid (e.g. 4 for a 4x4 grid): ");
        int gridSize = sc.nextInt();
        int mineCounts = checkMineCountCriteria(gridSize , sc);
        MineSweeperGame mineSweeperGame = new MineSweeperGame(gridSize,mineCounts);
        mineSweeperGame.startGame(sc);

    }

    public static int checkMineCountCriteria(int gridSize, Scanner sc) {
        int maxMineAllowed = gridSize * gridSize * 35/100;
        int mineCounts;
        while(true) {
            System.out.println("Enter the number of mines to place on the grid (maximum is 35% of the total squares): ");
            mineCounts = sc.nextInt();
            if (mineCounts <= maxMineAllowed) {
                break;
            }
            System.out.println("Invalid input. Please enter a number less than or equal to " + maxMineAllowed );
        }
        return mineCounts;
    }

}