package com.oocl.cultivation;

import java.util.ArrayList;

public class ParkingBoy {

    final ArrayList<ParkingLot> parkingLots;
    private String lastErrorMessage;

    public ParkingBoy(ArrayList<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
    }

    public ParkingLot getAvailableParkingLot(){
        for(ParkingLot parkingLot:parkingLots){
            boolean parkingLotIsFull = parkingLot.getAvailableParkingPosition()>0;
            if(!parkingLotIsFull){
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
        boolean noTicket = ticket==null;
        if(noTicket){
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
