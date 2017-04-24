# Balanced-Tree

In computer science, a B-tree is a self-balancing tree data structure that keeps data sorted and allows searches, sequential access, insertions, and deletions in logarithmic time. The B-tree is a generalization of a binary search tree in that a node can have more than two children. Unlike self-balancing binary search trees, the B-tree is optimized for systems that read and write large blocks of data. B-trees are a good example of a data structure for external memory. It is commonly used in databases and filesystems.

![B-Tree](http://bluerwhite.org/btree/tree-search.gif)

## Definition

- Every noda has at most m children.
- Every non-leaf node (except root) has at least ⌈m/2⌉ children.
- The root has at least two children if it is not a leaf node.
- A non-leaf node with k children contains k−1 keys.
- All leaves appear in the same level.

## Internal nodes

Internal nodes are all nodes except for leaf nodes and the root nodes. They are usally represented as an ordered set of elements, children and child pointers. Every internal node contains a maximum of U children and a minimum of L children. Thus, the number of elements is always 1 less than the number of child pointers (the number of elements is between L-1 and U-1). U must be either 2L or 2L-1; therefore each internal node is atleast half full.

## The root node

The root node's number of children has the same upper limit as internal nodes, but has nno lower limit. 

## Leaf nodes

Leaf nodes have the same restriction on the number of elements, but have no children, and no child pointers.
