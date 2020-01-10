package io.github.vampirestudios.tdg.utils.math;

/**
 * @author Joe van der Zwet (https://joezwet.me)
 */
public class Mat4f {
    private float[][] matrix;

    public Mat4f() {
        this.matrix = new float[4][4];
    }

    public Mat4f identity() {
        this.matrix[0][0] = 1;	this.matrix[0][1] = 0;	this.matrix[0][2] = 0;	this.matrix[0][3] = 0;
        this.matrix[1][0] = 0;	this.matrix[1][1] = 1;	this.matrix[1][2] = 0;	this.matrix[1][3] = 0;
        this.matrix[2][0] = 0;	this.matrix[2][1] = 0;	this.matrix[2][2] = 1;	this.matrix[2][3] = 0;
        this.matrix[3][0] = 0;	this.matrix[3][1] = 0;	this.matrix[3][2] = 0;	this.matrix[3][3] = 1;

        return this;
    }

    public Mat4f translate(Vec3f vector) {
        this.matrix[0][0] = 1;	this.matrix[0][1] = 0;	this.matrix[0][2] = 0;	this.matrix[0][3] = vector.getX();
        this.matrix[1][0] = 0;	this.matrix[1][1] = 1;	this.matrix[1][2] = 0;	this.matrix[1][3] = vector.getY();
        this.matrix[2][0] = 0;	this.matrix[2][1] = 0;	this.matrix[2][2] = 1;	this.matrix[2][3] = vector.getZ();
        this.matrix[3][0] = 0;	this.matrix[3][1] = 0;	this.matrix[3][2] = 0;	this.matrix[3][3] = 1;

        return this;
    }

    public Mat4f rotate(Vec3f vector) {
        Mat4f rotateX = new Mat4f();
        Mat4f rotateY = new Mat4f();
        Mat4f rotateZ = new Mat4f();

        Vec3f rotatedVector = new Vec3f((float) Math.toRadians(vector.getX()), (float) Math.toRadians(vector.getY()), (float) Math.toRadians(vector.getZ()));

        rotateZ.matrix[0][0] = (float)Math.cos(rotatedVector.getZ()); rotateZ.matrix[0][1] = -(float)Math.sin(rotatedVector.getZ()); rotateZ.matrix[0][2] = 0; rotateZ.matrix[0][3] = 0;
        rotateZ.matrix[1][0] = (float)Math.sin(rotatedVector.getZ()); rotateZ.matrix[1][1] =  (float)Math.cos(rotatedVector.getZ()); rotateZ.matrix[1][2] = 0; rotateZ.matrix[1][3] = 0;
        rotateZ.matrix[2][0] = 0;				  					  rotateZ.matrix[2][1] = 0;									     rotateZ.matrix[2][2] = 1; rotateZ.matrix[2][3] = 0;
        rotateZ.matrix[3][0] = 0;				 	 				  rotateZ.matrix[3][1] = 0;									     rotateZ.matrix[3][2] = 0; rotateZ.matrix[3][3] = 1;

        rotateX.matrix[0][0] = 1; rotateX.matrix[0][1] = 0;										rotateX.matrix[0][2] = 0;									   rotateX.matrix[0][3] = 0;
        rotateX.matrix[1][0] = 0; rotateX.matrix[1][1] = (float)Math.cos(rotatedVector.getX()); rotateX.matrix[1][2] = -(float)Math.sin(rotatedVector.getX()); rotateX.matrix[1][3] = 0;
        rotateX.matrix[2][0] = 0; rotateX.matrix[2][1] = (float)Math.sin(rotatedVector.getX()); rotateX.matrix[2][2] = (float)Math.cos(rotatedVector.getX());  rotateX.matrix[2][3] = 0;
        rotateX.matrix[3][0] = 0; rotateX.matrix[3][1] = 0;										rotateX.matrix[3][2] = 0;									   rotateX.matrix[3][3] = 1;

        rotateY.matrix[0][0] = (float)Math.cos(rotatedVector.getY()); rotateY.matrix[0][1] = 0; rotateY.matrix[0][2] = -(float)Math.sin(rotatedVector.getY()); rotateY.matrix[0][3] = 0;
        rotateY.matrix[1][0] = 0;									  rotateY.matrix[1][1] = 1; rotateY.matrix[1][2] = 0;									   rotateY.matrix[1][3] = 0;
        rotateY.matrix[2][0] = (float)Math.sin(rotatedVector.getY()); rotateY.matrix[2][1] = 0; rotateY.matrix[2][2] = (float)Math.cos(rotatedVector.getY());  rotateY.matrix[2][3] = 0;
        rotateY.matrix[3][0] = 0;									  rotateY.matrix[3][1] = 0; rotateY.matrix[3][2] = 0;									   rotateY.matrix[3][3] = 1;

        this.matrix = rotateZ.mul(rotateY.mul(rotateX)).getMatrix();

        return this;
    }

