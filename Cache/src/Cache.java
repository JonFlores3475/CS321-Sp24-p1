/**
 * Cache Class
 * CS 321 Spring 2024
 * @author Jon Flores
 */

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

    /**
     *
     * @param args0 int
     * @param args1 int
     * @param args2 int
     * @param args3 String
     */
    public Cache(int args0, int args1, int args2, String args3) { //Constructor for Cache Object
        if(args0 == 2) { //if 2Cache is requested
            cache1_size = args1;
            cache2_size = args2;
            Cache1 = new LinkedList<>();
            Cache2 = new LinkedList<>();
        }
        if(args0 == 1){//if Cache is requested
            cache1_size = args1;
            cache2_size = 0;
            Cache1 = new LinkedList<>();
        }
    }

    /**
     *
     * @param key Object
     * @return key Object
     */

    public static Object get(Object key) {
        if (cache2_size == 0) {//if single Cache
            r1++; //increment reference count
            if(Cache1.isEmpty()){//make sure Cache is not empty
                add(key);//if it is, add key
                return key;
            }
            //if Cache is not Empty
            ListIterator<Object> I = Cache1.listIterator(0);//Create a list iterator
            while (I.hasNext()) {//start stepping through list
                if (I.next().equals(key)) {//trigger to proceed through list as well as check each object in list
                    HC1++;//increment hit count if object is found
                    I.remove();//remove the found key object
                    break;//leave this loop
                }
            }
            add(key);//add key
            return key;
        }
        //else assume 2Cache
        r1++;//increment Cache1 reference number
        if (Cache1.isEmpty() && Cache2.isEmpty()) {//Make sure the Caches are not empty
            r1++;//if they are, increment Cache1 reference number
            r2++;//if they are, increment Cache2 reference number
        }
        if(Cache2.isEmpty()){//if just Cache2 is empty
            r2++;//increment Cache2 reference number
        }
        ListIterator<Object> I = Cache1.listIterator(0);//if all Caches are populated, creates a list iterator for Cache1
        while (I.hasNext()) {//start stepping through Cache1
            if (I.next().equals(key)) {//trigger to keep stepping through Cache1 as well as check each member for the key object
                HC1++;//if found increment Cache1 hit count
                I.remove();//remove the found key object
                detect = true;//set variable detect to true to tell us we do not need to check Cache2
                break;//leave this loop
            }
        }
        if(!detect) {//if detect is still false, we still have not found the key object, proceed to Cache2
            r2++;//increment Cache2 reference number
            ListIterator<Object> J = Cache2.listIterator(0);//Create a list iterator for Cache2
            while (J.hasNext()) {//Start stepping through Cache2
                if (J.next().equals(key)) {//trigger to keep stepping through Cache2 as well as check each member for the key object
                    HC2++;//if found increment the Cache2 hit counter
                    J.remove();//remove the found object
                    break;//leave this loop
                }
            }
        }
        add(key);//no matter what, we need to end by adding key to the front of both Caches
        detect = false;//reset detect variable
        return key;
    }

    /**
     *
     * @param value Object
     * @return value Object
     */
    public static Object add(Object value) {
        if (cache2_size == 0) {//determine if single Cache
            if(Cache1.isEmpty()) {//make sure the Cache is not empty
                Cache1.addFirst(value);//if it is, add value to front of Cache1
                return value;
            }
            if(Cache1.size() >= cache1_size){//if Cache1 ever exceeds size restriction
                while(Cache1.size() >= cache1_size){//while Cache1 is bigger than or equal to size restriction
                    Cache1.removeLast();//remove the last item in Cache1
                }
            }
            Cache1.addFirst(value);//no matter what, value is added to front of Cache1
            return value;
        }
        //else assume a 2Cache
        if(Cache1.isEmpty() && Cache2.isEmpty()){//make sure no Cache is empty, and if both are, then:
            Cache1.addFirst(value);//add value to front of Cache1
            Cache2.addFirst(value);//add value to front of Cache2
            return value;
        }
        if(Cache1.isEmpty()){//Make sure Cache1 is not empty
            while(Cache2.size() >= cache2_size){
                Cache2.removeLast();
            }
            Cache1.addAll(0,Cache2);//copy all of Cache2 into Cache1
            while(Cache1.size() >= cache1_size){//trim Cache1
                Cache1.removeLast();
            }
            Cache1.addFirst(value);//add value to front of Cache1
            Cache2.addFirst(value);//add value to front of Cache 2
            return value;
        }
        if(Cache2.isEmpty()){//Make sure Cache2 is not empty
            Cache2.addAll(0,Cache1);//copy all of Cache1 into Cache2
            while(Cache1.size() >= cache1_size){//make sure Cache1 is not too big
                Cache1.removeLast();
            }
            while(Cache2.size() >= cache2_size){
                Cache2.removeLast();
            }
            Cache1.addFirst(value);//add value to front of Cache1
            Cache2.addFirst(value);//add value to front of Cache2
            return value;
        }
        if(Cache1.get(0) == value && Cache2.get(0) == value){
            return value;
        }
        if(Cache1.get(0) == value && !(Cache2.get(0) == value)){
            while(Cache2.size() >= cache2_size){
                Cache2.removeLast();
            }
            Cache2.addFirst(value);
            return value;
        }
        if(Cache2.get(0) == value && !(Cache1.get(0)==value)){
            while(Cache1.size() > cache1_size){
                Cache1.removeLast();
            }
            Cache1.addFirst(value);
            return value;
        }
        if (Cache1.size() < cache1_size && Cache2.size() < cache2_size) {//if both Caches have space, then:
            Cache1.addFirst(value);//add value to front of Cache1
            Cache2.addFirst(value);//add value to front of Cache2
            return value;
            //this should be the majority of cases in the beginning
        }
        if (Cache1.size() < cache1_size && Cache2.size() == cache2_size) {//if Cache1 has room, but Cache2 does not
            Cache2.removeLast();//dump the last element of Cache2
            Cache1.addFirst(value);//add value to Cache1
            Cache2.addFirst(value);//add value to Cache2
            return value;
        }
        if(Cache1.size() == cache1_size && Cache2.size() == cache2_size){//if both Caches are at capacity
            temp = Cache1.getLast();//grab the last element of Cache1
            if(Cache2.contains(temp)) {
                Cache2.remove(temp);//make room in Cache2
                Cache2.add(Cache1.size(), temp);//add the last element of Cache1 to the corresponding position of Cache2
            }
            else{
                Cache2.removeLast();
                Cache2.add(Cache1.size(), temp);
            }
            Cache1.removeLast();//remove the last element of Cache 1
            Cache2.removeLast();//make room in Cache2 by removing the last element
            Cache1.addFirst(value);//add value to the front of Cache1
            Cache2.addFirst(value);//add value to the front of Cache2
            return value;
            //this should be the edge cases
        }
        if(Cache1.size() == cache1_size && Cache2.size() < cache2_size){//if Cache1 is full and Cache2 has space, then:
            temp = Cache1.getLast();//grab the last element of Cache1
            Cache2.add(Cache1.size(), temp);
            Cache1.removeLast();//remove the last element of Cache1
            while(Cache2.size() >= cache2_size){
                Cache2.removeLast();
            }
            Cache1.addFirst(value);//no matter what, add value to the front of Cache1
            Cache2.addFirst(value);//no matter what, add value to the front of Cache2
            return value;
            //this should be the majority of cases towards the middle and end
        }
        if(Cache1.size() > cache1_size && Cache2.size() > cache2_size){//if both Caches are too big
            while(Cache2.size() >= cache2_size){//while Cache2 is too big
                Cache2.removeLast();//drop the last element of Cache2
            }
            while(Cache1.size() > cache1_size){//while Cache1 is too big
                temp = Cache1.get(cache1_size);//grab the last element of Cache1
                if(Cache2.contains(temp)) {
                    Cache2.remove(temp);//make room in Cache2
                    Cache2.add(Cache1.size(), temp);//add the last element of Cache1 to the corresponding position of Cache2
                }
                else{
                    Cache2.add(Cache1.size(), temp);
                }
                Cache1.remove(temp);//drop the last element of Cache1
            }
            temp = Cache1.getLast();
            Cache2.add(cache1_size,temp);
            while(Cache2.size() >= cache2_size){//while Cache2 is too big
                Cache2.removeLast();//drop the last element of Cache2
            }
            Cache1.removeLast();
            Cache1.addFirst(value);//no matter what, add value to the front of Cache1
            Cache2.addFirst(value);//no matter what, add value to the front of Cache2
            return value;
            //this should not happen too often
        }
        return value;
    }

    /**
     *
     * @param key Object
     * @return key Object
     */
    public Object remove(Object key){//remove method
        if(Cache1.isEmpty()){//whether this is a Cache or 2Cache, this should happen
            return null;
        }
        if(Cache1.contains(key)){//whether this is a Cache or a 2Cache, this will need to be done
            ListIterator<Object> K = Cache1.listIterator(0);//create a list iterator for Cache1
            while(K.hasNext()){//start stepping through Cache1
                if(K.next().equals(key)){//trigger to step through Cache1 and look for key object
                    K.next();//step one past the found key object
                    temp = Cache1.get(K.previousIndex());//grab the found key object
                    K.remove();//remove the found key object
                    return temp;
                }
            }
        }
        if(cache2_size > 0) {//if this is a 2Cache
            if (Cache1.isEmpty() && Cache2.isEmpty() || !Cache1.contains(key) && !Cache2.contains(key)) {//if the Caches are empty or do not contain the key object
                return null;//nothing to return
            }
            if (Cache2.contains(key)) {//if Cache2 has the key object
                ListIterator<Object> L = Cache2.listIterator(0);//create a list iterator for the Cache2 object
                while (L.hasNext()) {//start stepping through Cache2
                    if (L.next().equals(key)) {//trigger to keep stepping through Cache2 as well as look for key object
                        L.next();//step one forward
                        temp = Cache2.get(L.previousIndex());//grab the found object
                        L.remove();//remove the found object
                        return temp;//return the found object
                    }
                }
            }
        }
        return null;//all else fails, return null
    }

    /**
     * return void
     */
    public static void clear(){//clear method
        Cache1.clear();//whether Cache or 2Cache, this can be done
        if(cache2_size > 0) {//if 2Cache
            Cache2.clear();//Clear Cache2
        }
    }
    @Override//override the toString method
    public String toString(){
        if(cache2_size == 0){//if this is a single Cache
            double HR1 = (HC1 / r1);//calculate hit ratio
            return("First level cache with " + cache1_size + " entries has been created\n..............................\nThe number of global references:\t" + r1 + "\nThe number of global cache hits:\t" + HC1 +"\nThe global hit ratio\t\t\t:\t" + HR1 + "\n\nThe number of 1st-level references:\t" + r1 + "\nThe number of 1st-level cache hits:\t" + HC1);
        }
        //else assume a 2Cache
        double HC = HC1 + HC2;//calculate global hit count
        double HR1 = (HC1 / r1);//calculate hit ratio 1
        double HR2 = (HC2 / r2);//calculate hit ratio 2
        double HR = (HC / r1);//calculate global hit ratio
        System.out.println("\n" + Cache1.size() +"\n" + Cache2.size());
        return("\nFirst level cache with " + cache1_size + " entries has been created\nSecond level cache with " + cache2_size +" entries has been created\n..............................\nThe number of global references:\t" + r1 + "\nThe number of global cache hits:\t" + HC +"\nThe global hit ratio\t\t\t:\t" + HR + "\n\nThe number of 1st-level references:\t" + r1 + "\nThe number of 1st-level cache hits:\t" + HC1 + "\nThe 1st-level cache hit ratio\t\t\t:\t" + HR1 + "\n\nThe number of 2nd-level references:\t" + r2 + "\nThe number of 2nd-level cache hits:\t" + HC2 + "\nThe 2nd-level cache hit ratio\t\t\t:\t" + HR2);
    }
}
