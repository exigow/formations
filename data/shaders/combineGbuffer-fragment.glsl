uniform sampler2D textureDiffuse;
uniform sampler2D textureEmissive;

varying vec2 texCoord;

void main() {
    vec3 diffuse = texture2D(textureDiffuse, texCoord).rgb;
    vec3 emissive = texture2D(textureEmissive, texCoord).rgb;
    gl_FragColor = vec4(diffuse + emissive, 1);
}
