#version 120

uniform sampler2D textureClean;
uniform sampler2D textureLens;
uniform sampler2D textureBloom;
uniform sampler2D textureDirt;

varying vec2 texCoord;

void main() {
    vec3 clean = texture2D(textureClean, texCoord).rgb;
    vec3 lens = texture2D(textureLens, texCoord).rgb;
    vec3 bloom = texture2D(textureBloom, texCoord).rgb * vec3(.145, .77, 1.86);
    vec3 dirt = texture2D(textureDirt, texCoord).rgb;
    float application = .5f;
    vec3 dirtFixed = vec3(application) * dirt + vec3(application);
    gl_FragColor = vec4(clean + (lens * dirtFixed) + bloom, 1);
}
