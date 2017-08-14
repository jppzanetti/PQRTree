package pqrtree;

/**
 * Defines the colors a node can have during the reduction.
 * A node is colored black when all its descendant leaves are contained in the
 * added constraint. White is for when the descendant leaves contain none of the
 * pertinent values or all of them. If neither of those apply, the node is
 * colored gray.
 * @author Joao
 */
enum Color {
    WHITE, GRAY, BLACK;
}
