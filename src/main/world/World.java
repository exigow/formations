package world;

import models.helpers.RandomEntityGenerator;

import java.util.HashSet;
import java.util.Set;

public class World {

  public final Set<Entity> entities = new HashSet<Entity>() {{
    for (int i = 0; i++ < 128;)
      add(RandomEntityGenerator.generate());
  }};

}
