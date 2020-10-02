abstract class GameObject
{
  /********** NAME ********************/
  private String name;
  public String name()
  {
    return name;
  }
  
  /********** POSITION ********************/
  
  private PVector pos;
  private PVector vel = new PVector(0,0);
  private PVector acc = new PVector(0,0);
  public PVector pos()
  {
    return pos;
  }
  public void set(float x, float y)
  {
    pos.x = x;
    pos.y = y;
  }
  public PVector vel()
  {
    return vel;
  }
  public void impart(float x, float y)
  {
    vel.x += x;
    vel.y += y;
  }
  public PVector acc()
  {
    return acc;
  }
  public void move()
  {
    vel.add(acc);
    pos.add(vel);
  }
  
  
  /********* Friction & Stopping ***********/
  boolean fricAble = false;
  float FRICTION = 0.2;
  boolean fricAble()
  {
    return fricAble;
  }
  public void fricAble(boolean s, float f)
  {
    hasMotion(true);
    fricAble = s;
    FRICTION = f;
  }
  
  void decelerateX()
  {
    if (vel.x > 0)
    {
      acc.x = -FRICTION;
    }
    else if (vel.x < 0)
    {
      acc.x = FRICTION;
    }
  }
  void decelerateY()
  {
    if (vel.y > 0)
    {
      acc.y = -FRICTION;
    }
    else if (vel.y < 0)
    {
      acc.y = FRICTION;
    }
  }
  void haltX()
  {
    vel.x = 0;
    acc.x = 0;
  }
  void haltY()
  {
    vel.y = 0;
    acc.y = 0;
  }
  
  
  /********** Shape *******************/
  private PVector size;
  
  /********* Collisions *****************/
  private boolean solid = false;
  public void solid(boolean s)
  {
    solid = s;
  }
  public boolean solid()
  {
    return solid;
  }
  public boolean coll(GameObject other)
  {
    boolean left = pos.x + size.x < other.pos.x;
    boolean leftColl = pos.x + size.x + vel.x >= other.pos.x;
    boolean above = pos.y + size.y < other.pos.y;
    boolean topColl = pos.y + size.y + vel.y >= other.pos.y;
    boolean under = pos.y > other.pos.y + other.size.y;
    boolean bottomColl = pos.y + vel.y <= other.pos.y + other.size.y;
    boolean right = pos.x > other.pos.x + other.size.x;
    boolean rightColl = pos.x + vel.x <= other.pos.x + other.size.x;
    
    if (!(above || under) && (left && leftColl || right && rightColl) )
    {
      if (other.solid)
      {
        if (other.movable)
        {
          vel.x *= (size.x*size.y / (size.x * size.y + other.size.x * other.size.y));
          other.vel.x = vel.x;
          other.vel.y = vel.y * (size.x*size.y / (size.x * size.y + other.size.x * other.size.y));
        }
        haltX();
      }
      return true;
    }
    if (!(left || right) && (above && topColl || under && bottomColl))
    {
      if (other.solid)
      {
        if (other.movable)
        {
          vel.y *= (size.x*size.y / (size.x * size.y + other.size.x * other.size.y));
          other.vel.y = vel.y;
          other.vel.x = vel.x * (size.x*size.y / (size.x * size.y + other.size.x * other.size.y));
        }
        haltY();
      }
      return true;
    }
    return false;
  }
  
  
  /********** Momentum *******************/
  private boolean hasMotion = false;
  public void hasMotion(boolean s)
  {
    vel = new PVector(0,0);
    acc = new PVector(0,0);
    hasMotion = s;
  }
  public boolean hasMotion()
  {
    return hasMotion;
  }
  private boolean movable = false;
  public void movable(boolean s)
  {
    hasMotion(true);
    movable = s;
  }
  
  /********** VISUALS ********************/
  private boolean shown = false;
  public boolean shown()
  {
    return shown;
  }
  public void shown(boolean s)
  {
    shown = s;
  }
  
  public void display()
  {
    fill(rgb[0], rgb[1], rgb[2]);
    rect(pos.x, pos.y, size.x, size.y);
  }
  
  /*****  Color *****/
  private int[] rgb = {0, 0, 0};
  public void setColor(int r, int g, int b)
  {
    rgb[0] = r;
    rgb[1] = g;
    rgb[2] = b;
  }
  
}
