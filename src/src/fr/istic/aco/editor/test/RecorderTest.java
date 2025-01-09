package fr.istic.aco.editor.test;

import fr.istic.aco.editor.commands.*;
import fr.istic.aco.editor.core.CommandOriginator;
import fr.istic.aco.editor.core.*;
import fr.istic.aco.editor.memento.Memento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RecorderTest {
    private Engine engine;
    private Invoker invoker;
    private Recorder recorder;
    private UndoManager undoManager;
    private MoveSelection moveSelection;
    private CopyTextCommand copyTextCommand;
    private InsertTextCommand insertTextCommand;
    private CutSelectedCommand cutSelectedCommand;
    private DeleteTextCommand deleteTextCommand;
    private PasteTextCommand pasteTextCommand;

    @BeforeEach
    public void setUp() {
        engine = new EngineImpl();
        invoker = new Invoker(engine);
        recorder = new Recorder();
        undoManager = new UndoManager(engine);
    }

    @Test
    @DisplayName("Recorder initial state")
    void testConstructor() {
        assertTrue(recorder.getCommandHistory().isEmpty());
        assertFalse(recorder.getIsRecording());
        assertFalse(recorder.getIsReplaying());
    }

    @Test
    @DisplayName("Testing start")
    void testStartRecording() {
        recorder.start();
        assertTrue(recorder.getIsRecording());
        assertTrue(recorder.getCommandHistory().isEmpty());
    }

    @Test
    @DisplayName("Testing stop")
    void testStopRecording() {
        recorder.start();
        recorder.stop();
        assertFalse(recorder.getIsRecording());
    }

    @Test
    @DisplayName("Testing save command")
    void testSaveDeleteCommand() {
        deleteTextCommand = new DeleteTextCommand(engine, recorder, undoManager);
        recorder.start();
        recorder.save(deleteTextCommand);
        List<Pair<CommandOriginator, Memento>> commandHistory = recorder.getCommandHistory();
        assertEquals(1, commandHistory.size());
        assertEquals(deleteTextCommand, commandHistory.getFirst().getFirst());
        assertNull(commandHistory.getFirst().getSecond());
    }

    @Test
    @DisplayName("Testing Replay command while no command recorded")
    void testReplayEmptyCommand() {
        assertThrows(IllegalStateException.class, () -> recorder.replay());
        assertFalse(recorder.getIsReplaying());
    }

    @Test
    @DisplayName("Testing Replay command while recording is active")
    public void testReplayWhileRecording() {
        copyTextCommand = new CopyTextCommand(engine, recorder, undoManager);
        pasteTextCommand = new PasteTextCommand(engine, recorder, undoManager);
        recorder.setIsRecording(true);
        recorder.save(copyTextCommand);
        recorder.save(pasteTextCommand);

        assertThrows(IllegalArgumentException.class, () -> recorder.replay());

        assertTrue(recorder.getIsRecording());
        assertFalse(recorder.getIsReplaying());
    }

    @Test
    @DisplayName("Testing Replay command while recording or replaying is active")
    void testReplayWhileReplaying () {
        copyTextCommand = new CopyTextCommand(engine, recorder, undoManager);
        recorder.setIsRecording(true);
        recorder.save(copyTextCommand);
        recorder.setIsRecording(false);
        recorder.setIsReplaying(true);
        assertThrows(IllegalArgumentException.class, () -> recorder.replay());
        assertTrue(recorder.getIsReplaying());
    }


    @Test
    @DisplayName("Testing Replay command")
    void testReplayCommand() {
        invoker.setText("This is a test.");
        insertTextCommand = new InsertTextCommand(engine, invoker, recorder, undoManager);
        moveSelection = new MoveSelection(engine, invoker, recorder, undoManager);
        cutSelectedCommand = new CutSelectedCommand(engine, recorder, undoManager);
        pasteTextCommand = new PasteTextCommand(engine, recorder, undoManager);
        copyTextCommand = new CopyTextCommand(engine, recorder, undoManager);
        deleteTextCommand = new DeleteTextCommand(engine, recorder, undoManager);
        recorder.start();
        recorder.save(insertTextCommand);
        recorder.save(moveSelection);
        recorder.save(cutSelectedCommand);
        recorder.save(pasteTextCommand);
        recorder.save(copyTextCommand);
        recorder.save(deleteTextCommand);
        recorder.stop();
        recorder.replay();
        assertEquals("This is a test.", engine.getBufferContents());
        assertFalse(recorder.getIsReplaying());
    }

    @Test
    @DisplayName("Test Execute Command While Not Recording")
    public void testInsertTextCommandNotRecording() {
        insertTextCommand = new InsertTextCommand(engine, invoker, recorder, undoManager);
        invoker.setText("This is a test.");
        insertTextCommand.execute();
        assertEquals(0, recorder.getCommandHistory().size());
    }
}
