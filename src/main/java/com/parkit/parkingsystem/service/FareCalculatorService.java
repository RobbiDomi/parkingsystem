package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.Ticket;



public class FareCalculatorService {




    public void calculateFare(Ticket ticket) {
        if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
            throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
        }

        double inTime = ticket.getInTime().getTime();
        double outTime = ticket.getOutTime().getTime();
        TicketDAO ticketDAO = new TicketDAO();

        //DONE  : Some tests are failing here. Need to check if this logic is correct .
        double durationInHour = (outTime - inTime) / 3600000;
        //DONE : The first 30 minutes free
        //TODO : 5% discount for regular user
        double freeHalfHour = 0.5;
        double durMinusHalf = durationInHour - freeHalfHour;
        String vhPlateKnown = ticket.getVehicleRegNumber();
        double discount5P = 0.95;


        System.out.println("duration(outTime - inTime)  " + durationInHour);
        switch (ticket.getParkingSpot().getParkingType()) {
            case CAR: {
                if (durationInHour > freeHalfHour ) {
                    ticket.setPrice(durMinusHalf * Fare.CAR_RATE_PER_HOUR);
                }
                else {
                    ticket.setPrice(0);
                }
                break;
            }
            case BIKE: {
                if (durationInHour > freeHalfHour) {
                    ticket.setPrice(durMinusHalf * Fare.BIKE_RATE_PER_HOUR);
                }
                else {
                    ticket.setPrice(0);
                }
                break;
            }
            default: throw new IllegalArgumentException("Unknown Parking Type");
        }
        if (ticketDAO.getTicket(vhPlateKnown)){
            ticket.setPrice(ticket.getPrice()*discount5P);
        }
    }
}
