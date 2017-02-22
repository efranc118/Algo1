import edu.princeton.cs.algs4.Quick;

public class BruteCollinearPoints {

  final private Point[] points;
  final private LineSegment[] segments;
  final private int segmentCount;

  public BruteCollinearPoints(final Point[] points) {
    if (points == null) throw new NullPointerException();
    for (int i = 0; i < points.length; i++) {
      for (int j = i + 1; j < points.length; j++) {
        if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();
      }
    }
    for (int i = 0; i < points.length; i++) {
      if (points[i] == null) throw new NullPointerException();
    }
    LineSegment[] tempLineSegments = new LineSegment[points.length];
    int tempSegmentCount = 0;
    Point[] tempPoints = new Point[points.length];
    System.arraycopy(points, 0, tempPoints, 0, points.length);
    Quick.sort(tempPoints);
    this.points = points;
    for (int i = 0; i < tempPoints.length - 3; i++) {
      for (int j = i + 1; j < tempPoints.length - 2; j++) {
        for (int k = j + 1; k < tempPoints.length - 1; k++) {
          for (int l = k + 1; l < tempPoints.length; l++) {
            if (tempPoints[i].slopeTo(tempPoints[j]) == tempPoints[i].slopeTo(tempPoints[k]) && tempPoints[i].slopeTo(tempPoints[j]) == tempPoints[i].slopeTo(tempPoints[l])) {
              tempLineSegments[tempSegmentCount++] = new LineSegment(tempPoints[i], tempPoints[l]);
              if (tempPoints[j].compareTo(tempPoints[k]) > 1 && tempPoints[j].compareTo(tempPoints[l]) > 1)
                tempLineSegments[tempSegmentCount++] = new LineSegment(tempPoints[i], tempPoints[l]);
              if (tempPoints[j].compareTo(tempPoints[k]) > 1 && tempPoints[j].compareTo(tempPoints[l]) < 0)
                tempLineSegments[tempSegmentCount++] = new LineSegment(tempPoints[i], tempPoints[l]);
              if (tempPoints[j].compareTo(tempPoints[k]) < 1 && tempPoints[k].compareTo(tempPoints[l]) > 0)
                tempLineSegments[tempSegmentCount++] = new LineSegment(tempPoints[i], tempPoints[l]);
            }
          }
        }
      }
    }

    segmentCount = tempSegmentCount;
    segments = new LineSegment[tempSegmentCount];
    for (int i = 0; i < tempSegmentCount; i++) {
      segments[i] = tempLineSegments[i];
    }
  }

//  private void createLineSegments(int i, int j, int k, int l) {
//    if (points[j].compareTo(points[k]) > 1 && points[j].compareTo(points[l]) > 1)
//      addSegments(new LineSegment(points[i], points[j]));
//    if (points[j].compareTo(points[k]) > 1 && points[j].compareTo(points[l]) < 0)
//      addSegments(new LineSegment(points[i], points[l]));
//    if (points[j].compareTo(points[k]) < 1 && points[k].compareTo(points[l]) > 0)
//      addSegments(new LineSegment(points[i], points[k]));
//  }
//
//  private void addSegments(LineSegment lineSegment) {
//    if (segmentCount == segments.length) {
//      LineSegment[] temp = new LineSegment[segments.length + 1];
//      System.arraycopy(segments, 0, temp, 0, segments.length);
//      segments = temp;
//    }
//    segments[segmentCount++] = lineSegment;
//  }


  public int numberOfSegments() {
    return segmentCount;
  }

  public LineSegment[] segments() {
    return segments;
  }
}
