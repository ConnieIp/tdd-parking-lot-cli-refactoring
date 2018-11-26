package com.oocl.cultivation.test;


import com.oocl.cultivation.*;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

public class TddParkingLotCliTest {
    //Given parking boy,parking lot,car
    //When customer give a car to parking boy to park and fetch
    //Then the car fetched should be same as the car parked.
    @Test
    public void should_park_a_car_to_a_parking_lot_and_get_it_back() {
        //Given
        Car car = new Car();
        ParkingLot parkingLot = new ParkingLot();
        ArrayList<ParkingLot> parkingLots=new ArrayList<>();
        parkingLots.add(parkingLot);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLots);

        //When
        ParkingTicket parkingTicket = parkingBoy.park(car);
        Car fetchedCar = parkingBoy.fetch(parkingTicket);

        //Then
        assertSame(car,fetchedCar);

    }

    //Given parking boy,parking lot,cars
    //When customer give multiple cars to parking boy to park and fetch
    //Then the car fetched should be same as the car parked.
    @Test
    void should_park_multiple_cars_to_a_parking_lot_and_get_them_back() {
        ParkingLot parkingLot = new ParkingLot();
        ArrayList<ParkingLot> parkingLots=new ArrayList<>();
        parkingLots.add(parkingLot);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLots);
        Car firstCar = new Car();
        Car secondCar = new Car();

        ParkingTicket firstTicket = parkingBoy.park(firstCar);
        ParkingTicket secondTicket = parkingBoy.park(secondCar);

        Car fetchedByFirstTicket = parkingBoy.fetch(firstTicket);
        Car fetchedBySecondTicket = parkingBoy.fetch(secondTicket);

        assertSame(firstCar, fetchedByFirstTicket);
        assertSame(secondCar, fetchedBySecondTicket);
    }

    //Given parking boy, wrong ticket
    //When parking boy fetch car
    //Then no car should be fetched
    @Test
    void should_not_fetch_any_car_once_ticket_is_wrong() {
        ParkingLot parkingLot = new ParkingLot();
        ArrayList<ParkingLot> parkingLots=new ArrayList<>();
        parkingLots.add(parkingLot);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLots);
        Car car = new Car();
        ParkingTicket wrongTicket = new ParkingTicket(parkingLot);

        ParkingTicket ticket = parkingBoy.park(car);

        assertNull(parkingBoy.fetch(wrongTicket));
        assertSame(car, parkingBoy.fetch(ticket));
    }

    //Given parking boy, ticket have been used
    //When parking boy fetch car
    //Then no car should be fetched
    @Test
    void should_not_fetch_any_car_once_ticket_has_been_used() {
        ParkingLot parkingLot = new ParkingLot();
        ArrayList<ParkingLot> parkingLots=new ArrayList<>();
        parkingLots.add(parkingLot);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLots);
        Car car = new Car();

        ParkingTicket ticket = parkingBoy.park(car);
        parkingBoy.fetch(ticket);

        assertNull(parkingBoy.fetch(ticket));
    }

    //Given parking boy, parking lot with no position, car
    //When parking boy park the car
    //Then car should not be parked
    @Test
    void should_not_park_cars_to_parking_lot_if_there_is_not_enough_position() {
        final int capacity = 1;
        ParkingLot parkingLot = new ParkingLot(capacity);
        ArrayList<ParkingLot> parkingLots=new ArrayList<>();
        parkingLots.add(parkingLot);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLots);

        parkingBoy.park(new Car());

        assertNull(parkingBoy.park(new Car()));
    }

    //Given parking boy, wrong ticket
    //When parking boy fetch car
    //Then parking boy output error msg "Unrecognized parking ticket."
    @Test
    void should_query_message_once_the_ticket_is_wrong() {
        ParkingLot parkingLot = new ParkingLot();
        ArrayList<ParkingLot> parkingLots=new ArrayList<>();
        parkingLots.add(parkingLot);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLots);
        ParkingTicket wrongTicket = new ParkingTicket(parkingLot);

        parkingBoy.fetch(wrongTicket);
        String message = parkingBoy.getLastErrorMessage();

        assertEquals("Unrecognized parking ticket.", message);
    }

    //Given parking boy, ticket has been used
    //When parking boy fetch car
    //Then parking boy output error msg "Unrecognized parking ticket."
    @Test
    void should_query_error_message_for_used_ticket() {
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

    //Given parking boy, no ticket
    //When parking boy fetch car
    //Then  no car should be fetched
    @Test
    void should_not_fetch_any_car_once_ticket_is_not_provided() {
        ParkingLot parkingLot = new ParkingLot();
        ArrayList<ParkingLot> parkingLots=new ArrayList<>();
        parkingLots.add(parkingLot);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLots);
        Car car = new Car();

        ParkingTicket ticket = parkingBoy.park(car);

        assertNull(parkingBoy.fetch(null));
        assertSame(car, parkingBoy.fetch(ticket));
    }

    //Given parking boy, no ticket
    //When parking boy fetch car
    //Then parking boy output error msg "Please provide your parking ticket."
    @Test
    void should_query_message_once_ticket_is_not_provided() {
        ParkingLot parkingLot = new ParkingLot();
        ArrayList<ParkingLot> parkingLots=new ArrayList<>();
        parkingLots.add(parkingLot);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLots);

        parkingBoy.fetch(null);

        assertEquals(
                "Please provide your parking ticket.",
                parkingBoy.getLastErrorMessage());
    }

    //Given parking boy, parking lot with no position
    //When parking boy fetch car
    //Then parking boy output error msg "The parking lot is full."
    @Test
    void should_get_message_if_there_is_not_enough_position() {
        final int capacity = 1;
        ParkingLot parkingLot = new ParkingLot(capacity);
        ArrayList<ParkingLot> parkingLots=new ArrayList<>();
        parkingLots.add(parkingLot);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLots);

        parkingBoy.park(new Car());
        parkingBoy.park(new Car());

        assertEquals("The parking lot is full.", parkingBoy.getLastErrorMessage());
    }

    //Given parking boy, parking lot 1 with no position managed by parking boy, parking lot 2 with position managed by parking boy
    //When parking boy park and fetch car
    //Then the car fetched should be fetched from parking lot 2.
    @Test
    public void should_park_a_car_to_a_parking_lot_with_enough_space_and_get_it_back() {
        //Given
        Car car = new Car();
        ParkingLot parkingLot1 = new ParkingLot(0);
        ParkingLot parkingLot2 = new ParkingLot();
        ArrayList<ParkingLot> parkingLots=new ArrayList<>();
        parkingLots.add(parkingLot1);
        parkingLots.add(parkingLot2);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLots);

        //When
        ParkingTicket parkingTicket = parkingBoy.park(car);
        Car fetchedCar = parkingBoy.fetch(parkingTicket);

        //Then
        assertSame(parkingLot2,parkingTicket.getParkingLot());
        assertSame(car,fetchedCar);

    }

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

    //Given super parking boy, parking lot 1 managed by parking boy, parking lot 2 with larger available position rate managed by parking boy
    //When parking boy park and fetch car
    //Then the car fetched should be fetched from parking lot 2.
    @Test
    public void should_park_a_car_to_a_parking_lot_with_larger_available_position_rate_and_get_it_back() {
        //Given
        Car car1 = new Car();
        Car car2 = new Car();
        Car car3 = new Car();
        ParkingLot parkingLot1 = new ParkingLot(3);
        ParkingLot parkingLot2 = new ParkingLot(2);
        ArrayList<ParkingLot> parkingLots=new ArrayList<>();
        parkingLots.add(parkingLot1);
        parkingLots.add(parkingLot2);
        SuperSmartParkingBoy parkingBoy = new SuperSmartParkingBoy(parkingLots);

        //When
        ParkingTicket parkingTicket1 = parkingBoy.park(car1);
        ParkingTicket parkingTicket2 = parkingBoy.park(car2);
        ParkingTicket parkingTicket3 = parkingBoy.park(car3);
        Car fetchedCar1 = parkingBoy.fetch(parkingTicket1);
        Car fetchedCar2 = parkingBoy.fetch(parkingTicket2);
        Car fetchedCar3 = parkingBoy.fetch(parkingTicket3);

        //Then
        assertSame(parkingLot1,parkingTicket1.getParkingLot());
        assertSame(parkingLot2,parkingTicket2.getParkingLot());
        assertSame(parkingLot1,parkingTicket3.getParkingLot());
        assertSame(car1,fetchedCar1);
        assertSame(car2,fetchedCar2);
        assertSame(car3,fetchedCar3);

    }

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
