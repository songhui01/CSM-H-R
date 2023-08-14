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
import weka.classifiers.Evaluation;
import weka.classifiers.functions.Logistic;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class WekaLogisticRegression {
    public static void main(String[] args) throws Exception {
        // Load the ARFF file
        DataSource source = new DataSource("config/data_2000_times_20500_regression.arff");
        Instances data = source.getDataSet();
        if (data.classIndex() == -1) {
            data.setClassIndex(data.numAttributes() - 1);
        }

        // Create a Logistic Regression model
        Logistic logistic = new Logistic();
        logistic.buildClassifier(data);

        // Evaluate the model using cross-validation
        Evaluation eval = new Evaluation(data);
        eval.crossValidateModel(logistic, data, 10, new java.util.Random(1));
        System.out.println(eval.toSummaryString());

        // Predict probability for a new instance (replace attribute values with real data)
        double[] newValues = new double[]{data.attribute(0).indexOfValue("Fair"),
                                          data.attribute(1).indexOfValue("Normal"),
                                          data.attribute(2).indexOfValue("Happy"),
                                          data.attribute(3).indexOfValue("26-35"),
                                          data.attribute(4).indexOfValue("Male"),
                                          data.attribute(5).indexOfValue("Professor")};
        // Create a new instance using the attribute values
        DenseInstance newInstance = new DenseInstance(1.0, newValues);
        newInstance.setDataset(data);

        // Predict the probability for the new instance
        double predictedProbability = logistic.distributionForInstance(newInstance)[1]; // Index 1 is for class "go_restaurant"

        System.out.println("Predicted Probability of going to a restaurant: " + predictedProbability);
    }
}

