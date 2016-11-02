#version 120

uniform sampler2D texture;

varying vec2 texCoord;

void main() {
    gl_FragData[0] = texture2D(texture, texCoord);
}
