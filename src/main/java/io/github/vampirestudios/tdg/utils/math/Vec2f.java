package io.github.vampirestudios.tdg.utils.math;

/**
 * @author Joe van der Zwet (https://joezwet.me)
 */
public class Vec2f {
    private float x;
    private float y;

    public Vec2f() {
        this.x = 0;
        this.y = 0;
    }

    public Vec2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float length() {
        return (float) Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public float dot(Vec2f vector) {
        return this.x * vector.getX() + this.y * vector.getY();
    }

    public Vec2f normalize() {
        float length = length();
        this.x /= length;
        this.y /= length;
        return this;
    }

    public Vec2f rotate(float angle) {
        float radians = (float) Math.toRadians(angle);
        return new Vec2f((float) (this.x * Math.cos(radians)) - (float) (y * Math.sin(radians)), (float) (x * Math.sin(radians) + (float) y * Math.cos(radians)));
    }

    public Vec2f add(Vec2f vector) {
        return new Vec2f(this.x + vector.getX(), this.y + vector.getY());
    }

    public Vec2f sub(Vec2f vector) {
        return new Vec2f(this.x - vector.getX(), this.y - vector.getY());
    }

    public Vec2f mul(Vec2f vector) {
        return new Vec2f(this.x * vector.getX(), this.y * vector.getY());
    }

    public Vec2f div(Vec2f vector) {
        return new Vec2f(this.x / vector.getX(), this.y / vector.getY());
    }

    public Vec2f add(float value) {
        return new Vec2f(this.x + value, this.y + value);
    }

    public Vec2f sub(float value) {
        return new Vec2f(this.x - value, this.y - value);
    }

    public Vec2f mul(float value) {
        return new Vec2f(this.x * value, this.y * value);
    }

    public Vec2f div(float value) {
        return new Vec2f(this.x / value, this.y / value);
    }

    public String toString() {
        return this.x + ", " + this.y;
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
}
