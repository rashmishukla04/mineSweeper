package main.model;

public class Cell {
    private boolean mine;
    private boolean revealed;
    private int adjacentMines;

    public boolean hasMine() { return mine; }
    public void placeMine() { this.mine = true; }

    public boolean isRevealed() { return revealed; }
    public void reveal() { this.revealed = true; }

    public int getAdjacentMines() { return adjacentMines; }
    public void setAdjacentMines(int count) { this.adjacentMines = count; }
}
