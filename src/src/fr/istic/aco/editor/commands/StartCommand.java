package fr.istic.aco.editor.commands;

import fr.istic.aco.editor.core.Recorder;

/**
 * The {@code StartCommand} class implements the functionality for starting the recording of commands.
 */
public class StartCommand implements Command {
    private Recorder recorder;

    /**
     * Constructs a {@code StartCommand} with the specified dependencies.
     *
     * @param recorder the command recorder for saving and replaying the command.
     */
    public StartCommand(Recorder recorder) {
        this.recorder = recorder;
    }

    /**
     * Executes the start recording operation.
     * <p>
     * This method invokes the {@link Recorder#start()} method to initiate the recording of commands.
     * </p>
     */

    @Override
    public void execute(){
        recorder.start();
    }
}
