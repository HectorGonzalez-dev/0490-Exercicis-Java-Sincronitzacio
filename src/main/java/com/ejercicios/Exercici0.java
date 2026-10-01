package com.ejercicios;

import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Exercici0 {

    public static void main(String[] args) {

        // Datos de entrada
        double[] data = {10, 20, 30, 40, 50};

        // Objeto para almacenar los resultados
        Results results = new Results();

        // Crea una barrera para 3 hilos
        CyclicBarrier barrier = new CyclicBarrier(3, new Runnable() {
            @Override
            public void run() {
                System.out.println("Resultado de la suma: " + results.sum);
                System.out.println("Resultado de la media: " + results.average);
                System.out.println("Resultado de la desviacion estandar: " + results.standardDeviation);
            }
        });

        // Executor de 3 hilos
        ExecutorService executor = Executors.newFixedThreadPool(3);

        executor.submit(new calculateSum(data, results, barrier));
        executor.submit(new calculateAverage(data, results, barrier));
        executor.submit(new calculateSD(data, results, barrier));

        // Cerrar el executor
        executor.shutdown();
    }
}

class Results {
    double sum;
    double average;
    double standardDeviation;
}

class calculateSum implements Runnable {
    private final double[] data;
    private final Results resultsObj;
    private final CyclicBarrier barrier;

    public calculateSum(double[] data, Results resultsObj, CyclicBarrier barrier) {
        this.data = data;
        this.resultsObj = resultsObj;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        resultsObj.sum = Arrays.stream(data).sum();
        System.out.println("Sum calculation finished.");

        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}

class calculateAverage implements Runnable {
    private final double[] data;
    private final Results resultsObj;
    private final CyclicBarrier barrier;

    public calculateAverage(double[] data, Results resultsObj, CyclicBarrier barrier) {
        this.data = data;
        this.resultsObj = resultsObj;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        resultsObj.average = Arrays.stream(data).average().orElse(0);
        System.out.println("Average calculation finished.");

        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}

class calculateSD implements Runnable {
    private final double[] data;
    private final Results resultsObj;
    private final CyclicBarrier barrier;

    public calculateSD(double[] data, Results resultsObj, CyclicBarrier barrier) {
        this.data = data;
        this.resultsObj = resultsObj;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        resultsObj.standardDeviation = standardDeviation(data);
        System.out.println("Standard Deviation calculation finished.");

        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    public static double standardDeviation(double[] data) {
        double mean = Arrays.stream(data).average().orElse(0);

        double variance = Arrays.stream(data)
                .map(x -> Math.pow(x - mean, 2))
                .average()
                .orElse(0);

        return Math.sqrt(variance);
    }
}