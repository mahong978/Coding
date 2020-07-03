package tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 二叉树
 * @param <BTN> 二叉树节点类型
 * @param <ValueType> 值类型
 */
public class AbstractBinaryTree<BTN extends BinaryTreeNode<BTN, ValueType>, ValueType> extends Tree<BTN, ValueType> {

    /**
     * 中序遍历
     * @return 遍历结果对应的列表
     */
    public List<BTN> infix() {
        List<BTN> result = new ArrayList<>();
        LinkedList<BTN> stack = new LinkedList<>();

        BTN currNode = root;
        while (currNode != null || !stack.isEmpty()) {
            if (currNode != null) {
                stack.push(currNode);
                currNode = currNode.left();
            } else {
                BTN parent = stack.pop();
                result.add(parent);
                currNode = parent.right();
            }
        }

        return result;
    }

    // 右右情况，进行左旋
    protected void rotateLeft(BTN parent) {
        BTN node = parent.right();
        // 如果父节点是根节点，则更新子节点为根节点即可
        // 否则将子节点取代父节点，成为祖父节点的子节点
        if (parent == root) {
            root = node;
        } else {
            BTN ancestor = parent.parent;
            if (ancestor.left() == parent) {
                ancestor.setLeft(node);
            } else {
                ancestor.setRight(node);
            }
        }
        node.parent = parent.parent;

        // 父节点成为子节点的左子节点，子节点的左子节点成为父节点的右子节点
        BTN left = node.left();
        parent.setRight(left);
        if (left != null) {
            left.parent = parent;
        }
        node.setLeft(parent);
        parent.parent = node;
    }

    // 左左情况，进行右旋
    protected void rotateRight(BTN parent) {
        BTN node = parent.left();
        if (parent == root) {
            root = node;
        } else {
            BTN ancestor = parent.parent;
            if (ancestor.left() == parent) {
                ancestor.setLeft(node);
            } else {
                ancestor.setRight(node);
            }
        }
        node.parent = parent.parent;

        BTN right = node.right();
        parent.setLeft(right);
        if (right != null) {
            right.parent = parent;
        }
        node.setRight(parent);
        parent.parent = node;
    }

}

/**
 * 二叉树节点的抽象类
 * @param <BTN>
 * @param <ValueType>
 */
class BinaryTreeNode<BTN extends BinaryTreeNode<BTN, ValueType>, ValueType> extends TreeNode<BTN, ValueType> {
    private BTN left;
    private BTN right;
    {
        children.add(null);
        children.add(null);
    }

    public BinaryTreeNode(ValueType value) {
        super(value);
    }

    public BinaryTreeNode(BTN parent, ValueType value) {
        super(parent, value);
    }

    public BTN left() {
        return left;
    }

    public void setLeft(BTN left) {
        this.left = left;
        children.set(0, left);
    }

    public BTN right() {
        return right;
    }

    public void setRight(BTN right) {
        this.right = right;
        children.set(1, right);
    }

    public void attach(BTN parent, boolean left) {
        super.attach(parent);

        if (parent == null) {
            return;
        }
        if (left) {
            parent.setLeft((BTN) this);
        } else {
            parent.setRight((BTN) this);
        }
    }

    @Override
    public void detach() {
        super.detach();

        if (parent == null) {
            return;
        }

        if (parent.left() == this) {
            parent.setLeft(null);
        } else {
            parent.setRight(null);
        }
        parent = null;
    }
}
