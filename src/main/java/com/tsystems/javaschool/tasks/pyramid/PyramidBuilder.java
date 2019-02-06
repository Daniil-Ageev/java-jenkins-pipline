package com.tsystems.javaschool.tasks.pyramid;

import java.util.Collections;
import java.util.List;

import javafx.util.Pair;

public class PyramidBuilder {

    /**
     * This function calculate a_n element from sequence.
     *             Each of element from this sequence is
     *             responsible for permissible array size for pyramid
     *
     * @param step th element
     * @return permissible array size
     */
    public static double function(int step){
        return  0.5 * step * (step + 1);
    }

    /**
     * This function validate array size and return height of pyramid.
     *
     * @param arraySize - array size to build
     * @return pair that consist:
     *                   1 element - boolean variable that check if size valid
     *                   2 element - height of pyramid
     */

    public static Pair<Boolean, Integer> validatePyramid(int arraySize){
        double validSize = 0;
        int step = 1;
        while (validSize < arraySize){
            validSize = function(step++);
        }
        return new Pair<>(validSize == arraySize, step - 1);
    }

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */

    public int[][] buildPyramid(List<Integer> inputNumbers) {
        Pair<Boolean,Integer> pair = validatePyramid(inputNumbers.size());
        if (!pair.getKey())
            throw new CannotBuildPyramidException();
        try {
            Collections.sort(inputNumbers, Collections.reverseOrder());
        } catch (NullPointerException e){
            throw new CannotBuildPyramidException();
        }
        int[][] array = new int[pair.getValue()][2*pair.getValue() - 1];
        int numElem = 0;
        for(int i = array.length - 1; i >= 0; i--){
            boolean parity = true;
            for (int j = array[i].length  - (array.length - i); j >= array.length - i - 1; j--){
                if(parity) {
                    array[i][j] = inputNumbers.get(numElem++);
                    parity = false;
                } else {
                    parity = true;
                }
            }
        }
        return array;
    }


}
