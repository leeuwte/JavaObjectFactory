package nl.leeuwte.objectfactory.nl.leeuwte.objectfactory.objects;

/**
 * Created by benny on 1-5-14.
 */
public class Auto implements IVervoer {


    private final String soort;

    public Auto(String soort) {
        this.soort = soort;
    }

    @Override
    public String getAandrijving() {
        return "brandstofmotor " + soort;
    }
}
