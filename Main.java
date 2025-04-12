import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.Scanner;

class Main {
  public final static int w = 2000;
  public final static int h = 900;
  public final static int refdepth = 5;
  public final static Color scenebackcolor = new Color(10, 10, 0);
  public final static Point cam = new Point(0, 0, 0);
  public final static Vector d = new Vector(0, 0, 1);
  public final static Vector y = new Vector(0, -1, 0);
  public final static Vector x = new Vector(1, 0, 0);
  public final static Camera camera = new Camera(cam, Main.w, Main.h, d, x, y);
  public final static double variablethatshouldntbehere =
      0.2; // the usage of this variable is unknown
  public final static CI staticlight = new CI(0.2, 0.2, 0.2);
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    sc.useLocale(Locale.US);
    double x, y, z, r;
    Scene scene = new Scene();
    // int n = sc.nextInt();
    // for (int i = 0; i < n; i++) {
    //   Sphere s = Sphere.readCSR9(sc);
    //   scene.addnonLobj(s);
    // }
    // int m = sc.nextInt();
    // for (int i = 0; i < m; i++) {
    //   Light l = Light.read6CI(sc);
    //   scene.addlight(l);
    // }
    BMP bmp = new BMP(Main.w, Main.h, "images/output.bmp");

    // default setup

    Sphere s = new Sphere(0.1, -0.1, 0.7, 0.1, new Color("#FEFE00"), 0.4, 0.5);
    scene.addnonLobj(s);
    s = new Sphere(-1, -0.4, 5, 0.1, new Color("#AA21CC"), 1, 0.1);
    scene.addnonLobj(s);
    s = new Sphere(-0.3, -0.1, 0.4, 0.25, new Color("#402090"), 0.7, 0.2);
    scene.addnonLobj(s);
    s = new Sphere(0, 0, 2, 0.05, new Color("#00EE90"), 1, 0.6);
    scene.addnonLobj(s);
    Sphere s2 = new Sphere(0.3, 0, 8, 3, new Color("#000000"), 0.5, 0.9);
    Lamp l = new Lamp(new Point(2, 4, 1), new CI(0.5, 0.5, 0.3));
    Star lsr = new Star(new Vector(-1, -1, 1), new CI(0.3));
    scene.addlight(lsr);
    Point p = new Point(0.0, -0.50, 0.0);
    Vector norm = new Vector(0, 1, 0);
    scene.addnonLobj(new Plane(p, norm, new Color("#700030"), 0.0, 0.7));
    scene.addnonLobj(s2);
    scene.addlight(l);
    scene.renderin(bmp);
  }

  public static void mainE(String[] args) throws Exception {
    Scanner sc = new Scanner(System.in);
    sc.useLocale(Locale.US);
    BMP bmp = new BMP(Main.w, Main.h, "output.bmp");
    Scene scene = new Scene();
    int n = sc.nextInt();
    for (int i = 0; i < n; i++) {
      Sphere s = Sphere.readCSR9(sc);
      scene.addnonLobj(s);
    }
    int m = sc.nextInt();
    Light l;
    for (int i = 0; i < m; i++) {
      l = Light.read4P(sc);
      scene.addlight(l);
    }
    scene.renderin(bmp);
  }
  public static byte[] i2ba(int data, int n) {
    byte[] result = new byte[n];
    result[0] = (byte)((data & 0x000000FF) >> 0);
    if (n > 1)
      result[1] = (byte)((data & 0x0000FF00) >> 8);
    if (n > 2)
      result[2] = (byte)((data & 0x00FF0000) >> 16);
    if (n > 2)
      result[3] = (byte)((data & 0xFF000000) >> 24);
    return result;
  }
  public static int readint(byte[] b) {
    int x = 0;
    for (int i = (int)b.length - 1; i >= 0; i--) {
      x <<= 8;
      x |= b[i] & 0xFF;
    }
    if (b.length == 1)
      return (byte)x;
    else if (b.length == 2)
      return (short)x;
    else
      return x;
  }
  public static int readbigint(byte[] b) {
    int x = 0;
    Collections.reverse(Arrays.asList(b));
    return readint(b);
  }
  public static int hex2int(String s) {
    int decimal = Integer.parseInt(s, 16);
    return decimal;
  }
  public static String int2hex(int n) {
    String Hex = Integer.toHexString(n & 0xFF);
    return Hex.toUpperCase();
  }
  public static String tocorrectlength(String x) {
    if (x.length() < 2) {
      return "0" + x;
    } else {
      return "" + x;
    }
  }
  public static int wichsphisseen(Sphere[] sphs, Point2D ppp) {
    double xscr = ppp.x;
    double yscr = ppp.y;
    Point p;
    int ans = 0;
    int n = sphs.length;
    double dmin, dcur;
    dmin = 1000000000;
    p = Point.im2vp(xscr, yscr, Main.camera);
    Vector v = new Vector(Main.camera.p, p);
    for (int j = 0; j < n; j++) {
      if (sphs[j].wronglyseen(p)) {
        dcur = sphs[j].disttocrossing(v);
        if (dmin > dcur) {
          ans = j + 1;
          dmin = dcur;
        }
      }
    }
    return ans;
  } // takes Screen Point
}

// return (norm.lengthed(2 * this.scalar(norm) * norm.l)).minus(this);