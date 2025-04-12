import java.util.ArrayList;
import java.util.Scanner;

public abstract class Light extends Obj {
  int typenumber = 10000;
  CI ci;
  Point p;
  abstract CI shine(Point p, Vector n);
  public Color pixelcolorofp(ArrayList<Light> ls, Point p) {
    return new Color("#000000");
  }
  public static Light read4P(Scanner sc) {
    double x, y, z, power;
    Light l;
    String type = sc.next();
    x = sc.nextDouble();
    y = sc.nextDouble();
    z = sc.nextDouble();
    power = sc.nextDouble();
    if (type.charAt(0) == 'd') {
      l = new Star(new Vector(x, y, z), power);
    } else {
      l = new Lamp(new Point(x, y, z), power);
    }
    return l;
  }
  public static Light read3(Scanner sc) {
    double x, y, z, power;
    Light l;
    String type = sc.next();
    x = sc.nextDouble();
    y = sc.nextDouble();
    z = sc.nextDouble();
    power = 1;
    if (type.charAt(0) == 'd') {
      l = new Star(new Vector(x, y, z), power);
    } else {
      l = new Lamp(new Point(x, y, z), power);
    }
    return l;
  }
  public static Light read6CI(Scanner sc) {
    double x, y, z, power;
    int cr, cg, cb;
    Light l;
    String type = sc.next();
    x = sc.nextDouble();
    y = sc.nextDouble();
    z = sc.nextDouble();
    CI ci = CI.read3(sc);
    if (type.charAt(0) == 'd') {
      l = new Star(new Vector(x, y, z), ci);
    } else {
      l = new Lamp(new Point(x, y, z), ci);
    }
    return l;
  }
  abstract CI blink(Obj o, Point p);
}
