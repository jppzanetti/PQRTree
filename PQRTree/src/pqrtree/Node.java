package pqrtree;

abstract class Node {

    /**
     * Stores the color of the node when it is defined.
     */
    private Color color;

    /**
     * The parent of the node. It is only set if the parent is a P-node or
     * if this node is the representative node among all its siblings in
     * the union-find structure.
     */
    private PQRNode parent;
    /**
     * If this is a non-representative child of a Q/R-node, 
     * points to a sibling node above it in the union-find tree.
     * Otherwise, points to itself.
     */
    private Node representant;
    /**
     * Rank of the node in the union-find tree. It is zero if the node is not
     * part of a union-find structure.
     */
    private int rank;

    /**
     * Holds the two immediate siblings of this node. This forms a symmetric 
     * list. No assumption at all can be made from siblings. The only way of
     * knowing which is "previous" and which is "next" is while traversing the
     * list. "Previous" is the one the traversal came from, "next" is the
     * opposite.
     */
    protected Node sibling[];

    /**
     * Marks whether the node has been visited during the reduction.
     */
    private boolean visited;
    /**
     * The number of pertinent children of this.
     */
    private int pertinentChildCount;
    /**
     * the number of pertinent leaves in the subtree rooted by this.
     */
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

    /**
     * "find" as in "union-find". Finds the representant of this node in the
     * union-find structure.
     * 
     * @return The representative sibling of this node.
     */
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

    /**
     * Tests whether every children of the node are colored black. A leaf
     * has no children, so it always tests true.
     * 
     * @return true if all the children of the node are colored black 
     * (of if the node is a leaf), false otherwise.
     */
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
