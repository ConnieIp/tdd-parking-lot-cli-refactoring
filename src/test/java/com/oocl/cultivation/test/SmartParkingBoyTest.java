package com.oocl.cultivation.test;

import com.oocl.cultivation.Car;
import com.oocl.cultivation.ParkingLot;
import com.oocl.cultivation.ParkingTicket;
import com.oocl.cultivation.SmartParkingBoy;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertSame;

public class SmartParkingBoyTest {

    //Given smart parking boy, parking lot 1 managed by parking boy, parking lot 2 with more empty position managed by parking boy
    //When parking boy park and fetch car
    //Then the car fetched should be fetched from parking lot 2.
    @Test
    public void should_park_a_car_to_a_parking_lot_with_more_empty_position_and_get_it_back() {
        //Given
        Car car1 = new Car();
        Car car2 = new Car();
        Car car3 = new Car();
        ParkingLot parkingLot1 = new ParkingLot(4);
        ParkingLot parkingLot2 = new ParkingLot(5);
        ArrayList<ParkingLot> parkingLots=new ArrayList<>();
        parkingLots.add(parkingLot1);
        parkingLots.add(parkingLot2);
        SmartParkingBoy parkingBoy = new SmartParkingBoy(parkingLots);

        //When
        ParkingTicket parkingTicket1 = parkingBoy.park(car1);
        ParkingTicket parkingTicket2 = parkingBoy.park(car2);
        ParkingTicket parkingTicket3 = parkingBoy.park(car3);
        Car fetchedCar1 = parkingBoy.fetch(parkingTicket1);
        Car fetchedCar2 = parkingBoy.fetch(parkingTicket2);
        Car fetchedCar3 = parkingBoy.fetch(parkingTicket3);

        //Then
        assertSame(parkingLot2,parkingTicket1.getParkingLot());
        assertSame(parkingLot1,parkingTicket2.getParkingLot());
        assertSame(parkingLot2,parkingTicket3.getParkingLot());
        assertSame(car1,fetchedCar1);
        assertSame(car2,fetchedCar2);
        assertSame(car3,fetchedCar3);

    }
}
