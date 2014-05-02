package nl.leeuwte.objectfactory;

import nl.leeuwte.objectfactory.nl.leeuwte.objectfactory.objects.Car;
import nl.leeuwte.objectfactory.nl.leeuwte.objectfactory.objects.IVervoer;
import org.junit.Test;

public class ObjectFactoryTest {



    @Test
    public void testDi1() throws Exception {

        initFactory();


//        Brommer b = ObjectFactory.InstanceOf(Brommer.class);
//        System.out.println(b.getAandrijving());

        Car x = ObjectFactory.InstanceOf(Car.class, "benzineauto");
        System.out.println(x.getAandrijving());
        Car y = ObjectFactory.InstanceOf(Car.class, "dieselauto");
        System.out.println(y.getAandrijving());
        Car z = ObjectFactory.InstanceOf(Car.class);
        System.out.println(z.getAandrijving());

//        Fiets f = ObjectFactory.InstanceOf(Fiets.class);
//        System.out.println(f.getAandrijving());

        IVervoer i = ObjectFactory.InstanceOf(IVervoer.class);
        System.out.println(i.getAandrijving());

//        Brommer b2 = ObjectFactory.InstanceOf(Brommer.class);
//        System.out.println(b2.getAandrijving());
    }

    private void initFactory() {

        //ObjectFactory.For(Auto.class).Use(new Auto("diesel"));
        ObjectFactory.For(Car.class).Use(new Car("benzine")).Name("benzineauto");
        ObjectFactory.For(Car.class).Use(new Car("diesel")).Name("dieselauto");

        String packageName = getClass().getPackage().getName();

        ObjectFactory.Scan(packageName);

    }

}