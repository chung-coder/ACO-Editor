package fr.istic.aco.editor.memento;

/**
 * Memento to save the states of the engine.
 */
public class EngineMemento implements Memento {
    String bufferContent = "";
    int beginIndex = 0;
    int endIndex = 0;
    String clipboard = "";

    public EngineMemento(String bufferContent, int beginIndex, int endIndex, String clipboard) {
        setBufferContent(bufferContent);
        setBeginIndex(beginIndex);
        setEndIndex(endIndex);
        setClipboard(clipboard);
    }

    /**
     * Retrieves the current content of the engine memento's buffer.
     *
     * @return the content of the buffer as a String.
     */
    public String getBufferContent() {
        return bufferContent;
    }

    /**
     * Updates the content of the engine memento's buffer.
     *
     * @param bufferContent the new content to set in the buffer.
     */
    public void setBufferContent(String bufferContent) {
        this.bufferContent = bufferContent;
    }

    /**
     * Retrieves the starting index of the engine memento
     *
     * @return the beginning index as an int
     */
    public int getBeginIndex() {
        return beginIndex;
    }

    /**
     * Sets the starting index of the engine memento
     *
     * @param beginIndex the new beginning index as an int
     */
    public void setBeginIndex(int beginIndex) {
        this.beginIndex = beginIndex;
    }

    /**
     * Retrieves the ending index of the engine memento
     *
     * @return the ending index as an int
     */
    public int getEndIndex() {
        return endIndex;
    }

    /**
     * Sets the ending index of the engine memento
     *
     * @param endIndex the new ending index as an int
     */
    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    /**
     * Retrieves the content currently stored in the engine memento's clipboard.
     *
     * @return the clipboard content as a String.
     */
    public String getClipboard() {
        return clipboard;
    }

    /**
     * Updates the content of the engine memento's clipboard.
     *
     * @param clipboard the new content to set in the clipboard.
     */
    public void setClipboard(String clipboard) {
        this.clipboard = clipboard;
    }


}
