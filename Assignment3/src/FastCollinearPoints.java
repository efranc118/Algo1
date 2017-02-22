import edu.princeton.cs.algs4.Insertion;
import edu.princeton.cs.algs4.Merge;

public class FastCollinearPoints {

  private LineSegment[] segments;
  private int segmentCount;


  public FastCollinearPoints(Point[] points) {
    for (int i = 0; i < points.length; i++) {
      for (int j = i + 1; j < points.length; j++) {
        if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();
      }
    }
    segments = new LineSegment[0];
    Merge.sort(points);
    Point[] lineSegmentEndPoints = new Point[points.length * 2];
    Double[] lineSegmentSlopes = new Double[points.length * 2];
    for (int i = 0; i < points.length; i++) {
      Double[] slopes = new Double[points.length - (i + 1)];
      Point[] slopeOrdered = new Point[points.length - (i + 1)];
      Point startingPoint = points[i];
      int slopeCounter = 0;
      for (int j = i + 1; j < points.length; j++) {
        slopeOrdered[slopeCounter] = points[j];
        slopes[slopeCounter++] = startingPoint.slopeTo(points[j]);
      }
      Merge.sort(slopes);
      Insertion.sort(slopeOrdered, points[i].slopeOrder());
      int collinearPoints = 1;
      for (int j = 0; j < slopes.length - 1; j++) {
        if (slopes[j].equals(slopes[j + 1])) {
          collinearPoints++;
        } else if (collinearPoints >= 3) {
          Point endpoint = slopeOrdered[j];
          Double slope = slopes[j];
          boolean alreadyProcessed = false;
          for (int k = 0; k < segmentCount; k++) {
            if (lineSegmentEndPoints[k].compareTo(endpoint) == 0 && lineSegmentSlopes[k].equals(slope)) alreadyProcessed = true;
          }
          if (!alreadyProcessed) {
            lineSegmentEndPoints[segmentCount] = endpoint;
            lineSegmentSlopes[segmentCount] = slope;
            createLineSegments(startingPoint, endpoint);
          }
          collinearPoints = 1;
        } else collinearPoints = 1;
      }
      if (collinearPoints >= 3) {
        Point endpoint = slopeOrdered[slopeOrdered.length - 1];
        boolean alreadyProcessed = false;
        for (int k = 0; k < segmentCount; k++) {
          if (lineSegmentEndPoints[k].compareTo(endpoint) == 0 && lineSegmentSlopes[k].equals(startingPoint.slopeTo(endpoint)))
            alreadyProcessed = true;
        }
        if (!alreadyProcessed) {
          lineSegmentEndPoints[segmentCount] = endpoint;
          lineSegmentSlopes[segmentCount] = slopes[slopes.length - 1];
          createLineSegments(startingPoint, slopeOrdered[slopeOrdered.length - 1]);
        }
      }

    }

  }

  private void createLineSegments(Point startingPoint, Point endingPoint) {
    for (int i = 0; i < segmentCount; i++) {

    }
    addSegments(new LineSegment(startingPoint, endingPoint));
  }

  private void addSegments(LineSegment lineSegment) {
    if (segmentCount == segments.length) {
      LineSegment[] temp = new LineSegment[segments.length + 1];
      System.arraycopy(segments, 0, temp, 0, segments.length);
      segments = temp;
    }
    segments[segmentCount++] = lineSegment;
  }

  public int numberOfSegments() {
    return segmentCount;
  }

  public LineSegment[] segments() {
    return segments;
  }
//  public Point[] sort(Point[] arr, int low, int high) {
//    if (high <= low) return arr;
//    StdRandom.shuffle(arr, low, high);
//    Point pivot = arr[low];
//    int i = low + 1;
//    int j = high;
//    while (i < j) {
//
//      while (arr[i].compareTo(pivot) < 0) i++;
//      while (arr[j].compareTo(pivot) > 0) j--;
//      exchange(arr, i, j);
//    }
//    exchange(arr, low, j);
//    arr = sort(arr, low, j);
//    arr = sort(arr, i, high);
//    return arr;
//  }
//
//
//  private Point[] exchange(Point[] arr, int i, int j) {
//    Point temp = arr[i];
//    arr[i] = arr[j];
//    arr[j] = temp;
//    return arr;
//  }

}
