
import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.persistence.CinemaPersitence;
import edu.eci.arsw.cinema.persistence.impl.InMemoryCinemaPersistence;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cristian
 */
public class InMemoryPersistenceTest {
    
    @Test
    public void deberiaConsultarLasFuncionesPorCinemaYFecha(){
        List<CinemaFunction> funciones = new ArrayList();
        
        Movie pelicula1 = new Movie("La favorita","Drama");
        CinemaFunction funcion = new CinemaFunction(pelicula1,"07/05/1999");
        
        Movie pelicula2 = new Movie("La momia","Accion");
        CinemaFunction funcion1 = new CinemaFunction(pelicula2,"07/05/1999");
        
        Movie pelicula3 = new Movie("life of PI","Drama");
        CinemaFunction funcion2 = new CinemaFunction(pelicula3,"07/05/1999");
        
        Movie pelicula4 = new Movie("interestelar","Drama");
        CinemaFunction funcion3 = new CinemaFunction(pelicula4,"07/05/1999");
        
        Movie pelicula5 = new Movie("la la land","Romance");
        CinemaFunction funcion4 = new CinemaFunction(pelicula5,"07/05/1999");
        
        funciones.add(funcion);
        funciones.add(funcion1);
        funciones.add(funcion2);
        funciones.add(funcion3);
        funciones.add(funcion4);
        
        Cinema c = new Cinema("CineColombia",funciones);
        
        assertEquals(funciones,c.getFunctions());
        
        CinemaPersitence cinePersistence = new InMemoryCinemaPersistence();
        try {
			cinePersistence.saveCinema(c);
		} catch (CinemaPersistenceException e) {
			assertFalse(true);
			e.printStackTrace();
		}
        
        boolean igual = true;
        List<CinemaFunction> funcionesProbadas;
		try {
			funcionesProbadas = cinePersistence.getFunctionsbyCinemaAndDate("CineColombia","07/05/1999" );
			for (int i = 0; i < funcionesProbadas.size() && igual; i++) {
				String cine1=funcionesProbadas.get(i).getMovie().getName();
				String cine2=funciones.get(i).getMovie().getName();
				if (!cine1.equals(cine2)) {
					igual = false;
				}
			}
	        assertTrue(igual);
		} catch (CinemaException e) {
			assertFalse(true);
			e.printStackTrace();
		}
        
    }
    
    @Test
    public void deberiaConsultarCinemaPorNombre(){
    	
    	List<CinemaFunction> funciones = new ArrayList();
        
        Movie pelicula1 = new Movie("La favorita","Drama");
        CinemaFunction funcion = new CinemaFunction(pelicula1,"07/05/1999");
        
        Movie pelicula2 = new Movie("La momia","Accion");
        CinemaFunction funcion1 = new CinemaFunction(pelicula2,"07/05/1999");
        
        funciones.add(funcion);
        funciones.add(funcion1);
        
        Cinema c = new Cinema("CineColombia",funciones);
        CinemaPersitence cinePersistence = new InMemoryCinemaPersistence();
        try {
			cinePersistence.saveCinema(c);
		} catch (CinemaPersistenceException e) {
			assertFalse(true);
			e.printStackTrace();
		}
        try {
			Cinema cinemaPrueba = cinePersistence.getCinemaByName("CineColombia");
			assertEquals(cinemaPrueba.getName(), "CineColombia");
		} catch (CinemaPersistenceException e) {
			assertFalse(true);
			e.printStackTrace();
		}
    }
    
    
    @Test
    public void deberiaPoderComprarUnTiquete(){
    	List<CinemaFunction> funciones = new ArrayList();
    	
    	 Movie pelicula1 = new Movie("La favorita","Drama");
         CinemaFunction funcion = new CinemaFunction(pelicula1,"07/05/1999");
         
         Movie pelicula2 = new Movie("La momia","Accion");
         CinemaFunction funcion1 = new CinemaFunction(pelicula2,"07/05/1999");
         
         funciones.add(funcion);
         funciones.add(funcion1);
         
         Cinema c = new Cinema("CineColombia",funciones);
         CinemaPersitence cinePersistence = new InMemoryCinemaPersistence();
         try {
 			cinePersistence.saveCinema(c);
 		} catch (CinemaPersistenceException e) {
 			assertFalse(true);
 			e.printStackTrace();
 		}
        
         try {
			cinePersistence.buyTicket(1, 1, "CineColombia", "07/05/1999", "La favorita");
		} catch (CinemaException e) {
			assertFalse(true);
			e.printStackTrace();
		}
         
         try {
 			cinePersistence.buyTicket(1, 1, "CineColombia", "07/05/1999", "La favorita");
 		} catch (CinemaException e) {
 			assertTrue(true);
 		}
    }
         
    

