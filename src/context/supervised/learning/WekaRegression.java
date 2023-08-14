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
package context.supervised.learning;

/**
 *
 * @author songhuiyue
 * weka libs:
 * https://stackoverflow.com/questions/50890984/maven-project-javafx-cant-add-weka-dependency
 */
import weka.classifiers.functions.SMO;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;

import weka.classifiers.functions.LinearRegression;
import weka.classifiers.Evaluation;

import weka.classifiers.functions.supportVector.PolyKernel;
import weka.classifiers.functions.SMOreg;


import weka.classifiers.trees.M5P;

//https://github.com/fracpete/lightgbm-weka-package
import weka.classifiers.functions.LightGBM;

import weka.classifiers.rules.DecisionTable;
import weka.classifiers.functions.MultilayerPerceptron;



public class WekaRegression {
    public static void main(String[] args) throws Exception {
        // Load data from the ARFF file
        DataSource source = new DataSource("config/data_2000_times_20500_regression.arff");
        Instances data = source.getDataSet();
        if (data.classIndex() == -1) {
            data.setClassIndex(data.numAttributes() - 1);
        }

        System.out.println("-------LinearRegression---------");
        // Create and configure the linear regression model
        LinearRegression model1 = new LinearRegression();
        model1.buildClassifier(data);
        // Evaluate the model using cross-validation
        Evaluation evaluation = new Evaluation(data);
        evaluation.crossValidateModel(model1, data, 10, new java.util.Random(1));
        System.out.println(evaluation.toSummaryString());

        System.out.println("-------SMOreg---------");
//        SMOreg model2 = new SMOreg();
//        // Evaluate the model using cross-validation
//        evaluation = new Evaluation(data);
//        evaluation.crossValidateModel(model2, data, 10, new java.util.Random(1));
//        System.out.println(evaluation.toSummaryString());
        
        System.out.println("-------PolyKernel---------");
//        PolyKernel kernel = new PolyKernel();
//        kernel.setExponent(2);
//        SMOreg model3 = new SMOreg();
//        model3.setKernel(kernel);
//        // Evaluate the model using cross-validation
//        evaluation = new Evaluation(data);
//        evaluation.crossValidateModel(model3, data, 10, new java.util.Random(1));
//        System.out.println(evaluation.toSummaryString());
        

        System.out.println("-------M5P---------");
        M5P model4 = new M5P();
        // Evaluate the model using cross-validation
        evaluation = new Evaluation(data);
        evaluation.crossValidateModel(model4, data, 10, new java.util.Random(1));
        System.out.println(evaluation.toSummaryString());


        System.out.println("-------LightGBM---------");
//        LightGBM model5 = new LightGBM();
//        // Evaluate the model using cross-validation
//        evaluation = new Evaluation(data);
//        evaluation.crossValidateModel(model5, data, 10, new java.util.Random(1));
//        System.out.println(evaluation.toSummaryString());
        
        
        System.out.println("-------DecisionTable---------");
        DecisionTable model6 = new DecisionTable();
        // Evaluate the model using cross-validation
        evaluation = new Evaluation(data);
        evaluation.crossValidateModel(model6, data, 10, new java.util.Random(1));
        System.out.println(evaluation.toSummaryString());

        
        System.out.println("-------MultilayerPerceptron---------");
        MultilayerPerceptron model7 = new MultilayerPerceptron();
        // Evaluate the model using cross-validation
        evaluation = new Evaluation(data);
        evaluation.crossValidateModel(model7, data, 10, new java.util.Random(1));
        System.out.println(evaluation.toSummaryString());


//        // Make predictions on new instances (replace with your new data)
//        // For example, let's predict the decision for a new person:
//        Instance newInstance = new DenseInstance(data.numAttributes());
//        newInstance.setValue(data.attribute("sex"), "male");
//        newInstance.setValue(data.attribute("age"), 35);
//        newInstance.setValue(data.attribute("blood_sugar_condition"), "normal");
//        newInstance.setValue(data.attribute("health_condition"), "good");
//        newInstance.setDataset(data);
//
//        double prediction = svm.classifyInstance(newInstance);
//        String predictedDecision = data.classAttribute().value((int) prediction);

//        System.out.println("Predicted Decision: " + predictedDecision);
    }
}


