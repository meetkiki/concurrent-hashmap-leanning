package com.meetkiki.sort;

import java.util.Arrays;

public class QuickSort {


    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 10, 2, 4, 5, 18, 3};
        new QuickSort().sort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    private void sort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int p = partition(arr, left, right);
        sort(arr, left, p - 1);
        sort(arr, p + 1, right);
    }

    private int partition(int[] arr, int left, int right) {
        int partition = left - 1;
        for (int i = left; i < right; i++) {
            if (arr[right] > arr[i]) {
                swap(arr, ++partition , i);
            }
        }
        swap(arr, ++partition, right);
        return partition;
    }

    private void swap(int[] arr, int l, int r) {
        if (l == r){
            return;
        }
        int temp = arr[l];
        arr[l] = arr[r];
        arr[r] = temp;
    }

}
