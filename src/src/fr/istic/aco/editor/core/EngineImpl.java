package fr.istic.aco.editor.core;

import fr.istic.aco.editor.memento.EngineMemento;
import fr.istic.aco.editor.memento.Memento;
/**
 * This class represents an implementation of the Engine interface, which
 * controls the main editing operations of a text editor such as insert, delete, cut, copy, and paste.
 */
public class EngineImpl implements Engine {
    private StringBuilder buffer = new StringBuilder();
    private String clipboard = "";
    private SelectionImpl selection;

    public EngineImpl(){
        selection = new SelectionImpl(buffer);
    }
    /**
     * Provides access to the selection control object
     *
     * @return the selection object
     */
    @Override
    public Selection getSelection() {
        return selection;
    }

    /**
     * Provides the whole contents of the buffer, as a string
     *
     * @return a copy of the buffer's contents
     */
    @Override
    public String getBufferContents() {
        return buffer.toString();
    }

    /**
     * Provides the clipboard contents
     *
     * @return a copy of the clipboard's contents
     */
    @Override
    public String getClipboardContents() {
        return clipboard;
    }

    /**
     * Removes the text within the interval
     * specified by the selection control object,
     * from the buffer.
     */
    @Override
    public void cutSelectedText() {
        int begin = selection.getBeginIndex();
        int end = selection.getEndIndex();
        clipboard = buffer.substring(begin, end);
        buffer.delete(begin, end);
        selection.setEndIndex(begin);
    }

    /**
     * Copies the text within the interval
     * specified by the selection control object
     * into the clipboard.
     */
    @Override
    public void copySelectedText() {
        int begin = selection.getBeginIndex();
        int end = selection.getEndIndex();
        clipboard = buffer.substring(begin, end);
    }

    /**
     * Replaces the text within the interval specified by the selection object with
     * the contents of the clipboard.
     */
    @Override
    public void pasteClipboard() {
        int begin = selection.getBeginIndex();
        int end = selection.getEndIndex();

        buffer.replace(begin, end, clipboard);

        int newEndIndex = begin + clipboard.length();
        selection.setEndIndex(newEndIndex);
        selection.setBeginIndex(newEndIndex);
    }

    /**
     * Inserts a string in the buffer, which replaces the contents of the selection
     *
     * @param s the text to insert
     */
    @Override
    public void insert(String s) {
        int begin = selection.getBeginIndex();
        int end = selection.getEndIndex();

        buffer.replace(begin, end, s);

        int newEndIndex = begin + s.length();
        selection.setEndIndex(newEndIndex);
        selection.setBeginIndex(newEndIndex);
    }

    /**
     * Removes the contents of the selection in the buffer
     */
    @Override
    public void delete() {
        int begin = selection.getBeginIndex();
        int end = selection.getEndIndex();

        buffer.delete(begin, end);

        selection.setEndIndex(begin);
    }

    /**
     * @param memento
     * Sets the attributes of the engine as in the memento
     */
    @Override
    public void setMemento(Memento memento) {
        EngineMemento m = (EngineMemento) memento;
        clipboard = m.getClipboard();
        if(selection.getEndIndex() > m.getBeginIndex()){
            selection.setBeginIndex(m.getBeginIndex());
            selection.setEndIndex(m.getEndIndex());
        } else {
            selection.setEndIndex(m.getEndIndex());
            selection.setBeginIndex(m.getBeginIndex());
        }
        buffer = new StringBuilder(m.getBufferContent());
    }

    /**
     *
     * @return a memento of the state of the engine ie the clipboard, the selection, the buffer
     */
    @Override
    public EngineMemento getMemento() {
        EngineMemento m = new EngineMemento(this.getBufferContents(), selection.getBeginIndex(), selection.getEndIndex(), this.getClipboardContents());
        return m;
    }
}
