import java.io.*;

public class IO {
    public  double[][] readFile(String fileName) throws IOException {
        String[][] data = new String[5][41];
        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
            int cnt = 0;
            String line;

            while (cnt!=5&&null != (line = br.readLine())) {
                data[cnt] = parseLine(line);
                cnt++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        double ans[][]=string2int(data);
        print(ans);
        return ans;
    }
    private static String[] parseLine(String line) {
        String[] nums = line.split(",");
        return nums;
    }
    private static double[][] string2int(String data[][]) {
        double[][] ans = new double[data.length][];
        for (int i = 0; i < data.length; i++) {
            ans[i]=new double[data[i].length];
            for (int j = 0; j < data[i].length; j++) {
                if(data[i][j]!=null){
                    ans[i][j]=Double.parseDouble(data[i][j]);
                }
            }
        }return ans;
    }
    //打印输出看一下
    public void print(double[][] data){
        for (int i = 0; i <data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                System.out.print(data[i][j]+" ");
            }
            System.out.println();
        }
    }
}
