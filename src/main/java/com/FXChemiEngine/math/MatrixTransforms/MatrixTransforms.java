package com.FXChemiEngine.math.MatrixTransforms;

import com.FXChemiEngine.math.UnityMath.Vector3;

/**Matrix and function for transpose 3 and 2 dimensional objects*/
public class MatrixTransforms {
    public static int scale_count = 1;
    public static int coord_x;
    public static int coord_y;
    public static int coord_z;
    public static float[][] Matrix_Offset = new float[4][4];
    public static float[][] Matrix_Transformation = new float[4][4];
    //matrix for move object, object_coord + matrix_coord
    static {
    Matrix_Offset[0][0] = 1; Matrix_Offset[0][1] = 0; Matrix_Offset[0][2] = 0; Matrix_Offset[0][3] = 0;
    Matrix_Offset[1][0] = 0; Matrix_Offset[1][1] = 1; Matrix_Offset[1][2] = 0; Matrix_Offset[1][3] = 0;
    Matrix_Offset[2][0] = 0; Matrix_Offset[2][1] = 0; Matrix_Offset[2][2] = 1; Matrix_Offset[2][3] = 0;
    Matrix_Offset[3][0] = coord_x; Matrix_Offset[3][1] = coord_y; Matrix_Offset[3][2] = coord_z; Matrix_Offset[3][3] = 1;}

    public static float[][] Matrix_Scaling = new float[4][4];
    //matrix for scaling object on scale_count
    static {
    Matrix_Scaling[0][0] = 1; Matrix_Scaling[0][1] = 0; Matrix_Scaling[0][2] = 0; Matrix_Scaling[0][3] = 0;
    Matrix_Scaling[1][0] = 0; Matrix_Scaling[1][1] = 1; Matrix_Scaling[1][2] = 0; Matrix_Scaling[1][3] = 0;
    Matrix_Scaling[2][0] = 0; Matrix_Scaling[2][1] = 0; Matrix_Scaling[2][2] = 1; Matrix_Scaling[2][3] = 0;
    Matrix_Scaling[3][0] = 0; Matrix_Scaling[3][1] = 0; Matrix_Scaling[3][2] = 0; Matrix_Scaling[3][3] = (float)(scale_count/10.0);}

    public static float[][] Matrix_ReflectionOX = new float[4][4];
    //matrix for reflect object of OX axis
    static {
    Matrix_ReflectionOX[0][0] = -1; Matrix_ReflectionOX[0][1] = 0; Matrix_ReflectionOX[0][2] = 0; Matrix_ReflectionOX[0][3] = 0;
    Matrix_ReflectionOX[1][0] = 0; Matrix_ReflectionOX[1][1] = 1; Matrix_ReflectionOX[1][2] = 0; Matrix_ReflectionOX[1][3] = 0;
    Matrix_ReflectionOX[2][0] = 0; Matrix_ReflectionOX[2][1] = 0; Matrix_ReflectionOX[2][2] = 1; Matrix_ReflectionOX[2][3] = 0;
    Matrix_ReflectionOX[3][0] = 0; Matrix_ReflectionOX[3][1] = 0; Matrix_ReflectionOX[3][2] = 1; Matrix_ReflectionOX[3][3] = 1;}

    public static float[][] Matrix_ReflectionOY = new float[4][4];
    //matrix for reflect object of OY axis
    static {
    Matrix_ReflectionOY[0][0] = 1; Matrix_ReflectionOY[0][1] = 0; Matrix_ReflectionOY[0][2] = 0; Matrix_ReflectionOY[0][3] = 0;
    Matrix_ReflectionOY[1][0] = 0; Matrix_ReflectionOY[1][1] = -1; Matrix_ReflectionOY[1][2] = 0; Matrix_ReflectionOY[1][3] = 0;
    Matrix_ReflectionOY[2][0] = 0; Matrix_ReflectionOY[2][1] = 0; Matrix_ReflectionOY[2][2] = 1; Matrix_ReflectionOY[2][3] = 0;
    Matrix_ReflectionOY[3][0] = 0; Matrix_ReflectionOY[3][1] = 0; Matrix_ReflectionOY[3][2] = 0; Matrix_ReflectionOY[3][3] = 1;}

