# 3D Rendering Engine Documentation

## Core Architecture

### Base Object Class
```java
abstract class Obj
```
- **Abstract base class** for all 3D objects
- **Extends**: All objects inherit from `Obj`

#### Fields
- `reflvl` - Reflection coefficient
- `smooth` - Surface smoothness  
- `transparency` - Light transmission (1-transparency = % light passing through)
- `c` - Base color

#### Key Methods
- `truelyseen(Point p, Vector v)` - Check if point visible from direction
- `POVonray(Point p, Vector v)` - Find intersection point with ray
- `pixelcolorofp(Light[] ls, Point p)` - Calculate color at point with lights
- `getnorm(Point p)` - Get surface normal at point
- `intensity(Light l, Point p)` - Compute light intensity at point
- `dist2POV(Point p, Vector v)` - Distance to intersection (or large number if none)

#### Special Class
```java
class NullObj extends Obj
```
- Used to throw as exception for invalid objects

---

## Geometric Primitives

### Point Class
- Standard 3D point with geometric operations
- Used for coordinates in 3D space

### Vector Class  
- Standard 3D vector with geometric operations
- Used for directions, normals, and rays

### Point2D Class
- Represents pixels on a screen
- Used for image coordinates

---

## Color System

### Color Class
**Constructors:**
- `Color(r, g, b)` - RGB values 0-255
- `Color(intensity)` - Grayscale (r=g=b)
- `Color("#hex")` - Hex code like #F0EEF0

### CI (Colored Intensity) Class
**Constructors:**
- `CI(r, g, b)` - RGB components
- `CI(q)` - Grayscale (r=g=b)  
- `CI(c)` - From Color object

**Operations:**
- `muled(k)` - Multiply all components by scalar
- `sumwith(ci)` - Add another CI (returns new)
- `sum(ci)` - Add in-place
- `avi()` - Average intensity

**Utility:**
- `read3(sc)` - Read RGB from scanner

---

## 3D Objects

### Sphere Class
```java
class Sphere extends Obj
```

**Constructors:**
- `Sphere(x, y, z, r)` - Center (x,y,z), radius r
- `Sphere(Point p, r)` - Center Point p, radius r

**Options:** Color c, smoothness, reflectivity reflvl

**Key Methods:**
- `truelyseen(Point p, Vector v)` - Visible from point p along vector v
- `POVonray(Point p, Vector v)` - Intersection point with ray
- `getnorm(Point p)` - Normal vector at point p  
- `intensity(Light[] ls, Point p)` - Color intensity from lights ls at point p

**Static Readers:**
- `readС7()` - Format: Sphere center_x center_y center_z radius
- `readS5()` - Format: Sphere center_x center_y center_z radius smoothness
- `read4()` - Format: Sphere center_x center_y center_z radius
- `readCS8()` - Format: Sphere center_x center_y center_z radius color smoothness
- `readCSR9()` - Format: Sphere center_x center_y center_z radius color smoothness reflvl

### Plane Class
```java
class Plane extends Obj
```

**Constructors:**
- `Plane(Point p, Vector norm)` - Creates plane with point and normalized normal vector
- `Plane(Point p, Vector norm, Color c)` - With custom color
- `Plane(Point p, Vector norm, Color c, double smooth, double r)` - Full parameters

**Key Properties:**
- Defined by plane equation: Ax + By + Cz + D = 0
- Normal vector always normalized to length 1
- Default: transparency=1, reflvl=0.5, gray color

**Methods:**
- `POVonray(Point p, Vector v)` - Intersection point with ray
- `truelyseen(Point p, Vector v)` - Checks if plane visible from point
- `distfrom(Point p)` - Signed distance from point to plane
- `getnorm(Point p)` - Returns normal vector

**Static Readers:**
- `readC9()` - Format: Plane point_x point_y point_z norm_x norm_y norm_z color
- `readCSR11()` - Format: Plane point_x point_y point_z norm_x norm_y norm_z color smoothness reflvl

---

## Lighting System

### Light Base Class
```java
abstract class Light extends Obj
```

**Properties:**
- Always returns black color for rendering
- Has position/direction `p` and color intensity `ci`
- `typenumber = 10000`

**Key Methods:**
- `shine(Point p, Vector n)` - Calculate illumination at point with normal
- `blink(Obj o, Point p)` - Compute light contribution for object at point

**Factory Methods:**
- `read4P(Scanner sc)` - Read light with position & power
- `read3(Scanner sc)` - Read light with position (power=1)
- `read6CI(Scanner sc)` - Read light with position & color intensity

### Lamp Class (Point Light)
```java
class Lamp extends Light
```

**Constructors:**
- `Lamp(Point p, double power)` - White light at point
- `Lamp(Point p, CI ci)` - Colored light at point
- `Lamp(Point p, double power, int[] colorshines)` - RGB colored light

**Lighting:**
- `shine(Point p, Vector n)` - Diffuse lighting based on surface normal
- `blink(Sphere s, Point p)` - Delegates to directional light calculation

**Visibility:**
- `truelyseen(Point p, Vector v)` - True if ray direction points toward lamp
- `POVonray(Point p, Vector u)` - Returns lamp position if visible

### Star Class (Directional Light)
```java
class Star extends Light
```

**Constructors:**
- `Star(Vector v, double power)` - Direction + intensity (grayscale)
- `Star(Vector v, CI ci)` - Direction + color
- `Star(Vector v, int[] colorshines)` - Direction + RGB array

**Key Methods:**
- `shine(Point p, Vector n)` - Calculate illumination based on surface normal
- `truelyseen(Point p, Vector v)` - Check if star is visible in direction
- `blink(Obj s, Point p)` - Compute specular highlight

---

## Rendering System

### Camera Class
```java
class Camera
```

**Fields:**
- `p` - Camera position
- `w, h` - Viewport dimensions
- `d` - Forward direction
- `x, y` - Right/up axes

**Methods:**
- `im2vp(int imx, int imy)` - Convert image coordinates to viewport point

### BMP Image Class
```java
class BMP
```

**Constructors:**
- `BMP(int w, int h, String path)` - Create new BMP with dimensions
- `BMP(File file)` - Load existing BMP file

**Core Methods:**
- `save()` - Write image to file
- `fillPixel(int x, int y, Color color)` - Set pixel color (Color/hex/RGB)
- `colorof(int x, int y)` - Get pixel color
- `fillRect()` - Fill rectangular area
- `fillImage()` - Fill entire image

**Internal:**
- Handles BMP header creation and padding
- Manages little-endian byte order
- Supports 24-bit RGB format

---

## Ray Tracing Pipeline

1. **Ray Generation**: Camera generates rays through viewport
2. **Intersection Testing**: `POVonray()` finds closest object intersection
3. **Visibility Check**: `truelyseen()` verifies unobstructed view
4. **Lighting Calculation**: `pixelcolorofp()` computes color with all lights
5. **Recursive Effects**: Reflections and transparency handled recursively
6. **Image Output**: BMP class writes final pixel colors to file