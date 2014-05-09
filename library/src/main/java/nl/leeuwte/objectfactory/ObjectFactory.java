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

    private static List<ObjectClass> objectInstances = new ArrayList<ObjectClass>();

    private static Class[] unconstructableClasses = new Class[]{String.class};

    public static <T> ObjectClass For(T classType) {
        ObjectClass oc = new ObjectClass(classType);
        objectInstances.add(oc);
        return oc;
    }

    public static void SetDebug(boolean enabled) {
        debugMode = enabled;
    }

    public static void Reset() {

        objectInstances.clear();
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

        ObjectClass oClass = FindObjectClass(classType, null);

        //Find instance, if found, return instance
        result = FindInstance(oClass, null);

        if (result != null) {

            if (result instanceof Class) {
                Class<T> resultClassType = (Class<T>) result;
                if (resultClassType != classType) {
                    return InstanceOf(resultClassType);
                }
            } else {
                if (debugMode) System.out.println("Object '" + classType + "' found: FindInstance");
                return result;
            }
        }


        //Nothing found yet, try to create instance with parameterless constructor
        result = ConstructSimpleInstance(classType);
        if (result != null) {
            if (debugMode) System.out.println("Object '" + classType + "' created: ConstructSimpleInstance");
            if (oClass != null && oClass.isSingleton()) {
                oClass.Use(result);
            }
            return result;
        }

        //Still nothing found, try to construct instance with parameterfull constructur
        result = ConstructInstance(classType);
        if (result != null) {
            if (debugMode) System.out.println("Object '" + classType + "' created: ConstructInstance");
            if (oClass != null && oClass.isSingleton()) {
                oClass.Use(result);
            }
            return result;
        }

        //Arghh...  nothing found, return null...
        //System.out.print(stackTraceElements);

        System.out.println("Cannot find or construct " + classType + ".");


        return null;
    }

    private static <T> T FindInstance(ObjectClass oClass, String name) {

        if (oClass == null)
            return null;

        if (oClass.getClassType() == oClass.getClassType() && (name == null || (oClass.getName() != null && oClass.getName().equals(name)))) {
            return oClass.getInstance();
        }

        return null;

    }

    private static <T> T FindInstance(Class<T> classType, String name) {

        ObjectClass oClass = FindObjectClass(classType, name);

        T result = FindInstance(oClass, name);

        if (result != null)
            return result;


        return null;

    }

    private static <T> ObjectClass FindObjectClass(Class<T> classType, String name) {
        for (ObjectClass oClass : objectInstances) {
            if (oClass.getClassType() == classType && (name == null || (oClass.getName() != null && oClass.getName().equals(name)))) {
                return oClass;
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
        try {

            for (Constructor<?> ctor : constructors) {

                Class<?>[] parameterTypes = ctor.getParameterTypes();

                Object[] parameters = new Object[parameterTypes.length];

                for (int i = 0; i < parameters.length; i++) {

                    Class<?> ptype = parameterTypes[i];

                    parameters[i] = InstanceOf(ptype);

                    if (parameters[i] == null)
                        throw new InstantiationException();

                }

                result = (T) ctor.newInstance(parameters);
            }
        } catch (InstantiationException e) {

        } catch (IllegalAccessException e) {

        } catch (InvocationTargetException e) {

        }


        return result;
    }

    @Deprecated
    public static void Scan(String packageName) {


        HashMap<String, Class<?>> classes = ClassEnumerator.getClassesForPackage(packageName);


        for (String className : classes.keySet()) {

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
