package nl.leeuwte.objectfactory;

/**
 * Created by benny on 1-5-14.
 */
public class ObjectClass {

    private final Class classType;
    private Object instance;

    public <T> ObjectClass(T classType) {
        this.classType = (Class) classType;

    }

    public <T> void Use(T instance) {
        this.instance = instance;
    }

    public <T> T getInstance() {
        return (T)instance;
    }

    public Class getClassType() {
        return classType;
    }
}
