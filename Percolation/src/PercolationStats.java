import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {

  private double[] trialData;

  public PercolationStats(int n, int numTrials) {
    if (n <= 0 || numTrials <= 0) {
      throw new IllegalArgumentException("The values for n and trials must be greater than 0");
    }
    trialData = new double[numTrials];
    for (int i = 0; i < numTrials; i++) {
      int percolationThreshold = 0;
      Percolation iteration = new Percolation(n);
      while (!iteration.percolates()) {
        int j = StdRandom.uniform(n) + 1;
        int k = StdRandom.uniform(n) + 1;
        if (!iteration.isOpen(j, k)) {
          iteration.open(j, k);
          percolationThreshold++;
        }
      }
      trialData[i] = percolationThreshold / Math.pow(n, 2);
    }
  }


  public double mean() {
    return StdStats.mean(trialData);
  }

  public double stddev() {
    return StdStats.stddev(trialData);
  }

  public double confidenceLo() {
    return mean() - (1.96 * StdStats.stddev(trialData)) / Math.sqrt(trialData.length);
  }

  public double confidenceHi() {
    return mean() + (1.96 * StdStats.stddev(trialData)) / Math.sqrt(trialData.length);
  }


  public static void main(String[] args) {
    int gridSize = StdIn.readInt();
    int numTrials = StdIn.readInt();

    PercolationStats percolationStats = new PercolationStats(gridSize, numTrials);

    System.out.println("mean \t= " + percolationStats.mean());
    System.out.println("stddev\t = " + percolationStats.stddev());
    System.out.println("95% confidence Interval\t = " +
      percolationStats.confidenceLo() + ", " +
      percolationStats.confidenceHi());
  }


}
