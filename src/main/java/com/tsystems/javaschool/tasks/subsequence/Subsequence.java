package com.tsystems.javaschool.tasks.subsequence;

import java.util.LinkedList;
import java.util.List;

public class Subsequence {

    /**
     * This function will find all indexes of first element from first sequence.
     *                      It's need to be done for correct order of sequence.
     *
     * @param y second sequence.
     * @param elem first elem of first sequence.
     * @return list of indexes from second sequence that contain first element from first sequence.
     */
    public static LinkedList listOfIndexForElem(List y, Object elem){
        LinkedList resultList = new LinkedList();
        for (int i = 0; i < y.size(); i++) {
            if(y.get(i).equals(elem)) {
                resultList.addLast(i);
            }
        }
        return resultList;
    }

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")

    public boolean find(List x, List y) {
        if(x == null || y == null){
            throw new IllegalArgumentException();
        }
        if(x.size() > y.size())
            return false;
        if(x.size() == 0)
            return true;

        LinkedList<Integer> listOfIndexes = listOfIndexForElem(y,x.get(0));
        for (int i = 0; i < listOfIndexes.size() ; i++) {
            List subList = y.subList(listOfIndexes.get(i),y.size());
            if(x.size() > subList.size())
                return false;

            int counter = 0;
            for (int j = 0; j < x.size(); j++) {
                boolean exist = false;
                for (int k = 0; k < subList.size(); k++) {
                    if(x.get(j).equals(subList.get(k))){
                        exist = true;
                        counter++;
                        break;
                    }
                }
                if(!exist) {
                    break;
                }
            }
            if (counter == x.size()){
                return true;
            }
        }
        return false;
    }
}
