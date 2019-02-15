
import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.persistence.impl.InMemoryCinemaPersistence;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
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
        
        Cinema c = new Cinema("CineColombia",funciones);
        
        assertEquals(funciones,c.getFunctions());
        
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
}
