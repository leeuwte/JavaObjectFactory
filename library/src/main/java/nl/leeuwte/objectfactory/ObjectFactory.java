package nl.leeuwte.objectfactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by benny on 1-5-14.
 */
public class ObjectFactory {

    private static List<ObjectClass> objectClasses = new ArrayList<ObjectClass>();

    public static <T> ObjectClass For(T classType) {
        ObjectClass oc = new ObjectClass(classType);
        objectClasses.add(oc);
        return oc;
    }


    public static <T> T InstanceOf(Class<T> classType) {

        T result = null;

        //Find instance, if found, return instance
        result = FindInstance(classType);
        if (result != null) {
            return result;
        }


        //Nothing found yet, try to create instance with parameterless constructor
        result = CreateSimpleInstance(classType);
        if (result != null) {
            For(classType).Use(result);
            return result;
        }

        //Still nothing found, try to construct instance with paramterfull constructur
        result = ConstructInstance(classType);
        if (result != null) {
            For(classType).Use(result);
            return result;
        }

        //Arghh...  nothing found, return null...
        return null;
    }

    private static <T> T FindInstance(Class<T> classType) {

        for (ObjectClass cClass : objectClasses) {
            if (cClass.getClassType() == classType) {
                return cClass.getInstance();
            }
        }
        return null;

    }

    private static <T> T CreateSimpleInstance(Class<T> classType) {

        T result = null;

        try {
            result = classType.newInstance();
        } catch (InstantiationException e) {
            //e.printStackTrace();
        } catch (IllegalAccessException e) {
            //e.printStackTrace();
        }


        return result;
    }

    private static <T> T ConstructInstance(Class<T> classType) {

        T result = null;

        Constructor<?>[] constructors = classType.getConstructors();

        for (Constructor<?> ctor : constructors) {

            Class<?>[] parameterTypes = ctor.getParameterTypes();

            Object[] parameters = new Object[parameterTypes.length];

            for (int i = 0; i < parameters.length; i++) {

                Class<?> ptype = parameterTypes[i];

                parameters[i] = InstanceOf(ptype);
            }

            try {
                result = (T) ctor.newInstance(parameters);
            } catch (InstantiationException e) {
                //e.printStackTrace();
            } catch (IllegalAccessException e) {
                //e.printStackTrace();
            } catch (InvocationTargetException e) {
                //e.printStackTrace();
            }

        }

        return result;
    }


}
