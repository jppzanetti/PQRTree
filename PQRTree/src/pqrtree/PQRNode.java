package pqrtree;

import java.util.LinkedList;

class PQRNode extends Node {

    /**
     * The first child of the node.
     */
    private Node firstChild;
    /**
     * The last child of the node.
     */
    private Node lastChild;
    /**
     * The child that is the representant of the union-find structure of 
     * this node's children. It is only set if this node has type Q or R.
     */
    private Node representativeChild;
    /**
     * Number of children of the node.
     */
    private int childCount;

    /**
     * The type of the node --- P, Q or R.
     */
    private PQRType type;
    /**
     * Whether the node has been deleted from the tree.
     * Nodes can be removed from the tree but still be needed for the union-find
     * tree structure. In this case they are removed from the tree, marked as
     * deleted, but kept in the union-find tree.
     */
    private boolean deleted;

    /**
     * A list that stores the gray children of the node.
     */
    private final LinkedList<Node> grayChildren;
    /**
     * A list that stores the black children of the node.
     */
    private final LinkedList<Node> blackChildren;

    PQRNode(PQRType type) {
        super();

        this.firstChild = null;
        this.lastChild = null;
        this.representativeChild = null;
        this.childCount = 0;

        this.type = type;
        this.deleted = false;

        this.grayChildren = new LinkedList<>();
        this.blackChildren = new LinkedList<>();
    }

    /**
     * Returns the type of the node.
     * 
     * @return The type of the node.
     */
    PQRType getType() {
        return type;
    }

    /**
     * Changes the type of the node.
     * 
     * @param type The new type for the node.
     */
    void setType(PQRType type) {
        this.type = type;
    }

    boolean isDeleted() {
        return deleted;
    }

    int getChildCount() {
        return this.childCount;
    }

    Node getRepresentativeChild() {
        return this.representativeChild;
    }

    void setRepresentativeChild(Node v) {
        this.representativeChild = v;
    }

    void addGrayChild(Node node) {
        this.grayChildren.add(node);
    }

    void addBlackChild(Node node) {
        this.blackChildren.add(node);
    }

    PQRNode getGrayChild() {
        return (PQRNode) this.grayChildren.poll();
    }

    /**
     * Add a node as a child of this node, without caring about its position.
     * Use it when this node has type P or R. Assumes v was already completely 
     * removed from any other parent node.
     * 
     * @param v The node to be added as a child.
     */
    void insertChild(Node v) {
        this.insertEnd(v);
    }

    /**
     * Add a node as the first child of this node. Use it when this node is a 
     * Q-node. Assumes v was already completely removed from any other parent
     * node.
     * 
     * @param v The node to be added as a child.
     */
    void insertBeginning(Node v) {
        v.setParent(this);
        v.sibling[1] = this.firstChild;
        this.childCount++;

        // If this had at least a child already
        if (this.firstChild != null) {
            // Attach v to the former first child where the sibling list ended
            if (this.firstChild.sibling[0] == null) {
                this.firstChild.sibling[0] = v;
            } else {
                this.firstChild.sibling[1] = v;
            }
        }

        // If v is the only child, it is also the last
        if (this.getChildCount() == 1) {
            this.lastChild = v;
            if (this.getType() != PQRType.P) {
                this.setRepresentativeChild(v);
            }
        }

        this.firstChild = v;

        if (v.getColor() == Color.BLACK) {
            this.blackChildren.add(v);
        }
        if (v.getColor() == Color.GRAY) {
            this.grayChildren.add(v);
        }
    }

    /**
     * Add a node as the last child of this node. Use it when this node is a 
     * Q-node Assumes v was already completely removed from any other parent 
     * node.
     * 
     * @param v The node to be added as a child.
     * 
     * @see PQRNode#insertBeginning(pqrtree.Node) 
     */
    void insertEnd(Node v) {
        v.setParent(this);
        v.sibling[0] = this.lastChild;
        this.childCount++;

        // If this had at least a child already
        if (this.lastChild != null) {
            // Attach v to the former first child where the sibling list ended
            if (this.lastChild.sibling[0] == null) {
                this.lastChild.sibling[0] = v;
            } else {
                this.lastChild.sibling[1] = v;
            }
        }

        // If v is the only child, it is also the first
        if (this.getChildCount() == 1) {
            this.firstChild = v;
            if (this.getType() != PQRType.P) {
                this.setRepresentativeChild(v);
            }
        }

        this.lastChild = v;

        if (v.getColor() == Color.BLACK) {
            this.blackChildren.add(v);
        }
        if (v.getColor() == Color.GRAY) {
            this.grayChildren.add(v);
        }
    }

