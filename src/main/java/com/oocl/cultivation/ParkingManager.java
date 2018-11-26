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

    private ParkingLot getAvailableParkingLot(){
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

    public String getLastErrorMessage() {
        return lastErrorMessage;
    }
}
