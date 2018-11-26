package com.oocl.cultivation;

import java.util.ArrayList;

public class SmartParkingBoy extends ParkingBoy {

    public SmartParkingBoy(ArrayList<ParkingLot> parkingLots) {
        super(parkingLots);
    }

    @Override
    public ParkingLot getAvailableParkingLot(){
        ParkingLot availParkingLot=null;
        int availableSpace=0;
        for(ParkingLot parkingLot:parkingLots){
            if(parkingLot.getAvailableParkingPosition()>availableSpace){
                availableSpace=parkingLot.getAvailableParkingPosition();
                availParkingLot=parkingLot;
            }
        }
        return availParkingLot;
    }
}
