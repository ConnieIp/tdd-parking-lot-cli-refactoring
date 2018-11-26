package com.oocl.cultivation.test;

import com.oocl.cultivation.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class ParkingManagerTest {

    //Given manager, parking boy managed by manager, car
    //When manager specify parking boy to park and fetch car
    //Then car should be fetched from parking lot managed by parking boy
    @Test
    public void should_manager_specify_parkingboy_to_park_and_fetch_a_car() {
        //Given
        Car car = new Car();
        ParkingLot parkingLot1 = new ParkingLot(0);
        ParkingLot parkingLot2 = new ParkingLot();
        ArrayList<ParkingLot> parkingLots=new ArrayList<>();
        parkingLots.add(parkingLot1);
        parkingLots.add(parkingLot2);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLots);
        ArrayList<ParkingBoy> parkingBoys = new ArrayList<>();
        parkingBoys.add(parkingBoy);
        ParkingManager parkingManager = new ParkingManager(new ArrayList<ParkingLot>(),parkingBoys);

        //When
        ParkingTicket parkingTicket = parkingManager.parkCarByParkingBoy(parkingBoy,car);
        Car fetchedCar = parkingManager.fetchCarByParkingBoy(parkingBoy,parkingTicket);

        //Then
        assertSame(parkingLot2,parkingTicket.getParkingLot());
        assertSame(car,fetchedCar);

    }

    //Given manager, parking lot 1 with no position managed by parking boy, parking lot 2 with position managed by parking boy
    //When manager park and fetch car
    //Then the car fetched should be fetched from parking lot 2.
    @Test
    public void should_manager_park_a_car_to_a_parking_lot_with_enough_space_and_get_it_back() {
        //Given
        Car car = new Car();
        ParkingLot parkingLot1 = new ParkingLot(0);
        ParkingLot parkingLot2 = new ParkingLot();
        ArrayList<ParkingLot> parkingLots=new ArrayList<>();
        parkingLots.add(parkingLot1);
        parkingLots.add(parkingLot2);
        ParkingManager parkingManager = new ParkingManager(parkingLots,new ArrayList<ParkingBoy>());

        //When
        ParkingTicket parkingTicket = parkingManager.park(car);
        Car fetchedCar = parkingManager.fetch(parkingTicket);

        //Then
        assertSame(parkingLot2,parkingTicket.getParkingLot());
        assertSame(car,fetchedCar);

    }


    //Given manager, parking boy, wrong ticket
    //When manager tells parking boy to fetch a car
    //Then manager should output error msg "Unrecognized parking ticket."
    @Test
    void should_manager_query_message_once_the_ticket_is_wrong_when_specify_parkingboy_to_park_car() {
        ParkingLot parkingLot = new ParkingLot();
        ArrayList<ParkingLot> parkingLots=new ArrayList<>();
        parkingLots.add(parkingLot);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLots);
        ParkingTicket wrongTicket = new ParkingTicket(parkingLot);
        ArrayList<ParkingBoy> parkingBoys = new ArrayList<>();
        parkingBoys.add(parkingBoy);
        ParkingManager parkingManager = new ParkingManager(new ArrayList<ParkingLot>(),parkingBoys);

        parkingManager.fetchCarByParkingBoy(parkingBoy,wrongTicket);
        String message = parkingManager.getLastErrorMessage();

        assertEquals("Unrecognized parking ticket.", message);
    }

    //Given parking boy, ticket has been used
    //When manager tells parking boy to fetch car
    //Then manager output error msg "Unrecognized parking ticket."
    @Test
    void should_manager_query_error_message_for_used_ticket_when_specify_parkingboy_to_park_car() {
        ParkingLot parkingLot = new ParkingLot();
        ArrayList<ParkingLot> parkingLots=new ArrayList<>();
        parkingLots.add(parkingLot);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLots);
        Car car = new Car();

        ParkingTicket ticket = parkingBoy.park(car);
        parkingBoy.fetch(ticket);
        parkingBoy.fetch(ticket);

        assertEquals(
                "Unrecognized parking ticket.",
                parkingBoy.getLastErrorMessage()
        );
    }

    //Given manger,parking boy, no ticket
    //When manger tells parking boy to fetch car
    //Then manager output error msg "Please provide your parking ticket."
    @Test
    void should_manager_query_message_once_ticket_is_not_provided_when_specify_parkingboy_to_park_car() {
        ParkingLot parkingLot = new ParkingLot();
        ArrayList<ParkingLot> parkingLots=new ArrayList<>();
        parkingLots.add(parkingLot);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLots);

        parkingBoy.fetch(null);

        assertEquals(
                "Please provide your parking ticket.",
                parkingBoy.getLastErrorMessage());
    }

    //Given manger,parking boy, parking lot with no position managed by parkingBoy
    //When manger tells parking boy to fetch car
    //Then manager output error msg "The parking lot is full."
    @Test
    void should_manager_get_message_if_there_is_not_enough_position_when_specify_parkingboy_to_park_car() {
        final int capacity = 1;
        ParkingLot parkingLot = new ParkingLot(capacity);
        ArrayList<ParkingLot> parkingLots=new ArrayList<>();
        parkingLots.add(parkingLot);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLots);

        parkingBoy.park(new Car());
        parkingBoy.park(new Car());

        assertEquals("The parking lot is full.", parkingBoy.getLastErrorMessage());
    }

}
