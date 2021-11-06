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

    private static void recursiceReflection(ArrayList<Object> list, Object parent) throws IllegalAccessException {
        if(parent!=null){
            Field[] fields=parent.getClass().getFields();

            for(Field f:fields){
                //1. if field is a collection (list, map,...), iterate over children
                if (List.class.isAssignableFrom(f.getType())) {
                    for(Object o:(List)f.get(parent)){
                        if(o.getClass().isPrimitive()||isJavaLang(o)){
                            list.add(o);
                        }
                        //if java is is own created class (Person, Adress, Toe) --> recursive that object
                        else{
                            recursiceReflection(list, o);
                        }
                    }
                }
                //2. if field is array
                if(f.getType().isArray()){
                    for(int i=0; i<Array.getLength(f.get(parent)); i++){
                        Object o=Array.get(f.get(parent), i);
                        //if field is primitive (int, double, boolean) OR java class (String, Integer, Double) --> add to list
                        if(o.getClass().isPrimitive()||isJavaLang(o)){
                            list.add(o);
                        }
                        //if java is is own created class (Person, Adress, Toe) --> recursive that object
                        else{
                            recursiceReflection(list, o);
                        }
                    }
                }

                //3
                //if field is primitive (int, double, boolean) OR java class (String, Integer, Double) --> add to list
                Object o=f.get(parent);
                if(f.getType().isPrimitive()||isJavaLang(o)){
                    list.add(o);
                }
                //if java is is own created class (Person, Adress, Toe) --> recursive that object
                else{
                    recursiceReflection(list, o);
                }
            }
        }

    }

    public static boolean isJavaLang(Object check) {
        return check.getClass().getName().startsWith("java.lang");
    }
}
