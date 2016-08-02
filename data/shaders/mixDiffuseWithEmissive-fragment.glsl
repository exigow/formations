uniform sampler2D textureDiffuse;
uniform sampler2D textureEmissive;
uniform sampler2D textureEmissiveBlurred;
uniform float noiseOffset;

varying vec2 texCoord;

float rand(vec2 co){
    return fract(sin(dot(co.xy, vec2(12.9898 + noiseOffset, 78.233 + noiseOffset))) * 43758.5453);
}

float calculateVignette(vec2 coord) {
	vec2 uv = coord.xy;
    uv *=  1.0 - uv.yx;
    float vig = uv.x*uv.y * 45.0 * (.5 + .5 * rand(coord));
    return clamp(pow(vig, 0.25), 0, 1);
}

// todo CHROMATIC ABBERATION

void main() {
    vec3 diffuse = texture2D(textureDiffuse, texCoord).rgb;
    vec3 emissive = texture2D(textureEmissive, texCoord).rgb * .5;
    vec3 emissiveBlurred = texture2D(textureEmissiveBlurred, texCoord).rgb;
    vec3 vignette = calculateVignette(texCoord);
    vec3 color = (diffuse + emissive + emissiveBlurred) * vignette;
    gl_FragColor = vec4(color, 1);
}
