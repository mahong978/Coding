package tree;

import java.util.Random;

/**
 * 树堆
 * 最小堆形式
 * @param <ValueType>
 */
public class Treap<ValueType extends Comparable<ValueType>>
        extends AbstractBinarySearchTree<
            AbstractBinarySearchTree.BinarySearchNode<Treap.TreapValue<ValueType>>,
            Treap.TreapValue<ValueType>> {
    public static class TreapValue<ValueType extends Comparable<ValueType>>
        implements Comparable<TreapValue<ValueType>> {

        private static final Random RANDOM = new Random();

        private final ValueType value;
        private final int priority;

        public TreapValue(ValueType value) {
            this.value = value;
            this.priority = RANDOM.nextInt();
        }

        @Override
        public int compareTo(TreapValue<ValueType> o) {
            return value.compareTo(o.value);
        }
    }

    public void insert(ValueType key) {
        TreapValue<ValueType> value = new TreapValue<>(key);
        BinarySearchNode<TreapValue<ValueType>> node = new BinarySearchNode<>(value);

        if (root == null) {
            root = node;
            return;
        }

        insert(root, node);
    }

    public void delete(ValueType key) {
        BinarySearchNode<TreapValue<ValueType>> node = search(new TreapValue<>(key));
        if (node == null) {
            return;
        }

        delete(node);
    }

    private void insert(BinarySearchNode<TreapValue<ValueType>> parent, BinarySearchNode<TreapValue<ValueType>> node) {
        boolean isLessThanParent = node.value.compareTo(parent.value) < 0;
        if (isLessThanParent) {
            if (parent.left() == null) {
                parent.setLeft(node);
            } else {
                insert(parent.left(), node);
            }
            if (parent.left().value.priority < node.value.priority) {
                rotateRight(node);
            }
        } else {
            if (parent.right() == null) {
                parent.setRight(node);
            } else {
                insert(parent.right(), node);
            }
            if (parent.right().value.priority < node.value.priority) {
                rotateLeft(node);
            }
        }
    }

    private void delete(BinarySearchNode<TreapValue<ValueType>> node) {
        BinarySearchNode<TreapValue<ValueType>> leftChildNode = node.left();
        BinarySearchNode<TreapValue<ValueType>> rightChildNode = node.right();
        if (leftChildNode != null && rightChildNode != null) {
            // 如果有左右子节点，则需要把这个节点转下去，将优先级较小的子节点转上去，直到这个节点转成叶子节点
            if (leftChildNode.value.priority < rightChildNode.value.priority) {
                rotateRight(leftChildNode);
            } else {
                rotateLeft(rightChildNode);
            }
            delete(node);
        } else if (leftChildNode == null) {
            // 如果没有左子节点，直接替换成右子节点
            if (node == root) {
                root = rightChildNode;
            } else {
                BinarySearchNode<TreapValue<ValueType>> right = node.right();
                node.setRight(null);
                if (node.parent.left() == node) {
                    node.parent.setLeft(right);
                } else {
                    node.parent.setRight(right);
                }
            }
        } else if (node.right() == null) {
            // 如果没有右子节点，直接替换成左子节点
            if (node == root) {
                root = leftChildNode;
            } else {
                BinarySearchNode<TreapValue<ValueType>> right = node.left();
                node.setLeft(null);
                if (node.parent.left() == node) {
                    node.parent.setLeft(right);
                } else {
                    node.parent.setRight(right);
                }
            }
        }
    }

}
