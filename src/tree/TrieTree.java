package tree;

public class TrieTree extends Tree<TrieTree.TrieTreeNode, Character> {

    public static class TrieTreeNode extends TreeNode<TrieTreeNode, Character> {
        public TrieTreeNode(Character value) {
            super(value);
        }
    }

}
