package tree;

/**
 * 二叉搜索树
 * @param <BSN> 二叉搜索树节点类型
 * @param <ValueType> 值类型
 */
public class AbstractBinarySearchTree<BSN extends AbstractBinarySearchTree.AbstractBinarySearchNode<BSN, ValueType>, ValueType extends Comparable<ValueType>>
        extends AbstractBinaryTree<BSN, ValueType> {

    public static class BinarySearchNode<ValueType extends Comparable<ValueType>> extends AbstractBinarySearchNode<BinarySearchNode<ValueType>, ValueType> {

        public BinarySearchNode(ValueType value) {
            super(value);
        }

        public BinarySearchNode(BinarySearchNode<ValueType> parent, ValueType value) {
            super(parent, value);
        }
    }

    public static class AbstractBinarySearchNode<BSN extends AbstractBinarySearchNode<BSN, ValueType>, ValueType extends Comparable<ValueType>>
            extends BinaryTreeNode<BSN, ValueType> implements Comparable<AbstractBinarySearchNode<BSN, ValueType>> {

        public AbstractBinarySearchNode(ValueType value) {
            super(value);
        }

        public AbstractBinarySearchNode(BSN parent, ValueType value) {
            super(parent, value);
        }

        @Override
        public int compareTo(AbstractBinarySearchNode<BSN, ValueType> o) {
            return value.compareTo(o.value);
        }
    }

    public BSN search(ValueType value) {
        BSN node = root;
        while (node != null) {
            int compare = value.compareTo(node.value);
            if (compare == 0) {
                return node;
            } else if (compare < 0) {
                node = node.left();
            } else {
                node = node.right();
            }
        }
        return null;
    }

}
