package com.oocl.cultivation;

import java.util.HashMap;
import java.util.Map;

public class ParkingLot {
    private final int capacity;
    private Map<ParkingTicket, Car> cars = new HashMap<>();

    public ParkingLot() {
        this(10);
    }

    public ParkingLot(int capacity) {
        this.capacity = capacity;
    }

    public int getAvailableParkingPosition() {
        return capacity - cars.size();
    }

    public float getAvailableParkingPositionRate() {
        return (float)getAvailableParkingPosition()/capacity;
    }


    public ParkingTicket addCar(Car car){
        if(getAvailableParkingPosition()>0){
            ParkingTicket parkingTicket= new ParkingTicket(this);
            cars.put(parkingTicket, car);
            return parkingTicket;
        }
        return null;
    }

    public Car getCar(ParkingTicket parkingTicket){
        Car fetchedCar = cars.get(parkingTicket);
        cars.remove(parkingTicket);
        return fetchedCar;
    }
}
