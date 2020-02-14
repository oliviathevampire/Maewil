#version 330 core
#define MAX_SIZE 100
in vec3 position;
in vec3 color;
in vec2 textureCoord;

out vec3 passColor;
out vec2 passTextureCoord;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;
uniform vec3 positionOffset[MAX_SIZE];

void main() {
    gl_Position = projection * view * model * vec4(position + positionOffset[gl_InstanceID], 1.0);
    passColor = color;
    passTextureCoord = textureCoord;
}