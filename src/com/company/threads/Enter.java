package com.company.threads;

import com.company.entities.Car;
import com.company.entities.Parking;
import com.company.entities.Ticket;

import java.util.Map;

public class Enter extends Thread {
    private static int sleeptime = 5000;
    private int cars;

    public static void setSleeptime(int sleeptime) {
        Enter.sleeptime = sleeptime*1000;
    }

    @Override
    public void run() {
        while (cars > 0) {
            Car thiscar = new Car();
            System.out.println("Подъезжает машина "+thiscar.getLicenseplate());
            try {
                sleep(sleeptime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Parking.addMapping(thiscar);
            for (Map.Entry<Ticket, Car> each : Parking.getMapping().entrySet())
                if (each.getValue().equals(thiscar))
            System.out.println("Въехала машина " + thiscar.getLicenseplate()+" | выдан билет №"+each.getKey().getId());
            cars--;
        }
    }

    public void setCars(int cars) {
        this.cars = cars;
    }
}
