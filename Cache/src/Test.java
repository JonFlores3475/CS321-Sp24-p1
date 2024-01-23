import static java.lang.Integer.parseInt;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileWriter;


public class Test extends Cache {
    private static final long start = System.currentTimeMillis();
    private final Cache Cache;
    private static Test T;
    private static File file;

    public Test(int args0, int args1, int args2, String args3) {
        super(args0, args1, args2, args3);
        Cache = new Cache(args0, args1, args2, args3);
    }

    public static void main(String[] args) throws FileNotFoundException {
        if ((args.length < 3 || args.length > 4) || (parseInt(args[0]) < 1 || parseInt(args[0]) > 2)){
            System.out.println("\nUsage: java Test <1> <cache1-size> <input file name>\nfor single cache or\n java Test <2> <cache1-size> <cache2-size> <input file name>\nfor 2Cache.");
            System.exit(1);
        }
        if(args.length == 3){
            T = new Test(parseInt(args[0]), parseInt(args[1]), 0, args[2]);
            file = new File(args[2]);
        }
        if(args.length == 4) {
            T = new Test(parseInt(args[0]), parseInt(args[1]), parseInt(args[2]), args[3]);
            file = new File(args[3]);
        }
        Scanner scan = new Scanner(file);
        if(parseInt(args[0]) == 2) {
            while (scan.hasNext()) {
                String key = scan.next();
                get2(key);
            }
        }
        if(parseInt(args[0]) == 1){
            while (scan.hasNext()) {
                String key = scan.next();
                get1(key);
            }
        }
        long elapsed = System.currentTimeMillis() - start;
        if(parseInt(args[0]) == 2) {
            System.out.println(T.Cache.toString2());
        }
        if(parseInt(args[0]) == 1){
            System.out.println(T.Cache.toString1());
        }
        try {
            File out = new File("README.txt");
            if (out.createNewFile()) {
                System.out.println("\nFile successfully created: " + out.getName());
            } else {
                System.out.println("\nFile already exists.");
                out.delete();
                System.out.println("\nFile deleted.");
                out = new File("README.txt");
                System.out.println("\nFile recreated.");
            }
        } catch (IOException e) {
            System.out.println("\nAn error occurred.");
            e.printStackTrace();
        }
        try{
            FileWriter writer = new FileWriter("README.txt");
            writer.write(elapsed+" MS");
            writer.close();
            System.out.println("\nSuccessfully wrote to file.");
        }
        catch(IOException e){
            System.out.println("\nAn error occurred.");
            e.printStackTrace();
        }
        System.exit(0);
    }
}
