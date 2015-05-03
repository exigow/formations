package models;

import models.helpers.RandomEntityGenerator;

import java.util.ArrayList;
import java.util.Collection;

public class World {

  public final Collection<Entity> entities = new ArrayList<Entity>() {{
    for (int i = 0; i++ < 128;)
      add(RandomEntityGenerator.generate());
  }};

}
