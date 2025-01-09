package fr.istic.aco.editor.memento;
/**
 * Memento to save the parameters of MoveSelection Commands.
 */
public class SelectionMemento implements Memento {
    private int begin,end;

    public SelectionMemento() {
    }

    /**
     * Sets the beginning index of the Selection memento
     *
     * @param begin the new starting index as an int
     */
    public void setBegin(int begin) {
        this.begin = begin;
    }

    /**
     * Sets the ending index of the Selection memento
     *
     * @param end the new ending index as an int
     */
    public void setEnd(int end) {
        this.end = end;
    }

    /**
     * Retrieves the beginning index of the memento
     *
     * @return the beginning index as an int
     */
    public int getBegin() {
        return begin;
    }

    /**
     * Retrieves the ending index of the memento
     *
     * @return the ending index as an int
     */
    public int getEnd() {
        return end;
    }

}
