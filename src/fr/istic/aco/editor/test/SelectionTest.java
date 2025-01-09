package fr.istic.aco.editor.test;

import fr.istic.aco.editor.core.Selection;
import fr.istic.aco.editor.core.SelectionImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SelectionTest {

    private Selection selection;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        selection = new SelectionImpl(new StringBuilder("test"));
    }

    @Test
    void testConstructor() {
        // we could have another parameters, ex：beginIndex, endIndex
        Selection s = new SelectionImpl(new StringBuilder());
        // assertEquals is used to check if two objects are equal

        assertEquals(s.getBeginIndex(), 0);
        assertEquals(s.getEndIndex(), 0);
        assertEquals(s.getBufferBeginIndex(), 0);
        assertEquals(s.getBufferEndIndex(), 0);
    }

    @Test
    void testConstructor2() {
        // we could have another parameters, ex：beginIndex, endIndex
        Selection s = new SelectionImpl(new StringBuilder("abcde"), 0, 3 );
        assertEquals(s.getBeginIndex(), 0);
        assertEquals(s.getEndIndex(), 3);
        assertEquals(s.getBufferBeginIndex(), 0);
        assertEquals(s.getBufferEndIndex(), 5);
    }

    @Test
    void testEmptyStringBuilder() {
        Selection s = new SelectionImpl(new StringBuilder());
        assertEquals(s.getBeginIndex(), 0);
        assertEquals(s.getEndIndex(), 0);
    }

    @Test
    void testSetEndIndex() {
        Selection s = new SelectionImpl(new StringBuilder("12345"));
        int newEndIndex = 3;
        s.setEndIndex(newEndIndex);
        Assertions.assertEquals(s.getEndIndex(), newEndIndex);
    }

    @Test
    void testSetBeginIndex() {
        Selection s = new SelectionImpl(new StringBuilder("12345"), 2, 3);
        assertEquals(s.getBeginIndex(), 2);
        Assertions.assertEquals(s.getEndIndex(), 3);
    }

    @Test
    void testSetBeginEndIndexSameValue() {
        Selection s = new SelectionImpl(new StringBuilder("123456"), 2, 2);
        assertEquals(s.getBeginIndex(), 2);
        assertEquals(s.getEndIndex(), 2);
    }

    @Test
    void testSetBeginIndexException() {
        Selection s = new SelectionImpl(new StringBuilder("12345"));
        int newEndIndex = 3;
        s.setEndIndex(newEndIndex);
        assertThrows(IndexOutOfBoundsException.class, () -> s.setBeginIndex(s.getBufferBeginIndex() - 1));
    }

    @Test
    void testSetBeginIndexException2() {
        Selection s = new SelectionImpl(new StringBuilder("12345"));
        int newEndIndex = 3;
        s.setEndIndex(newEndIndex);
        assertThrows(IndexOutOfBoundsException.class, () -> s.setBeginIndex(4));
    }

    @Test
    void testSetEndIndexException() {
        Selection s = new SelectionImpl(new StringBuilder("12345"));
        int newEndIndex = 5;
        assertThrows(IndexOutOfBoundsException.class, () -> s.setEndIndex(newEndIndex + 1));
    }

    @Test
    void testSetEndIndexException2() {
        Selection s = new SelectionImpl(new StringBuilder("12345"));
        int newBeginIndex = 3;
        s.setEndIndex(newBeginIndex);
        s.setBeginIndex(newBeginIndex);
        assertThrows(IndexOutOfBoundsException.class, () -> s.setEndIndex(1));
    }

    @Test
    void testGetters() {
        Selection s = new SelectionImpl(new StringBuilder("123456"), 1, 3);
        assertEquals(s.getBeginIndex(), 1);
        assertEquals(s.getEndIndex(), 3);
        assertEquals(s.getBufferBeginIndex(), 0);
        assertEquals(s.getBufferEndIndex(), 6);
    }

    @Test
    void testBufferModification() {
        StringBuilder buffer = new StringBuilder("12345");
        Selection s = new SelectionImpl(buffer, 1, 4);
        buffer.append("678"); // Modify the buffer
        assertEquals(s.getBufferEndIndex(), 8); // Assuming buffer end index updates dynamically
    }

}
