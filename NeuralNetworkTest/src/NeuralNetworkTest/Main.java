package NeuralNetworkTest;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // data from xor gate (strict or)
        double[][] input = { {0, 0},
                             {1, 0},
                             {0, 1},
                             {1, 1} };
        
        double[][] expectedOutput = { {0}, {1}, {1}, {0} };
        
        // create neural network
        NeuralNetwork brain = new NeuralNetwork(2, 10, 1);
        brain.fit(input, expectedOutput, 50000); // train network (large bc small input)
        
        ArrayList<Double> output;
        for(double d[] : input) {
            output = brain.predict(d);
            System.out.println(output.toString());
        }
    }

}
