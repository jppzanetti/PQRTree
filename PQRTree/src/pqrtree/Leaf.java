package pqrtree;

public class Leaf extends Node {

    private int value;

    public Leaf(int i) {
        super();

        this.value = i;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean areAllChildrenBlack() {
        return true;
    }
}
