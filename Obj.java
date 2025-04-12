import java.util.ArrayList;

public abstract class Obj {
  public double reflvl;
  double smooth;
  double transparency; // (1 - transparency) is a percent of light, that goes
                       // through the object
  int typenumber;
  Color c;
  abstract boolean truelyseen(Point p, Vector v);
  abstract Point POVonray(Point p, Vector v);
  Point POVonray(Point camera, Point p) {
    return this.POVonray(camera, new Vector(camera, p));
  }
  double dist2POV(Point p, Vector v) {
    if (this.truelyseen(p, v)) {
      Point x = this.POVonray(p, v);
      return x.distfrom(p);
    } else {
      return 1000000 + this.typenumber;
    }
  }; // means dist 2 point of crossing
  abstract Color pixelcolorofp(ArrayList<Light> ls, Point p);
  public CI intensity(Light l, Point p) {
    return l.shine(p, this.getnorm(p)).sumwith(l.blink(this, p));
  }
  abstract Vector getnorm(Point p);
}