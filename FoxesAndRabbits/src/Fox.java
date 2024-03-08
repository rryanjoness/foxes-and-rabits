import java.util.List;
import java.util.Random;

public class Fox extends Animal {
    //TODO: assign correct values to readonly fields
    private final int BREEDING_AGE = 1;
    private final int MAX_AGE = 2;
    private final int MAX_LITTER_SIZE = 1000;
    private final int RABBIT_FOOD_VALUE = 1;
    private final Random rand = new Random();

    private int age;
    private int foodLevel;

    public Fox(boolean alive, char sex, Location location){
        super(alive, sex, location);
    }

    public void act(List<Animal> newAnimals){
        //TODO: give body
    }

    public void incrementAge(){
        age++;
    }

    public void incrementHunger(){
        foodLevel--;
    }

    public Location findFood(){
        //TODO: give body
        return super.getLocation();
    }


}
