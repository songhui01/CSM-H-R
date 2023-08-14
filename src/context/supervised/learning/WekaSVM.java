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
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;

import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.classifiers.lazy.IBk;
import weka.classifiers.functions.SMO;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.bayes.NaiveBayes;


public class WekaSVM {
    public static void main(String[] args) throws Exception {
        // Load data from the ARFF file
        DataSource source = new DataSource("config/data_2000_times_20500.arff");
        Instances data = source.getDataSet();
        if (data.classIndex() == -1) {
            data.setClassIndex(data.numAttributes() - 1);
        }

        System.out.println("-------J48---------");
        // Create a classifier
        Classifier classifier = new J48();
        // Train the classifier
        classifier.buildClassifier(data);
        // Evaluate the model using cross-validation
        Evaluation evaluation = new Evaluation(data);
        evaluation.crossValidateModel(classifier, data, 10, new java.util.Random(1));
        System.out.println(evaluation.toSummaryString());

        
        System.out.println("-------RF---------");
        // Create a classifier
        classifier = new RandomForest();

        // Train the classifier
        classifier.buildClassifier(data);
        // Evaluate the model using cross-validation
        evaluation = new Evaluation(data);
        evaluation.crossValidateModel(classifier, data, 10, new java.util.Random(1));
        System.out.println(evaluation.toSummaryString());
        
        
        System.out.println("-------IBk---------");
        // Create a classifier
        classifier = new IBk();
        // Train the classifier
        classifier.buildClassifier(data);
        // Evaluate the model using cross-validation
        evaluation = new Evaluation(data);
        evaluation.crossValidateModel(classifier, data, 10, new java.util.Random(1));
        System.out.println(evaluation.toSummaryString());
        
        System.out.println("-------SMO---------");
        // Create a classifier
        classifier = new SMO();
        // Train the classifier
        classifier.buildClassifier(data);
        // Evaluate the model using cross-validation
        evaluation = new Evaluation(data);
        evaluation.crossValidateModel(classifier, data, 10, new java.util.Random(1));
        System.out.println(evaluation.toSummaryString());
        
//        // Get the coefficients of the attributes
//        double[] coefficients = evaluation.coefficients();
//
//        // Identify the unaffected attributes (e.g., with coefficients close to zero)
//        for (int i = 0; i < coefficients.length; i++) {
//            if (Math.abs(coefficients[i]) < 0.001) {
//                System.out.println("Attribute " + i + " is likely unaffected.");
//            }
//        }

        
        System.out.println("-------MultilayerPerceptron---------");
        // Create a classifier
        classifier = new MultilayerPerceptron();
        // Train the classifier
        classifier.buildClassifier(data);
        // Evaluate the model using cross-validation
        evaluation = new Evaluation(data);
        evaluation.crossValidateModel(classifier, data, 10, new java.util.Random(1));
        System.out.println(evaluation.toSummaryString());
        
        System.out.println("-------NaiveBayes---------");
        // Create a classifier
        classifier = new NaiveBayes();
        // Train the classifier
        classifier.buildClassifier(data);
        // Evaluate the model using cross-validation
        evaluation = new Evaluation(data);
        evaluation.crossValidateModel(classifier, data, 10, new java.util.Random(1));
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


