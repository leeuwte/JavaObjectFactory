package nl.leeuwte.objectfactory.nl.leeuwte.objectfactory.objects;

/**
 * Created by benny on 1-5-14.
 */
public class Fiets implements IVervoer {

    private final Car car;

    public Fiets(Car car) {
      this.car = car;
    }

//    @Override
    public String getAandrijving() {
        return "trappers " + car.getAandrijving();
    }
}
