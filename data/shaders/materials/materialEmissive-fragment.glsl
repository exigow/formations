#version 120

uniform sampler2D texture;
uniform sampler2D colorTexture;

varying vec2 texCoord;

void main() {
    float alpha = texture2D(colorTexture, texCoord).a;
    vec3 emissive = texture2D(texture, texCoord).rgb;
    gl_FragColor = vec4(emissive, alpha);
}
