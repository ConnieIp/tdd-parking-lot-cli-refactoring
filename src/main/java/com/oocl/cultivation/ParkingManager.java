package com.oocl.cultivation;

import java.util.ArrayList;

public class ParkingManager{
    private final ArrayList<ParkingBoy> parkingBoys;
    private final ArrayList<ParkingLot> parkingLots;
    private String lastErrorMessage;

    public ParkingManager(ArrayList<ParkingLot> parkingLots, ArrayList<ParkingBoy> parkingBoys) {
        this.parkingLots = parkingLots;
        this.parkingBoys = parkingBoys;
    }

    public ParkingTicket parkCarByParkingBoy(ParkingBoy parkingBoy,Car car){
        ParkingTicket parkedCar = parkingBoy.park(car);
        getLastErrorMessageFromParkingBoy(parkingBoy);
        return parkedCar;
    }

    public Car fetchCarByParkingBoy(ParkingBoy parkingBoy,ParkingTicket parkingTicket){
        Car fetchedcar=parkingBoy.fetch(parkingTicket);
        getLastErrorMessageFromParkingBoy(parkingBoy);
        return fetchedcar;
    }

    private void getLastErrorMessageFromParkingBoy(ParkingBoy parkingBoy){
        lastErrorMessage = parkingBoy.getLastErrorMessage();
    }

    public ParkingLot getAvailableParkingLot(){
        for(ParkingLot parkingLot:parkingLots){
            if(parkingLot.getAvailableParkingPosition()>0){
                return parkingLot;
            }
        }
        return null;
    }
    public ParkingTicket park(Car car) {
        ParkingLot parkingLot=getAvailableParkingLot();
        if(parkingLot==null){
            lastErrorMessage = "The parking lot is full.";
            return null;
        }else {
            ParkingTicket parkingTicket = parkingLot.addCar(car);
            if (parkingTicket == null) {
                lastErrorMessage = "The parking lot is full.";
            }
            return parkingTicket;
        }
    }

    public Car fetch(ParkingTicket ticket) {
        if(ticket==null){
            lastErrorMessage="Please provide your parking ticket.";
            return null;
        }
        ParkingLot parkingLot=ticket.getParkingLot();
        if(!parkingLots.contains(parkingLot)){
            lastErrorMessage="Unrecognized parking ticket.";
            return null;
        }
        Car fetchedCar=parkingLot.getCar(ticket);
        if(fetchedCar==null) {
            lastErrorMessage="Unrecognized parking ticket.";
        }
        return fetchedCar;
    }

    public String getLastErrorMessage() {
        return lastErrorMessage;
    }
}
