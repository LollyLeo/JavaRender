
import java.util.ArrayList;
import java.util.Scanner;

class Sphere extends Obj {
  double x, y, z, r, l;
  Vector v;
  Point p;
  public Sphere(double x, double y, double z, double r) {
    this.transparency = 1;
    this.reflvl = 0;
    this.typenumber = 20000;
    this.x = x;
    this.y = y;
    this.z = z;
    this.r = r;
    this.p = new Point(this.x, this.y, this.z);
    this.l = Math.sqrt((z * z + y * y + x * x));
    this.v = new Vector(this.x, this.y, this.z);
    this.c = new Color("#FFFFFF");
    this.smooth = 0;
  }
  public Sphere(double x, double y, double z, double r, double smooth) {
    this(x, y, z, r);
    this.smooth = smooth;
  }
  public Sphere(double x, double y, double z, double r, Color c) {
    this(x, y, z, r);
    this.c = c;
  }
  public Sphere(double x, double y, double z, double r, Color c,
                double smooth) {
    this(x, y, z, r, c);
    this.smooth = smooth;
  }
  public Sphere(double x, double y, double z, double r, Color c, double smooth,
                double reflvl) {
    this(x, y, z, r, c, smooth);
    this.reflvl = reflvl;
  }
  public Sphere(Point p, double r) { this(p.x, p.y, p.z, r); }
  public Sphere(Point p, double r, Color c) {
    this(p, r);
    this.c = c;
  }
  public boolean truelyseen(Point ppp, Vector vvv) {
    Sphere s = new Sphere(new Point(new Vector(ppp, this.p)), this.r);
    return s.wronglyseen(new Point(vvv));
  } // whether this is seen from Point p on ray v
  public Point POVonray(Point ppp, Vector vvv) {
    if (!(this.truelyseen(ppp, vvv))) {
      System.out.println("Sphere is not seen on this ray");
    }
    Sphere s = new Sphere(new Point(new Vector(ppp, this.p)), this.r);
    return s.pointofcrossingonray(vvv).sumwith(new Vector(ppp));
  } // May need try/catch
  public Vector getnorm(Point p) { return new Vector(p).minus(this.v); }
  public CI intensity(ArrayList<Light> ls, Point p) {
    CI intens = new CI(0, 0, 0);
    Light l;
    for (int i = 0; i < ls.size(); i++) {
      l = ls.get(i);
      intens = intens.sumwith(this.intensity(l, p));
    }
    return intens;
  }
  public double disttocrossing(Vector u) {
    double h = this.v.vectorp(u) / u.l;
    return this.l * this.v.coswith(u) - Math.sqrt(this.r * this.r - h * h);
  }
  public static Sphere readС7(Scanner sc) {
    double sx, sy, sz, r;
    int cr, cg, cb;
    sx = sc.nextDouble();
    sy = sc.nextDouble();
    sz = sc.nextDouble();
    r = sc.nextDouble();
    cr = sc.nextInt();
    cg = sc.nextInt();
    cb = sc.nextInt();
    return new Sphere(sx, sy, sz, r, new Color(cr, cg, cb));
  }
  public static Sphere readS5(Scanner sc) {
    double sx, sy, sz, r, smooth;
    int cr, cg, cb;
    sx = sc.nextDouble();
    sy = sc.nextDouble();
    sz = sc.nextDouble();
    r = sc.nextDouble();
    smooth = sc.nextDouble();
    return new Sphere(sx, sy, sz, r, smooth);
  }
  public static Sphere read4(Scanner sc) {
    double sx, sy, sz, r;
    sx = sc.nextDouble();
    sy = sc.nextDouble();
    sz = sc.nextDouble();
    r = sc.nextDouble();
    return new Sphere(sx, sy, sz, r);
  }
  public static Sphere readCS8(Scanner sc) {
    double sx, sy, sz, r, smooth;
    int cr, cg, cb;
    sx = sc.nextDouble();
    sy = sc.nextDouble();
    sz = sc.nextDouble();
    r = sc.nextDouble();
    cr = sc.nextInt();
    cg = sc.nextInt();
    cb = sc.nextInt();
    smooth = sc.nextDouble();
    return new Sphere(sx, sy, sz, r, new Color(cr, cg, cb), smooth);
  }
  public static Sphere readCSR9(Scanner sc) {
    double sx, sy, sz, r, smooth, reflvl;
    int cr, cg, cb;
    sx = sc.nextDouble();
    sy = sc.nextDouble();
    sz = sc.nextDouble();
    r = sc.nextDouble();
    cr = sc.nextInt();
    cg = sc.nextInt();
    cb = sc.nextInt();
    smooth = sc.nextDouble();
    reflvl = sc.nextDouble();
    return new Sphere(sx, sy, sz, r, new Color(cr, cg, cb), smooth, reflvl);
  }
  public CI getblinked(Light l, Point p) { return l.blink(this, p); }
  public static boolean isequal(Sphere s1, Sphere s2) {
    if (s1.x == s2.x && s1.y == s2.y && s1.z == s2.z) {
      return true;
    } else {
      return false;
    }
  }
  // bad functions:
  public boolean wronglyseen(Point p) {
    Vector v = new Vector(p);
    double k1 = v.scalar(v);
    double k2 = 2 * this.v.scalar(v);
    double k3 = this.v.scalar(this.v) - this.r * this.r;
    if (k2 * k2 - 4 * k1 * k3 >= 0) {
      return true;
    } else {
      return false;
    }
  } /// DO NOT USE
  public boolean seenonraytowards(Point p) {
    Vector u = new Vector(p);
    double h = this.v.vectorp(u) / u.l;
    if (h <= r) {
      return true;
    } else {
      return false;
    }
  } // takes Vp point
  public Point pointofcrossingonray(Vector u) {
    return new Point(u.lengthed(disttocrossing(u)));
  }
  public Color pixelcolorofp(ArrayList<Light> ls, Point p) {
    return this.c.muled(this.intensity(ls, p).sumwith(Main.staticlight));
  }
}