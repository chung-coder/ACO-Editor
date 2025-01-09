package fr.istic.aco.editor.core;

import fr.istic.aco.editor.memento.Memento;

/**
 * The Originator interface defines methods for managing the state of an object.
 * Any object implementing this interface can save its state into a Memento and
 * restore its state from a Memento. This follows the Memento design pattern.
 */
public interface Originator {
    /**
     * sets the attributes of the object calling this method to match the attributes the memento
     *
     * @param memento
     */
    void setMemento(Memento memento);

    /**
     * saves the state of the object calling this method in a memento matching the type of the object
     *
     * @return a memento of the object
     */
    Memento getMemento();
}
