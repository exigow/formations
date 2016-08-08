#version 120

uniform sampler2D textureClean;
uniform sampler2D textureBloom;
uniform sampler2D textureBloomHalo;

varying vec2 texCoord;

void main() {
    vec3 clean = texture2D(textureClean, texCoord).rgb;
    vec3 bloom = texture2D(textureBloom, texCoord).rgb;
    vec3 bloomHalo = texture2D(textureBloomHalo, texCoord).rgb * vec3(.25, .65, 1) * 1.5;
    gl_FragColor = vec4(clean + (bloom + bloomHalo) / 2, 1);
}
