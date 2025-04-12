import java.util.ArrayList;
import java.util.Collections;
public class Scene {
  ArrayList<Obj> nonLobjects = new ArrayList<>();
  ArrayList<Light> lights = new ArrayList<>();
  Color back;
  public Scene() { this.back = Main.scenebackcolor; }
  public Scene(ArrayList<Obj> objs) {
    this.nonLobjects = objs;
    this.back = new Color(0, 0, 0);
  }
  public void renderin(BMP bmp) {
    for (int imx = 0; imx < bmp.w; imx++) {
      for (int imy = 0; imy < bmp.h; imy++) {
        Point2D p2d = new Point2D(imx, imy);
        Point realp = Point.im2vp(imx, imy, Main.camera);
        //                if (this.nonLobjects.get(2).truelyseen(Main.cam.p, new
        //                Vector(Main.cam.p, realp))){
        //                    //System.out.println(imy);
        //                    System.out.println(this.lights.get(6).blink(this.nonLobjects.get(2),
        //                    this.nonLobjects.get(2).POVonray(Main.cam.p,
        //                    realp)).avi());
        //                }
        Obj olo =
            this.firstonray(Main.camera.p, new Vector(Main.camera.p, realp));
        if (olo.getClass().getSuperclass().getName().equals("Light")) {
          bmp.fillPixel(imx, imy, new Color(255));
        } else {
          Obj o = this.whichobjisseen(p2d);
          if (o.getClass().getName().equals("Nullobj")) {
            // System.out.println(imx + " " + imy);
            bmp.fillPixel(imx, imy, this.back);
          } else {
            Point pov =
                o.POVonray(Main.camera.p, new Vector(Main.camera.p, realp));
            Color relc = this.smshPLUSref(o, pov, Main.refdepth);
            bmp.fillPixel(imx, imy, relc);
          }
        }
      }
    }
    bmp.save();
  }
  public void addnonLobj(Obj obj) { this.nonLobjects.add(obj); }
  public void addlight(Light l) {
    if (l.getClass().getName().equals("LinedLamp")) {
      System.out.println("Wrong light adding");
    }
    this.lights.add(l);
  }
  public void addlight(LinedLamp l) {
    for (int i = 0; i < l.k; i++) {
      this.addlight(l.lights.get(i));
    }
  }
  public ArrayList<Obj> seenobjs(Point p, Vector v) {
    ArrayList<Obj> objs = new ArrayList<Obj>();
    Obj o;
    for (int i = 0; i < this.nonLobjects.size(); i++) {
      o = this.nonLobjects.get(i);
      if (o.truelyseen(p, v)) {
        //                if (o.getClass().getName().equals("Plane")){
        //                    System.out.println(1);
        //               }
        if (new Vector(p, o.POVonray(p, v)).coswith(v) > 0) {
          objs.add(this.nonLobjects.get(i));
        }
      }
    }

    return objs;
  } // возможно неправильно
  // public Obj[] seenAndNotObscured(Point p, Vector v){}
  public ArrayList<Obj> maybeseenobjs(Point p, Vector v) {
    ArrayList<Obj> objs = new ArrayList<Obj>();
    objs = this.seenobjs(p, v);
    Obj o;
    for (int i = 0; i < this.lights.size(); i++) {
      o = this.lights.get(i);
      if (o.truelyseen(p, v)) {
        if (new Vector(p, o.POVonray(p, v)).coswith(v) > 0) {
          objs.add(this.lights.get(i));
        }
      }
    }
    return objs;
  }
  public Obj firstonray(Point p, Vector v) {
    p = p.slightlyMovedTwrds(v);
    ArrayList<Obj> seenobjs = this.maybeseenobjs(p, v);
    if (seenobjs.size() == 0) {
      return new Nullobj();
    } else {
      return seenobjs.get(0);
    }
  }
  public Obj first_opaque(Point p, Vector v) {
    p = p.slightlyMovedTwrds(v);
    ArrayList<Obj> seenobjs = this.seenobjs(p, v);
    if (seenobjs.size() == 0) {
      return new Nullobj();
    } else {
      if (seenobjs.size() == 1) {
        return seenobjs.get(0);
      } else {
        Obj ans = new Nullobj();
        Collections.sort(seenobjs, new ObjComparator(p, v));
        for (int i = 0; i < seenobjs.size(); i++) {
          if (seenobjs.get(i).transparency == 1) {
            ans = seenobjs.get(i);
            break;
          }
        }
        return ans;
      }
    }
  }
  public Obj whichobjisseen(Point2D ppp) {
    double xscr = ppp.x;
    double yscr = ppp.y;
    Point p = Point.im2vp(xscr, yscr, Main.camera);
    return this.first_opaque(Main.camera.p, new Vector(Main.camera.p, p));
  }
  public CI sumshine(Obj o, Point p) {
    CI sumshine = new CI(0, 0, 0);
    Light li;
    for (int i = 0; i < this.lights.size(); i++) {
      li = this.lights.get(i);
      if (this.lighthasimpact(li, p)) {
        sumshine.sum(o.intensity(li, p));
      }
    }
    return sumshine.sumwith(Main.staticlight);
  }
  public Color smshPLUSref(Obj o, Point p, int refdepth) {
    if (refdepth < 1) {
      return o.c.muled(1 - o.reflvl).muled(this.sumshine(o, p));
    } else {
      Vector v = new Vector(Main.camera.p, p).muled(-1);
      v = v.reflect(o.getnorm(p));
      Obj nexto = this.first_opaque(p, v);
      if (nexto.getClass().getName().equals("Nullobj")) {
        return o.c.muled(1 - o.reflvl).muled(this.sumshine(o, p));
      } else {
        Point pofnexto = nexto.POVonray(p, v);
        Color nextc = this.smshPLUSref(nexto, pofnexto, refdepth - 1);
        return o.c.muled(1 - o.reflvl)
            .muled(this.sumshine(o, p))
            .sumed(nextc.muled(o.reflvl));
      }
    }
  }
  public CI reflectionWWW(Obj o, Point p, int refdepth) { return new CI(0); }
  public boolean lighthasimpact(Light l, Point p) {
    Vector v = new Vector(p, l.p);
    Obj o2 = this.first_opaque(p, v);
    if (o2.getClass().getName().equals("Nullobj")) {
      return true;
    } else {
      if (o2.POVonray(p, v).distfrom(p) >= l.p.distfrom(p)) {
        return true;
      } else {
        return false;
      }
    }
  }
  public boolean lighthasimpactWWW(Light l, Point p) { return true; }
  public boolean pointhasimpact(Point pasl, Point p) {
    Vector v = new Vector(p, pasl);
    Obj o2 = this.first_opaque(p, v);
    if (o2.getClass().getName().equals("Nullobj")) {
      return true;
    } else {
      if (o2.POVonray(p, v).distfrom(p) >= pasl.distfrom(p)) {
        return true;
      } else {
        return false;
      }
    }
  }
}