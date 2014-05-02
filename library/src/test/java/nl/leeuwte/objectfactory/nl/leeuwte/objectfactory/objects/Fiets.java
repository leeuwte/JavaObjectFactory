package nl.leeuwte.objectfactory.nl.leeuwte.objectfactory.objects;

/**
 * Created by benny on 1-5-14.
 */
public class Fiets implements IVervoer {

    private final Auto auto;

    public Fiets(Auto auto) {
      this.auto = auto;
    }

    @Override
    public String getAandrijving() {
        return "trappers " + auto.getAandrijving();
    }
}
