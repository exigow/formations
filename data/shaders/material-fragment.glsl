#version 120

uniform sampler2D textureDiffuse;
uniform sampler2D textureEmissive;
uniform vec3 ambientColor;

varying vec2 texCoord;
varying float alpha;
const float farClipping = 65536f;// far clipping plane distance

void main() {
    vec4 diffuse = texture2D(textureDiffuse, texCoord);
    vec3 ambientDiffuse = diffuse.rgb * ambientColor;
    vec3 emissive = texture2D(textureEmissive, texCoord).rgb;
    float transparency = diffuse.a * alpha;
    float depth = clamp(1f - gl_FragCoord.z / gl_FragCoord.w / farClipping, 0f, 1f);
    gl_FragData[0] = vec4(ambientDiffuse, transparency);
    gl_FragData[1] = vec4(emissive, transparency);
    gl_FragData[2] = vec4(vec3(depth), transparency);
}