    public static float[][] Matrix_ReflectionXY = new float[4][4];
    //matrix for reflect object of XY axis
    static {
    Matrix_ReflectionXY[0][0] = 1; Matrix_ReflectionXY[0][1] = 0; Matrix_ReflectionXY[0][2] = 0; Matrix_ReflectionXY[0][3] = 0;
    Matrix_ReflectionXY[1][0] = 0; Matrix_ReflectionXY[1][1] = 1; Matrix_ReflectionXY[1][2] = 0; Matrix_ReflectionXY[1][3] = 0;
    Matrix_ReflectionXY[2][0] = 0; Matrix_ReflectionXY[2][1] = 0; Matrix_ReflectionXY[2][2] = -1; Matrix_ReflectionXY[2][3] = 0;
    Matrix_ReflectionXY[3][0] = 0; Matrix_ReflectionXY[3][1] = 0; Matrix_ReflectionXY[3][2] = 0; Matrix_ReflectionXY[3][3] = 1;}

    public static float[][] Matrix_RotationZ = new float[4][4];
    //matrix for rotation of Z axis
    static {
    Matrix_RotationZ[0][0] = (float)Math.cos(Math.PI / 2); Matrix_RotationZ[0][1] = (float)Math.sin(Math.PI / 2); Matrix_RotationZ[0][2] = 0; Matrix_RotationZ[0][3] = 0;
    Matrix_RotationZ[1][0] = (float)-Math.sin(Math.PI / 2); Matrix_RotationZ[1][1] = (float)Math.cos(Math.PI / 2); Matrix_RotationZ[1][2] = 0; Matrix_RotationZ[1][3] = 0;
    Matrix_RotationZ[2][0] = (float)(-50 * (Math.cos(Math.PI / 2) - 1) + 50 * Math.sin(Math.PI / 2)); Matrix_RotationZ[2][1] = (float)(-50 * (Math.cos(Math.PI / 2) - 1) - 50 * Math.sin(Math.PI / 2)); Matrix_RotationZ[2][2] = 0; Matrix_RotationZ[2][3] = 1;
    Matrix_RotationZ[3][0] = 0; Matrix_RotationZ[3][1] = 0; Matrix_RotationZ[3][2] = 0; Matrix_RotationZ[3][3] = 0;}
    public static float[][] Matrix_RotationY = new float[4][4];
    //matrix for rotation of Y axis
    static {
    Matrix_RotationY[0][0] = (float)Math.cos(Math.PI / 2); Matrix_RotationY[0][1] = (float)(Math.sin(Math.PI / 2)); Matrix_RotationY[0][2] = 0;
    Matrix_RotationY[1][0] = (float)-Math.sin(Math.PI / 2); Matrix_RotationY[1][1] = (float)(Math.cos(Math.PI / 2)); Matrix_RotationY[1][2] = 0;
    Matrix_RotationY[2][0] = (float)(-50 * (Math.cos(Math.PI / 2) - 1) + 50 * Math.sin(Math.PI / 2)); Matrix_RotationY[2][1] = (float)(-50 * (Math.cos(Math.PI / 2) - 1) - 50 * Math.sin(Math.PI / 2)); Matrix_RotationY[2][2] = 1;
    Matrix_RotationY[3][0] = 0; Matrix_RotationY[3][1] = 0; Matrix_RotationY[3][2] = 0; Matrix_RotationY[3][3] = 0;}

    public static float[][] Matrix_RotationX = new float[4][4];
    //matrix for rotation of X axis
    static {
    Matrix_RotationX[0][0] = (float)Math.cos(Math.PI / 2); Matrix_RotationX[0][1] = (float)Math.sin(Math.PI / 2); Matrix_RotationX[0][2] = 0;
    Matrix_RotationX[1][0] = (float)-Math.sin(Math.PI / 2); Matrix_RotationX[1][1] = (float)Math.cos(Math.PI / 2); Matrix_RotationX[1][2] = 0;
    Matrix_RotationX[2][0] = (float)(-50 * (Math.cos(Math.PI / 2) - 1) + 50 * Math.sin(Math.PI / 2)); Matrix_RotationX[2][1] = (float)(-50 * (Math.cos(Math.PI / 2) - 1) - 50 * Math.sin(Math.PI / 2)); Matrix_RotationX[2][2] = 1;
        Matrix_RotationX[3][0] = (float)0.0; Matrix_RotationX[3][1] = 0; Matrix_RotationX[3][2] = 0; Matrix_RotationX[3][3] = 0;}

