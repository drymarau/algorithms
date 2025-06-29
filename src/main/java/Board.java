import java.util.Arrays;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdRandom;

public class Board {

    private final int[][] tiles;
    private short emptyRow;
    private short emptyColumn;
    private short twinRow1;
    private short twinRow2;
    private short twinColumn1;
    private short twinColumn2;
    private int hamming;
    private int manhattan;

    public Board(int[][] tiles) {
        this.tiles = new int[tiles.length][tiles.length];
        for (short row = 0; row < tiles.length; row++) {
            for (short column = 0; column < tiles[row].length; column++) {
                var tile = tiles[row][column];
                this.tiles[row][column] = tile;
                if (tile == 0) {
                    emptyRow = row;
                    emptyColumn = column;
                    continue;
                }
                var expectedRow = row(tile);
                var expectedColumn = column(tile);
                if (row != expectedRow || column != expectedColumn) {
                    hamming++;
                    manhattan += Math.abs(row - expectedRow) + Math.abs(column - expectedColumn);
                }
            }
        }
        do {
            twinRow1 = (short) StdRandom.uniformInt(tiles.length);
            twinColumn1 = (short) StdRandom.uniformInt(tiles.length);
        } while (tiles[twinRow1][twinColumn1] == 0);
        do {
            twinRow2 = (short) StdRandom.uniformInt(tiles.length);
            twinColumn2 = (short) StdRandom.uniformInt(tiles.length);
        } while (tiles[twinRow2][twinColumn2] == 0 || (twinRow1 == twinRow2 && twinColumn1 == twinColumn2));
    }

    public String toString() {
        var builder = new StringBuilder();
        builder.append(tiles.length);
        builder.append('\n');
        for (int[] row : tiles) {
            builder.append(' ');
            for (int column = 0; column < row.length; column++) {
                builder.append(String.format("%2d", row[column]));
                if (column != row.length - 1) {
                    builder.append(' ');
                } else {
                    builder.append('\n');
                }
            }
        }
        return builder.toString();
    }

    public int dimension() {
        return tiles.length;
    }

    public int hamming() {
        return hamming;
    }

    public int manhattan() {
        return manhattan;
    }

    public boolean isGoal() {
        return hamming == 0;
    }

    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null) return false;
        if (getClass() != y.getClass()) return false;
        var other = (Board) y;
        return Arrays.deepEquals(this.tiles, other.tiles);
    }

    public Iterable<Board> neighbors() {
        var neighbors = new Stack<Board>();
        var directions = new short[][]{new short[]{-1, 0}, new short[]{0, 1}, new short[]{1, 0}, new short[]{0, -1}};
        for (var direction : directions) {
            var dRow = direction[0];
            var dColumn = direction[1];
            var row = emptyRow + dRow;
            if (row < 0 || row >= dimension()) continue;
            var column = emptyColumn + dColumn;
            if (column < 0 || column >= dimension()) continue;
            exchange(emptyRow, emptyColumn, row, column);
            neighbors.push(new Board(tiles));
            exchange(emptyRow, emptyColumn, row, column);
        }
        return neighbors;
    }

    public Board twin() {
        exchange(twinRow1, twinColumn1, twinRow2, twinColumn2);
        var board = new Board(tiles);
        exchange(twinRow1, twinColumn1, twinRow2, twinColumn2);
        return board;
    }

    private int row(int tile) {
        if (tile <= 0) throw new IllegalArgumentException();
        return (tile - 1) / dimension();
    }

    private int column(int tile) {
        if (tile <= 0) throw new IllegalArgumentException();
        return (tile - 1) % dimension();
    }

    private void exchange(int row1, int column1, int row2, int column2) {
        var tmp = tiles[row1][column1];
        tiles[row1][column1] = tiles[row2][column2];
        tiles[row2][column2] = tmp;
    }

    public static void main(String[] args) {

    }
}
