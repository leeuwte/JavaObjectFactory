package nl.leeuwte.objectfactory.nl.leeuwte.objectfactory.objects;

/**
 * Created by benny on 1-5-14.
 */
public class Car implements IVervoer {


    private final String soort;

    public Car(String soort) {
        this.soort = soort;
    }

    //    @Override
    public String getAandrijving() {
        return "brandstofmotor '" + soort + "'";
    }
}