    public static float[][] setTransformationMatrix(Vector3 position, Vector3 rotation, Vector3 scale){
        float[][] result = new float[4][4];

        Offset(position, new Vector3());
        RotationX(rotation.x, new Vector3());
        RotationY(rotation.y, new Vector3());
        RotationZ(rotation.z, new Vector3());
        Scale(scale, new Vector3());

        float[][] rotationMatrix = MatrixMultiplication(Matrix_RotationX, MatrixMultiplication(Matrix_RotationY, Matrix_RotationZ, 4, 4, 4), 4, 4, 4);

        result = MatrixMultiplication(Matrix_Offset, MatrixMultiplication(rotationMatrix, Matrix_Scaling, 4, 4, 4), 4, 4, 4);

        Matrix_Transformation = result;

        return result;
    }

   /* public static float[] multiply(Matrix4f matrix, Matrix4f other) {
        Matrix4f result = Matrix4f.identity();

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                result.set(i, j, matrix.get(i, 0) * other.get(0, j) +
                        matrix.get(i, 1) * other.get(1, j) +
                        matrix.get(i, 2) * other.get(2, j) +
                        matrix.get(i, 3) * other.get(3, j));
            }
        }

        return result;
    }*/

    public static void Scale(Vector3 scale, Vector3 point){
        float[][] newDot = new float[1][4];
        newDot[0][0] = point.x;
        newDot[0][1] = point.y;
        newDot[0][2] = point.z;
        newDot[0][3] = 1;

        Matrix_Scaling[0][0] = scale.x;
        Matrix_Scaling[1][1] = scale.y;
        Matrix_Scaling[2][2] = scale.z;

        newDot = MatrixMultiplication(newDot, Matrix_Scaling, 1, 4, 4);

        point.x = (int)(newDot[0][0]);
        point.y = (int)(newDot[0][1]);
        point.z = (int)(newDot[0][2]);
    }
    public static void Offset(Vector3 position, Vector3 point){
        float[][] newDot = new float[1][4];
        newDot[0][0] = point.x;
        newDot[0][1] = point.y;
        newDot[0][2] = point.z;
        newDot[0][3] = 1;

        Matrix_Offset[3][0] = position.x;
        Matrix_Offset[3][1] = position.y;
        Matrix_Offset[3][2] = position.z;

        newDot = MatrixMultiplication(newDot, Matrix_Offset, 1, 4, 4);

        point.x = (int)(newDot[0][0]);
        point.y = (int)(newDot[0][1]);
        point.z = (int)(newDot[0][2]);
    }

