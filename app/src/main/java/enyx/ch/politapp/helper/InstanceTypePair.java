package enyx.ch.politapp.helper;

/**
 * Created by adrien.manikon on 24.08.15.
 */
public class InstanceTypePair {

    private String instance;

    private TypeTag type;

    public InstanceTypePair(String instance, TypeTag type) {
        this.instance = instance;
        this.type = type;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public TypeTag getType() {
        return type;
    }

    public void setType(TypeTag type) {
        this.type = type;
    }
}
