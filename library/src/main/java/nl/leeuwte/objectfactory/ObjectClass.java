package nl.leeuwte.objectfactory;

/**
 * Created by benny on 1-5-14.
 */
public class ObjectClass {

    private final Class classType;
    private Object instance;
    private String name = null;

    public <T> ObjectClass(T classType) {
        this.classType = (Class) classType;
    }

    public <T> ObjectClass Use(T instance) {
        this.instance = instance;
        return this;
    }

    public void Name(String name) {
        this.name = name;

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
}
