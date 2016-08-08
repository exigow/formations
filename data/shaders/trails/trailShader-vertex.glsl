#version 120

uniform mat4 projection;

attribute vec4 positionAttr;
attribute vec2 texCoordAttr;
attribute float lifeAttr;

varying vec2 texCoord;
varying float life;

void main() {
    texCoord = texCoordAttr;
    life = lifeAttr;
    gl_Position = projection * positionAttr;
}
