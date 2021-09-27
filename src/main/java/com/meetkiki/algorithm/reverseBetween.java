package com.meetkiki.algorithm;

//给你单链表的头指针 head 和两个整数 left 和 right ，其中 left <= right 。请你反转从位置 left 到位置 right 的链
//表节点，返回 反转后的链表 。
// 
//
// 示例 1： 
//
// 
//输入：head = [1,2,3,4,5], left = 2, right = 4
//输出：[1,4,3,2,5]
// 
//
// 示例 2： 
//
// 
//输入：head = [5], left = 1, right = 1
//输出：[5]
// 
//
// 
//
// 提示： 
//
// 
// 链表中节点数目为 n 
// 1 <= n <= 500 
// -500 <= Node.val <= 500 
// 1 <= left <= right <= n 
// 
//
// 
//
// 进阶： 你可以使用一趟扫描完成反转吗？ 
// Related Topics 链表 👍 1036 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode() {}
 * ListNode(int val) { this.val = val; }
 * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class reverseBetween {
    public void reverseBetween(ListNode head, int left, int right) {
        if (left == right) {
            return;
        }
        ListNode newHead = new ListNode(0);
        newHead.next = head;
        ListNode pre = newHead;
        int preCount = left - 1, curCount = 0;
        while (pre != null && curCount != preCount) {
            pre = pre.next;
            curCount++;
        }
        if (pre == null || pre.next == null) {
            return;
        }
        // 1,2,3,4,5
        //   2,  4
        // 1,4,3,2,5
        ListNode last = pre, cur = pre.next;
        while (curCount != right - 1) {
            ListNode temp = cur.next;
            cur.next = temp.next;
            temp.next = last.next;
            last.next = temp;
            curCount++;
        }
    }


    public static void main(String[] args) {
        final int[] ints = {1, 2, 3, 4, 5};
        ListNode head = new ListNode(), last = head;
        for (int anInt : ints) {
            ListNode node = new ListNode(anInt);
            last.next = node;
            last = node;
        }
        new reverseBetween().reverseBetween(head.next, 2, 4);
    }


    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

}
//leetcode submit region end(Prohibit modification and deletion)
