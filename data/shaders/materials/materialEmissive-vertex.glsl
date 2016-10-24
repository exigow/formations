#version 120

uniform mat4 projection;

attribute vec4 positionAttr;
attribute vec2 texCoordAttr;
attribute float alphaAttr;

varying vec2 texCoord;
varying float alpha;

void main() {
    texCoord = texCoordAttr;
    alpha = alphaAttr;
    gl_Position = projection * positionAttr;
}
