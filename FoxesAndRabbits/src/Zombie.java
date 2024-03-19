import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Zombie extends Animal {
    
    // Characteristics shared by all zombies (class variables).
    
    // Zombies do not breed, they infect. Thus, breeding age is set to 0.
    private static final int BREEDING_AGE = 0;
    // The age to which a Zombie can live, they do not die, but eventually decompose.
    private static final int MAX_AGE = 25;
    // Zombies will always successfully infect prey.
    private static final double BREEDING_PROBABILITY = 1;
    // Zombies can infect one animal at a time.
    private static final int MAX_LITTER_SIZE = 1;

    private int willMove = 0;
    
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();

    
    public Zombie(boolean randomAge, Field field, Location location){
        
        super(field, location, Color.green, 0.001);
        if(randomAge) {
            super.setAge(rand.nextInt(MAX_AGE));
        }

    }

    /**
     * This is what the Zombie does most of the time: it hunts for
     * prey to infect.
     * @param field The field currently occupied.
     * @param newFoxes A list to return newly born foxes.
     */
    public void act(List<Animal> newZombies){
        incrementAge();
            if(isAlive()){
            Location brains = findBrains();
            if(brains != null){
                newZombies.add(infect(brains));
            }

            Location newLocation =  getField().freeAdjacentLocation(getLocation());
            //check if zombie is able to move yet
            if(willMove == 6){
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
    }

    /**
     * Look for rabbits adjacent to the current location.
     * Only the first live rabbit is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findBrains()
    {
        Field field = getField();
        if(field != null){
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
          while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Fox || animal instanceof Rabbit) {
                Animal prey = (Animal) animal;
                if(prey.isAlive()) { 
                    prey.setDead();
                    // Remove the infected animal from the field.
                    return where;
                }
            }
          }
        }
        return null;
    }

    
    /**
     * puts new Zombie in the place of the infected Animal (replaces animal with a zombie, or 'infects' the animal)
     * @return the new Zombie
     */
    private Animal infect(Location where){
        Field field = getField();
        Zombie infected = new Zombie(false, field, where);
        return infected;
    }

    /**
     *  Return the breeding age of this zombie. 
     *  @return The breeding age of this zombie.
     */
    protected int getBreedingAge(){
        return BREEDING_AGE;
    }

        /**
     * Return the max age of this zombie
     * @return int the max age of this zombie
     */
    protected int getMaxAge(){
        return MAX_AGE;
    }

        /**
     * Return the breeding probabilty for this zombie
     * @return the breeding probabilty for this zombie
     */
    protected double getBreedingProbability(){
        return BREEDING_PROBABILITY;
    }

    /**
     * Return the max litter size for this zombie
     * @return The max litter size for this zombie
     */
    protected int getMaxLitterSize(){
        return MAX_LITTER_SIZE;
    }

    
    /**
     * creates a new Animal of the type Zombie
     * @return the new Zombie
     */
    public Animal makeAnimal(boolean randAge, Field field, Location location){
        Animal zombie = new Zombie(randAge, field, location);
        return zombie;
    }

}