    /**
     * Insert node v as a child of this node, between nodes i and j. Nodes i and
     * j have to consecutive siblings.
     * 
     * @param v The new child.
     * @param i Child of this node, linked to j.
     * @param j Child of this node, linked to i.
     * 
     * @see PQRNode#insertBeginning(pqrtree.Node) 
     */
    void insertBetween(Node v, Node i, Node j) {
        v.sibling[0] = i;
        v.sibling[1] = j;

        if (i == null) {
            if (j == this.firstChild) {
                this.firstChild = v;
            } else {
                this.lastChild = v;
            }
        } else if (i.sibling[0] == j) {
            i.sibling[0] = v;
        } else {
            i.sibling[1] = v;
        }

        if (j == null) {
            if (i == this.firstChild) {
                this.firstChild = v;
            } else {
                this.lastChild = v;
            }
        } else if (j.sibling[0] == i) {
            j.sibling[0] = v;
        } else {
            j.sibling[1] = v;
        }

        v.setParent(this);
        this.childCount++;

        if (v.getColor() == Color.BLACK) {
            this.blackChildren.add(v);
        } else if (v.getColor() == Color.GRAY) {
            this.grayChildren.add(v);
        }
    }

    /**
     * Removes v as a child of this node. Doesn't remove v from colored lists.
     * 
     * @param v A child of this node.
     */
    void removeChild(Node v) {
        this.childCount--;

        Node v0 = v.sibling[0];
        Node v1 = v.sibling[1];

        if (v0 == null) {
            if (this.firstChild == v) {
                this.firstChild = v1;
            }
            if (this.lastChild == v) {
                this.lastChild = v1;
            }
        } else if (v0.sibling[0] == v) {
            v0.sibling[0] = v1;
        } else {
            v0.sibling[1] = v1;
        }

        if (v1 == null) {
            if (this.firstChild == v) {
                this.firstChild = v0;
            }
            if (this.lastChild == v) {
                this.lastChild = v0;
            }
        } else if (v1.sibling[0] == v) {
            v1.sibling[0] = v0;
        } else {
            v1.sibling[1] = v0;
        }

        v.sibling[0] = null;
        v.sibling[1] = null;
    }

    /**
     * Remove the node from its parent and mark it as deleted. Nodes are not 
     * actually destroyed because they might be part of a union-find tree.
     */
    private void destroy() {
        PQRNode p = this.getParent();
        if (p != null) {
            p.removeChild(this);
        }
        this.deleted = true;
    }

    /**
     * Invert the order of the children of the node.
     */
    private void reverse() {
        Node tmp = this.firstChild;
        this.firstChild = this.lastChild;
        this.lastChild = tmp;
    }

    /*
     ************************************
     * Operations to repair gray nodes. *
     ************************************
     */
    void joinBlackChildren() {
        if ((this.blackChildren.size() > 1) && (this.blackChildren.size() < this.getChildCount())) {
            PQRNode b = new PQRNode(PQRType.P);
            b.visit();
            this.insertChild(b);
            while (!this.blackChildren.isEmpty()) {
                Node bi = this.blackChildren.poll();
                this.removeChild(bi);
                b.insertEnd(bi);
            }
            b.setColor(Color.BLACK);
        }
    }

    PQRNode transformPIntoQ(PQRNode r) {
        // Create gray node g of type Q child of r after v
        PQRNode g = new PQRNode(PQRType.Q);
        g.visit();
        r.insertBetween(g, this, this.sibling[0]);
        g.setColor(Color.GRAY);
        r.grayChildren.removeLast();

        // Move black children
        if (this.blackChildren.size() > 1) {
            PQRNode b = new PQRNode(PQRType.P);
            b.visit();
            g.insertEnd(b);
            while (!this.blackChildren.isEmpty()) {
                Node bi = this.blackChildren.poll();
                this.removeChild(bi);
                b.insertEnd(bi);
            }
            b.setColor(Color.BLACK);
        } else {
            while (!this.blackChildren.isEmpty()) {
                Node bi = this.blackChildren.poll();
                this.removeChild(bi);
                g.insertEnd(bi);
            }
        }

        // Move gray children
        while (!this.grayChildren.isEmpty()) {
            Node gi = this.grayChildren.poll();
            this.removeChild(gi);
            g.insertEnd(gi);
        }

        this.setColor(Color.WHITE);

        // Move v and its white children
        if (this.getChildCount() > 1) {
            r.removeChild(this);
            g.insertEnd(this);
            this.setColor(Color.WHITE);
        } else {
            if (this.getChildCount() == 1) {
                Node w = this.firstChild;
                this.removeChild(w);
                g.insertEnd(w);
            }
            this.destroy();
        }

        return g;
    }

