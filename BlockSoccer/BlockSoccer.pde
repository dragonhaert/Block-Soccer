GameObject[] gameObjects = new GameObject[11]; //Make sure to update this!!!
int scoreA = 0;
int scoreB = 0;

void setup()
{
  width = 800;
  height = 640;
  size(800,640);
  
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
  block(5).fricAble(true, 0.005);
  
  gameObjects[7] = new EventSpace(10, 10, 10, height - 20);
  
  gameObjects[8] = new EventSpace(width - 20, 10, 10, height - 20);
  
  gameObjects[9] = new EventSpace(20, 10, width - 40, 5);
  gameObjects[10] = new EventSpace(20, height - 15, width - 40, 5);
  
}

void draw()
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
      if (g.vel().x > -0.1 && g.vel().x < 0.1) g.haltX();
      if (g.vel().y > -0.1 && g.vel().y < 0.1) g.haltY();
    }
  }
  
}

void keyPressed()
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

void keyReleased()
{
  if (key == 'a'|| key == 'd') ((Player) player(1)).decelerateX();
  if (key == 'w'|| key == 's') ((Player) player(1)).decelerateY();
  
  if (keyCode == LEFT|| keyCode == RIGHT) ((Player) player(2)).decelerateX();
  if (keyCode == UP|| keyCode == DOWN) ((Player) player(2)).decelerateY();
}

//Easy grabber methods :)
GameObject player(int p)
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

GameObject block(int b)
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

GameObject event(int e)
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
