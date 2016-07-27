layout(location = 0) out vec4 colorOut;
layout(location = 1) out vec4 emissiveOut;

void main() {
  colorOut = vec4(1, 0, 0, 1);
  emissiveOut = vec4(0, 1, 0, 1);
}