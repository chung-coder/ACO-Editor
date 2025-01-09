package fr.istic.aco.editor.test;

import fr.istic.aco.editor.core.Engine;
import fr.istic.aco.editor.core.EngineImpl;
import fr.istic.aco.editor.memento.EngineMemento;
import fr.istic.aco.editor.core.Selection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EngineTest {

    private Engine engine;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        engine = new EngineImpl();
    }

    @Test
    @DisplayName("Engine initial state")
    void testConstructor() {
        assertEquals("", engine.getBufferContents());
        assertEquals("", engine.getClipboardContents());
    }

    @Test
    @DisplayName("Buffer must be empty after initialisation")
    void getSelection() {
        Selection selection = engine.getSelection();
        Assertions.assertEquals(selection.getBufferBeginIndex(),selection.getBeginIndex());
        Assertions.assertEquals("",engine.getBufferContents());
    }

    @Test
    void getBufferContents() {
        Assertions.assertEquals("",engine.getBufferContents());
    }

    @Test
    void getClipboardContents() {
        Assertions.assertEquals("",engine.getClipboardContents());
    }

    @Test
    void insertSelectedText() {
        engine.insert("hello!");
        Assertions.assertEquals("hello!",engine.getBufferContents());
    }

    @Test
    void cutSelectedText() {
        engine.insert("hello! this is a test.");
        engine.getSelection().setBeginIndex(0);
        engine.getSelection().setEndIndex(7);
        engine.cutSelectedText();
        assertEquals("hello! ", engine.getClipboardContents());
        assertEquals("this is a test.", engine.getBufferContents());
    }

    @Test
    void copySelectedText() {
        engine.insert("hello! this is a test.");
        engine.getSelection().setBeginIndex(0);
        engine.getSelection().setEndIndex(7);
        engine.copySelectedText();
        assertEquals("hello! ", engine.getClipboardContents());
        assertEquals("hello! this is a test.", engine.getBufferContents());
    }

    @Test
    void pasteClipboard() {
        engine.insert("hello! this is a test.");
        engine.getSelection().setBeginIndex(0);
        engine.getSelection().setEndIndex(7);
        engine.copySelectedText();
        engine.getSelection().setBeginIndex(7);
        engine.pasteClipboard();
        assertEquals("hello! hello! this is a test.", engine.getBufferContents());
    }

    @Test
    void deleteSelectedText() {
        engine.insert("hello! this is a test.");
        engine.getSelection().setBeginIndex(0);
        engine.getSelection().setEndIndex(7);
        engine.delete();
        Assertions.assertEquals("this is a test.",engine.getBufferContents());
    }

    @Test
    @DisplayName("Test Get Engine Memento")
    void testGetMemento() {
        engine.insert("this is a test.");
        engine.getSelection().setBeginIndex(0);
        engine.getSelection().setEndIndex(4);
        engine.copySelectedText();

        EngineMemento memento = (EngineMemento) engine.getMemento();

        assertEquals("this is a test.", memento.getBufferContent());
        assertEquals(0, memento.getBeginIndex());
        assertEquals(4, memento.getEndIndex());
        assertEquals("this", memento.getClipboard());
    }

    @Test
    @DisplayName("Test Set Engine Memento")
    void testSetMemento() {
        engine.insert("this is a test.");
        engine.getSelection().setBeginIndex(0);
        engine.getSelection().setEndIndex(4);
        engine.copySelectedText();

        EngineMemento memento = (EngineMemento) engine.getMemento();

        engine.delete();
        engine.insert("Hello,");
        engine.getSelection().setBeginIndex(0);
        engine.getSelection().setEndIndex(5);

        engine.setMemento(memento);

        assertEquals("this is a test.", engine.getBufferContents());
        assertEquals(0, engine.getSelection().getBeginIndex());
        assertEquals(4, engine.getSelection().getEndIndex());
        assertEquals("this", engine.getClipboardContents());
    }

}