    public Mat4f scale(Vec3f vector) {
        this.matrix[0][0] = vector.getX(); this.matrix[0][1] = 0;				this.matrix[0][2] = 0;			  this.matrix[0][3] = 0;
        this.matrix[1][0] = 0;			  this.matrix[1][1] = vector.getY(); this.matrix[1][2] = 0;			  this.matrix[1][3] = 0;
        this.matrix[2][0] = 0;			  this.matrix[2][1] = 0;				this.matrix[2][2] = vector.getZ(); this.matrix[2][3] = 0;
        this.matrix[3][0] = 0;			  this.matrix[3][1] = 0;				this.matrix[3][2] = 0;			  this.matrix[3][3] = 1;

        return this;
    }

    public Mat4f projection(float fov, float aspectRatio, float zNear, float zFar) {
        float tanHalfFOV = (float) Math.tan(Math.toRadians(fov / 2));
        float zRange = zNear - zFar;

        this.matrix[0][0] = 1.0f / (tanHalfFOV * aspectRatio); this.matrix[0][1] = 0;					this.matrix[0][2] = 0;						 this.matrix[0][3] = 0;
        this.matrix[1][0] = 0;								  this.matrix[1][1] = 1.0f / tanHalfFOV;	this.matrix[1][2] = 0;						 this.matrix[1][3] = 0;
        this.matrix[2][0] = 0;								  this.matrix[2][1] = 0;					this.matrix[2][2] = (-zNear - zFar) / zRange; this.matrix[2][3] = 2 * zFar * zNear / zRange;
        this.matrix[3][0] = 0;								  this.matrix[3][1] = 0;					this.matrix[3][2] = 1;						 this.matrix[3][3] = 0;

        return this;
    }

    public Mat4f rotateAround(float angle, Vec3f axis) {
        Mat4f rotate = new Mat4f();

        float x = axis.getX();
        float y = axis.getY();
        float z = axis.getZ();

        float sin = (float) Math.sin(Math.toRadians(angle));
        float cos = (float) Math.cos(Math.toRadians(angle));
        float C = 1.0f - cos;

        rotate.matrix[0][0] = cos + x * x * C;     rotate.matrix[0][1] = x * y * C - z * sin; rotate.matrix[0][2] = x * z * C + y * sin; rotate.matrix[0][3] = 0;
        rotate.matrix[1][0] = x * y * C + z * sin; rotate.matrix[1][1] = cos + y * y * C;     rotate.matrix[1][2] = y * z * C - x * sin; rotate.matrix[1][3] = 0;
        rotate.matrix[2][0] = x * z * C - y * sin; rotate.matrix[2][1] = y * z * C + x * sin; rotate.matrix[2][2] = cos + z * z * C;     rotate.matrix[2][3] = 0;
        rotate.matrix[3][0] = 0;				   rotate.matrix[3][1] = 0;					  rotate.matrix[3][2] = 0; 				     rotate.matrix[3][3] = 1;

        this.matrix = rotate.getMatrix();

        return this;
    }

    public Mat4f mul(Mat4f m) {
        Mat4f result = new Mat4f();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result.set(i, j, this.matrix[i][0] * m.get(0, j) +
                        this.matrix[i][1] * m.get(1, j) +
                        this.matrix[i][2] * m.get(2, j) +
                        this.matrix[i][3] * m.get(3, j));
            }
        }

        return result;
    }

    public float[][] getMatrix() {
        return this.matrix;
    }

    public void setMatrix(float[][] matrix) {
        this.matrix = this.matrix;
    }

    public float get(int x, int y) {
        return this.matrix[x][y];
    }

    public void set(int x, int y, float value) {
        this.matrix[x][y] = value;
    }

    public String toString() {
        String matrix = "";
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matrix += (get(i, j)) + ", ";
            }
            matrix += "\n";
        }
        return matrix + "";
    }
}
