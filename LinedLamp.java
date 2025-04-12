import java.util.ArrayList;
import java.util.Scanner;

public class LinedLamp extends Light {
  Point A, B;
  CI ci;
  int k;
  public static final int kk = 100;
  ArrayList<Lamp> lights;
  public LinedLamp(Point A, Point B, CI ci) {
    this.k = LinedLamp.kk;
    this.A = A;
    this.B = B;
    this.ci = ci;
    Vector v = new Vector(A, B);
    this.lights = new ArrayList();
    for (int i = 0; i < k; i++) {
      CI ccii = ci.muled((double)1 / k);
      lights.add(new Lamp(A.sumwith(v.muled((double)i / k)), ccii));
    }
  }
  boolean truelyseen(Point p, Vector v) {
    boolean boo = false;
    for (int i = 0; i < k; i++) {
      if (this.lights.get(i).truelyseen(p, v)) {
        boo = true;
        break;
      }
    }
    return boo;
  }
  Point POVonray(Point p, Vector v) { return null; }
  Vector getnorm(Point p) { return null; }
  CI shine(Point p, Vector n) {
    CI sumshine = new CI(0);
    for (int i = 0; i < k; i++) {
      sumshine.sum(this.lights.get(i).shine(p, n));
    }
    return sumshine;
  }
  CI blink(Obj o, Point p) {
    CI sumblink = new CI(0);
    for (int i = 0; i < k; i++) {
      sumblink.sum(this.lights.get(i).blink(o, p));
    }
    return sumblink;
  }
  public static LinedLamp readP7(Scanner sc) {
    Point a = Point.read3(sc);
    Point b = Point.read3(sc);
    double p = sc.nextDouble();
    return new LinedLamp(a, b, new CI(p));
  }
}
