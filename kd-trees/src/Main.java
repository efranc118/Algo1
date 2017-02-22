import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdRandom;

public class Main {

  public static void main(String[] args) {
    KdTree pointSet = new KdTree();
    pointSet.insert(new Point2D(.7, .2));
    pointSet.insert(new Point2D(.5, .4));
    pointSet.insert(new Point2D(.2, .3));
    pointSet.insert(new Point2D(.4, .7));
    pointSet.insert(new Point2D(.9, .6));

//    for(int i = 0; i < 10000; i++) {
//      System.out.println("Before: " + pointSet.size() );
//      pointSet.insert(new Point2D(StdRandom.uniform(), StdRandom.uniform()));
//      System.out.println("After: " + pointSet.size());
//    }


    System.out.println(pointSet.contains(new Point2D(.7,.2)));
    System.out.println(pointSet.contains(new Point2D(.4,.7)));
    System.out.println(pointSet.contains(new Point2D(.2,.3)));
    System.out.println(pointSet.contains(new Point2D(.2,.5)));
    Iterable<Point2D> list = pointSet.range(new RectHV(0, 0, 1, 1));
//    for (Point2D point : list) {
//      System.out.println(point.x() + ", " + point.y());
//    }
//    pointSet.draw();
  }
}
