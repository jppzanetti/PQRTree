# PQRTree

The PQR-Tree is a generalization of the PQ-Tree.

It is a data structure to solve the consecutive-ones problem (C1P). Given a set of elements S, and a collection C of subsets of S. The goal is to find orderings of E where every subset of C is consecutive. Such orderings are called valid. The PQ-Tree is a data structure that represents all the valid orderings for a given input. The PQR-Tree expands on the PQ-Tree by having more information in cases where no valid ordering exists.

## Usage

The main class to interact with is the `PQRTree` class.

The constructor, `PQRTree(int n)`, builds an universal PQR-Tree with `n` elements, labeled from `0` to `n - 1`. An universal tree is a tree without any constraint, rooted at a P-node with all the leaves under it.

Having a PQR-Tree, the main method to update it is `reduce()`. It adds one constraint to the tree. For example, given a PQR-Tree `t`, `t.reduce({1, 2, 3})` updates `t` to represent only orderings where 1, 2, and 3 are consecutive.

## CLIReduce

A simple example on how to use PQR-Trees is in the `CLIReduce` class. It implements an application to build and update a PQR-Tree through the CLI.