    void moveAwayFromLCA(PQRNode v) {
        if (v.getFirstChild().getColor().ordinal() < v.getLastChild().getColor().ordinal()) {
            v.reverse();
        }

        // Move black child
        while (!this.blackChildren.isEmpty()) {
            Node bi = this.blackChildren.poll();
            this.removeChild(bi);
            v.insertBeginning(bi);
        }

        // Move gray children
        while (!this.grayChildren.isEmpty()) {
            Node gi = this.grayChildren.poll();
            if (gi != v) {
                this.removeChild(gi);
                v.insertBeginning(gi);
            }
        }

        v.setColor(Color.WHITE); // v is now the LCA
        this.grayChildren.clear();

        if (this.getChildCount() == 1) {
            this.removeChild(v);
            if (this.getParent() != null) {
                this.getParent().insertEnd(v);
            }
            this.destroy();
        }
    }

    private Node getFirstChild() {
        return this.firstChild;
    }

    private Node getLastChild() {
        return this.lastChild;
    }

    void mergeIntoLCA() {
        PQRNode r = this.getParent();

        // Union
        Node thisRep = this.getRepresentativeChild();
        Node rRep = r.getRepresentativeChild();
        if (thisRep.getRank() > rRep.getRank()) {
            rRep.setRepresentant(thisRep);
            r.setRepresentativeChild(thisRep);
        } else { // thisRep and rRep are always different
            thisRep.setRepresentant(rRep);
            if (thisRep.getRank() == rRep.getRank()) {
                rRep.incRank();
            }
        }

        // Get the siblings
        Node outLighter = this.sibling[0];
        Node outDarker = this.sibling[1];
        if ((outDarker == null)
                || ((outLighter != null) && (outLighter.getColor().ordinal() > outDarker.getColor().ordinal()))) {
            Node tmp = outDarker;
            outDarker = outLighter;
            outLighter = tmp;
        }

        // Merge the lists
        Node leftChild = this.getFirstChild();
        Node rightChild = this.getLastChild();
        if (leftChild.getColor().ordinal() < rightChild.getColor().ordinal()) {
            if (leftChild.sibling[0] == null) {
                leftChild.sibling[0] = outLighter;
            } else {
                leftChild.sibling[1] = outLighter;
            }

            if (outLighter != null) {
                if (outLighter.sibling[0] == this) {
                    outLighter.sibling[0] = leftChild;
                } else {
                    outLighter.sibling[1] = leftChild;
                }
            } else {
                r.firstChild = leftChild;
            }

            if (rightChild.sibling[0] == null) {
                rightChild.sibling[0] = outDarker;
            } else {
                rightChild.sibling[1] = outDarker;
            }

            // outDarker should never be null
            if (outDarker.sibling[0] == this) {
                outDarker.sibling[0] = rightChild;
            } else {
                outDarker.sibling[1] = rightChild;
            }
        } else {
            if (rightChild.sibling[0] == null) {
                rightChild.sibling[0] = outLighter;
            } else {
                rightChild.sibling[1] = outLighter;
            }

            if (outLighter != null) {
                if (outLighter.sibling[0] == this) {
                    outLighter.sibling[0] = rightChild;
                } else {
                    outLighter.sibling[1] = rightChild;
                }
            } else {
                r.firstChild = rightChild;
            }

            if (leftChild.sibling[0] == null) {
                leftChild.sibling[0] = outDarker;
            } else {
                leftChild.sibling[1] = outDarker;
            }

            // outDarker should never be null
            if (outDarker.sibling[0] == this) {
                outDarker.sibling[0] = leftChild;
            } else {
                outDarker.sibling[1] = leftChild;
            }
        }

        // Set type R if necessary
        if ((this.type == PQRType.R) && (r.getType() == PQRType.Q)) {
            r.setType(PQRType.R);
        }
        
        // Update LCA child count and colored children lists
        r.childCount = r.childCount + this.childCount;
        r.blackChildren.addAll(this.blackChildren);
        r.grayChildren.addAll(this.grayChildren);
        
        // Destroy this node
        this.sibling[0] = null;
        this.sibling[1] = null;
        this.destroy();
    }

