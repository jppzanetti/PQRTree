package pqrtree;

class Leaf extends Node {

    private final int value;

    Leaf(int i) {
        super();

        this.value = i;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    boolean areAllChildrenBlack() {
        return true;
    }
}
