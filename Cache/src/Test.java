import static java.lang.Integer.parseInt;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileWriter;


public class Test extends Cache {
    private static final long start = System.currentTimeMillis();
    private final Cache Cache;

    public Test(int args1, int args2, String args3) {
        super(args1, args2, args3);
        Cache = new Cache(args1, args2, args3);
    }

    public static void main(String[] args) throws FileNotFoundException {
        if (!(args.length == 3)) {
            System.out.println("\nUsage: java Test <cache1-size> <cache2-size> <input file name>");
            System.exit(1);
        }
        Test T = new Test(parseInt(args[0]), parseInt(args[1]), args[2]);
        File file = new File(args[2]);
        Scanner scan = new Scanner(file);
        while (scan.hasNext()) {
            String key = scan.next();
            get(key);
        }
        long elapsed = System.currentTimeMillis() - start;
        System.out.println(T.Cache.toString());
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
