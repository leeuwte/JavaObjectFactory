package nl.leeuwte.objectfactory.nl.leeuwte.objectfactory.objects;

/**
 * Created by benny on 1-5-14.
 */
public class Car implements IVervoer {


    private String type = "default";

    public Car(String type) {
        this.type = type;
    }


    @Override
    public String getAandrijving() {
        return type;
    }
}
