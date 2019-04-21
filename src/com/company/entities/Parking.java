package com.company.entities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Parking {
    private static int totalspace;
    private static HashMap<Ticket, Car> mapping =  new HashMap<>();
    private static Set<Ticket> tickets = new HashSet<>();

    public static int getTotalspace() {
        return totalspace;
    }

    public static void setTotalspace(int totalspace) {
        Parking.totalspace = totalspace;
    }

    public static synchronized HashMap<Ticket, Car> getMapping() {
        return mapping;
    }

    public static synchronized void addMapping(Car car){
        for (Ticket each: tickets) {
            mapping.put(each, car);
            tickets.remove(each);
            break;
        }
    }

    public static void deleteMapping(Ticket ticket){
        tickets.add(ticket);
        mapping.remove(ticket);
    }

    public static Set<Ticket> getTickets() {
        return tickets;
    }

}
