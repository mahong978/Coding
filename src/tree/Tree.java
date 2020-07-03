package tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * 树的抽象类
 * @param <TreeNodeType> 树节点类型
 * @param <ValueType> 值类型
 */
public abstract class Tree<TreeNodeType extends TreeNode<TreeNodeType, ValueType>, ValueType> {

    protected TreeNodeType root;

    /**
     * 前序遍历
     * @return 遍历结果对应的节点列表
     */
    public List<TreeNodeType> prefix() {
        List<TreeNodeType> result = new ArrayList<>();
        LinkedList<TreeNodeType> queue = new LinkedList<>();
        queue.push(root);

        while (!queue.isEmpty()) {
            TreeNodeType node = queue.remove();
            result.add(node);
            for (int i = node.children.size() - 1; i >= 0; i--) {
                TreeNodeType child = node.children.get(i);
                if (child != null) {
                    queue.push(child);
                }
            }
        }

        return result;
    }

    /**
     * 后序遍历
     * @return 遍历结果对应的节点列表
     */
    public List<TreeNodeType> postfix() {
        List<TreeNodeType> result = new ArrayList<>();
        Stack<TreeNodeType> stack1 = new Stack<>();
        Stack<TreeNodeType> stack2 = new Stack<>();
        stack1.push(root);

        while (!stack1.empty()) {
            TreeNodeType node = stack1.pop();
            for (TreeNodeType child : node.children) {
                if (child != null) {
                    stack1.push(child);
                }
            }
            stack2.push(node);
        }

        while (!stack2.empty()) {
            result.add(stack2.pop());
        }
        return result;
    }

//    public abstract void insert(ValueType value);
//
//    public abstract void remove(ValueType value);
}

/**
 * 树节点的抽象类
 * @param <TN> 节点具体类型
 * @param <ValueType> 值类型
 */
abstract class TreeNode<TN extends TreeNode<TN, ValueType>, ValueType> {

    protected TN parent;

    protected List<TN> children = new ArrayList<>();

    protected ValueType value;

    public TreeNode(ValueType value) {
        this.value = value;
    }

    public TreeNode(TN parent, ValueType value) {
        this.parent = parent;
        this.value = value;
    }

    public void addChild(TN child) {
        children.add(child);
    }

    public boolean isLeft() {
        return children.isEmpty();
    }

    public void attach(TN parent) {
        detach();

        if (parent == null) {
            return;
        }

        this.parent = parent;
        parent.children.add((TN) this);
    }

    public void detach() {
        if (parent == null) {
            return;
        }

        parent.children.remove(this);
        parent = null;
    }

//    public List<AVLNode> getChildren() {
//        return children;
//    }


    @Override
    public String toString() {
        return value.toString();
    }
}