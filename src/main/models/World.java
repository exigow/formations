package models;

import models.helpers.EntityRandomizer;

import java.util.ArrayList;
import java.util.Collection;

public class World {

  public final Collection<Entity> entities = new ArrayList<Entity>() {{
    for (int i = 0; i++ < 128;)
      add(EntityRandomizer.generate());
  }};

}
