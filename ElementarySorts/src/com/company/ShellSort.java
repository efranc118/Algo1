package com.company;

public class ShellSort {

  static int[] sort(int[] arr) {


    int h = (arr.length - 1) / 3;
    while( h >= 1) {
      for (int i = h; i < arr.length; i++) {
        for (int j = i; j >= h; j--) {
          while (arr[j] < arr[j - h]) {
            arrSwap(arr, j, j-h);
          }
        }
      }
      h = h / 3;
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
