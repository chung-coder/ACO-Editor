package fr.istic.aco.editor.core;
/**
 * Provides access to selection control operations
 */
public class SelectionImpl implements Selection {
    private StringBuilder buffer;
    private int beginIndex;
    private int endIndex;
    private final int BUFFER_BEGIN_INDEX = 0;

    public SelectionImpl(StringBuilder buffer){
        this.buffer = buffer;
        setBeginIndex(BUFFER_BEGIN_INDEX);
        setEndIndex(BUFFER_BEGIN_INDEX);
    }

    public SelectionImpl(StringBuilder buffer, int beginIndex, int endIndex){
        this.buffer = buffer;
        setEndIndex(endIndex);
        setBeginIndex(beginIndex);
    }

    /**
     * Provides the index of the first character designated
     * by the selection.
     *
     * @return the begin index
     */
    @Override
    public int getBeginIndex() {
        return beginIndex;
    }

    /**
     * Provides the index of the first character
     * after the last character designated
     * by the selection.
     *
     * @return the end index
     */
    @Override
    public int getEndIndex() {
        return endIndex;
    }

    /**
     * Provides the index of the first character in the buffer
     *
     * @return the buffer's begin index
     */
    @Override
    public int getBufferBeginIndex() {
        return BUFFER_BEGIN_INDEX;
    }

    /**
     * Provides the index of the first "virtual" character
     * after the end of the buffer
     *
     * @return the post end buffer index
     */
    @Override
    public int getBufferEndIndex() {
        return buffer.length();
    }

    /**
     * Changes the value of the begin index of the selection
     *
     * @param beginIndex, must be within the buffer index range
     * @throws IndexOutOfBoundsException if the beginIndex is out of bounds
     */
    @Override
    public void setBeginIndex(int beginIndex) {
        if (beginIndex > this.endIndex || beginIndex < getBufferBeginIndex()){
            throw new IndexOutOfBoundsException("beginIndex is greater than endIndex or beginIndex is less than 0.");
        } else {
            this.beginIndex = beginIndex;
        }
    }

    /**
     * Changes the value of the end index of the selection
     *
     * @param endIndex, must be within the buffer index range
     * @throws IndexOutOfBoundsException if the beginIndex is out of bounds
     */
    @Override
    public void setEndIndex(int endIndex) {
        if (endIndex < this.beginIndex || endIndex > getBufferEndIndex()){
            throw new IndexOutOfBoundsException("endIndex is less than beginIndex or endIndex is less than 0.");
        } else {
            this.endIndex = endIndex;
        }
    }
}
