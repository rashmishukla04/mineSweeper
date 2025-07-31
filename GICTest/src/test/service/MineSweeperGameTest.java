package test.service;

import main.model.Cell;
import main.service.MineSweeperGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MineSweeperGameTest {

    private MineSweeperGame mineSweeperGame;
    private int size = 4;
    private int mineCount = 3;

    @BeforeEach
    void setup() {
        mineSweeperGame = new MineSweeperGame(size, mineCount);
    }

    @Test
    void testGridInitialization() throws Exception {
        Cell[][] grid = getGrid(mineSweeperGame);
        assertEquals(size, grid.length);
        assertEquals(size, grid[0].length);
    }

    @Test
    void testMineCountAfterInitialization() throws Exception {
        Cell[][] grid = getGrid(mineSweeperGame);
        int actualMines = countMines(grid);
        assertEquals(mineCount, actualMines, "Mine count should match expected value");
    }

    @Test
    void testAdjacentMineCalculation() throws Exception {
        Cell[][] grid = getGrid(mineSweeperGame);
        clearGrid(grid);
        grid[1][1].placeMine();
        invokePrivateMethod(mineSweeperGame, "calculateAdjacents");
        int[][] neighbors = {
                {0, 0}, {0, 1}, {0, 2},
                {1, 0},         {1, 2},
                {2, 0}, {2, 1}, {2, 2}
        };

        for (int[] pos : neighbors) {
            assertEquals(1, grid[pos[0]][pos[1]].getAdjacentMines(),
                    "Adjacent mines count should be 1 for " + pos[0] + "," + pos[1]);
        }
        assertEquals(0, grid[1][1].getAdjacentMines());
    }

    @Test
    void testRevealEmptyCellShouldCascade() throws Exception {
        Cell[][] grid = getGrid(mineSweeperGame);
        clearGrid(grid);
        invokePrivateMethod(mineSweeperGame, "calculateAdjacents");
        invokeReveal(mineSweeperGame, 0, 0);
        int revealed = countRevealed(grid);
        assertEquals(size * size, revealed, "All cells should be revealed in empty board");
    }

    @Test
    void testRevealSingleCell() throws Exception {
        Cell[][] grid = getGrid(mineSweeperGame);
        clearGrid(grid);
        grid[2][2].setAdjacentMines(1);
        invokeReveal(mineSweeperGame, 2, 2);
        assertTrue(grid[2][2].isRevealed());
    }



    private Cell[][] getGrid(MineSweeperGame game) throws Exception {
        Field field = MineSweeperGame.class.getDeclaredField("grid");
        field.setAccessible(true);
        return (Cell[][]) field.get(game);
    }

    private void clearGrid(Cell[][] grid) {
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[0].length; j++)
                grid[i][j] = new Cell();
    }

    private int countMines(Cell[][] grid) {
        int count = 0;
        for (Cell[] row : grid)
            for (Cell cell : row)
                if (cell.hasMine()) count++;
        return count;
    }

    private int countRevealed(Cell[][] grid) {
        int count = 0;
        for (Cell[] row : grid)
            for (Cell cell : row)
                if (cell.isRevealed()) count++;
        return count;
    }

    private void invokeReveal(MineSweeperGame game, int row, int col) throws Exception {
        var method = MineSweeperGame.class.getDeclaredMethod("reveal", int.class, int.class);
        method.setAccessible(true);
        method.invoke(game, row, col);
    }

    private void invokePrivateMethod(MineSweeperGame game, String methodName) throws Exception {
        var method = MineSweeperGame.class.getDeclaredMethod(methodName);
        method.setAccessible(true);
        method.invoke(game);
    }
}

