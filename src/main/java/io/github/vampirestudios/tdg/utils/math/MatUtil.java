package io.github.vampirestudios.tdg.utils.math;

/**
 * @author Joe van der Zwet (https://joezwet.me)
 */
public class MatUtil {

    public static Mat4f createTransformationMatrix(Vec3f translation, Vec3f rotation, Vec3f scale) {
        Mat4f matrix = new Mat4f().identity();
        matrix.translate(translation);
        matrix.rotate(rotation);
        matrix.scale(scale);
        return matrix;
    }
}
