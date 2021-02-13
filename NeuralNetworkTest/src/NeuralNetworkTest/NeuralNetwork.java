package NeuralNetworkTest;

import java.util.ArrayList;

public class NeuralNetwork {
    Matrix weightsIn; // input & hidden layer
    Matrix weightsOut; // output and hidden layer
    Matrix biasHid; // hidden layer
    Matrix biasOut; // output layer
    double learnRate = .01; // learning rate of network
    
    public NeuralNetwork(int in, int hid, int out) {
        weightsIn = new Matrix(hid, in);
        weightsOut = new Matrix(out, hid);
        
        biasHid = new Matrix(hid, 1);
        biasOut = new Matrix(out, 1);
    }
    
    public ArrayList<Double> predict(double[] x) { // forward propagation
        Matrix input = Matrix.fromArray(x); // given inputs
        Matrix hidden = Matrix.multiply(weightsIn, input); // multiply with weights of hidden layer
        hidden.add(biasHid); // add bias
        hidden.sigmoid();
        
        Matrix output = Matrix.multiply(weightsOut, hidden); // multiply weights of hidden layer with calculated matrix
        output.add(biasOut); // add bias
        output.sigmoid();
        
        return output.toArray(); // flatten to list and return
    }
    
    public void train(double[] x, double[] y) { // train network with input and expected output
        // forward propagate
        Matrix input = Matrix.fromArray(x); // given input x
        Matrix hidden = Matrix.multiply(weightsIn, input); // multiply with hidden layer weights
        hidden.add(biasHid); // add bias
        hidden.sigmoid();
        
        Matrix output = Matrix.multiply(weightsOut, hidden); // multiply hidden weights with previous matrix
        output.add(biasOut); // add bias
        output.sigmoid();
        
        // set target
        Matrix target = Matrix.fromArray(y); // given input y
        
        // calculate error from set target and output
        Matrix error = Matrix.subtract(target, output);
        
        // compute gradients for back propagation
        Matrix gradient = output.dsigmoid();
        gradient.multiply(error);
        gradient.multiply(learnRate);
        
        Matrix hiddenTrans = Matrix.transpose(hidden); // take transpose of hidden matrix
        Matrix weightsOutDelta = Matrix.multiply(gradient, hiddenTrans); // calculate deltas
        
        // tweak output weights and biases
        weightsOut.add(weightsOutDelta);
        biasOut.add(gradient);
        
        Matrix weightsOutTrans = Matrix.transpose(weightsOut); // take transpose of output weights
        Matrix hidError = Matrix.multiply(weightsOutTrans, error); // multiply new weights with error matrix
        
        // compute gradients for back propagation
        Matrix hidGradient = hidden.dsigmoid();
        hidGradient.multiply(hidError);
        hidGradient.multiply(learnRate);
        
        Matrix inTrans = Matrix.transpose(input); // take transpose of input matrix
        Matrix inDelta = Matrix.multiply(hidGradient, inTrans); // calculate deltas
        
        // tweak input/hidden weights and biases
        weightsIn.add(inDelta);
        biasHid.add(hidGradient);
    }
    
    public void fit(double[][] x, double[][] y, int epochs) {
        for(int i = 0; i < epochs; i++) { // for given number of times
            int sample = (int)(Math.random() * x.length); // create sample point
            this.train(x[sample],  y[sample]); // train network
        }
    }
}
