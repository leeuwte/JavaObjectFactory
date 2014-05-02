package nl.leeuwte.objectfactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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


    public static <T> T InstanceOf(Class<T> classType, String name) {

        T result = null;


        //Find instance, if found, return instance
        result = FindInstance(classType, name);
        if (result != null) {
            if (debugMode) System.out.println("Object '" + classType + "' found: FindInstance");
            return result;
        }

        return result;

    }

    public static <T> T InstanceOf(Class<T> classType) {

        T result = null;


        //Find instance, if found, return instance
        result = FindInstance(classType, null);
        if (result != null) {
            if (debugMode) System.out.println("Object '" + classType + "' found: FindInstance");
            return result;
        }


        //Nothing found yet, try to create instance with parameterless constructor
        result = ConstructSimpleInstance(classType);
        if (result != null) {
            if (debugMode) System.out.println("Object '" + classType + "' created: ConstructSimpleInstance");
            For(classType).Use(result);
            return result;
        }

        //Still nothing found, try to construct instance with parameterfull constructur
        result = ConstructInstance(classType);
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

    private static <T> T FindInstance(Class<T> classType, String name) {

        for (ObjectClass cClass : objectClasses) {
            if (cClass.getClassType() == classType && (name == null || cClass.getName().equals(name))) {
                return cClass.getInstance();
            }
        }
        return null;

    }

    private static <T> T ConstructSimpleInstance(Class<T> classType) {

        T result = null;

        try {


            //Exclude unconstructableClasses from default construction
            if (Arrays.asList(unconstructableClasses).contains(classType))
                throw new InstantiationException();

            result = classType.newInstance();
        } catch (InstantiationException e) {

        } catch (IllegalAccessException e) {

        }


        return result;
    }

    private static <T> T ConstructInstance(Class<T> classType) {

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

            } catch (IllegalAccessException e) {

            } catch (InvocationTargetException e) {

            }

        }

        return result;
    }

    public static void Scan(String packageName) {


        HashMap<String, Class<?>> classes = ClassEnumerator.getClassesForPackage(packageName);


        for(String className : classes.keySet()) {

            if (!className.startsWith("I")) {
                Class<?> iface = classes.get("I" + className);

                if (iface != null) {

                    Class<?> cls = classes.get(className);
                    Object clsInstance = InstanceOf(cls);

                    if (cls != null && clsInstance != null) {
                        For(iface).Use(clsInstance);
                    }
                }
            }
        }

    }
}
