package nl.leeuwte.objectfactory.nl.leeuwte.objectfactory.objects;

/**
 * Created by benny on 9-5-14.
 */
public class Plane implements IVervoer {

    private final IVervoer vervoer;

    public Plane(IVervoer vervoer) {
        this.vervoer = vervoer;
    }

    @Override
    public String getAandrijving() {
        return "straalmotor " + vervoer.getAandrijving();
    }
}
