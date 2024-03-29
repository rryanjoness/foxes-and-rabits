import java.awt.Color;
import java.util.List;
import java.util.Random;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael KÃ¶lling
 * @version 2011.07.31
 */
public abstract class Animal
{
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;

    

    //the animal's age
    private int age;

    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();

    // The color to be displayed on the grid for this animal.
    private Color color;

    //The probability of the Animal to be swpaned on reset (in Simulator)
    private double creationProbability;

    
    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param color The color that this Animal will display on the Simulator
     * @param creationProbabilty The probabilty that this Animal will be spawned at any given space on the grid at reset
     */
    public Animal(Field field, Location location, Color color, double creationProbability)
    {
        alive = true;
        this.field = field;
        setLocation(location);
        this.color = color;
        this.creationProbability = creationProbability;

        age= 0;
    }
    
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    abstract public void act(List<Animal> newAnimals);

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
    }
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField()
    {
        return field;
    }

    /**
     * Return the animal's age
     * @return the animal's age
     */
    protected int getAge(){
        return age;
    }

    /**
     * Set the animals age
     * @param int the new value for animals age
     */
    protected void setAge(int age){
        this.age = age;
    }
    
    /**
     * Return the breeding age of this animal
     * @return The breeding age of this animal
     */
    abstract protected int getBreedingAge();

    /**
     * An animal can breed if it has reached the breeding age)
     * @return true if animal can breed
     */
    public boolean canBreed(){
        return age >= getBreedingAge();
    }

    /**
     * Return the max age of this animal
     * @return int the max age of this animal
     */
    abstract protected int getMaxAge();

    /**
     * Increase the age. This could result in the animal's death.
     */
    protected void incrementAge()
    {
        age++;
        if(age > getMaxAge()) {
            setDead();
        }
    }

    /**
     * Return the breeding probabilty for this animal
     * @return the breeding probabilty for this animal
     */
    abstract protected double getBreedingProbability();

    /**
     * Return the max litter size for this animal
     * @return The max litter size for this animal
     */
    abstract protected int getMaxLitterSize();

    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    protected int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= getBreedingProbability()) {
            births = rand.nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }

    /**
     * Returns color for this animal
     * @return Color for this animal
     */
    public Color getColor(){
        return color;
    }

    /**
     * Returns creation probabilty for this Animal
     * @return creation probability for this Animal
     */
    public double getCreationProbability(){
        return creationProbability;
    }

    /*
     * creates a new Animal of the subclass' type
     */
    abstract protected Animal makeAnimal(boolean randAge, Field field, Location location);

    /**
     * Check whether or not this animal is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newAnimals A list to return newly born animals.
     */
    public void giveBirth(List<Animal> newAnimals)
    {
        // New foxes are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Animal newAnimal = makeAnimal(false, field, loc);
            newAnimals.add(newAnimal);
        }
    }

}