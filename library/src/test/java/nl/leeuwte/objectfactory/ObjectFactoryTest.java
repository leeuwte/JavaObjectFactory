package nl.leeuwte.objectfactory;

import nl.leeuwte.objectfactory.nl.leeuwte.objectfactory.objects.Auto;
import nl.leeuwte.objectfactory.nl.leeuwte.objectfactory.objects.Brommer;
import nl.leeuwte.objectfactory.nl.leeuwte.objectfactory.objects.Fiets;
import nl.leeuwte.objectfactory.nl.leeuwte.objectfactory.objects.IVervoer;
import org.junit.Test;

import static org.junit.Assert.*;

public class ObjectFactoryTest {



    @Test
    public void testDi1() throws Exception {

        initFactory();


        Brommer b = ObjectFactory.InstanceOf(Brommer.class);
        System.out.println(b.getAandrijving());

        Auto x = ObjectFactory.InstanceOf(Auto.class);
        System.out.println(x.getAandrijving());

        Fiets f = ObjectFactory.InstanceOf(Fiets.class);
        System.out.println(f.getAandrijving());

        IVervoer i = ObjectFactory.InstanceOf(IVervoer.class);
        System.out.println(i.getAandrijving());

    }

    private void initFactory() {

        ObjectFactory.For(Auto.class).Use(new Auto("diesel"));
        ObjectFactory.For(IVervoer.class).Use(new Auto("benzine"));

    }

}