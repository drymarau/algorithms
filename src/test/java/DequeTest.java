import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

public class DequeTest {

    private Deque<Integer> deque;

    @BeforeEach
    void setUp() {
        deque = new Deque<>();
    }

    @Test
    void isEmpty_returns_correct_value() {
        assertTrue(deque.isEmpty());
        for (int i = 0; i < 3; i++) {
            deque.addFirst(i);
            assertFalse(deque.isEmpty());
        }
    }

    @Test
    void size_returns_correct_value() {
        for (int i = 0; i < 3; i++) {
            deque.addFirst(i);
            assertEquals(i + 1, deque.size());
        }
        for (int i = 2; i >= 0; i--) {
            deque.removeFirst();
            assertEquals(i, deque.size());
        }
    }

    @Test
    void addFirst_throws_if_item_is_null() {
        assertThrowsExactly(IllegalArgumentException.class, () -> deque.addFirst(null));
    }

    @Test
    void addFirst_adds_item_to_the_beginning_of_the_queue() {
        for (int i = 0; i < 3; i++) {
            deque.addFirst(i);
        }
        for (int i = 2; i >= 0; i--) {
            assertEquals(i, deque.removeFirst());
        }
    }

    @Test
    void addLast_throws_if_item_null() {
        assertThrowsExactly(IllegalArgumentException.class, () -> deque.addLast(null));
    }

    @Test
    void addLast_adds_item_to_the_end_of_the_queue() {
        for (int i = 0; i < 3; i++) {
            deque.addLast(i);
        }
        for (int i = 2; i >= 0; i--) {
            assertEquals(i, deque.removeLast());
        }
    }

    @Test
    void removeFirst_throws_if_deque_is_empty() {
        assertThrowsExactly(NoSuchElementException.class, deque::removeFirst);
    }

    @Test
    void removeLast_throws_if_deque_is_empty() {
        assertThrowsExactly(NoSuchElementException.class, deque::removeLast);
    }

    @Test
    void iterator_returns_correct_value() {
        for (int i = 0; i < 3; i++) {
            deque.addFirst(i);
        }
        var array = new int[3];
        var i = 0;
        for (var item : deque) {
            array[i++] = item;
        }
        assertArrayEquals(new int[]{2, 1, 0}, array);
    }
}
