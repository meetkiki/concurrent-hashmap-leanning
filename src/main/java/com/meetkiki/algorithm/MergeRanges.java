package com.meetkiki.algorithm;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;


public class MergeRanges {

    public static void main(String[] args) {
        TreeSet<Range> treeSet = new TreeSet<>();
        treeSet.add(new Range(1, 4));
        treeSet.add(new Range(2, 5));
        treeSet.add(new Range(3, 8));
        treeSet.add(new Range(8, 9));
        treeSet.add(new Range(8, 9));
        treeSet.add(new Range(9, 11));
        treeSet.add(new Range(10, 12));
        treeSet.add(new Range(3, 5));
        treeSet.add(new Range(9, 11));
        treeSet.add(new Range(111, 123));
        treeSet.add(new Range(123, 2222));
        treeSet.add(new Range(160, 1231));
        treeSet.add(new Range(1610, 3231));
        System.out.println(new MergeRanges().mergeRanges(treeSet));
        System.out.println(new MergeRanges().mergeRanges(new TreeSet<>()));
    }

    public List<Range> mergeRanges(TreeSet<Range> ranges) {
        final List<Range> res = new ArrayList<>();
        if (ranges == null || ranges.isEmpty()){
            return res;
        }
        final List<Range> rangeList = new ArrayList<>(ranges);
        int index = 0;
        Range last = null;
        for (int i = 0; i < rangeList.size() - 1; i++) {
            Range cur = last == null ? rangeList.get(i) : last;
            Range next = rangeList.get(i + 1);
            if (cur.right <= next.left) {
                index++;
                Range merge = rangeList.get(index);
                merge.left = next.left;
                merge.right = next.right;
                last = merge;
            } else {
                Range merge = rangeList.get(index);
                merge.left = Math.min(cur.left, next.left);
                merge.right = Math.max(cur.right, next.right);
                last = merge;
            }
        }
        for (int i = 0; i <= index; i++) {
            res.add(rangeList.get(i));
        }
        return res;
    }

    public static class Range implements Comparable<Range> {
        private int left;
        private int right;

        public Range(int left, int right) {
            if (left >= right){
                throw new IllegalArgumentException();
            }
            this.left = left;
            this.right = right;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Range range = (Range) o;
            return left == range.left && right == range.right;
        }

        @Override
        public int hashCode() {
            return Objects.hash(left, right);
        }

        @Override
        public String toString() {
            return "Range{" +
                    "left=" + left +
                    ", right=" + right +
                    '}';
        }

        @Override
        public int compareTo(Range other) {
            return this.left - other.left;
        }
    }


}

