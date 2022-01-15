import java.io.*;

/**
 *
 */
public class main {
    public static void main(String[] args) {
        double[][] data;
        IO io=new IO();
        CalculateG test=new CalculateG();
        try {
            data=io.readFile("data.txt");
            test.QU(data);
            test.V(data);
            test.G(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        }
    }



