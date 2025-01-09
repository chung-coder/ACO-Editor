package fr.istic.aco.editor.test;
import fr.istic.aco.editor.commands.*;
import fr.istic.aco.editor.core.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InvokerTest {
    private Engine engine;
    private Invoker invoker;
    private Selection selection;
    private Recorder recorder;
    private UndoManager undoManager;

    @BeforeEach
    public void setUp() {
        engine = new EngineImpl();
        invoker = new Invoker(engine);
        recorder = new Recorder();
        undoManager = new UndoManager(engine);
    }

    @Test
    @DisplayName("Invoker initial state")
    void testConstructor() {
        assertTrue(invoker.getCommands().isEmpty());
        assertEquals("", invoker.getText());
        assertEquals(0, invoker.getEndIndex());
        assertEquals(0, invoker.getBeginIndex());
    }

    @Test
    @DisplayName("Testing getters and setters")
    void testGettersSetters() {
        invoker.setText("test");
        assertEquals("test", invoker.getText());
        invoker.setEndIndex(5);
        assertEquals(5, invoker.getEndIndex());
        invoker.setBeginIndex(3);
        assertEquals(3, invoker.getBeginIndex());
    }


    @Test
    @DisplayName("Testing add command")
    void testAddCommands() {
        invoker.addCommand("i", new InsertTextCommand(engine, invoker, recorder, undoManager));
        assertEquals(1, invoker.getCommands().size());
        assertTrue(invoker.getCommands().containsKey("i"));
        assertEquals(invoker.getCommands().get("i").getClass(), InsertTextCommand.class);
    }

    @Test
    @DisplayName("Testing play command and class concrete command selection at the same time")
    void testplayCommand() {
        engine.insert("hello! this is a test.");
        invoker.setBeginIndex(2);
        invoker.setEndIndex(5);
        invoker.addCommand("m", new MoveSelection(engine, invoker, recorder, undoManager));
        invoker.playCommand("m");
        assertEquals(engine.getSelection().getBeginIndex(), 2);
        Assertions.assertEquals(engine.getSelection().getEndIndex(), 5);

    }

    @Test
    @DisplayName("Testing play command with a unknown command.")
    void testplayCommandThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {invoker.playCommand("i");});
    }

    @Test
    @DisplayName("Testing concrete command insert")
    void testInsertCommand() {
        invoker.setText("test");
        invoker.addCommand("i", new InsertTextCommand(engine, invoker, recorder, undoManager));
        invoker.playCommand("i");
        Assertions.assertEquals("test",engine.getBufferContents());

    }

    @Test
    @DisplayName("Testing concrete command copy")
    void testCopyCommand() {
        engine.insert("hello! this is a test.");
        engine.getSelection().setBeginIndex(0);
        engine.getSelection().setEndIndex(7);
        invoker.addCommand("c", new CopyTextCommand(engine,recorder, undoManager));
        invoker.playCommand("c");
        assertEquals("hello! ", engine.getClipboardContents());
        assertEquals("hello! this is a test.", engine.getBufferContents());
    }

    @Test
    @DisplayName("Testing concrete command Paste")
    void testPasteCommand() {
        engine.insert("hello! this is a test.");
        engine.getSelection().setBeginIndex(0);
        engine.getSelection().setEndIndex(7);
        engine.copySelectedText();
        engine.getSelection().setBeginIndex(7);
        invoker.addCommand("v", new PasteTextCommand(engine, recorder, undoManager));
        invoker.playCommand("v");
        assertEquals("hello! hello! this is a test.", engine.getBufferContents());
    }

    @Test
    @DisplayName("Testing concrete command Cut")
    void testCutCommand() {
        engine.insert("hello! this is a test.");
        engine.getSelection().setBeginIndex(0);
        engine.getSelection().setEndIndex(7);
        invoker.addCommand("x", new CutSelectedCommand(engine, recorder, undoManager));
        invoker.playCommand("x");
        assertEquals("hello! ", engine.getClipboardContents());
        assertEquals("this is a test.", engine.getBufferContents());
    }

    @Test
    @DisplayName("Testing concrete command Delete")
    void testDeleteCommand() {
        engine.insert("hello! this is a test.");engine.getSelection().setBeginIndex(0);
        engine.getSelection().setEndIndex(7);
        invoker.addCommand("d", new DeleteTextCommand(engine, recorder, undoManager));
        invoker.playCommand("d");
        assertEquals("this is a test.", engine.getBufferContents());
    }

    @Test
    @DisplayName("Testing concrete command MoveSelection")
    void MoveSelectionCommand() {
        engine.insert("hello! this is a test.");
        invoker.setBeginIndex(2);
        invoker.setEndIndex(5);
        engine.insert("hello! this is a test.");
        invoker.addCommand("m", new MoveSelection(engine, invoker, recorder, undoManager));
        invoker.playCommand("m");
        assertEquals(2, engine.getSelection().getBeginIndex());
        assertEquals(5, engine.getSelection().getEndIndex());
    }

}
