package fr.istic.aco.editor.core;

import fr.istic.aco.editor.memento.Memento;

import java.util.ArrayList;
import java.util.List;

/**
 * The Recorder class is responsible for recording, saving, and replaying commands.
 * It stores a history of commands and their corresponding mementos.
 * This allows for re-executing the same sequence of commands in the future.
 */
public class Recorder {
    private List<Pair<CommandOriginator, Memento>> commandHistory ;
    private Boolean isRecording;
    private Boolean isReplaying;

    public Recorder() {
        commandHistory = new ArrayList<Pair<CommandOriginator, Memento>>();
        setIsRecording(false);
        setIsReplaying(false);
    }

    public List<Pair<CommandOriginator, Memento>> getCommandHistory(){
        return commandHistory;
    }

    /**
     * enable or disable the function recording
     * @param isRecording
     */
    public void setIsRecording(Boolean isRecording){
        this.isRecording = isRecording;
    }

    /**
     * indicates if the engine is currently recording
     * @return true if recording is active, false otherwise.
     */
    public boolean getIsRecording(){
        return isRecording;
    }

    /**
     * enable or disable the function replaying
     * @param isReplaying
     */
    public void setIsReplaying(Boolean isReplaying){
        this.isReplaying = isReplaying;
    }

    /**
     * indicates if the engine is currently replaying
     * @return true if recording is active, false otherwise.
     */
    public boolean getIsReplaying(){
        return isReplaying;
    }

    /**
     * Clears the command history to ensure a fresh recording session.
     *  Sets the recording state to true.
     */
    public void start(){
        System.out.println("Start Recording");
        commandHistory.clear();
        setIsRecording(true);
    }

    /**
     * Sets the recording state to false.
     */
    public void stop(){
        System.out.println("Stop Recording");
        setIsRecording(false);
    }


    /**
     * Saves the current state of a command to the command history if recording is active.
     * Checks if the system is in recording mode.
     * Creates a pair containing the command as an id and its associated memento.
     * Adds the pair to the command history for future replay operations.
     *
     * @param command The CommandOriginator object whose state needs to be saved.
     */
    public void save(CommandOriginator command){
        if(getIsRecording()){
            Pair<CommandOriginator, Memento> pair = new Pair<>(command, command.getMemento());
            commandHistory.add(pair);
        }
    }


    /**
     * @throws IllegalStateException if the History is empty
     * replays all the commands of the command History.
     * each command is executed after setting its corresponding memento.
     * sets replaying at false after all commands have been executed
     * @throws IllegalArgumentException if replaying or recording are active
     */
    public void replay() {
        if(commandHistory.isEmpty()){
            throw new IllegalStateException("the list of saved commands is empty");
        }
        else {
            if(!getIsRecording() && !getIsReplaying()){
                setIsReplaying(true);
                for(Pair<CommandOriginator, Memento> pair : commandHistory){
                    CommandOriginator command = pair.getFirst();
                    Memento memento = pair.getSecond();
                    command.setMemento(memento);
                    command.execute();
                }
                setIsReplaying(false);
            }
            else{
                throw new IllegalArgumentException("You can not record commands while replaying, nor replay while replaying. Try to stop the recording first");
            }
        }
    }
}
