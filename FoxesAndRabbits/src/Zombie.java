import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Zombie extends Animal {
    
    // Characteristics shared by all foxes (class variables).
    
    // Zombies do not breed, they infect. Thus, breeding age is set to 0.
    private static final int BREEDING_AGE = 0;
    // The age to which a Zombie can live, they do not die, but eventually decompose.
    private static final int MAX_AGE = 2147483647;
    // Zombies will always successfully infect prey.
    private static final double BREEDING_PROBABILITY = 1;
    // Zombies can infect one animal at a time.
    private static final int MAX_LITTER_SIZE = 1;

    private int willMove = 3;
    
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    public Zombie(Field field, Location location){
        super(field, location);
    }

    /**
     * This is what the Zombie does most of the time: it hunts for
     * prey to infect.
     * @param field The field currently occupied.
     * @param newFoxes A list to return newly born foxes.
     */
    public void act(){
        Location brains = findBrains();
        if(brains != null){
            infect(brains);
        }


        Location newLocation =  getField().freeAdjacentLocation(getLocation());
        //check if zombie is able to move yet
        if(willMove == 3){
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
            willMove = 0;
        }
        else{
            willMove++;
        }

    }

    /**
     * Look for rabbits adjacent to the current location.
     * Only the first live rabbit is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findBrains()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal != null) {
                Animal prey = (Animal) animal;
                if(prey.isAlive()) { 
                    prey.setDead();
                    // Remove the infected animal from the field.
                    return where;
                }
            }
        }
        return null;
    }

    private Animal infect(Location where){
        Field field = getField();
        Zombie infected = new Zombie(field, where);
        return infected;
    }
    

}
