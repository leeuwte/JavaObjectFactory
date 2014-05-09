package nl.leeuwte.objectfactory;

/**
 * Created by benny on 1-5-14.
 */
public class ObjectClass {

    private final Class classType;
    private Object instance;
    private String name = null;
    private boolean isSingleton = false;

    public <T> ObjectClass(T classType) {
        this.classType = (Class) classType;
    }


    public <T> ObjectClass Use(T instance) {
        this.instance = instance;
        return this;
    }

    public ObjectClass Name(String name) {
        this.name = name;
        return this;
    }

    public ObjectClass AsSingleton() {
        this.isSingleton = true;
        return this;

    }

    public <T> T getInstance() {
        return (T) instance;
    }

    public Class getClassType() {
        return classType;
    }

    public String getName() {
        return name;
    }

    public boolean isSingleton() {
        return isSingleton;
    }
}
