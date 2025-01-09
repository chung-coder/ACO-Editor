package fr.istic.aco.editor.test;

import fr.istic.aco.editor.commands.InsertTextCommand;
import fr.istic.aco.editor.core.*;
import fr.istic.aco.editor.memento.InsertMemento;
import fr.istic.aco.editor.memento.Memento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InsertTest {
    private Engine engine;
    private Invoker invoker;
    private Recorder recorder;
    private UndoManager undoManager;
    private InsertTextCommand insertTextCommand;

    @BeforeEach
    public void setUp() {
        engine = new EngineImpl();
        invoker = new Invoker(engine);
        recorder = new Recorder();
        undoManager = new UndoManager(engine);
        insertTextCommand = new InsertTextCommand(engine, invoker, recorder, undoManager);
    }

    @Test
    @DisplayName("Test Insert Memento")
    public void testMementoSetterAndGetter() {
        InsertMemento memento = new InsertMemento();

        memento.setText("This is a test.");
        assertEquals("This is a test.", memento.getText());
    }

    @Test
    @DisplayName("Test Get Memento")
    public void testGetMemento() {
        invoker.setText("This is a test.");
        Memento memento = insertTextCommand.getMemento();

        assertEquals("This is a test.", ((InsertMemento) memento).getText());
    }

    @Test
    @DisplayName("Test Set Memento")
    public void testSetMemento() {
        InsertMemento memento = new InsertMemento();
        memento.setText("This is a test.");
        insertTextCommand.setMemento(memento);
        assertEquals("This is a test.", invoker.getText());
    }

    @Test
    @DisplayName("Test Execute InsertTextCommand While Recording")
    public void testInsertTextCommand() {
        invoker.setText("This is a test.");
        recorder.setIsRecording(true);
        insertTextCommand.execute();
        assertEquals("This is a test.", engine.getBufferContents());
        assertEquals(1, recorder.getCommandHistory().size());
        assertEquals(insertTextCommand, recorder.getCommandHistory().getFirst().getFirst());
        assertEquals("This is a test.", ((InsertMemento)recorder.getCommandHistory().getFirst().getSecond()).getText());
    }
}
