package com.ejercicios;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Exercici1 {
    
    public static void main(String[] args) {
        ParkingLot parking = new ParkingLot(3);

        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int i = 1; i <= 10; i++) {
            executor.submit(new Car(i, parking));
        }

        executor.shutdown();
    }

}

class ParkingLot {

    private final Semaphore semaphore;

    public ParkingLot(int capacity) {
        semaphore = new Semaphore(capacity);
    }

    public void enter(int carId) throws InterruptedException {
        if (semaphore.tryAcquire()) {
            System.out.println("[PARKING] Coche " + carId + " ha entrado al parking.");
        } else {
            System.out.println("[ESPERA] Coche " + carId + " esta esperando para entrar.");

            semaphore.acquire();

            System.out.println("[PARKING] Coche " + carId + " ha entrado al parking.");
        }
    }

    public void exit(int carId) {
        System.out.println("[SALIDA] Coche " + carId + " ha salido del parking.");
        semaphore.release();
    }
}

class Car implements Runnable {

    private final int id;
    private final ParkingLot parking;

    public Car(int id, ParkingLot parking) {
        this.id = id;
        this.parking = parking;
    }

    @Override
    public void run() {

        try {
            parking.enter(id);

            long time = (long) (Math.random() * 3000 + 2000);
            Thread.sleep(time);

            parking.exit(id);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}