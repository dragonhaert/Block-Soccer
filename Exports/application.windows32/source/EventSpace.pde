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
