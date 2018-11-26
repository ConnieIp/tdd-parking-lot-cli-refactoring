package com.oocl.cultivation;

public class ParkingTicket {
    private ParkingLot parkingLot;

    public ParkingTicket(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }
}
