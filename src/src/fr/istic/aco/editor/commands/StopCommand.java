package fr.istic.aco.editor.commands;

import fr.istic.aco.editor.core.Recorder;

/**
 * The {@code StopCommand} class implements the functionality for stopping the recording of commands.
 */
public class StopCommand implements Command {
    private Recorder recorder;

    /**
     * Constructs a {@code StopCommand} with the specified dependencies.
     *
     * @param recorder the command recorder for saving and replaying the command.
     */
    public StopCommand(Recorder recorder) {
        this.recorder = recorder;
    }

    /**
     * Executes the stop recording operation.
     * <p>
     * This method invokes the {@link Recorder#stop()} method to terminate the recording of commands.
     * </p>
     */
    @Override
    public void execute(){
        recorder.stop();
    }
}
