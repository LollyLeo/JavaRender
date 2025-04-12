
import java.util.ArrayList;

public class Nullobj extends Obj {
  public Color pixelcolorofp(ArrayList<Light> ls, Point p) {
    return new Color("#000000");
  }
  CI intensity1(Light l, Point p) { return null; }
  @Override
  Vector getnorm(Point p) {
    return null;
  }
  public Nullobj() {}
  @Override
  boolean truelyseen(Point p, Vector v) {
    return false;
  }
  @Override
  Point POVonray(Point p, Vector v) {
    return null;
  }
  static boolean isequal(Nullobj no1, Nullobj no2) { return true; }
}