    void mergePNode() {
        PQRNode r = this.getParent();

        // Determine darkest direction
        Node lightestDir = this.sibling[0];
        Node darkestDir = this.sibling[1];
        if ((darkestDir == null)
            || ((lightestDir != null) 
                && (lightestDir.getColor().ordinal() > darkestDir.getColor().ordinal()))) {
            Node tmp = darkestDir;
            darkestDir = lightestDir;
            lightestDir = tmp;
        }

        // Move black children
        if (this.blackChildren.size() > 1) {
            PQRNode b = new PQRNode(PQRType.P);
            b.visit();
            r.insertBetween(b, this, darkestDir);
            darkestDir = b;
            while (!this.blackChildren.isEmpty()) {
                Node bi = this.blackChildren.poll();
                this.removeChild(bi);
                b.insertEnd(bi);
            }
            b.setColor(Color.BLACK);
        } else {
            while (!this.blackChildren.isEmpty()) {
                Node bi = this.blackChildren.poll();
                this.removeChild(bi);
                r.insertBetween(bi, this, darkestDir);
                darkestDir = bi;
            }
        }

        // Move gray children
        while (!this.grayChildren.isEmpty()) {
            Node gi = this.grayChildren.poll();
            this.removeChild(gi);
            r.insertBetween(gi, this, darkestDir);
            darkestDir = gi;
        }

        this.setColor(Color.WHITE);

        // If v has only one white child, move it
        if (this.getChildCount() <= 1) {
            if (this.getChildCount() == 1) {
                Node w = this.firstChild;
                this.removeChild(w);
                r.insertBetween(w, this, darkestDir);
            }
            this.destroy();
        }
    }

    /*
     **************************************
     * Operations to adjust the LCA       *
     * after repairing all gray children. *
     **************************************
     */
    void adjust() {
        switch (this.type) {
            case P:
                this.joinBlackChildren();
                break;

            case Q:
                this.adjustQ();
                break;

            default:
                break;
        }
    }

    private void adjustQ() {
        int whiteCount = 0;
        for (Node b : this.blackChildren) {
            if ((b.sibling[0] == null) || (b.sibling[0].getColor() == Color.WHITE)) {
                whiteCount++;
            }
            if ((b.sibling[1] == null) || (b.sibling[1].getColor() == Color.WHITE)) {
                whiteCount++;
            }
        }

        if (whiteCount > 2) {
            this.type = PQRType.R;
        }
    }

    @Override
    void cleanUp() {
        super.cleanUp();

        this.grayChildren.clear();
        this.blackChildren.clear();
    }

    /*
     **************
     * Utilities. *
     **************
     */
    
    /**
     * Returns a string representation of the PQR subtree rooted at this node.
     * 
     * @return The string representation of the node.
     * @see PQRTree.toString()
     */
    @Override
    public String toString() {
        String s = "";

        switch (this.type) {
            case P:
                s += "(";
                break;

            case Q:
                s += "[";
                break;

            case R:
                s += "{";
                break;

            default:
                break;
        }

        Node child = this.getFirstChild();
        Node prevChild = null;
        int nextChild;
        while (child != null) {
            s += child.toString();
            if (child != this.getLastChild()) {
                s += " ";
            }

            // Get next child
            if (child.sibling[0] == prevChild) {
                nextChild = 1;
            } else {
                nextChild = 0;
            }
            prevChild = child;
            child = child.sibling[nextChild];
        }

        switch (this.type) {
            case P:
                s += ")";
                break;

            case Q:
                s += "]";
                break;

            case R:
                s += "}";
                break;

            default:
                break;
        }

        return s;
    }

    @Override
    boolean areAllChildrenBlack() {
        return (this.blackChildren.size() == this.getChildCount());
    }
}
