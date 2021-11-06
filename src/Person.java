import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Person {
    public String name;
    public boolean male;
    public Integer age;
    public Adress adress;
    public List<Toe> toes;
    public int[]points;

    public Person(String name,boolean male, Integer age, Adress adress,List<Toe> toes,int[]points) {
        this.name = name;
        this.male=male;
        this.age = age;
        this.adress = adress;
        this.toes=toes;
        this.points=points;

    }
}
