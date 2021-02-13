package NeuralNetworkTest;

import java.util.ArrayList;

public class Matrix {
    double [][]data;
    int numRows;
    int numCols;
    
    Matrix(int rows, int cols) { // create matrix with given rows and cols
        numRows = rows;
        numCols = cols;
        data = new double[numRows][numCols];
        
        for(int i = 0; i < numRows; i++) {
            for(int j = 0; j < numCols; j++)
                data[i][j] = Math.random()*2 - 1; // values are random between -1 and 1
        }
    }
    
    public void add(double num) { // add given value to all matrix elements
        for(int i = 0; i < numRows; i++) {
            for(int j = 0; j < numCols; j++)
               data[i][j] += num;
        }
    }
    
    public void add(Matrix m) { // add matrix elements to corresponding matrix elements
        for(int i = 0; i < numRows; i++) {
            for(int j = 0; j < numCols; j++)
               data[i][j] += m.data[i][j];
        }
    }
    
    public static Matrix subtract(Matrix a, Matrix b) { // subtract Matrix b from Matrix a
        Matrix sub = new Matrix(a.numRows, a.numCols); // create empty matrix with a's dimensions
        for(int i = 0; i < a.numRows; i++) {
            for(int j = 0; j < a.numCols; j++)
               sub.data[i][j] = a.data[i][j] - b.data[i][j]; // element equal to respective a - b element
        }
        
        return sub; // return created matrix
    }
    
    public static Matrix transpose(Matrix m) { // give transpose of given matrix
        Matrix trans = new Matrix(m.numCols, m.numRows); // create empty matrix with m's rows and columns flipped
        for(int i = 0; i < m.numRows; i++) {
            for(int j = 0; j < m.numCols; j++)
               trans.data[j][i] = m.data[i][j]; // transposed element of m
        }
        
        return trans;
    }
    
    public static Matrix multiply(Matrix a, Matrix b) { // give product of the two matrices using dot product
        Matrix mult = new Matrix(a.numRows, b.numCols); // create empty matrix
        double sum; // keeps track of dot product
        
        for(int i = 0; i < mult.numRows; i++) {
            for(int j = 0; j < mult.numCols; j++) {
                sum = 0;
                for(int k = 0; k < a.numCols; k++)
                    sum += a.data[i][k]*b.data[k][j]; // dot product down a's columns and across b's rows
                mult.data[i][j] = sum; // mult's element is the ab dot product
            }
        }
        
        return mult;
    }
    
    public void multiply(Matrix m) { // multiply martix's elements with elements of given matrix
        for(int i = 0; i < m.numRows; i++) {
            for(int j = 0; j < m.numCols; j++)
               this.data[i][j] *= m.data[i][j]; // multiply with m's element
        }
    }
    
    public void multiply(double num) { // multiply all elements by given number
        for(int i = 0; i < numRows; i++) {
            for(int j = 0; j < numCols; j++)
               this.data[i][j] *= num;
        }
    }
    
    public void sigmoid() { // performs sigmoid function on each matrix element
        for(int i = 0; i < numRows; i++) {
            for(int j = 0; j < numCols; j++)
               this.data[i][j] = 1/(1 + Math.exp(-this.data[i][j]));
        }
    }
    
    public Matrix dsigmoid() { // gives matrix on which the derivative sigmoid function has been performed
        Matrix dsig = new Matrix(this.numRows, this.numCols);
        for(int i = 0; i < numRows; i++) {
            for(int j = 0; j < numCols; j++)
               dsig.data[i][j] = this.data[i][j] * (1 - this.data[i][j]);
        }
        
        return dsig;
    }
    
    public static Matrix fromArray(double[] a) {
        Matrix m = new Matrix(a.length, 1); // create singular column matrix
        for (int i = 0; i < a.length; i++)
            m.data[i][0] = a[i]; // set element from array
        
        return m;
    }
    
    public ArrayList<Double> toArray() {
        ArrayList<Double> a = new ArrayList<>();
        
        for (int i = 0; i < this.numRows; i++) {
            a.add(this.data[i][0]);
        }
        
        return a;
    }
}
