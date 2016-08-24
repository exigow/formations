#version 120

uniform sampler2D texture;
uniform vec2 texel;

varying vec2 texCoord;

float kernel[9] = float[](
    0, -1, 0,
    -1, 5, -1,
    0, -1, 0
);

vec2 offset[9] = vec2[](
    vec2(-texel.x, -texel.y),
    vec2(0, -texel.y),
    vec2(texel.x, -texel.y),
    vec2(-texel.x, 0),
    vec2(0, 0),
    vec2(texel.x, 0),
    vec2(-texel.x, texel.y),
    vec2(0, texel.y),
    vec2(texel.x, texel.y)
);


void main() {
    vec4 result = vec4(0);
    for (int i = 0; i < 9; i++)
        result += texture2D(texture, texCoord + offset[i]) * kernel[i];
    gl_FragColor = result;
}
