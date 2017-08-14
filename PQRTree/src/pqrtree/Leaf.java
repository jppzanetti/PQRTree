package pqrtree;

class Leaf extends Node {

    /**
     * A number that servers as the label of the leaf.
     */
    private final int value;

    /**
     * Initializes a new leaf.
     * 
     * @param i The leaf label.
     */
    Leaf(int i) {
        super();

        this.value = i;
    }

    @Override
    /**
     * Returns a string representation of the leaf. It is simply the string
     * representation of the leaf label.
     */
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    boolean areAllChildrenBlack() {
        return true;
    }
}
