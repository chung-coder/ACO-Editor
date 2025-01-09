package fr.istic.aco.editor.memento;

/**
 * Memento to save the text of the insert commands.
 */
public class InsertMemento implements Memento {
    private String text;

    public InsertMemento() {
    }

    /**
     *retrieves the text stored by the memento
     *
     * @return the text inserted with the insert command
     */
    public String getText() {
        return text;
    }

    /**
     * sets the attribute text of the memento
     * @param text the text to insert
     */
    public void setText(String text) {
        this.text = text;
    }
}
