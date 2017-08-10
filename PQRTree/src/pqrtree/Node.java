package pqrtree;

abstract class Node {

    private Color color;

    private PQRNode parent;
    private Node representant;
    private int rank;

    protected Node sibling[];

    private boolean visited;
    private int pertinentChildCount;
    private int pertinentLeafCount;

    Node() {
        super();

        this.color = Color.WHITE;

        this.parent = null;
        this.representant = this;
        this.rank = 0;

        this.sibling = new Node[2];

        this.visited = false;
        this.pertinentChildCount = 0;
        this.pertinentLeafCount = 0;
    }

    Color getColor() {
        return color;
    }

    private Node find() {
        if (this.representant != this) {
            this.representant = this.representant.find();
        }
        return this.representant;
    }

    PQRNode getParent() {
        if (this.representant == this) {
            return parent;
        } else {
            return this.find().getParent();
        }
    }

    void setColor(Color color) {
        this.color = color;

        if (color == Color.GRAY) {
            this.getParent().addGrayChild(this);
        }
        if (color == Color.BLACK) {
            this.getParent().addBlackChild(this);
        }
    }

    void setParent(PQRNode parent) {
        if (parent == null) {
            this.parent = null;
            this.representant = this;
        } else if (parent.getType() == PQRType.P) {
            this.parent = parent;
            this.representant = this;
        } else if (parent.getChildCount() == 0) {
            this.parent = parent;
            this.representant = this;
        } else {
            this.parent = null;
            this.representant = parent.getRepresentativeChild();
        }
    }

    void setRepresentant(Node v) {
        this.representant = v;
    }

    int getRank() {
        return this.rank;
    }

    void incRank() {
        this.rank += 1;
    }

    abstract boolean areAllChildrenBlack();

    void cleanUp() {
        this.color = Color.WHITE;
        this.visited = false;
        this.pertinentChildCount = 0;
        this.pertinentLeafCount = 0;
    }

    boolean isVisited() {
        return this.visited;
    }

    void visit() {
        this.visited = true;
    }

    int getPertinentChildCount() {
        return this.pertinentChildCount;
    }

    void setPertinentChildCount(int n) {
        this.pertinentChildCount = n;
    }

    int getPertinentLeafCount() {
        return this.pertinentLeafCount;
    }

    void setPertinentLeafCount(int n) {
        this.pertinentLeafCount = n;
    }
}
