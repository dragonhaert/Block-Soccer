import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class BlockSoccer extends PApplet {

GameObject[] gameObjects = new GameObject[11]; //Make sure to update this!!!
int scoreA = 0;
int scoreB = 0;

public void setup()
{
  width = 800;
  height = 640;
  
  
  gameObjects[0] = new Block(0, -10, width, 20);
  block(1).shown(true);
  
  gameObjects[1] = new Block(-10, 0, 20, height);
  block(2).shown(true);
  
  gameObjects[2] = new Block(width -10, 0, 20, height);
  block(3).shown(true);
  
  gameObjects[3] = new Block(0, height - 10, width, 20);
  block(4).shown(true);

  gameObjects[4] = new Player(width/4, height/2);
  player(1).setColor(255,0,0);
  player(1).solid(true);
  
  gameObjects[5] = new Player(3*width/4, height/2);
  player(2).setColor(0,150,200);
  player(2).solid(true);
  
  gameObjects[6] = new Block(width/2, height/2, 30, 30);
  block(5).movable(true);
  block(5).setColor(255,150,0);
  block(5).shown(true);
  block(5).fricAble(true, 0.005f);
  
  gameObjects[7] = new EventSpace(10, 10, 10, height - 20);
  
  gameObjects[8] = new EventSpace(width - 20, 10, 10, height - 20);
  
  gameObjects[9] = new EventSpace(20, 10, width - 40, 5);
  gameObjects[10] = new EventSpace(20, height - 15, width - 40, 5);
  
}

public void draw()
{
  background(80,0,150);
  
  //Events
  if (block(5).coll(event(1)))
  {
    System.out.println("Blue Score!");
    scoreA++;
    setup();
  }
  
  if (block(5).coll(event(2)))
  {
    System.out.println("Red Score!");
    scoreB++;
    setup();
  }
  
  if (block(5).coll(event(3)))
  {
    block(5).impart(0, 2);
  }
  
  if (block(5).coll(event(4)))
  {
    block(5).impart(0, -2);
  }
  
  //GameObject functionality
  for (GameObject g: gameObjects)
  {
    
    if(g.hasMotion())
    {
      for (GameObject other: gameObjects)
      {
        if (!(g == other)) g.coll(other);
      }
      g.move();
    }
    
    if(g.shown())
    {
      g.display();
    }
    
    if(g.hasMotion)
    {
      if (g.fricAble())
      {
        g.decelerateX();
        g.decelerateY();
      }
      if (g.vel().x > -0.1f && g.vel().x < 0.1f) g.haltX();
      if (g.vel().y > -0.1f && g.vel().y < 0.1f) g.haltY();
    }
  }
  
}

public void keyPressed()
{
  if (key == 'w') ((Player) player(1)).moveUp();
  if (key == 'a') ((Player) player(1)).moveLeft();
  if (key == 's') ((Player) player(1)).moveDown();
  if (key == 'd') ((Player) player(1)).moveRight();
  
  if (keyCode == UP) ((Player) player(2)).moveUp();
  if (keyCode == LEFT) ((Player) player(2)).moveLeft();
  if (keyCode == DOWN) ((Player) player(2)).moveDown();
  if (keyCode == RIGHT) ((Player) player(2)).moveRight();
  
}

public void keyReleased()
{
  if (key == 'a'|| key == 'd') ((Player) player(1)).decelerateX();
  if (key == 'w'|| key == 's') ((Player) player(1)).decelerateY();
  
  if (keyCode == LEFT|| keyCode == RIGHT) ((Player) player(2)).decelerateX();
  if (keyCode == UP|| keyCode == DOWN) ((Player) player(2)).decelerateY();
}

//Easy grabber methods :)
public GameObject player(int p)
{
  int i = 1;
  for (GameObject g: gameObjects)
  {
    if (g.name.equals("player"))
    {
      if (i == p) return g;
      else i++;
    }
  }
  return null;
}

public GameObject block(int b)
{
  int i = 1;
  for (GameObject g: gameObjects)
  {
    if (g.name.equals("block"))
    {
      if (i == b) return g;
      else i++;
    }
  }
  return null;
}

public GameObject event(int e)
{
  int i = 1;
  for (GameObject g: gameObjects)
  {
    if (g.name.equals("event"))
    {
      if (i == e) return g;
      else i++;
    }
  }
  return null;
}
public class Block extends GameObject
{
  public Block(float x, float y, float w, float h)
  {
    super.name = "block";
    super.pos = new PVector(x,y);
    super.size = new PVector(w,h);
    super.solid = true;
  }
  
  
}
public class EventSpace extends GameObject
{
  EventSpace(float x, float y, float w, float h)
  {
    super.name = "event";
    super.pos = new PVector(x,y);
    super.size = new PVector(w,h);
    hasMotion(false);
  }
  
}
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
  float FRICTION = 0.2f;
  public boolean fricAble()
  {
    return fricAble;
  }
  public void fricAble(boolean s, float f)
  {
    hasMotion(true);
    fricAble = s;
    FRICTION = f;
  }
  
  public void decelerateX()
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
  public void decelerateY()
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
  public void haltX()
  {
    vel.x = 0;
    acc.x = 0;
  }
  public void haltY()
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
public class Player extends GameObject
{
  float speed = 3;
  
  public Player(float x, float y)
  {
    super.name = "player";
    super.size = new PVector(40, 40);
    super.pos = new PVector(x, y);
    super.hasMotion = true;
    shown(true);
  }
  
  public void moveLeft()
  {
    super.vel.x = -speed;
    super.acc.x = 0;
  }
  
  public void moveRight()
  {
    super.vel.x = speed;
    super.acc.x = 0;
  }
  
  public void moveUp()
  {
    super.vel.y = -speed;
    super.acc.y = 0;
  }
  
  public void moveDown()
  {
    super.vel.y = speed;
    super.acc.y = 0;
  }
  
}
  public void settings() {  size(800,640); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "BlockSoccer" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
