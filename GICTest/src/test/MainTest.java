package test;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static main.Main.checkMineCountCriteria;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {
    @Test
    public void testCheckMineCountWithValidInput() {
        String input = "3";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        int gridSize = 4;
        int mineCount = checkMineCountCriteria(gridSize, scanner);

        assertEquals(3, mineCount);
    }

}
