import java.util.Scanner;
public class Point2D {
  double x, y;
  public Point2D(double x, double y) {
    this.x = x;
    this.y = y;
  }
  public String toString() { return this.x + " " + this.y; }

  public static Point2D read2(Scanner sc) {
    double imx, imy;
    imx = sc.nextDouble();
    imy = sc.nextDouble();
    return new Point2D(imx, imy);
  }
}
