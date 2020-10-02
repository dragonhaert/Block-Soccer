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
