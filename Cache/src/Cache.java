import java.util.LinkedList;
import java.util.ListIterator;
public class Cache <E> {
    private double r1 = 0;
    private double r2 = 0;
    private double HC1 = 0;
    private double HC2 = 0;
    private int cache1_size;
    private int cache2_size;
    private E temp;
    private LinkedList<E> Cache1;
    private LinkedList<E> Cache2;

    public Cache(int args1, int args2) {
        cache1_size = args1;
        cache2_size = args2;
        Cache1 = new LinkedList<>();
        Cache2 = new LinkedList<>();
    }

    public E get(E key) {
        r1++;
        if (Cache1.isEmpty() && Cache2.isEmpty() || !Cache1.contains(key) && !Cache2.contains(key)) {
            return null;
        }
        if (!Cache1.contains(key) && Cache2.contains(key)) {
            r2++;
            ListIterator<E> I = Cache2.listIterator(0);
            while (I.hasNext()) {
                if (I.next().equals(key)) {
                    HC2++;
                    temp = Cache2.get(I.previousIndex());
                    I.remove();
                    Cache1.addFirst(temp);
                    Cache2.addFirst(temp);
                    return temp;
                }
            }
        }
        if (Cache1.contains(key) && !Cache2.contains(key)) {
            Cache2.addFirst(key);
            ListIterator<E> J = Cache1.listIterator(0);
            while (J.hasNext()) {
                if (J.next().equals(key)) {
                    HC1++;
                    temp = Cache1.get(J.previousIndex());
                    J.remove();
                    Cache1.addFirst(temp);
                    return temp;
                }
            }
        }
        return null;
    }

    public E add(E value) {
        if (Cache1.size() < cache1_size && Cache2.size() < cache2_size) {
            Cache1.addFirst(value);
            Cache2.addFirst(value);
            return null;
        }
        if (Cache1.size() > cache1_size && Cache2.size() < cache2_size) {
            temp = Cache1.getLast();
            Cache2.addFirst(temp);
            Cache1.removeLast();
            Cache1.addFirst(value);
            return temp;
        }
        if (Cache1.size() > cache1_size && Cache2.size() > cache2_size) {
            Cache1.removeLast();
            Cache2.removeLast();
            Cache1.addFirst(value);
            Cache2.addFirst(value);
            return null;
        }
        if (Cache1.size() < cache1_size && Cache2.size() > cache2_size) {
            Cache1.addFirst(value);
            Cache2.removeLast();
            Cache2.addFirst(value);
            return null;
        }
        return null;
    }
    public E remove(E key){
        if(Cache1.isEmpty() && Cache2.isEmpty() || !Cache1.contains(key) && !Cache2.contains(key)){
            return null;
        }
        if(Cache1.contains(key)){
            ListIterator<E> K = Cache1.listIterator(0);
            while(K.hasNext()){
                if(K.next().equals(key)){
                    temp = Cache1.get(K.previousIndex());
                    K.remove();
                    return temp;
                }
            }
        }
        if(Cache2.contains(key)){
            ListIterator <E> L = Cache2.listIterator(0);
            while(L.hasNext()){
                if(L.next().equals(key)){
                    temp = Cache2.get(L.previousIndex());
                    L.remove();
                    return temp;
                }
            }
        }
        return null;
    }
    public void clear(){
        if(!Cache1.isEmpty() && !Cache2.isEmpty()){
            Cache1.clear();
            Cache2.clear();
        }
    }
    @Override
    public String toString(){
        double HC = HC1 + HC2;
        double HR1 = (HC1 / r1) * 100;
        double HR2 = (HC2 / r2) * 100;
        double HR = (HC / r1) * 100;
        return("First level cache with " + cache1_size + " entries has been created\nSecond level cache with " + cache2_size +" entries has been created\n..............................\nThe number of global references:\t" + r1 + "\nThe number of glabal cache hits:\t" + HC +"The global hit ratio\t\t\t:\t" + HR + "\n\nThe number of 1st-level references:\t" + r1 + "\nThe number of 1st-level cache hits:\t" + HC1 + "\nThe 1st-level cache hit ratio\t\t\t:\t" + HR1 + "\n\nThe number of 2nd-level references:\t" + r2 + "\nThe number of 2nd-level cache hits:\t" + HC2 + "\nThe 2nd-level cache hit ratio\t\t\t:\t" + HR2);
    }
}
