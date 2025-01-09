package fr.istic.aco.editor.core;

import fr.istic.aco.editor.memento.EngineMemento;

import java.util.ArrayList;
import java.util.List;

/**
 * The UndoManager class is responsible for managing the undo and redo operations.
 * It tracks the history of the engine's states and allows the engine to revert to previous states (undo)
 * or reapply a reverted state (redo). It uses mementos to store and restore states.
 */
public class UndoManager {
    List<EngineMemento> pastStates;
    List<EngineMemento> futureStates;
    private Engine engine;

    public UndoManager(Engine engine) {
        pastStates = new ArrayList<>();
        futureStates = new ArrayList<>();
        this.engine=engine;
    }
    public Engine getEngine() {
        return engine;
    }

    public List<EngineMemento> getFutureStates() {
        return futureStates;
    }

    public List<EngineMemento> getPastStates() {
        return pastStates;
    }

    /**
     * @throws  IllegalStateException if there is nothing to undo
     * reverts the state of the engine to its previous state
     * the current state is removed from pastStates and is placed in the future states
     * uses the memento to retrieve the state of the engine
     */
    public void undo(){
        if (pastStates.isEmpty()){
            throw new IllegalStateException("Nothing to undo: No previous states available.");
        }
        else{
            EngineMemento state = pastStates.getLast();
            pastStates.remove(state);
            futureStates.addFirst((EngineMemento) engine.getMemento());
            engine.setMemento(state);
        }
    }

    /**
     * @throws  IllegalStateException if there is nothing to redo
     * reverts the state of the engine to its next state
     * the current state is added to PastStates using a memento
     * the first FutureStates is removed from the List and is used to set the state of the engine
     */
    public void redo(){
        if (futureStates.isEmpty()){
            throw new IllegalStateException("Nothing to redo: No previous states available.");
        }
        else{
            EngineMemento state = futureStates.getFirst();
            futureStates.remove(state);
            pastStates.addLast((EngineMemento) engine.getMemento());
            engine.setMemento(state);
        }
    }

    /**
     * Saves the given engine state (memento) to the pastStates list
     * If the futureStates list is not empty, it is cleared
     * @param memento The current state of the engine encapsulated as an EngineMemento.
     */
    public void store(EngineMemento memento){
        if(!this.futureStates.isEmpty()){
            futureStates.clear();
        }
        pastStates.add(memento);
    }
}
