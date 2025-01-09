package fr.istic.aco.editor.test;

import fr.istic.aco.editor.commands.*;
import fr.istic.aco.editor.core.Engine;
import fr.istic.aco.editor.core.EngineImpl;
import fr.istic.aco.editor.core.Recorder;
import fr.istic.aco.editor.core.UndoManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommandTest {
    private Engine engine;
    private Recorder recorder;
    private UndoManager undoManager;
    private StartCommand start;
    private StopCommand stop;
    private ReplayCommand replay;
    private UndoCommand undo;
    private RedoCommand redo;

    @BeforeEach
    public void setUp() {
        engine = new EngineImpl();
        recorder = new Recorder();
        undoManager = new UndoManager(engine);
        start = new StartCommand(recorder);
        stop = new StopCommand(recorder);
        replay = new ReplayCommand(recorder);
        undo = new UndoCommand(undoManager);
        redo = new RedoCommand(undoManager);
    }

    @Test
    @DisplayName("Test Start Class")
    public void testExecuteStart() {
        start.execute();
        assertTrue(recorder.getIsRecording());
    }

    @Test
    @DisplayName("Test Stop Class")
    public void testExecuteStop() {
        start.execute();
        stop.execute();
        assertFalse(recorder.getIsRecording());
    }

    @Test
    @DisplayName("Test Replay Class")
    public void testReplayStop() {
        assertThrows(IllegalStateException.class, () -> replay.execute());
        assertFalse(recorder.getIsReplaying());
    }

    @Test
    @DisplayName("Test Undo Class")
    public void testUndo() {
        assertThrows(IllegalStateException.class, () -> undo.execute());
    }

    @Test
    @DisplayName("Test Redo Class")
    public void testRedo() {
        assertThrows(IllegalStateException.class, () -> redo.execute());
    }
}
