import java.util.Scanner;

public class CI { // Colored Intencity
  double r, g, b;
  // ci(l.r * l.power, l.g * l.power, l.b * l.power)
  public CI(double r, double g, double b) {
    this.r = r;
    this.g = g;
    this.b = b;
  }
  public CI(double q) {
    this.r = q;
    this.g = q;
    this.b = q;
  }
  public CI(Color c) {
    this.r = c.r;
    this.g = c.g;
    this.b = c.b;
  }
  public CI muled(double k) {
    return new CI(this.r * k, this.g * k, this.b * k);
  }
  public CI sumwith(CI ci) {
    return new CI(this.r + ci.r, this.g + ci.g, this.b + ci.b);
  }
  public void sum(CI ci) {
    this.r += ci.r;
    this.g += ci.g;
    this.b += ci.b;
  }
  public static CI read3(Scanner sc) {
    double cr, cg, cb;
    cr = sc.nextDouble();
    cg = sc.nextDouble();
    cb = sc.nextDouble();
    return new CI(cr, cg, cb);
  }
  public double avi() { return (this.r + this.g + this.b) / 3; }

} // Colored Intensity
