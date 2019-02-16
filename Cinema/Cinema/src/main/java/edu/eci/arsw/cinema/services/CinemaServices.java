/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.services;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.persistence.CinemaPersitence;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author cristian
 */
public class CinemaServices {
    @Autowired
    CinemaPersitence cps = null;
    
    public void addNewCinema(Cinema c){
        try {
			cps.saveCinema(c);
		} catch (CinemaPersistenceException e) {
			e.printStackTrace();
		}
    }
    
    public Set<Cinema> getAllCinemas(){
        Set<Cinema> cinemas= new HashSet<Cinema>(cps.getCinemas().values());
		return cinemas;
    }
    
    /**
     * 
     * @param name cinema's name
     * @return the cinema of the given name created by the given author
     * @throws CinemaException
     */
    public Cinema getCinemaByName(String name) throws CinemaException{
        Cinema cine = null;
        try {
            cine = cps.getCinemaByName(name);
        } catch (CinemaPersistenceException ex) {
            Logger.getLogger(CinemaServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cine;
    }
    
    
    public void buyTicket(int row, int col, String cinema, String date, String movieName){
        try {
			cps.buyTicket(row, col, cinema, date, movieName);
		} catch (CinemaException e) {
			e.printStackTrace();
		}
    }
    
    public List<CinemaFunction> getFunctionsbyCinemaAndDate(String cinema, String date) {
    	List<CinemaFunction> funciones = new ArrayList<CinemaFunction>();
    	try {
			funciones = cps.getFunctionsbyCinemaAndDate(cinema, date);
		} catch (CinemaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return funciones;
    }


}
