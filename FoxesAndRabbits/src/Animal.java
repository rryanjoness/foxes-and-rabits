import java.util.List;

public abstract class Animal {
    private boolean alive;
    private char sex;
    //private Field field;
    private Location location;

    public Animal(boolean alive, char sex, Location loc){
        this.alive = alive;
        this.sex = sex;
        this.location = location;
    }


    public Location getLocation() {
        return location;
    }

    public char getSex() {
        return sex;
    }
    
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }




    abstract public void act(List<Animal> newAnimals);

    public boolean isAlive(){
        return alive;
    }

    public void setDead(){
        alive = false;
    }

    public void setLocation(Location location){
        //TODO: give body
    }

    public char assignSex(){
        //TODO: give body
        return 'm';
    }
}
