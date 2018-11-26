package com.oocl.cultivation;

import sun.security.krb5.internal.Ticket;

import java.util.ArrayList;

public class ParkingBoy {

    final ArrayList<ParkingLot> parkingLots;
    private String lastErrorMessage;

    public ParkingBoy(ArrayList<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
    }

    public ParkingLot getAvailableParkingLot(){
        for(ParkingLot parkingLot:parkingLots){
            boolean parkingLotIsFull = parkingLot.getAvailableParkingPosition()==0;
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
            return parkingLot.addCar(car);
        }
    }

    public Car fetch(ParkingTicket ticket) {
        if(checkNoTicket(ticket)||checkWrongTicket(ticket)){
            return null;
        }
        ParkingLot parkingLot=ticket.getParkingLot();
        if(!managedParkingLot(parkingLot)){
            return null;
        }
        return parkingLot.getCar(ticket);
    }

    public String getLastErrorMessage() {
        return lastErrorMessage;
    }

    private boolean checkNoTicket(ParkingTicket ticket){
        boolean noTicket = ticket==null;
        if(noTicket){
            lastErrorMessage="Please provide your parking ticket.";
        }

        return noTicket;
    }

    private boolean checkWrongTicket(ParkingTicket ticket){
        boolean wrongTicket = ticket.getParkingLot()==null || (ticket.getParkingLot()!=null && !ticket.getParkingLot().checkTicketValid(ticket));
        if(wrongTicket){
            lastErrorMessage="Unrecognized parking ticket.";
        }
        return wrongTicket;
    }

    private boolean managedParkingLot(ParkingLot parkingLot){
        boolean parkingLotManagedByParkingBoy=parkingLots.contains(parkingLot);
        if(!parkingLotManagedByParkingBoy){
            lastErrorMessage="Unrecognized parking ticket.";
        }
        return parkingLotManagedByParkingBoy;
    }

}
