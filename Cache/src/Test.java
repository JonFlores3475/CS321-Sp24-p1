/**
 * Test Class for Cache Class
 * CS 321 Spring 2024
 * @author Jon Flores
 */
import static java.lang.Integer.parseInt;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileWriter;


public class Test extends Cache {
    private static final long start = System.currentTimeMillis(); //start timer
    private final Cache Cache; //create Cache
    private static Test T; //create test
    private static File file; //create file

    public Test(int args0, int args1, int args2, String args3) {//constructor fot the Test class
        super(args0, args1, args2, args3);//pass arguments to Cache class
        Cache = new Cache(args0, args1, args2, args3);//Create new cache
    }

    public static void main(String[] args) throws FileNotFoundException {//main method
        //user input error handling start
        if ((args.length < 3 || args.length > 4) || (parseInt(args[0]) < 1 || parseInt(args[0]) > 2) || (args.length == 3 && parseInt(args[0]) == 2) || (args.length == 4 && parseInt(args[0]) == 1)){
            System.out.println("\nUsage: java Test <1> <cache1-size> <input file name>\nfor single cache or\n java Test <2> <cache1-size> <cache2-size> <input file name>\nfor 2Cache.");
            System.exit(1);
        }
        if(args.length == 4 && parseInt(args[2]) == 0){
            System.out.println("\nCache 2 of size 0 results in single cache.");
        }
        if(parseInt(args[1]) == 0){
            System.out.println("\nCache1-size must be non-zero positive integer.");
            System.out.println("\nUsage: java Test <1> <cache1-size> <input file name>\nfor single cache or\n java Test <2> <cache1-size> <cache2-size> <input file name>\nfor 2Cache.");
            System.exit(1);
        }
        if((args.length == 3 && parseInt(args[1]) < 0) || (args.length == 4 && (parseInt(args[1]) < 0) || parseInt(args[2]) < 0)){
            System.out.println("\nCache sizes must be positive integer values.");
            System.out.println("\nUsage: java Test <1> <cache1-size> <input file name>\nfor single cache or\n java Test <2> <cache1-size> <cache2-size> <input file name>\nfor 2Cache.");
            System.exit(1);
        }
        //end of user input error handling
        if(args.length == 3){//if single Cache
            T = new Test(parseInt(args[0]), parseInt(args[1]), 0, args[2]);
            file = new File(args[2]);
        }
        if(args.length == 4) {//if 2Cache
            T = new Test(parseInt(args[0]), parseInt(args[1]), parseInt(args[2]), args[3]);
            file = new File(args[3]);
        }
        Scanner scan = new Scanner(file);//create a new file scanner
        while (scan.hasNext()) {//step through file
            String key = scan.next();//grab strings as they appear
            get(key);//call to Cache get method
        }
        long elapsed = System.currentTimeMillis() - start;//calculate elapsed time
        System.out.println(T.Cache.toString());//print out the appropriate toString
        try {//now we try to make a README.txt file with the time printed in it
            File out = new File("README.txt");//create new file
            if (out.createNewFile()) {
                System.out.println("\nFile successfully created: " + out.getName());//if file did not previously exist, we are good
            } else {//if file already exits
                System.out.println("\nFile already exists.");//notify user
                out.delete();//delete the existing file
                System.out.println("\nFile deleted.");//notify user
                out = new File("README.txt");//recreate file
                System.out.println("\nFile recreated.");//notify user
            }
        } catch (IOException e) {//if anything else happens
            System.out.println("\nAn error occurred.");//notify user of error
            e.printStackTrace();//print stack trace
        }
        try{//try to write to file
            FileWriter writer = new FileWriter("README.txt");//create new file writer
            writer.write("## Project # 1: Cache \n" +
                    "\n" +
                    "    Author: Jon Flores\n" +
                    "    Class: CS321\n" +
                    "    Semester: Spring 2024\n"+
                    "## Overview\n" +
                    "\nThis project took a look at the inefficiencies of Linked Lists by implementing a Cache and 2Cache."+
                    "\n" +
                    "\n## Reflection\n"+
                    "This project took me until the last day to finish; however, I learned a lot about being prepared for and addressing bugs as well as the file writer and scanner."+
                    "\n\n## Compiling and Using\n" +
                    "\n" +
                    "Compiling and using this code is fairly straight-forward. To compile, simply enter the appropriate directory, open a console, and enter the following:\n" +
                    "\n" +
                    "\n$javac *.java"+
                    "\n\nYou will notice that this has compiled all java files in the directory, and this should be fine so long as these are the only .java files in the directory. However, if you wish to compile solely the files for the project, enter the following: "+
                    "\n\n$javac Cache.java"+
                    "\n\n$javac Test.java"+
                    "\n\nNow, to run the program, enter the following: " +
                    "\n\njava Test <1> <cache1-size> <input file name>\n\nfor single cache or\n\n java Test <2> <cache1-size> <cache2-size> <input file name>\n\nfor 2Cache."+
                    "\n\n## Results"+
                    "\n\nTime to run most recent: "+elapsed+" MS");//write time and MS
            writer.close();//close the writer
            System.out.println("\nSuccessfully wrote to file.");//notify user of success
        }
        catch(IOException e){//if anything goes wrong
            System.out.println("\nAn error occurred.");//notify user
            e.printStackTrace();//print stack trace
        }
        System.exit(0);//end the program safely
    }
}
