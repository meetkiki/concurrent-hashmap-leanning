package com.meetkiki.algorithm;//根据一棵树的中序遍历与后序遍历构造二叉树。
//
// 注意: 
//你可以假设树中没有重复的元素。 
//
// 例如，给出 
//
// 中序遍历 inorder = [9,3,15,20,7]
//后序遍历 postorder = [9,15,7,20,3] 
//
// 返回如下的二叉树： 
//
//     3
//   / \
//  9  20
//    /  \
//   15   7
// 
// Related Topics 树 数组 哈希表 分治 二叉树 👍 584 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode() {}
 * TreeNode(int val) { this.val = val; }
 * TreeNode(int val, TreeNode left, TreeNode right) {
 * this.val = val;
 * this.left = left;
 * this.right = right;
 * }
 * }
 */
class buildTree {

    public static void main(String[] args) {
        final int[] inorder = {9, 3, 15, 20, 7};
        final int[] postorder = {9, 15, 7, 20, 3};
        final TreeNode node = new buildTree().buildTree(inorder, postorder);
        System.out.println(node);
    }

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        return buildTree(0, inorder.length - 1, 0, postorder.length - 1, inorder, postorder);
    }

    private TreeNode buildTree(int leftin, int rightin, int leftpost, int rightpost, int[] inorder, int[] postorder) {
        if (leftin > rightin) {
            return null;
        }
        int val = postorder[rightpost];
        TreeNode root = new TreeNode(val);
        int currentIn = leftin;
        while (currentIn < rightin && inorder[currentIn] != postorder[rightpost]) {
            currentIn++;
        }
        // [9,3,15,20,7]
        // [9,15,7,20,3]
        // 左子树的长度
        int left = currentIn - leftin;
        root.left = buildTree(leftin, currentIn - 1, leftpost, leftpost + left - 1, inorder, postorder);
        root.right = buildTree(currentIn + 1, rightin, leftpost + left, rightpost - 1, inorder, postorder);
        return root;
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
