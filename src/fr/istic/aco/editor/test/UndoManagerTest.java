package fr.istic.aco.editor.test;

import fr.istic.aco.editor.core.Engine;
import fr.istic.aco.editor.core.EngineImpl;
import fr.istic.aco.editor.core.UndoManager;
import fr.istic.aco.editor.memento.EngineMemento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UndoManagerTest {
    private Engine engine;
    private UndoManager undoManager;

    @BeforeEach
    public void setUp() {
        engine = new EngineImpl();
        undoManager = new UndoManager(engine);
    }

    @Test
    @DisplayName("UndoManager initial state")
    void testConstructor() {
        assertEquals(0, undoManager.getPastStates().size());
        assertEquals(0, undoManager.getFutureStates().size());
        assertEquals(engine, undoManager.getEngine());
    }

    @Test
    @DisplayName("Test UndoManager Store")
    void testStoreMemento() {
        engine.insert("this is a test.");

        EngineMemento memento = (EngineMemento) engine.getMemento();

        undoManager.store(memento);

        assertEquals(1, undoManager.getPastStates().size());
        assertTrue(undoManager.getFutureStates().isEmpty());
    }

    @Test
    @DisplayName("Test UndoManager Store when futureStates is not empty")
    void testStoreMementoClearFutureStates() {
        EngineMemento memento1 = (EngineMemento) engine.getMemento();
        undoManager.store(memento1);
        engine.insert("test1");

        EngineMemento memento2 = (EngineMemento) engine.getMemento();
        undoManager.store(memento2);
        engine.insert("test2");

        undoManager.undo();
        engine.insert("test3");
    }

    @Test
    @DisplayName("Test undo in a normal behavior")
    void testUndo() {
        EngineMemento memento1 = (EngineMemento) engine.getMemento();
        undoManager.store(memento1);
        engine.insert("test1");
        undoManager.undo();
        EngineMemento memento2 = (EngineMemento) engine.getMemento();
        undoManager.store(memento2);
        engine.insert("test2");
        assertEquals(1, undoManager.getPastStates().size());
        assertEquals(0, undoManager.getFutureStates().size());
    }

    @Test
    @DisplayName("Test undo when previous states is empty")
    void testUndoThrowsExceptionWhenEmpty() {
        assertThrows(IllegalStateException.class, () -> {undoManager.undo();});
    }

    @Test
    @DisplayName("Test undo in a normal behavior")
    void testRedo() {
        EngineMemento memento1 = (EngineMemento) engine.getMemento();
        undoManager.store(memento1);
        engine.insert("test1");
        EngineMemento memento2 = (EngineMemento) engine.getMemento();
        undoManager.store(memento2);
        engine.insert("test2");
        undoManager.undo();
        undoManager.redo();
        assertEquals(2, undoManager.getPastStates().size());
        assertEquals(0, undoManager.getFutureStates().size());
    }

    @Test
    @DisplayName("Test undo when previous states is empty")
    void testRedoThrowsExceptionWhenEmpty() {
        assertThrows(IllegalStateException.class, () -> { undoManager.redo();});
    }
}
