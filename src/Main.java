import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        ArrayList<Toe>toes= new ArrayList<>(Arrays.asList(new Toe(true,98),new Toe(false,101)));
        Person p=new Person("thomas",true,25,new Adress("hofstraat",78),toes,new int[]{1,2,3});
        ArrayList<Object>list=new ArrayList<>();
        recursiceReflection(list,p);
        list.forEach(o-> System.out.println(o));
    }

    private static void recursiceReflection(ArrayList<Object> list, Object o) throws IllegalAccessException {
        if(o!=null){
            Field[] fields=o.getClass().getFields();

            for(Field f:fields){
                //1. if field is a collection (list, map,...), iterate over children
                if (List.class.isAssignableFrom(f.getType())) {
                    for(Object o2:(List)f.get(o)){
                        if(o2.getClass().isPrimitive()||isJavaLang(o2)){
                            list.add(o2);
                        }
                        //if java is is own created class (Person, Adress, Toe) --> recursive that object
                        else{
                            recursiceReflection(list, o2);
                        }
                    }
                }
                //2. if field is array
                if(f.getType().isArray()){
                    for(int i=0; i<Array.getLength(f.get(o)); i++){
                        Object o2=Array.get(f.get(o), i);
                        //if field is primitive (int, double, boolean) OR java class (String, Integer, Double) --> add to list
                        if(o2.getClass().isPrimitive()||isJavaLang(o2)){
                            list.add(o2);
                        }
                        //if java is is own created class (Person, Adress, Toe) --> recursive that object
                        else{
                            recursiceReflection(list, o2);
                        }
                    }
                }

                //3
                //if field is primitive (int, double, boolean) OR java class (String, Integer, Double) --> add to list
                if(f.getType().isPrimitive()||isJavaLang(f.get(o))){
                    list.add(f.get(o));
                }
                //if java is is own created class (Person, Adress, Toe) --> recursive that object
                else{
                    recursiceReflection(list, f.get(o));
                }
            }
        }

    }

    public static boolean isJavaLang(Object check) {
        return check.getClass().getName().startsWith("java.lang");
    }
}
