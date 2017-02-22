import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {


  private WeightedQuickUnionUF weightedQuickUnionUF;
  private WeightedQuickUnionUF bottomQuickUnionUF;
  private boolean[][] grid;
  private int n;
  private int virtual;
  private boolean isPercolated;

  public Percolation(int n) {
    if (n <= 0) throw new IllegalArgumentException();
    this.n = n;
    this.virtual = n * n;

    weightedQuickUnionUF = new WeightedQuickUnionUF(n * n + 1);
    for (int i = 0; i < n; i++) {
      weightedQuickUnionUF.union(n * n, i);
    }
    bottomQuickUnionUF = new WeightedQuickUnionUF(n * n + 1);
    for (int i = (n - 1) * n; i < n * n; i++) {
      bottomQuickUnionUF.union(n * n, i);
    }

    grid = new boolean[n][n];
  }

  public void open(int i, int j) {
    i = i - 1;
    j = j - 1;
    checkValues(i, j);
    grid[i][j] = true;
    if (i - 1 >= 0 && grid[i - 1][j]) {
      weightedQuickUnionUF.union(getPosition(i - 1, j), getPosition(i, j));
      bottomQuickUnionUF.union(getPosition(i - 1, j), getPosition(i, j));
    }
    if (i + 1 < n && grid[i + 1][j]) {
      weightedQuickUnionUF.union(getPosition(i + 1, j), getPosition(i, j));
      bottomQuickUnionUF.union(getPosition(i + 1, j), getPosition(i, j));

    }
    if (j - 1 >= 0 && grid[i][j - 1]) {
      weightedQuickUnionUF.union(getPosition(i, j - 1), getPosition(i, j));
      bottomQuickUnionUF.union(getPosition(i, j - 1), getPosition(i, j));
    }
    if (j + 1 < n && grid[i][j + 1]) {
      weightedQuickUnionUF.union(getPosition(i, j + 1), getPosition(i, j));
      bottomQuickUnionUF.union(getPosition(i, j + 1), getPosition(i, j));
    }

    if (!isPercolated) {
      if (bottomQuickUnionUF.connected(virtual, getPosition(i, j)) && weightedQuickUnionUF.connected(virtual, getPosition(i, j)))
        isPercolated = true;
    }
  }

  public boolean isOpen(int i, int j) {
    i = i - 1;
    j = j - 1;
    checkValues(i, j);
    return (grid[i][j]);
  }

  public boolean isFull(int i, int j) {
    i = i - 1;
    j = j - 1;
    checkValues(i, j);
    return (weightedQuickUnionUF.connected(virtual, (i * n) + j) && grid[i][j]);
  }

  public boolean percolates() {
    return isPercolated;
  }

  private void checkValues(int i, int j) {
    if (i < 0 || j < 0 || i >= n || j >= n) {
      throw new IndexOutOfBoundsException();
    }
  }

  private int getPosition(int i, int j) {
    return i * n + j;
  }


  public static void main(String[] args) {
    Percolation test = new Percolation(1);
//    printIsFull(test);
//    System.out.println(test.isOpen(1,1));
//    System.out.println(test.isOpen(1,1));
//    test.open(1,1);
//    System.out.println(test.isOpen(1,1));
//    System.out.println(test.isOpen(1,1));
    // test.open(2, 1);
//    printIsFull(test);
    // test.open(3, 1);
//    printIsFull(test);
//
    // test.open(1, 1);
//    printIsFull(test);
//    //test.open(1, 3);
//    printIsFull(test);
//    //test.open(2, 3);
//    printIsFull(test);
//    test.open(3, 3);
//    printIsFull(test);
    //test.open(3, 2);

    //printIsFull(test);


    System.out.print(test.percolates());


  }

  static private void printIsFull(Percolation test) {
    System.out.print(test.isFull(1, 1));
    System.out.print(test.isFull(1, 2));
    System.out.println(test.isFull(1, 3));
    System.out.print(test.isFull(2, 1));
    System.out.print(test.isFull(2, 2));
    System.out.println(test.isFull(2, 3));
    System.out.print(test.isFull(3, 1));
    System.out.print(test.isFull(3, 2));
    System.out.println(test.isFull(3, 3));
    System.out.println();
  }


}
