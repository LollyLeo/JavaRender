import java.util.Arrays;

public class Plane extends Obj {
  double A, B, C, D;
  Vector norm;
  Point p;
  public Plane(Point p, Vector norm) {
    this.transparency = 1;
    this.norm = norm.lengthed(1);
    this.c = new Color(110);
    this.reflvl = 0.5;
    this.typenumber = 5000;
    this.A = norm.x;
    this.B = norm.y;
    this.C = norm.z;
    this.D = -1 * (p.x * A + p.y * B + p.z * C);
    this.p = p;
  }
  public Plane(Point p, Vector norm, Color c) {
    this(p, norm);
    this.c = c;
  }
  public Plane(Point p, Vector n, Color c, double smooth, double r) {
    this(p, n, c);
    this.smooth = smooth;
    this.reflvl = r;
  }
  Point POVonray(Point p, Vector v) {
    double t = -1 * this.distfrom(p) / (A * v.x + B * v.y + C * v.z);
    if (t < 0) {
      System.out.println("Plane Vision warning");
      return p.sumwith(v.muled(10000));
    } else {
      Point pov = p.sumwith(v.muled(t));
      if (this.distfrom(pov) > 0.0001) {
        System.out.println("Wrong POV");
      }
      return pov;
    }
  }
  boolean truelyseen(Point p, Vector v) {
    double t = -1 * this.distfrom(p) / (A * v.x + B * v.y + C * v.z);
    if (t > 0) {
      return true;
    } else {
      return false;
    }
  }
  @Override
  Color pixelcolorofp(ArrayList<Light> ls, Point p) {
    return null;
  }
  CI intensity1(Light l, Point p) {
    return l.shine(p, this.norm).sumwith(l.blink(this, p));
  }
  Vector getnorm(Point p) { return this.norm; }
  double distfrom(Point p) { return p.x * A + p.y * B + p.z * C + D; }
  public static Plane readC9(Scanner sc) {
    Point pp = Point.read3(sc);
    Vector vv = Vector.read3(sc);
    Color cl = Color.read3(sc);
    return new Plane(pp, vv, cl);
  }
  public static Plane readCSR11(Scanner sc) {
    Point pp = Point.read3(sc);
    Vector vv = Vector.read3(sc);
    Color cl = Color.read3(sc);
    double s = sc.nextDouble();
    double r = sc.nextDouble();
    return new Plane(pp, vv, cl, s, r);
  }
}
