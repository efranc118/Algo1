package com.company;

public class BottomUpMergeSort {

  int[] sort(int[] arr) {
    int size = 2;
    int[] aux = new int[arr.length];
    System.arraycopy(arr, 0, aux, 0, arr.length);

    int low = 0;
    int high = low + size;
    while(size < arr.length) {
      int mid = low + (high - low) / 2;

      for(int i = low; i <= size; i++) {
        if(aux[low] <= aux[high]) arr[i] = aux[low];
        if(aux[low] > aux[high]) arr[i] = aux[high];
      }


    }


  }


}