    /**Multiplication two matrix*/
    public static float[][] MatrixMultiplication(float[][] matrixA, float[][] matrixB, int a, int b, int c) {
        float[][] matrixC = new float[a][b];
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < b; j++) {
                matrixC[i][j] = 0;
                for (int k = 0; k < c; k++) {
                    matrixC[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }
        return matrixC;
    }

    /**Function for Rotation in X axis on ct angle*/
    public static Vector3 RotationX(float ct, Vector3 point) {
        float[][] newDot = new float[1][4];
        newDot[0][0] = point.x;
        newDot[0][1] = point.y;
        newDot[0][2] = point.z;
        newDot[0][3] = 1;

        double angel = (-ct * Math.PI) / 180.0;

        Matrix_RotationX[0][0] = 1; Matrix_RotationX[0][1] = 0; Matrix_RotationX[0][2] = 0; Matrix_RotationX[0][3] = 0;
        Matrix_RotationX[1][0] = 0; Matrix_RotationX[1][1] = (float)Math.cos(angel); Matrix_RotationX[1][2] = (float)-Math.sin(angel); Matrix_RotationX[1][3] = 0;
        Matrix_RotationX[2][0] = 0; Matrix_RotationX[2][1] = (float)Math.sin(angel); Matrix_RotationX[2][2] = (float)Math.cos(angel); Matrix_RotationX[2][3] = 0;

        newDot = MatrixMultiplication(newDot, Matrix_RotationX, 1, 4, 4);

        point.x = (int)(newDot[0][0]);
        point.y = (int)(newDot[0][1]);
        point.z = (int)(newDot[0][2]);
        return point;
    }

    /**Function for Rotation in Y axis on ct angle*/
    public static Vector3 RotationY(float ct, Vector3 point) {
        float[][] newDot = new float[1][4];
        newDot[0][0] = point.x;
        newDot[0][1] = point.y;
        newDot[0][2] = point.z;
        newDot[0][3] = 1;

        double angel = (-ct * Math.PI) / 180.0;

        Matrix_RotationY[0][0] = (float)Math.cos(angel); Matrix_RotationY[0][1] = 0; Matrix_RotationY[0][2] = (float)Math.sin(angel); Matrix_RotationY[0][3] = 0;
        Matrix_RotationY[1][0] = 0; Matrix_RotationY[1][1] = 1; Matrix_RotationY[1][2] = 0; Matrix_RotationY[1][3] = 0;
        Matrix_RotationY[2][0] = (float)-Math.sin(angel); Matrix_RotationY[2][1] = 0; Matrix_RotationY[2][2] = (float)Math.cos(angel); Matrix_RotationY[2][3] = 0;

        newDot = MatrixMultiplication(newDot, Matrix_RotationY, 1, 4, 4);

        point.x = (int)(newDot[0][0]);
        point.y = (int)(newDot[0][1]);
        point.z = (int)(newDot[0][2]);
        return point;
    }

    /**Function for Rotation in Z axis on ct angle*/
    public static Vector3 RotationZ(float ct, Vector3 point) {
        float[][] newDot = new float[1][4];
        newDot[0][0] = point.x;
        newDot[0][1] = point.y;
        newDot[0][2] = point.z;
        newDot[0][3] = 1;

        double angel = (-ct * Math.PI) / 180.0;

        Matrix_RotationZ[0][0] = (float)Math.cos(angel); Matrix_RotationZ[0][1] = (float)Math.sin(angel); Matrix_RotationZ[0][2] = 0; Matrix_RotationZ[0][3] = 0;
        Matrix_RotationZ[1][0] = (float)-Math.sin(angel); Matrix_RotationZ[1][1] = (float)Math.cos(angel); Matrix_RotationZ[1][2] = 0; Matrix_RotationZ[1][3] = 0;
        Matrix_RotationZ[2][0] = 0; Matrix_RotationZ[2][1] = 0; Matrix_RotationZ[2][2] = 1; Matrix_RotationZ[2][3] = 0;

        newDot = MatrixMultiplication(newDot, Matrix_RotationZ, 1, 4, 4);

        point.x = (int)(newDot[0][0]);
        point.y = (int)(newDot[0][1]);
        point.z = (int)(newDot[0][2]);
        return point;
    }

    /**Function for Scaling object on ck*/
    public static Vector3 Scaling(int ck, Vector3 point) {
        float[][] newDot = new float[1][4];
        newDot[0][0] = point.x;
        newDot[0][1] = point.y;
        newDot[0][2] = point.z;
        newDot[0][3] = 1;

        Matrix_Scaling[3][3] = (float)(ck / 10.0);

        newDot = MatrixMultiplication(newDot, Matrix_Scaling, 1, 4, 4);

        point.x = (int)(newDot[0][0]);
        point.y = (int)(newDot[0][1]);
        point.z = (int)(newDot[0][2]);
        return point;
    }

    /**Function for Move object in object_coord + cx, cy, cz*/
    public static Vector3 Offset(int cx, int cy, int cz, Vector3 point) {
        float[][] newDot = new float[1][4];
        newDot[0][0] = point.x;
        newDot[0][1] = point.y;
        newDot[0][2] = point.z;
        newDot[0][3] = 1;

        Matrix_Offset[3][0] = 1 * cx;
        Matrix_Offset[3][1] = 1 * cy;
        Matrix_Offset[3][2] = -1 * cz;

        newDot = MatrixMultiplication(newDot, Matrix_Offset, 1, 4, 4);

        point.x = (int)(newDot[0][0]);
        point.y = (int)(newDot[0][1]);
        point.z = (int)(newDot[0][2]);
        return point;
    }
}
