import java.util.LinkedList;
import java.util.ListIterator;
public class Cache {
    private static int r1 = 0;
    private static int r2 = 0;
    private static double HC1 = 0;
    private static double HC2 = 0;
    private static int cache1_size;
    private static int cache2_size;
    private static boolean detect = false;
    private static Object temp;
    private static LinkedList<Object> Cache1;
    private static LinkedList<Object> Cache2;

    public Cache(int args0, int args1, int args2, String args3) {
        if(args0 == 2) {
            cache1_size = args1;
            cache2_size = args2;
            Cache1 = new LinkedList<>();
            Cache2 = new LinkedList<>();
        }
        if(args0 == 1){
            cache1_size = args1;
            cache2_size = 0;
            Cache1 = new LinkedList<>();
        }
    }

    public static Object get(Object key) {
        if (cache2_size == 0) {
            r1++;
            if(Cache1.isEmpty()){
                add(key);
                return key;
            }
            ListIterator<Object> I = Cache1.listIterator(0);
            while (I.hasNext()) {
                if (I.next().equals(key)) {
                    HC1++;
                    I.remove();
                    break;
                }
            }
            add(key);
            return key;
        }
        r1++;
        if (Cache1.isEmpty() && Cache2.isEmpty()) {
            r1++;
            r2++;
        }
        if(Cache2.isEmpty()){
            r2++;
        }
        ListIterator<Object> I = Cache1.listIterator(0);
        while (I.hasNext()) {
            if (I.next().equals(key)) {
                HC1++;
                I.remove();
                detect = true;
                break;
            }
        }
        if(!detect) {
            ListIterator<Object> J = Cache2.listIterator(0);
            r2++;
            while (J.hasNext()) {
                if (J.next().equals(key)) {
                    HC2++;
                    J.remove();
                    break;
                }
            }
        }
        add(key);
        detect = false;
        return key;
    }

    public static Object add(Object value) {
        if (cache2_size == 0) {
            if(Cache1.isEmpty()) {
                Cache1.addFirst(value);
                return value;
            }
            if(Cache1.size() >= cache1_size){
                while(Cache1.size() >= cache1_size){
                    Cache1.removeLast();
                }
            }
            Cache1.addFirst(value);
            return value;
        }
        if(!Cache1.isEmpty() && Cache2.isEmpty()){
            Cache2 = Cache1;
            Cache1.addFirst(value);
            Cache2.addFirst(value);
            return value;
        }
        if(Cache1.isEmpty() && Cache2.size() < cache2_size) {
            Cache1.addFirst(value);
            Cache2.addFirst(value);
            return value;
        }
        if(Cache1.isEmpty()){
            while(Cache2.size() >= cache2_size){
                Cache2.removeLast();
            }
            Cache1 = Cache2;
            while(Cache1.size() >= cache1_size){
                Cache1.removeLast();
            }
            Cache1.addFirst(value);
            Cache2.addFirst(value);
            return value;
        }
        if (Cache1.size() < cache1_size && Cache2.size() < cache2_size) {
            Cache1.addFirst(value);
            Cache2.addFirst(value);
            return value;
        }

        if (Cache1.size() < cache1_size && Cache2.size() == cache2_size) {
            Cache2.removeLast();
            Cache1.addFirst(value);
            Cache2.addFirst(value);
            return value;
        }
        if(Cache1.size() == cache1_size && Cache2.size() == cache2_size){
            temp = Cache1.getLast();
            Cache1.removeLast();
            Cache2.removeLast();
            Cache2.addFirst(temp);
            Cache2.removeLast();
            Cache1.addFirst(value);
            Cache2.addFirst(value);
            return value;
        }
        if(Cache1.size() == cache1_size && Cache2.size() < cache2_size){
            temp = Cache1.getLast();
            Cache1.removeLast();
            Cache2.addFirst(temp);
            if(Cache2.size() == cache2_size){
                Cache2.removeLast();
            }
            Cache1.addFirst(value);
            Cache2.addFirst(value);
            return value;
        }
        if(Cache1.size() > cache1_size && Cache2.size() == cache2_size){
            while(Cache1.size() >= cache1_size){
                temp = Cache1.getLast();
                Cache2.removeLast();
                Cache2.addFirst(temp);
            }
            Cache1.addFirst(value);
            Cache2.removeLast();
            Cache2.addFirst(value);
            return value;
        }
        if(Cache1.size() > cache1_size && Cache2.size() > cache2_size){
            while(Cache1.size() >= cache1_size){
                temp = Cache1.getLast();
                Cache2.removeLast();
                Cache2.addFirst(temp);
                Cache1.removeLast();
            }
            while(Cache2.size() >= cache2_size){
                Cache2.removeLast();
            }
            Cache1.addFirst(value);
            Cache2.addFirst(value);
            return value;
        }
        if(Cache1.size() < cache1_size){
            while(Cache2.size() >= cache2_size){
                Cache2.removeLast();
            }
            Cache2.addFirst(value);
            Cache1.addFirst(value);
            return value;
        }
        if(Cache1.size() > cache1_size){
            while(Cache1.size() >= cache1_size){
                temp = Cache1.getLast();
                Cache2.addFirst(temp);
                Cache1.removeLast();
            }
            if(Cache2.size() >= cache2_size){
                add(value);
            }
            Cache1.addFirst(value);
            Cache2.addFirst(value);
            return value;
        }
        return value;
    }
    public Object remove(Object key){
        if(Cache1.contains(key)){
            ListIterator<Object> K = Cache1.listIterator(0);
            while(K.hasNext()){
                if(K.next().equals(key)){
                    temp = Cache1.get(K.previousIndex());
                    K.remove();
                    return temp;
                }
            }
        }
        if(cache2_size > 0) {
            if (Cache1.isEmpty() && Cache2.isEmpty() || !Cache1.contains(key) && !Cache2.contains(key)) {
                return null;
            }

            if (Cache2.contains(key)) {
                ListIterator<Object> L = Cache2.listIterator(0);
                while (L.hasNext()) {
                    if (L.next().equals(key)) {
                        temp = Cache2.get(L.previousIndex());
                        L.remove();
                        return temp;
                    }
                }
            }
        }
        return null;
    }
    public static void clear(){
        Cache1.clear();
        if(cache2_size > 0) {
            Cache2.clear();
        }
    }
    @Override
    public String toString(){
        if(cache2_size == 0){
            double HR1 = (HC1 / r1);
            return("First level cache with " + cache1_size + " entries has been created\n..............................\nThe number of global references:\t" + r1 + "\nThe number of global cache hits:\t" + HC1 +"\nThe global hit ratio\t\t\t:\t" + HR1 + "\n\nThe number of 1st-level references:\t" + r1 + "\nThe number of 1st-level cache hits:\t" + HC1);
        }
        double HC = HC1 + HC2;
        double HR1 = (HC1 / r1);
        double HR2 = (HC2 / r2);
        double HR = (HC / r1);
        return("First level cache with " + cache1_size + " entries has been created\nSecond level cache with " + cache2_size +" entries has been created\n..............................\nThe number of global references:\t" + r1 + "\nThe number of global cache hits:\t" + HC +"\nThe global hit ratio\t\t\t:\t" + HR + "\n\nThe number of 1st-level references:\t" + r1 + "\nThe number of 1st-level cache hits:\t" + HC1 + "\nThe 1st-level cache hit ratio\t\t\t:\t" + HR1 + "\n\nThe number of 2nd-level references:\t" + r2 + "\nThe number of 2nd-level cache hits:\t" + HC2 + "\nThe 2nd-level cache hit ratio\t\t\t:\t" + HR2);
    }
}
