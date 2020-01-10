package io.github.vampirestudios.tdg.utils.math;

/**
 * @author Joe van der Zwet (https://joezwet.me)
 */
public class Vec3f {
    private float x;
    private float y;
    private float z;

    public Vec3f() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
    }

    public Vec3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float length() {
        return (float)Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    public float dot(Vec3f vec3f) {
        return this.x * vec3f.getX() + this.y * vec3f.getY() + this.z * vec3f.getZ();
    }

    public Vec3f normalize() {
        float length = this.length();
        this.x /= length;
        this.y /= length;
        this.z /= length;

        return this;
    }

    public Vec3f cross(Vec3f vector) {
        float newX = this.y * vector.getZ() - this.z * vector.getY();
        float newY = this.z * vector.getX() - this.x * vector.getZ();
        float newZ = this.x * vector.getY() - this.y * vector.getX();

        return new Vec3f(newX, newY, newZ);
    }

    public Vec3f add(Vec3f vector) {
        return new Vec3f(this.x + vector.getX(), this.y + vector.getY(), this.z + vector.getZ());
    }

    public Vec3f sub(Vec3f vector) {
        return new Vec3f(this.x - vector.getX(), this.y - vector.getY(), this.z - vector.getZ());
    }

    public Vec3f mul(Vec3f vector) {
        return new Vec3f(this.x * vector.getX(), this.y * vector.getY(), this.z * vector.getZ());
    }

    public Vec3f div(Vec3f vector) {
        return new Vec3f(this.x / vector.getX(), this.y / vector.getY(), this.z / vector.getZ());
    }

    public Vec3f add(float value) {
        return new Vec3f(this.x + value, this.y + value, this.z + value);
    }

    public Vec3f sub(float value) {
        return new Vec3f(this.x - value, this.y - value, this.z - value);
    }

    public Vec3f mul(float value) {
        return new Vec3f(this.x * value, this.y * value, this.z * value);
    }

    public Vec3f div(float value) {
        return new Vec3f(this.x / value, this.y / value, this.z / value);
    }

    public String toString() {
        return this.x + ", " + this.y + ", " + this.z;
    }

    public float getX() {
        return this.x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return this.y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return this.z;
    }

    public void setZ(float z) {
        this.z = z;
    }
}