    @Test
    public void saveNewAndLoadTest() throws CinemaPersistenceException{
        InMemoryCinemaPersistence ipct=new InMemoryCinemaPersistence();

        String functionDate = "2018-12-18 15:30";
        List<CinemaFunction> functions= new ArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie 2","Action"),functionDate);
        CinemaFunction funct2 = new CinemaFunction(new Movie("The Night 2","Horror"),functionDate);
        functions.add(funct1);
        functions.add(funct2);
        Cinema c=new Cinema("Movies Bogotá",functions);
        ipct.saveCinema(c);
        
        assertNotNull("Loading a previously stored cinema returned null.",ipct.getCinemaByName(c.getName()));
        assertEquals("Loading a previously stored cinema returned a different cinema.",ipct.getCinemaByName(c.getName()), c);
    }


    @Test
    public void saveExistingCinemaTest() {
        InMemoryCinemaPersistence ipct=new InMemoryCinemaPersistence();
        
        String functionDate = "2018-12-18 15:30";
        List<CinemaFunction> functions= new ArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie 2","Action"),functionDate);
        CinemaFunction funct2 = new CinemaFunction(new Movie("The Night 2","Horror"),functionDate);
        functions.add(funct1);
        functions.add(funct2);
        Cinema c=new Cinema("Movies Bogotá",functions);
        
        try {
            ipct.saveCinema(c);
        } catch (CinemaPersistenceException ex) {
            fail("Cinema persistence failed inserting the first cinema.");
        }
        
        List<CinemaFunction> functions2= new ArrayList<>();
        CinemaFunction funct12 = new CinemaFunction(new Movie("SuperHeroes Movie 3","Action"),functionDate);
        CinemaFunction funct22 = new CinemaFunction(new Movie("The Night 3","Horror"),functionDate);
        functions.add(funct12);
        functions.add(funct22);
        Cinema c2=new Cinema("Movies Bogotá",functions2);
        try{
            ipct.saveCinema(c2);
            fail("An exception was expected after saving a second cinema with the same name");
        }
        catch (CinemaPersistenceException ex){
        }
    }
    
    
    @Test
    public void deberiaFiltrarLasPeliculasGenero() {
    	InMemoryCinemaPersistence ipct=new InMemoryCinemaPersistence();
    	
    	List<Movie> listaGeneros = new ArrayList<>();
    	
    	List<CinemaFunction> funciones = new ArrayList();
        
        Movie pelicula1 = new Movie("La favorita","Drama");
        listaGeneros.add(pelicula1);
        CinemaFunction funcion = new CinemaFunction(pelicula1,"07/05/1999");
        
        Movie pelicula2 = new Movie("La momia","Accion");
        CinemaFunction funcion1 = new CinemaFunction(pelicula2,"07/05/1999");
        
        Movie pelicula3 = new Movie("life of PI","Drama");
        listaGeneros.add(pelicula3);
        CinemaFunction funcion2 = new CinemaFunction(pelicula3,"07/05/1999");
        
        Movie pelicula4 = new Movie("interestelar","Drama");
        listaGeneros.add(pelicula4);
        CinemaFunction funcion3 = new CinemaFunction(pelicula4,"07/05/1999");
        
        funciones.add(funcion);
        funciones.add(funcion1);
        funciones.add(funcion2);
        funciones.add(funcion3);
        
        Cinema c = new Cinema("CineColombia",funciones);
        
        try {
            ipct.saveCinema(c);
        } catch (CinemaPersistenceException ex) {
            fail("Cinema persistence failed inserting the first cinema.");
        }
        List<Movie> pruebaFiltro = null; 
        
        try {
        	pruebaFiltro = ipct.filterMovies("CineColombia", "07/05/1999", "Drama");
		} catch (CinemaException e) {
			e.printStackTrace();
		} catch (NullPointerException es) {
			es.printStackTrace();
		}
        
        boolean flag = true;
        if(listaGeneros.size() == pruebaFiltro.size()) {
        	for (int i = 0 ; i <listaGeneros.size() && flag; i++) {
        		Movie movie1 = listaGeneros.get(i);
        		Movie movie2 = pruebaFiltro.get(i);
        		if(!movie1.getName().equals(movie2.getName())) {
        			flag = false;
        		}
    		}
        }
        assertTrue(flag);
    }
}
