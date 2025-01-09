package fr.istic.aco.editor.test;

import fr.istic.aco.editor.commands.MoveSelection;
import fr.istic.aco.editor.core.*;
import fr.istic.aco.editor.memento.Memento;
import fr.istic.aco.editor.memento.SelectionMemento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoveSelectionTest {
    private Engine engine;
    private Invoker invoker;
    private Recorder recorder;
    private UndoManager undoManager;
    private MoveSelection moveSelection;

    @BeforeEach
    public void setUp() {
        engine = new EngineImpl();
        invoker = new Invoker(engine);
        recorder = new Recorder();
        undoManager = new UndoManager(engine);
        moveSelection = new MoveSelection(engine, invoker, recorder, undoManager);
    }

    @Test
    @DisplayName("Test Move Selection Memento SetBegin and GetBegin")
    public void testMementoSetBeginAndGetBegin() {
        SelectionMemento memento = new SelectionMemento();

        memento.setBegin(1);
        assertEquals(1, memento.getBegin());
    }

    @Test
    @DisplayName("Test Move Selection Memento SetEnd and GetEnd")
    public void testMementoSetEndAndGetEnd() {
        SelectionMemento memento = new SelectionMemento();

        memento.setEnd(1);
        assertEquals(1, memento.getEnd());
    }

    @Test
    @DisplayName("Test Get Memento")
    public void testGetMemento() {
        invoker.setText("This is a test.");
        invoker.setBeginIndex(1);
        invoker.setEndIndex(3);
        Memento memento = moveSelection.getMemento();
        assertEquals(1, ((SelectionMemento) memento).getBegin());
        assertEquals(3, ((SelectionMemento) memento).getEnd());
    }

    @Test
    @DisplayName("Test Set Memento")
    public void testSetMemento() {
        invoker.setText("This is a test.");
        SelectionMemento memento = new SelectionMemento();
        memento.setBegin(1);
        memento.setEnd(3);

        moveSelection.setMemento(memento);
        assertEquals(1, invoker.getBeginIndex());
        assertEquals(3, invoker.getEndIndex());
    }

    @Test
    @DisplayName("Test Execute MoveSelection While Recording")
    public void testMoveSelectionCommand() {
        engine.insert("This is a test.");
        engine.getSelection().setBeginIndex(1);
        engine.getSelection().setEndIndex(3);

        invoker.setBeginIndex(4);
        invoker.setEndIndex(6);

        recorder.setIsRecording(true);
        moveSelection.execute();

        assertEquals(1, recorder.getCommandHistory().size());
        assertEquals(moveSelection, recorder.getCommandHistory().getFirst().getFirst());
        assertEquals(4, ((SelectionMemento)recorder.getCommandHistory().getFirst().getSecond()).getBegin());
        assertEquals(6, ((SelectionMemento)recorder.getCommandHistory().getFirst().getSecond()).getEnd());
    }
}
