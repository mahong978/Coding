package tree;

import javafx.scene.chart.ValueAxis;

/**
 * 红黑树
 *
 * 性质1：每个节点要么是黑色，要么是红色。
 * 性质2：根节点是黑色。
 * 性质3：每个叶子节点（NIL）是黑色。
 * 性质4：每个红色结点的两个子结点一定都是黑色。
 * 性质5：任意一结点到每个叶子结点的路径都包含数量相同的黑结点。
 *
 * @param <ValueType>
 */
public class RBTree<ValueType extends Comparable<ValueType>>
        extends AbstractBinarySearchTree<RBTree.RBTreeNode<ValueType>, ValueType> {
    
    public static class RBTreeNode<ValueType extends Comparable<ValueType>> extends AbstractBinarySearchTree.AbstractBinarySearchNode<RBTreeNode<ValueType>, ValueType> {
        private boolean isRed;
        public RBTreeNode(ValueType value, boolean isRed) {
            super(value);
            this.isRed = isRed;
        }
        public boolean isRed() {
            return isRed;
        }
        public boolean isBlack() {
            return !isRed;
        }
    }

    private static class NILNode<ValueType extends Comparable<ValueType>> extends RBTreeNode<ValueType> {
        public NILNode() {
            super(null, false);
        }
    }

    public void insert(ValueType key) {
        RBTreeNode<ValueType> node = new RBTreeNode<>(key, true);

        // 空树
        if (root == null) {
            // 性质1，根节点必须是黑色
            node.isRed = false;
            root = node;
            return;
        }

        RBTreeNode<ValueType> currNode = root;
        RBTreeNode<ValueType> parent = null;
        while (currNode != null) {
            parent = currNode;

            int compare = node.compareTo(currNode);
            if (compare == 0) {
                return;
            } else if (compare < 0) {
                currNode = currNode.left();
            } else {
                currNode = currNode.right();
            }
        }

        boolean isLeftChild = node.compareTo(parent) < 0;
        if (isLeftChild) {
            parent.setLeft(node);
        } else {
            parent.setRight(node);
        }
        node.parent = parent;

        maintainBalanceAfterInsert(node);
    }

    public void delete(ValueType key) {
        if (root == null) {
            return;
        }

        // 查找目标节点，如果找不到直接返回
        RBTreeNode<ValueType> targetNode = search(key);
        if (targetNode == null) {
            return;
        }

        // 被删除节点
        // 如果目标节点只有一个子节点或者没有子节点，则目标节点是被删除节点
        // 否则用后继节点
        RBTreeNode<ValueType> deletedNode;
        if (targetNode.left() == null || targetNode.right() == null) {
            deletedNode = targetNode;
        } else {
            deletedNode = targetNode.right();
            while (deletedNode.left() != null) {
                deletedNode = deletedNode.left();
            }
        }

        // 更新目标节点的值为被删除节点的值
        if (targetNode != deletedNode) {
            targetNode.value = deletedNode.value;
        }

        // 替代节点
        // 根据上面的规则可以确定，替代节点最多只有一个子节点
        RBTreeNode<ValueType> replacingNode;
        if (deletedNode.left() != null) {
            replacingNode = deletedNode.left();
        } else {
            replacingNode = deletedNode.right();
        }

        // 替代节点替代被删除节点
        RBTreeNode<ValueType> parentNodeOfDeletedNode = deletedNode.parent;
        if (parentNodeOfDeletedNode == null) {
            root = replacingNode;
        } else {
            if (parentNodeOfDeletedNode.left() == deletedNode) {
                parentNodeOfDeletedNode.setLeft(replacingNode);
            } else {
                parentNodeOfDeletedNode.setRight(replacingNode);
            }
        }

        // 如果无替代节点，即被删除节点无子节点，删除完就行了
        if (replacingNode != null) {
            replacingNode.parent = parentNodeOfDeletedNode;
            // 如果替代节点是黑色的，需要保持平衡
            if (!replacingNode.isRed) {
                maintainBalanceAfterDelete(replacingNode);
            }
        }
    }

    private void maintainBalanceAfterInsert(RBTreeNode<ValueType> node) {
        RBTreeNode<ValueType> parent = node.parent;
        // 如果父节点是黑色的，不影响平衡
        if (!parent.isRed) {
            return;
        }

        // 如果父节点是红色的，违反性质4，需要调整
        RBTreeNode<ValueType> ancestor = parent.parent;
        RBTreeNode<ValueType> parentSibling = parent == ancestor.left() ?
                ancestor.right() :
                ancestor.left();
        if (parentSibling != null && parentSibling.isRed) {
            // 如果父节点的兄弟节点也是红色，则将BRR改成RBR
            ancestor.isRed = true;
            parent.isRed = false;
            parentSibling.isRed = false;
        } else {
            // 父节点的兄弟节点不存在或者是黑色的
            boolean isParentLeftChild = parent == ancestor.left();
            boolean isLeftChild = node == parent.left();
            if (isParentLeftChild) {
                // 如果是父节点的左节点
                if (isLeftChild) {
                    ancestor.isRed = true;
                    parent.isRed = false;
                    rotateRight(ancestor);
                } else {
                    rotateLeft(parent);
                    maintainBalanceAfterInsert(parent);
                }
            } else {
                // 如果是父节点的右节点
                if (isLeftChild) {
                    ancestor.isRed = true;
                    parent.isRed = false;
                    rotateLeft(ancestor);
                } else {
                    rotateRight(parent);
                    maintainBalanceAfterInsert(parent);
                }
            }
        }
    }

    private void maintainBalanceAfterDelete(RBTreeNode<ValueType> node) {
        while (node != root && !node.isRed) {
            // 一直循环到非黑
            RBTreeNode<ValueType> parent = node.parent;
            boolean isLeftChild = node == parent.left();
            RBTreeNode<ValueType> sibling = isLeftChild ? parent.right() : parent.left();
            if (isLeftChild) {
                // 如果节点是父节点的左子节点
                if (sibling.isRed) {
                    // 如果兄弟节点是红色
                    // 兄弟节点设为黑色
                    // 父节点设为红色
                    // 父节点左旋
                    // 更新节点的兄弟节点
                    sibling.isRed = false;
                    parent.isRed = true;
                    rotateLeft(parent);
                    sibling = node.parent.right();
                }
                if (sibling.left().isBlack() && sibling.right().isBlack()) {
                    // 如果兄弟节点的子节点都是黑色
                    // 兄弟节点设为红色
                    // 基于父节点调整
                    sibling.isRed = true;
                    node = parent;
                } else {
                    if (sibling.left().isRed() && sibling.right().isBlack()) {
                        // 如果兄弟节点的左子节点是红色，右子节点是黑色
                        // 兄弟节点设为红色
                        // 兄弟节点的左子节点设为黑色
                        // 兄弟节点右旋
                        sibling.isRed = true;
                        sibling.left().isRed = false;
                        rotateRight(sibling);
                    }
                    if (sibling.right().isRed()) {
                        // 如果兄弟节点的右子节点是红色
                        // 兄弟节点设为父节点的颜色
                        // 父节点设为黑色
                        // 兄弟节点的右子节点设为黑色
                        // 父节点左旋
                        sibling.isRed = parent.isRed;
                        parent.isRed = false;
                        sibling.right().isRed = false;
                        rotateLeft(parent);
                    }
                }
            } else {
                // 跟前面是对称的
                if (sibling.isRed) {
                    sibling.isRed = false;
                    parent.isRed = true;
                    rotateRight(parent);
                    sibling = node.parent.left();
                }
                if (sibling.left().isBlack() && sibling.right().isBlack()) {
                    sibling.isRed = true;
                    node = parent;
                } else {
                    if (sibling.left().isBlack() && sibling.right().isRed()) {
                        sibling.isRed = true;
                        sibling.right().isRed = false;
                        rotateLeft(sibling);
                    }
                    if (sibling.left().isRed()) {
                        sibling.isRed = parent.isRed;
                        parent.isRed = false;
                        sibling.left().isRed = false;
                        rotateRight(parent);
                    }
                }
            }
        }

        // 根节点一定是黑色的
        node.isRed = false;
    }
}
