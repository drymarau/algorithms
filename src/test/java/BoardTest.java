import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BoardTest {

    @Test
    void equals_returns_false_if_object_is_null() {
        var board = new Board(new int[][]{new int[]{1, 2}, new int[]{0, 3}});
        assertNotEquals(null, board);
    }

    @Test
    void equals_returns_false() {
        var board = new Board(new int[][]{new int[]{1, 2}, new int[]{0, 3}});
        var anotherBoard = new Board(new int[][]{new int[]{2, 1}, new int[]{0, 3}});
        assertNotEquals(board, anotherBoard);
    }

    @Test
    void equals_returns_true() {
        var tiles = new int[][]{new int[]{1, 2}, new int[]{0, 3}};
        var board = new Board(tiles);
        var anotherBoard = new Board(tiles);
        assertEquals(board, anotherBoard);
    }

    @Test
    void isGoal_returns_correct_value() {
        var board = new Board(new int[][]{new int[]{8, 1, 3}, new int[]{4, 0, 2}, new int[]{7, 6, 5}});
        assertFalse(board.isGoal());
        board = new Board(new int[][]{new int[]{1, 2, 3}, new int[]{4, 5, 6}, new int[]{7, 8, 0}});
        assertTrue(board.isGoal());
    }

    @Test
    void hamming_returns_correct_value() {
        var board = new Board(new int[][]{new int[]{8, 1, 3}, new int[]{4, 0, 2}, new int[]{7, 6, 5}});
        assertEquals(5, board.hamming());
        board = new Board(new int[][]{new int[]{1, 2, 3}, new int[]{4, 5, 6}, new int[]{7, 8, 0}});
        assertEquals(0, board.hamming());
    }

    @Test
    void manhattan_returns_correct_value() {
        var board = new Board(new int[][]{new int[]{8, 1, 3}, new int[]{4, 0, 2}, new int[]{7, 6, 5}});
        assertEquals(10, board.manhattan());
        board = new Board(new int[][]{new int[]{1, 2, 3}, new int[]{4, 5, 6}, new int[]{7, 8, 0}});
        assertEquals(0, board.manhattan());
    }
}
