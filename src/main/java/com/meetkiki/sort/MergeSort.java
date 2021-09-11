package com.meetkiki.sort;

import java.util.Arrays;

public class MergeSort {

    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 10, 2, 4, 5, 18, 3};
        int[] dest = Arrays.copyOf(arr, arr.length);
        new MergeSort().sort(arr, dest, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    private void sort(int[] arr, int[] dest, int left, int right) {
        if (left >= right) {
            return;
        }
        int mid = (left + right) / 2;
        sort(arr, dest, left, mid);
        sort(arr, dest, mid + 1, right);
        int k = left, i = left, j = mid + 1;
        while (i <= mid && j <= right) {
            if (arr[i] < arr[j]) {
                dest[k++] = arr[i++];
            } else {
                dest[k++] = arr[j++];
            }
        }
        while (i <= mid) {
            dest[k++] = arr[i++];
        }
        while (j <= right) {
            dest[k++] = arr[j++];
        }
        if (right + 1 - left >= 0) {
            System.arraycopy(dest, left, arr, left, right + 1 - left);
        }
    }


}
