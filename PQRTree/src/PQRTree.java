package pqrtree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Scanner;

public class PQRTree {
	private PQRNode root;
	private Leaf[] leaf;
	private LinkedList<PQRNode> visitedNodes;

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
		
		this.visitedNodes = new LinkedList<PQRNode>();
	}
	
	public void reduce(int[] c) {
		if (c.length < 2) {
			return;
		}
		
		PQRNode r = this.bubble(c);
		r = this.repairGray(r);
		r.adjust();
		this.uncolor(c);
	}
	
	public PQRNode bubble(int[] c) {
		LinkedList<Node> queue = new LinkedList<Node>();
		int offTheTop = 0;
		
		// First phase: count the pertinent children of each node
		for (int i : c) {
			this.leaf[i].visit();
			queue.add(this.leaf[i]);
		}
		while (queue.size() + offTheTop > 1) {
			Node v = queue.poll();
			PQRNode p = v.getParent();
			
			if (p == null) offTheTop = 1;
			else {
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
			
			if (v.getPertinentLeafCount() == c.length) return (PQRNode) v;
			
			if (v.areAllChildrenBlack()) v.setColor(Color.BLACK);
			else v.setColor(Color.GRAY);
			
			p.setPertinentChildCount(p.getPertinentChildCount() - 1);
			p.setPertinentLeafCount(p.getPertinentLeafCount() + v.getPertinentLeafCount());
			if (p.getPertinentChildCount() == 0) queue.add(p);
		}
		
		return null;
	}
	
	public PQRNode repairGray(PQRNode r) {
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
	
	public void uncolor(int[] c) {
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
	
	public String toString() {
		return root.toString();
	}
	
	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(System.in);
		int tmpRestriction[] = null;
		
		// Create initial universal tree
        int elementNumber = scanner.nextInt();
        PQRTree pqrtree = new PQRTree(elementNumber);
        System.out.println(pqrtree.toString());
        
        // Read restrictions until empty line
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line = in.readLine();
        while (!line.isEmpty()) {
        	Scanner scanLine = new Scanner(line);
        	int restriction[] = null;
    		int i = 0;
            
            tmpRestriction = new int[elementNumber];
            while(scanLine.hasNextInt()){
     		   tmpRestriction[i++] = scanLine.nextInt();
     		}
            
            restriction = new int[i];
            for (int j = 0; j < i; j++) {
    			restriction[j] = tmpRestriction[j];
    		}
            
            pqrtree.reduce(restriction);
            System.out.println(pqrtree.toString());
            
            line = in.readLine();
		}
	}
}