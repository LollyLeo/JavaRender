import java.util.Scanner;

public class Point {
  double x, y, z;
  public Point(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }
  public Point(Vector u) {
    this.x = u.x;
    this.y = u.y;
    this.z = u.z;
  }
  public String toString() { return this.x + " " + this.y + " " + this.z; }
  public boolean isequal(Point p) {
    return (this.x == p.x && this.y == p.y && this.z == p.z);
  }
  public Point sumwith(Vector u) {
    return new Point(this.x + u.x, this.y + u.y, this.z + u.z);
  }
  public static Point im2vp(double imx, double imy, double w, double h,
                            double d) {
    double x = (imx - w / 2) / w;
    double y = ((-imy + h / 2)) / h;
    return new Point(x, y, d).sumwith(new Vector(Main.camera.p));
  }
  public static Point im2vp(double imx, double imy, Camera camera) {
    return camera.im2vp(imx, imy);
  }
  public static Point im2vp(Point2D p2d, double w, double h, double d) {
    return Point.im2vp(p2d.x, p2d.y, w, h, d);
  }
  public double distfrom(Point p) { return new Vector(this, p).l; }
  public static Point read3(Scanner sc) {
    double x, y, z;
    x = sc.nextDouble();
    y = sc.nextDouble();
    z = sc.nextDouble();
    return new Point(x, y, z);
  }
  public Point slightlyMovedTwrds(Point p) {
    return this.slightlyMovedTwrds(new Vector(this, p));
  }
  public Point slightlyMovedTwrds(Vector u) {
    return this.sumwith(u.lengthed(0.0001));
  }
}
