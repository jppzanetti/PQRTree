package pqrtree;

public abstract class Node {

    private Color color;

    private PQRNode parent;
    private Node representant;
    private int rank;

    protected Node sibling[];

    private boolean visited;
    private int pertinentChildCount;
    private int pertinentLeafCount;

    public Node() {
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

    public Color getColor() {
        return color;
    }

    private Node find() {
        if (this.representant != this) {
            this.representant = this.representant.find();
        }
        return this.representant;
    }

    public PQRNode getParent() {
        if (this.representant == this) {
            return parent;
        } else {
            return this.find().getParent();
        }
    }

    public void setColor(Color color) {
        this.color = color;

        if (color == Color.GRAY) {
            this.getParent().addGrayChild(this);
        }
        if (color == Color.BLACK) {
            this.getParent().addBlackChild(this);
        }
    }

    public void setParent(PQRNode parent) {
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

    public void setRepresentant(Node v) {
        this.representant = v;
    }

    public int getRank() {
        return this.rank;
    }

    public void incRank() {
        this.rank += 1;
    }

    public abstract boolean areAllChildrenBlack();

    public void cleanUp() {
        this.color = Color.WHITE;
        this.visited = false;
        this.pertinentChildCount = 0;
        this.pertinentLeafCount = 0;
    }

    public boolean isVisited() {
        return this.visited;
    }

    public void visit() {
        this.visited = true;
    }

    public int getPertinentChildCount() {
        return this.pertinentChildCount;
    }

    public void setPertinentChildCount(int n) {
        this.pertinentChildCount = n;
    }

    public int getPertinentLeafCount() {
        return this.pertinentLeafCount;
    }

    public void setPertinentLeafCount(int n) {
        this.pertinentLeafCount = n;
    }
}
