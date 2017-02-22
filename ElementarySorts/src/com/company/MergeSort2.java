package com.company;

public class MergeSort2 {


  public static int[] sort(int[] arr, int low, int high) {
    if (high <= low) return arr;
    int mid = low + (high - low) / 2;
    arr = sort(arr, low, mid);
    arr = sort(arr, mid + 1, high);
    arr = merge(arr, low, mid, high);
    return arr;
  }


  public static int[] merge(int[] arr, int low, int mid, int high) {
    int[] aux = new int[arr.length];

    System.arraycopy(arr, 0, aux, 0, arr.length);

    int k = low;
    int i = low;
    int j = mid + 1;

    for (; k <= high; k++) {
      if (i > mid) arr[k] = aux[j++];
      else if (j > high) arr[k] = aux[i++];
      else if (aux[i] <= aux[j]) arr[k] = aux[i++];
      else if (aux[i] > aux[j]) arr[k] = aux[j++];
    }
    return arr;
  }


  public static void main(String[] args) {
    int[] arr = new int[10];
    arr[0] = 10;
    arr[1] = 5;
    arr[2] = 14;
    arr[3] = 6;
    arr[4] = 20;
    arr[5] = 1;
    arr[6] = 50;
    arr[7] = 13;
    arr[8] = 11;
    arr[9] = 16;

    arr = sort(arr, 0, arr.length - 1);
    for (int i = 0; i < arr.length; i++) {
      System.out.print(arr[i] + " ");
    }
  }


}
