/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.persistence.impl;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.persistence.CinemaPersitence;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 *
 * @author cristian
 */
@Service
public class InMemoryCinemaPersistence implements CinemaPersitence{
    
    private final Map<String,Cinema> cinemas=new HashMap<>();

    public InMemoryCinemaPersistence() {
        //load stub data
        String functionDate = "2018-12-18 15:30";
        List<CinemaFunction> functions= new ArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie","Action"),functionDate);
        CinemaFunction funct2 = new CinemaFunction(new Movie("The Night","Horror"),functionDate);
        functions.add(funct1);
        functions.add(funct2);
        Cinema c=new Cinema("cinemaX",functions);
        cinemas.put("cinemaX", c);
    }    

    @Override
    public void buyTicket(int row, int col, String cinema, String date, String movieName) throws CinemaException {
        try {
            Cinema cine = cinemas.get(cinema);
            for (CinemaFunction funcion : cine.getFunctions()) {
                if(funcion.getDate().equals(date) && funcion.getMovie().getName().equals(movieName)){
                    funcion.buyTicket(row, col);
                }
            }
        } catch (Exception e) {
        	throw new CinemaException("Seat booked"); 
        }
        
    }

    
    
    @Override
    public List<CinemaFunction> getFunctionsbyCinemaAndDate(String cinema, String date) throws CinemaException {
        try {
            Cinema cine=cinemas.get(cinema);
            List<CinemaFunction> funciones = new ArrayList<CinemaFunction>();
            for (CinemaFunction funcion : cine.getFunctions()) {
                if(funcion.getDate().equals(date)){
                    funciones.add(funcion);
                }
            }
            
            return funciones; 
        } catch (Exception e) {
        	throw new CinemaException("Cinema not found"); 
        }
        
    }

    @Override
    public void saveCinema(Cinema c) throws CinemaPersistenceException {
        if (cinemas.containsKey(c.getName())){
            
            throw new CinemaPersistenceException("The given cinema already exists: "+c.getName());
        }
        else{
            cinemas.put(c.getName(),c);
        }   
    }

    @Override
    public Cinema getCinemaByName(String name) throws CinemaPersistenceException {
        return cinemas.get(name);
    }
    
    
    
}
