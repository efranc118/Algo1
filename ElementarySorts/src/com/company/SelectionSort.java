package com.company;

public class SelectionSort {


  static int[] sort(int[] arr) {
    for (int i = 0; i < arr.length; i++) {
      int minLocation = i;
      for (int j = i + 1; j < arr.length; j++) {
        if (arr[j] < arr[minLocation]) {
          minLocation = j;
        }
      }
      arrSwap(arr, i, minLocation);
    }
    return arr;
  }

  static void arrSwap(int[] arr, int i, int j) {
    int value = arr[j];
    arr[j] = arr[i];
    arr[i] = value;
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

    arr = sort(arr);
    for (int i = 0; i < arr.length; i++) {
      System.out.print(arr[i] + " ");
    }
  }


}
