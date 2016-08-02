uniform sampler2D textureDiffuse;
uniform sampler2D textureEmissive;
uniform sampler2D textureEmissiveBlurred;

varying vec2 texCoord;

void main() {
    vec3 diffuse = texture2D(textureDiffuse, texCoord).rgb;
    vec3 emissive = texture2D(textureEmissive, texCoord).rgb * .25;
    vec3 emissiveBlurred = texture2D(textureEmissiveBlurred, texCoord).rgb;
    gl_FragColor = vec4(diffuse + emissive + emissiveBlurred, 1);
}
