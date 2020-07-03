package tree;

import java.util.List;
import java.util.Stack;

/**
 * 笛卡尔树
 *
 * 具有堆性质，中序遍历可还原原来数组的顺序
 * @param <ValueType>
 */
public class CartesianTree<ValueType extends Comparable<ValueType>>
        extends AbstractBinarySearchTree <
                    AbstractBinarySearchTree.BinarySearchNode<CartesianTree.CartesianTreeValue<ValueType>>,
                    CartesianTree.CartesianTreeValue<ValueType>> {

    public static class CartesianTreeValue<ValueType extends Comparable<ValueType>> implements Comparable<CartesianTreeValue<ValueType>> {
        public int index;
        public ValueType value;

        public CartesianTreeValue(int index, ValueType value) {
            this.index = index;
            this.value = value;
        }

        @Override
        public int compareTo(CartesianTreeValue<ValueType> o) {
            return value.compareTo(o.value);
        }

        @Override
        public String toString() {
            return "(" + index + ", " + value.toString() + ")";
        }
    }

    public CartesianTree(ValueType[] elements) {
        if (elements == null || elements.length == 0) {
            return;
        }

        root = new BinarySearchNode<>(new CartesianTreeValue<>(0, elements[0]));
        Stack<BinarySearchNode<CartesianTreeValue<ValueType>>> rightChain = new Stack<>();
        rightChain.push(root);

        for (int i = 1; i < elements.length; i++) {
            ValueType val = elements[i];
            CartesianTreeValue<ValueType> value = new CartesianTreeValue<>(i, val);
            BinarySearchNode<CartesianTreeValue<ValueType>> parent = null;
            // 一直出栈，直到弹出一个值不比自己大的节点（小于等于）
            while (!rightChain.empty() && (parent = rightChain.peek()).value.compareTo(value) > 0) {
                rightChain.pop();
            }
            // 上面所找的节点不存在
            if (rightChain.empty()) {
                parent = null;
            }

            BinarySearchNode<CartesianTreeValue<ValueType>> node = new BinarySearchNode<>(value);
            if (parent == null) {
                // 不存在比自己小（小于等于）的节点，则自己就成为根节点
                node.setLeft(root);
                root = node;
            } else {
                // 存在比自己小的节点，则成为它的右节点，父节点原来的右节点成为自己的左节点
                node.setLeft(parent.right());
                parent.setRight(node);
            }

            rightChain.push(node);
        }
    }

    public static void main(String[] args) {
        Integer[] arr = new Integer[] { 9, 3, 7, 1, 8, 12, 10, 20, 15, 18, 5 };
        CartesianTree<Integer> tree = new CartesianTree<>(arr);
        List<BinarySearchNode<CartesianTreeValue<Integer>>> prefix = tree.prefix();
        for (BinarySearchNode<CartesianTreeValue<Integer>> node : prefix) {
            System.out.print(node.value.value);
            System.out.print(' ');
        }
        System.out.println();
        List<BinarySearchNode<CartesianTreeValue<Integer>>> infix = tree.infix();
        for (BinarySearchNode<CartesianTreeValue<Integer>> node : infix) {
            System.out.print(node.value.value);
            System.out.print(' ');
        }
        System.out.println();
        List<BinarySearchNode<CartesianTreeValue<Integer>>> postfix = tree.postfix();
        for (BinarySearchNode<CartesianTreeValue<Integer>> node : postfix) {
            System.out.print(node.value.value);
            System.out.print(' ');
        }
        System.out.println("haha");
    }

}
