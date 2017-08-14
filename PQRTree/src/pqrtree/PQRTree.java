package pqrtree;

import java.util.LinkedList;

/**
 * The main class for solving the Consecutive Ones Problem (C1P). Builds and
 * updates a PQR-tree according to the given constraint sets.
 * <p>
 * <b>Usage:</b>
 * 
 * The first step is to build a initial (universal) tree, with 
 * PQRTree.PQRTree(n). This tree has n leaves, labeled from 0 to n - 1.
 * 
 * Then, constraints can be added to the tree one by one using PQRTree.reduce().
 * 
 * @author Joao
 */
public class PQRTree {

    /**
     * The root of the tree.
     */
    private PQRNode root;
    
    /**
     * An array of all the leaves.
     */
    private Leaf[] leaf;
    
    /**
     * A list to hold all nodes that are visited during the reduction process.
     */
    private final LinkedList<PQRNode> visitedNodes;

    /**
     * Builds and returns an universal tree with n leaves.
     * 
     * @param n The number of leaves in the tree. 
     */
    public PQRTree(int n) {
        super();

        this.root = null;
        this.leaf = null;

        // Create root
        this.root = new PQRNode(PQRType.P);

        // Create the leaf list
        this.leaf = new Leaf[n];

        // Create and insert leaves
        for (int i = 0; i < n; i++) {
            this.leaf[i] = new Leaf(i);
            this.root.insertEnd(this.leaf[i]);
        }

        this.visitedNodes = new LinkedList<>();
    }

    /**
     * Adds one constraint set to the tree, updating its structure as necessary.
     * 
     * @param c An array of integers that must be consecutive.
     */
    public void reduce(int[] c) {
        if (c.length < 2) {
            return;
        }

        PQRNode r = this.bubble(c);
        r = this.repairGray(r);
        r.adjust();
        this.uncolor(c);
    }

    /**
     * Colors the tree with regards to the new constraint.
     * 
     * @param c The constraint being added.
     * @return The least common ancestor (LCA) of all the pertinent nodes.
     *         This is the node that serves as the start for the updates to the
     *         tree.
     */
    private PQRNode bubble(int[] c) {
        LinkedList<Node> queue = new LinkedList<>();
        int offTheTop = 0;

        // First phase: count the pertinent children of each node
        for (int i : c) {
            this.leaf[i].visit();
            queue.add(this.leaf[i]);
        }
        while (queue.size() + offTheTop > 1) {
            Node v = queue.poll();
            PQRNode p = v.getParent();

            if (p == null) {
                offTheTop = 1;
            } else {
                if (!p.isVisited()) {
                    queue.add(p);
                    p.visit();
                    this.visitedNodes.add(p);
                }
                p.setPertinentChildCount(p.getPertinentChildCount() + 1);
            }
        }

        // Second phase: color the nodes and find the LCA
        queue.clear();
        for (int i : c) {
            this.leaf[i].setPertinentLeafCount(1);
            queue.add(this.leaf[i]);
        }
        while (!queue.isEmpty()) {
            Node v = queue.poll();
            Node p = v.getParent();

            if (v.getPertinentLeafCount() == c.length) {
                return (PQRNode) v;
            }

            if (v.areAllChildrenBlack()) {
                v.setColor(Color.BLACK);
            } else {
                v.setColor(Color.GRAY);
            }

            p.setPertinentChildCount(p.getPertinentChildCount() - 1);
            p.setPertinentLeafCount(p.getPertinentLeafCount() + v.getPertinentLeafCount());
            if (p.getPertinentChildCount() == 0) {
                queue.add(p);
            }
        }

        return null;
    }

    /**
     * Updates the colored tree, eliminating all gray nodes.
     * 
     * @param r The LCA.
     * @return The LCA after the repair.
     */
    private PQRNode repairGray(PQRNode r) {
        PQRNode v = r.getGrayChild();
        PQRNode newLCA = r;

        while (v != null) {
            // Prepare the LCA
            if (newLCA.getType() == PQRType.P) {
                if (v.getType() == PQRType.P) {
                    // Transform P node into Q node
                    v = v.transformPIntoQ(newLCA);
                }

                // Join black children
                newLCA.joinBlackChildren();

                // Move children away from the LCA
                newLCA.moveAwayFromLCA(v);
                if ((this.root == newLCA) && (newLCA.getChildCount() == 0)) {
                    this.root = v;
                }
                newLCA = v;
            } else {
                if (v.getType() == PQRType.P) {
                    // Merge P node
                    v.mergePNode();
                } else {
                    // Merge into the LCA
                    v.mergeIntoLCA();
                }
            }
            v = newLCA.getGrayChild();
        }

        return newLCA;
    }

    /**
     * Resets the tree after the reduction.
     * 
     * @param c The constraint just added.
     */
    private void uncolor(int[] c) {
        for (int i : c) {
            this.leaf[i].cleanUp();

            PQRNode p = this.leaf[i].getParent();
            if ((p != null) && (p.isVisited())) {
                p.cleanUp();
                this.visitedNodes.add(p);
            }
        }
        while (!this.visitedNodes.isEmpty()) {
            PQRNode v = this.visitedNodes.poll();
            v.cleanUp();

            PQRNode p = v.getParent();
            if ((p != null) && (p.isVisited())) {
                p.cleanUp();
                this.visitedNodes.add(p);
            }
        }
    }

    /**
     * Returns a string representation of the PQR tree. It uses a variant of
     * the nested parenthesis method of representing trees in text, in which
     * the type of the node is denoted by the type of brackets used: 
     * 
     * <ul>
     * <li>P-nodes are represented by parenthesis ()
     * <li>Q-nodes are represented by square brackets []
     * <li>R-nodes are represented by curly braces {}
     * </ul>
     * 
     * Examples:
     * 
     * A universal tree with 5 leaves is represented by the string
     * "(0 1 2 3 4)".
     * 
     * @return The string representation of the tree.
     */
    @Override
    public String toString() {
        return root.toString();
    }
}
