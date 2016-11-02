#version 120

uniform sampler2D textureDiffuse;
uniform sampler2D textureEmissive;
uniform vec3 ambientColor;

varying vec2 texCoord;
varying float alpha;

void main() {
    vec4 diffuse = texture2D(textureDiffuse, texCoord);
    vec3 ambientDiffuse = diffuse.rgb * ambientColor;
    vec3 emissive = texture2D(textureEmissive, texCoord).rgb;
    float transparency = diffuse.a * alpha;
    gl_FragData[0] = vec4(ambientDiffuse, transparency);
    gl_FragData[1] = vec4(emissive, transparency);
}
