uniform mat4 projection;

attribute vec4 positionAttr;
attribute vec2 texCoordAttr;

varying vec2 texCoord;

void main() {
    texCoord = texCoordAttr;
    gl_Position = projection * positionAttr;
}
