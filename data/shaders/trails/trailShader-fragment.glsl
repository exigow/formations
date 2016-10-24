#version 120

uniform sampler2D texture;

varying vec2 texCoord;
varying float alpha;

float luminance(vec3 rgb) {
    vec3 w = vec3(0.2125, 0.7154, 0.0721);
    return dot(rgb, w);
}

void main() {
    vec3 color = texture2D(texture, texCoord).rgb;
    float alpha = alpha * luminance(color) * .75;
    gl_FragColor = vec4(color, alpha);
}
