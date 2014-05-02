package nl.leeuwte.objectfactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by benny on 1-5-14.
 */
public class ObjectFactory {

    private static boolean debugMode = false;
    private static List<ObjectClass> objectClasses = new ArrayList<ObjectClass>();

    private static Class[] unconstructableClasses = new Class[]{String.class};

    public static <T> ObjectClass For(T classType) {
        ObjectClass oc = new ObjectClass(classType);
        objectClasses.add(oc);
        return oc;
    }

    public static void SetDebug(boolean enabled) {
        debugMode = enabled;
    }


    public static <T> T InstanceOf(Class<T> classType) {

        List<StackTraceElement> stackTraceElements = new ArrayList<StackTraceElement>();
        T result = null;


        //Find instance, if found, return instance
        result = FindInstance(classType);
        if (result != null) {
            if (debugMode) System.out.println("Object '" + classType + "' found: FindInstance");
            return result;
        }


        //Nothing found yet, try to create instance with parameterless constructor
        result = ConstructSimpleInstance(classType, stackTraceElements);
        if (result != null) {
            if (debugMode) System.out.println("Object '" + classType + "' created: ConstructSimpleInstance");
            For(classType).Use(result);
            return result;
        }

        //Still nothing found, try to construct instance with parameterfull constructur
        result = ConstructInstance(classType, stackTraceElements);
        if (result != null) {
            if (debugMode) System.out.println("Object '" + classType + "' created: ConstructInstance");
            For(classType).Use(result);
            return result;
        }

        //Arghh...  nothing found, return null...
        //System.out.print(stackTraceElements);

        System.out.println("Cannot find or construct " + classType + ".");


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

    private static <T> T ConstructSimpleInstance(Class<T> classType, List<StackTraceElement> stackTraceElements) {

        T result = null;

        try {


            //Exclude unconstructableClasses from default construction
            if (Arrays.asList(unconstructableClasses).contains(classType))
                throw new InstantiationException();

            result = classType.newInstance();
        } catch (InstantiationException e) {
            stackTraceElements.addAll(Arrays.asList(e.getStackTrace()));

        } catch (IllegalAccessException e) {
            stackTraceElements.addAll(Arrays.asList(e.getStackTrace()));

        }


        return result;
    }

    private static <T> T ConstructInstance(Class<T> classType, List<StackTraceElement> stackTraceElements) {

        //Exclude unconstructableClasses from instance construction
        if (Arrays.asList(unconstructableClasses).contains(classType))
            return null;

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
                stackTraceElements.addAll(Arrays.asList(e.getStackTrace()));
            } catch (IllegalAccessException e) {
                stackTraceElements.addAll(Arrays.asList(e.getStackTrace()));
            } catch (InvocationTargetException e) {
                stackTraceElements.addAll(Arrays.asList(e.getStackTrace()));
            }

        }

        return result;
    }


}
