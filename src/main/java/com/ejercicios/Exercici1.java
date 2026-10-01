package com.ejercicios;

import java.util.Arrays;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Exercici1 {

    public static void main(String[] args) {

        // Datos de entrada
        double[] data = {10, 20, 30, 40, 50};

        // Objeto para almacenar los resultados
        Results results = new Results();

        // Crea una barrera para 3 hilos
        CyclicBarrier barrier = new CyclicBarrier(3, new Runnable() {
            @Override
            public void run() {
                // Cosas
            }
        });

        // Executor de 3 hilos
        ExecutorService executor = Executors.newFixedThreadPool(3);
    }
}

class Results {
    double sum;
    double mean;
    double standardDeviation;
}

class calculateSum implements Runnable {
    private final int taskId;
    private final double[] data;
    private final Results resultsObj;

    public calculateSum(int taskId, double[] data, Results resultsObj) {
        this.taskId = taskId;
        this.data = data;
        this.resultsObj = resultsObj;
    }

    @Override
    public void run() {
        resultsObj.sum = Arrays.stream(data).sum();
    }
}

class calculateMean implements Runnable {
    private final int taskId;
    private final double[] data;
    private final Results resultsObj;

    public calculateMean(int taskId, double[] data, Results resultsObj) {
        this.taskId = taskId;
        this.data = data;
        this.resultsObj = resultsObj;
    }

    @Override
    public void run() {
        resultsObj.sum = Arrays.stream(data).sum();
    }
}