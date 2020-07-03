package tree;

import java.util.List;

/**
 * AVL树
 * @param <ValueType> 值类型
 */
public class AVL<ValueType extends Comparable<ValueType>> extends AbstractBinarySearchTree<AVL.AVLNode<ValueType>, ValueType> {
    public static class AVLNode<ValueType extends Comparable<ValueType>> extends AbstractBinarySearchTree.AbstractBinarySearchNode<AVLNode<ValueType>, ValueType> {
        int height;

        public AVLNode(ValueType value) {
            super(value);
        }

        public AVLNode(AVLNode<ValueType> parent, ValueType value) {
            super(parent, value);
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }

    /**
     * 插入值
     * @param value 值
     */
    public void insert(ValueType value) {
        AVLNode<ValueType> node = new AVLNode<>(value);
        insert(node);
    }

    /**
     * 插入节点
     * @param node 节点
     */
    public void insert(AVLNode<ValueType> node) {
        // 如果根为空，则直接作为根节点
        if (root == null) {
            root = node;
            return;
        }

        AVLNode<ValueType> parent = root;
        // 找到位置并插入
        while (true) {
            if (node.compareTo(parent) < 0) {
                if (parent.left() == null) {
                    parent.setLeft(node);
                    node.parent = parent;
                    break;
                } else {
                    parent = parent.left();
                }
            } else {
                if (parent.right() == null) {
                    parent.setRight(node);
                    node.parent = parent;
                    break;
                } else {
                    parent = parent.right();
                }
            }
        }

        // 如果插入后导致不平衡，进行平衡
        AVLNode<ValueType> unbalancedNode = updateDepthUpward(node);
        if (unbalancedNode != null) {
            balance(unbalancedNode);
        }
    }

    // 从指定节点向上更新各个节点的高度，
    // 并返回第一个发生不平衡的节点
    private AVLNode<ValueType> updateDepthUpward(AVLNode<ValueType> leaf) {
        AVLNode<ValueType> unbalancedNode = null;

        int depth = leaf.height;
        AVLNode<ValueType> node = leaf.parent;
        while (node != null) {
            node.height = Math.max(node.height, ++depth);

            if (unbalancedNode == null && Math.abs(depthDiff(node)) > 1) {
                unbalancedNode = node;
            }

            node = node.parent;
        }

        return unbalancedNode;
    }

    private void updateDepth(AVLNode<ValueType> node) {
        AVLNode<ValueType> left = node.left();
        AVLNode<ValueType> right = node.right();
        int lDepth = left == null ? -1 : left.height;
        int rDepth = right == null ? -1 : right.height;
        node.height = Math.max(lDepth, rDepth) + 1;
    }

    // 平衡指定的子树
    private void balance(AVLNode<ValueType> unbalancedNode) {
        int diff = depthDiff(unbalancedNode);
        if (diff > 1) {
            // 右树较高
            int rDiff = depthDiff(unbalancedNode.right());
            if (rDiff > 0) {
                // 右右情况
                rotateLeft(unbalancedNode);
            } else {
                // 右左情况
                rotateRightLeft(unbalancedNode);
            }
        } else if (diff < -1) {
            // 左树较高
            int rDiff = depthDiff(unbalancedNode.left());
            if (rDiff < 0) {
                // 左左情况
                rotateRight(unbalancedNode);
            } else {
                // 左右情况
                rotateLeftRight(unbalancedNode);
            }
        }
    }

    // 计算指定节点和它的兄弟节点的高度差
    private int depthDiff(AVLNode<ValueType> node) {
        AVLNode<ValueType> left = node.left();
        AVLNode<ValueType> right = node.right();
        int lDepth = left == null ? -1 : left.height;
        int rDepth = right == null ? -1 : right.height;
        return rDepth - lDepth;
    }

    @Override
    protected void rotateLeft(AVLNode<ValueType> parent) {
        AVLNode<ValueType> rightChildNode = parent.right();
        super.rotateLeft(parent);

        // 更新父节点和右子节点的高度
        updateDepth(parent);
        updateDepth(rightChildNode);
    }

    @Override
    protected void rotateRight(AVLNode<ValueType> parent) {
        AVLNode<ValueType> leftChildNode = parent.left();
        super.rotateRight(parent);

        updateDepth(parent);
        updateDepth(leftChildNode);
    }

    // 左右情况，先左旋再右旋
    private void rotateLeftRight(AVLNode<ValueType> parent) {
        rotateLeft(parent.left());
        rotateRight(parent);
    }

    // 右左情况，先右旋再左旋
    private void rotateRightLeft(AVLNode<ValueType> parent) {
        rotateRight(parent.right());
        rotateLeft(parent);
    }

    public static void main(String[] args) {
//        AVL<Integer> avl = new AVL<>();
//        int[] arr = { 1, 2, 3, 0, -1, 5, 4, -3, -2, -4 };
//        for (int elem : arr) {
//            avl.insert(elem);
//        }
//        List<AVLNode<Integer>> infix = avl.infix();
//        for (AVLNode<Integer> node : infix) {
//            System.out.print(node);
//            System.out.print(' ');
//        }

        AVL<Integer> avl = new AVL<>();
        for (int i = 0; i < 10000; i++) {
            avl.insert(i);
        }
        System.out.println(avl.root.height);
        List<AVLNode<Integer>> infix = avl.infix();
        for (AVLNode<Integer> node : infix) {
            System.out.print(node);
            System.out.print(' ');
        }
    }

}
