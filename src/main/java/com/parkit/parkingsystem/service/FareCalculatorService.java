package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.Ticket;

import java.util.Calendar;


public class FareCalculatorService {



    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
        }

        double inTime = ticket.getInTime().getTime();
        double outTime = ticket.getOutTime().getTime();




        //DONE  : Some tests are failing here. Need to check if this logic is correct .
        double durationInHour = (outTime - inTime) / 3600000;
        //TODO : The first 30 minutes free
        //TODO : 5% discount for regular user
        double freeHalfHour = 1800000;

        System.out.println("duration " + durationInHour);
        switch (ticket.getParkingSpot().getParkingType()){

            case CAR: {
                if (durationInHour > freeHalfHour);
                ticket.setPrice(durationInHour * Fare.CAR_RATE_PER_HOUR);
                break;
            }
            case BIKE: {
                if (durationInHour > freeHalfHour);
                ticket.setPrice(durationInHour * Fare.BIKE_RATE_PER_HOUR);
                break;
            }
            default: throw new IllegalArgumentException("Unknown Parking Type");
        }
    }
}