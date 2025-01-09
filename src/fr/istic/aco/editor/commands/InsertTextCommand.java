package fr.istic.aco.editor.commands;

import fr.istic.aco.editor.core.*;
import fr.istic.aco.editor.memento.EngineMemento;
import fr.istic.aco.editor.memento.InsertMemento;
import fr.istic.aco.editor.memento.Memento;

/**
 * The {@code InsertTextCommand} class implements the functionality for inserting a string in the buffer,
 * which replaces the contents of the selection
 */
public class InsertTextCommand implements CommandOriginator {
    private Engine engine;
    private Invoker invoker;
    private Recorder recorder;
    private UndoManager undoManager;

    /**
     * Constructs a {@code CopyTextCommand} with the specified dependencies.
     *
     * @param engine the text editing engine responsible for text editing actions
     * @param invoker the invoker object that provides the text to be inserted
     * @param recorder the command recorder for saving and replaying the command
     * @param undoManager the undo manager for handling undo and redo functionality
     */
    public InsertTextCommand(Engine engine, Invoker invoker, Recorder recorder, UndoManager undoManager) {
        this.engine = engine;
        this.invoker = invoker;
        this.recorder = recorder;
        this.undoManager = undoManager;
    }

    /**
     * Creates and returns a memento representing the current state of the insert command.
     * <p>
     * The memento stores the text to be inserted, which is retrieved from the {@link Invoker}.
     * </p>
     *
     * @return a memento containing the text to be inserted
     */
    @Override
        public Memento getMemento() {
        InsertMemento m = new InsertMemento();
        m.setText(invoker.getText());
        return m;
    }

    /**
     * Restores the state of the command from the provided memento.
     * <p>
     * The text stored in the memento is set back to the {@link Invoker}.
     * </p>
     *
     * @param memento the memento object containing the state to restore
     */
    @Override
    public void setMemento(Memento memento) {
        String text = ((InsertMemento) memento).getText();
        invoker.setText(text);
    }

    /**
     * Executes the command by inserting a string in the buffer
     * <p>
     * Before executing, the current state of the {@link Engine} is stored in the
     * {@link UndoManager} as a memento. The command is also saved in the {@link Recorder}
     * for replay functionality.
     * </p>
     */
    @Override
    public void execute(){
        EngineMemento m = (EngineMemento) engine.getMemento();
        undoManager.store(m);
        engine.insert(invoker.getText());
        recorder.save(this);
    }
}
