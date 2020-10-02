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
  
  void moveLeft()
  {
    super.vel.x = -speed;
    super.acc.x = 0;
  }
  
  void moveRight()
  {
    super.vel.x = speed;
    super.acc.x = 0;
  }
  
  void moveUp()
  {
    super.vel.y = -speed;
    super.acc.y = 0;
  }
  
  void moveDown()
  {
    super.vel.y = speed;
    super.acc.y = 0;
  }
  
}
