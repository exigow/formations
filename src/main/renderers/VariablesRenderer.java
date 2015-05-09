package renderers;

import agents.RenderAgent;

import java.util.HashMap;
import java.util.Map;

public class VariablesRenderer {

  private final Map<String, Object> variables = new HashMap<>();

  public void render(RenderAgent agent) {
    String buffer = buildString(variables);
    renderVariables(agent, buffer);
  }

  private static String buildString(Map<String, Object> map) {
    StringBuilder buffer = new StringBuilder();
    for (String key : map.keySet())
      buffer.append("\"").append(key).append("\" = ").append(map.get(key)).append("\n");
    return buffer.toString();
  }

  private void renderVariables(RenderAgent agent, String buffer) {
    agent.batch.begin();
    agent.debugFont.drawMultiLine(agent.batch, buffer, 0, 0);
    agent.batch.end();
  }

  public void update(String key, Object value) {
    variables.put(key, value);
  }

}
