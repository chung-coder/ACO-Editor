package fr.istic.aco.editor.commands;

import fr.istic.aco.editor.core.Recorder;

/**
 * The {@code ReplayCommand} class implements the functionality for replaying a sequence of previously recorded commands.
 */
public class ReplayCommand implements Command {
    private Recorder recorder;

    /**
     * Constructs a {@code ReplayCommand} with the specified dependencies.
     *
     * @param recorder the command recorder for saving and replaying the command.
     */
    public ReplayCommand(Recorder recorder) {
        this.recorder = recorder;
    }

    /**
     * Executes the replay operation.
     * <p>
     * This method invokes the {@link Recorder#replay()} method to replay the sequence of recorded commands.
     * </p>
     */
    @Override
    public void execute(){
        recorder.replay();
    }
}
