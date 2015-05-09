package renderers;

import agents.RenderAgent;

import java.util.HashMap;
import java.util.Map;

public class VariablesRenderer {

  private final Map<String, Object> variables = new HashMap<>();

  public void render(RenderAgent agent) {
    StringBuilder buffer = new StringBuilder();
    for (String key : variables.keySet())
      buffer.append(key).append(" = ").append(variables.get(key)).append("\n");
    agent.batch.begin();
    agent.debugFont.drawMultiLine(agent.batch, buffer.toString(), 0, 0);
    agent.batch.end();
  }

  public void present(String key, Object value) {
    variables.put(key, value);
  }

}
