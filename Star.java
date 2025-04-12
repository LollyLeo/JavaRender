public class Star extends Light {
  Vector v;
  public Star(Vector v, double power) {
    this.transparency = 0;
    this.v = v;
    this.ci = new CI(power, power, power);
    v = v.muled(-10000);
    this.p = new Point(v.x, v.y, v.z);
  }
  public Star(Vector v, CI ci) {
    this(v, 0);
    this.ci = ci;
  }
  public Star(Vector v, int[] colorshines) {
    this(v, new CI(colorshines[0], colorshines[1], colorshines[2]));
  }
  public CI shine(Point p, Vector n) {
    double r = Math.max(n.coswith(this.v.muled(-1)) * this.ci.r, 0);
    double g = Math.max(n.coswith(this.v.muled(-1)) * this.ci.g, 0);
    double b = Math.max(n.coswith(this.v.muled(-1)) * this.ci.b, 0);
    return new CI(r, g, b);
  }
  public boolean truelyseen(Point p, Vector v) {
    if (this.v.coswith(v) == -1) {
      return true;
    } else {
      return false;
    }
  }

  public Point POVonray(Point p, Vector u) {
    if (!(this.truelyseen(p, u))) {
      System.out.println("Star is not seen on this ray");
    }
    return p.sumwith(u.lengthed(100000));
  }
  CI intensity1(Light l, Point p) { return null; }
  @Override
  Vector getnorm(Point p) {
    return null;
  }

  public CI blink(Obj s, Point p) {
    double mp = Math.pow(
        Math.max(
            0,
            this.v.reflect(s.getnorm(p)).coswith(new Vector(Main.camera.p, p))),
        s.smooth);
    double r = this.ci.r * mp;
    double g = this.ci.g * mp;
    double b = this.ci.b * mp;
    // System.out.println(mp);
    if (mp < 0) {
      System.out.println("<<<<<<<<<<<<<");
    }
    return new CI(r, g, b);
  }
}
