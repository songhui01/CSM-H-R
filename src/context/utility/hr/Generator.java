/*
 * Copyright (c) 2023, songhuiyue
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package context.utility.hr;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import java.util.HashMap;
import java.util.Map;

import java.lang.reflect.Field;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 *
 * @author songhuiyue
 */
public class Generator {
    protected static int number_of_groups = 10;
    protected static int number_of_arrays = 5;
    
    protected static List<Integer> array_lengths = new ArrayList<>();
    // this list only stores all possible indexes of each conditions
    protected static List<int[]> indexes_arrays = new ArrayList<>();
    
    // Map the index combination with a probability, the probability is a median of a range obtained by counting the number of groups
    // If the index is not found anywhere, then the output probability would be the default one, which is the {-1}'s probability
    protected static Map<String, Double> indexCombProbabilityMap = new HashMap<>();
    
    public Generator() {}
    
    
    public static void generateIndexCombProbabilityMap(int number_of_g, String generator_collection_className, String[] condition_sequences) throws ClassNotFoundException, IllegalAccessException {
        number_of_groups = number_of_g;
        number_of_arrays = condition_sequences.length;
        
        // Get the Class object for the specified class name
        Class<?> generator_class = Class.forName("context.utility.hr." + generator_collection_className);
        
//        Field[] fields = generator_class.getDeclaredFields();
//        System.out.println(generator_collection_className);

        int possible_combinations = 1;
        try {
            for(int i = 0; i < number_of_arrays; i++) {
                // Get the field object using reflection
                Field field = generator_class.getField(condition_sequences[i]);
                
                // Access the array using the field object
                String[] arr = (String[]) field.get(null);
                System.out.println(condition_sequences[i]);
                array_lengths.add(arr.length);
                
                possible_combinations *= arr.length;
            
            }
            
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        
        if (number_of_g > possible_combinations || number_of_g < 1) {
            throw new IllegalArgumentException("number_of_groups is illegal.");
        }
        
        int[] comb = new int[number_of_arrays];
        //Form indexes array
        formIndexesArray(comb, 0);
        
        // Use a hashmap to store those arrays and mapping to a probability
        // Suppose staying a place instead of visiting home or a restaurant, the prob is always 0.2
        // TODO this type of parameter can be set as a global parameter
//        double step = (double)(1 - 0.2) / number_of_groups;
        double step = (double)(1) / number_of_groups;
        // The probability will be the median of that range
//        System.out.println("step is: "+ step);
        double start = 0.0;
        for (int i = 0; i < indexes_arrays.size(); i++) {
            double tmp = (double)(step + start);
//            System.out.println(tmp);
            indexCombProbabilityMap.put(Arrays.toString(indexes_arrays.get(i)), tmp);
            start = tmp;
        }
        
    }
    
    /**
     * IndexesArrays are {[0,0,0,0], [0,1,0,0] ...} they are all possible combinations of indexes, we only store a limited number of them.
     * @param temp
     * @param level 
     */
    public static void formIndexesArray(int[] temp, int level) {
        if(level == number_of_arrays - 1 && indexes_arrays.size() <= number_of_groups - 1) {
            
            for(int i = 0; i < array_lengths.get(level); i++) {
                temp[level] = i;
                int[] newArray = Arrays.copyOf(temp, temp.length);
                indexes_arrays.add(newArray);
                
                if(indexes_arrays.size() == number_of_groups - 1) {
                    indexes_arrays.add(new int[]{-1});
                    return;
                }
            }
            
        } else if(level < number_of_arrays - 1 && indexes_arrays.size() <= number_of_groups - 1) {
            for(int i = 0; i < array_lengths.get(level); i++) {
                temp[level] = i;
                formIndexesArray(temp, level + 1);
            }
        }
    }
    
    public int getNumber_of_groups() {
        return number_of_groups;
    }

    public void setNumber_of_groups(int number_of_groups) {
        this.number_of_groups = number_of_groups;
    }

    public int getNumber_of_arrays() {
        return number_of_arrays;
    }

    public void setNumber_of_arrays(int number_of_arrays) {
        this.number_of_arrays = number_of_arrays;
    }
    
    public static BufferedWriter getWriter(String fileName) {
        try {
            // Create a FileWriter to write to the file
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            
            return writer;
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    
    public static void closeWriter(BufferedWriter writer) {
        try {
            
            writer.close();
            System.out.println("Content has been written to the file");

        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    
}
