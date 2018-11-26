package com.oocl.cultivation;

import java.util.ArrayList;

public class ParkingManager extends ParkingBoy{
    private final ArrayList<ParkingBoy> parkingBoys;

    public ParkingManager(ArrayList<ParkingLot> parkingLots, ArrayList<ParkingBoy> parkingBoys) {
        super(parkingLots);
        this.parkingBoys = parkingBoys;
    }

    public ParkingTicket parkCarByParkingBoy(ParkingBoy parkingBoy,Car car){
        ParkingTicket parkedCar = parkingBoy.park(car);
        lastErrorMessage = parkingBoy.getLastErrorMessage();
        return parkedCar;
    }

    public Car fetchCarByParkingBoy(ParkingBoy parkingBoy,ParkingTicket parkingTicket){
        Car fetchedcar=parkingBoy.fetch(parkingTicket);
        lastErrorMessage = parkingBoy.getLastErrorMessage();
        return fetchedcar;
    }
}
