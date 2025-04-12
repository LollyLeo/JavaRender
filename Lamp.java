public class Lamp extends Light {
  public Lamp(Point p, double power) {
    this.p = p;
    this.ci = new CI(power, power, power);
    this.transparency = 0;
  }
  public Lamp(Point p, CI ci) {
    this.p = p;
    this.ci = ci;
  }
  public Lamp(Point p, double power, int[] colorshines) {
    this(p, power);
    this.ci = new CI(colorshines[0], colorshines[1], colorshines[2]);
  }
  public CI shine(Point p, Vector n) {
    if (p.isequal(this.p)) {
      return this.ci;
    } else {
      double r = Math.max(n.coswith(new Vector(p, this.p)) * this.ci.r, 0);
      double g = Math.max(n.coswith(new Vector(p, this.p)) * this.ci.g, 0);
      double b = Math.max(n.coswith(new Vector(p, this.p)) * this.ci.b, 0);
      return new CI(r, g, b);
    }
  }
  public boolean truelyseen(Point p, Vector v) {
    if (v.coswith(new Vector(p, this.p)) >= 1 - 0.000001) {
      return true;
    } else {
      return false;
    }
  }
  public Point POVonray(Point p, Vector u) {
    if (!(this.truelyseen(p, u))) {
      System.out.println("Lamp is not seen on this ray");
    }
    return this.p;
  } // MAYBE needs try/catch block to check whether this is seen on ray u
  CI intensity1(Light l, Point p) { return null; }
  @Override
  Vector getnorm(Point p) {
    return null;
  }
  public CI blink(Obj s, Point p) {
    Star sun = new Star(new Vector(this.p, p), this.ci);
    return sun.blink(s, p);
  }
}
