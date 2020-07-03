package tree;

public class BinaryTree<ValueType> extends AbstractBinaryTree<BinaryTree.SimpleBinaryTreeNode<ValueType>, ValueType> {

    static class SimpleBinaryTreeNode<ValueType> extends BinaryTreeNode<SimpleBinaryTreeNode<ValueType>, ValueType> {

        public SimpleBinaryTreeNode(ValueType value) {
            super(value);
        }
    }

}
