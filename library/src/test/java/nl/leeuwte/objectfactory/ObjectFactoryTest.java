package nl.leeuwte.objectfactory;

import nl.leeuwte.objectfactory.nl.leeuwte.objectfactory.objects.*;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertNotSame;
import static junit.framework.TestCase.assertEquals;

public class ObjectFactoryTest {

    @Before
    public void reset() {
        ObjectFactory.Reset();
    }

    @Test
    public void testSimpleClassWithImplementation() throws Exception {


        ObjectFactory.For(Car.class).Use(new Car("fuel"));
        Car c = ObjectFactory.InstanceOf(Car.class);

        assertEquals(c.getAandrijving(), "fuel");

    }

    @Test
    public void testSimpleClassWithName() throws Exception {

        ObjectFactory.For(Car.class).Use(new Car("aaaa"));
        ObjectFactory.For(Car.class).Use(new Car("bbbb")).Name("bcar");

        Car c = ObjectFactory.InstanceOf(Car.class, "bcar");

        assertEquals(c.getAandrijving(), "bbbb");

    }


    @Test
    public void testInterfaceWithClassType() throws Exception {

        ObjectFactory.For(IVervoer.class).Use(Brommer.class);

        IVervoer iv = ObjectFactory.InstanceOf(IVervoer.class);

        assertEquals(iv.getAandrijving(), "brommer");


    }


    @Test
    public void testClassWithoutClassType() throws Exception {

        Brommer b = ObjectFactory.InstanceOf(Brommer.class);

        assertEquals(b.getAandrijving(), "brommer");
    }

    @Test
    public void testIfClassesAreDifferent() throws Exception {


        Brommer b1 = ObjectFactory.InstanceOf(Brommer.class);
        Brommer b2 = ObjectFactory.InstanceOf(Brommer.class);


        assertNotSame(b1, b2);
    }


    @Test
    public void testIfClassesAreDifferentWithInstance() throws Exception {

        ObjectFactory.For(Brommer.class).AsSingleton();

        Brommer b1 = ObjectFactory.InstanceOf(Brommer.class);
        Brommer b2 = ObjectFactory.InstanceOf(Brommer.class);

        assertEquals(b1, b2);
    }

    @Test
    public void testConstructClassInConstructor() throws Exception {

        ObjectFactory.For(Car.class).Use(new Car("car"));

        Fiets f = ObjectFactory.InstanceOf(Fiets.class);

        assertEquals(f.getAandrijving(), "trappers car");
    }


    @Test
    public void testConstructInterfaceInConstructor() throws Exception {

        ObjectFactory.For(IVervoer.class).Use(new Car("car"));

        Plane f = ObjectFactory.InstanceOf(Plane.class);

        assertEquals(f.getAandrijving(), "straalmotor car");

    }


}