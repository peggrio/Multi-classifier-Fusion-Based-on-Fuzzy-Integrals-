import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

//拿shufflnet这个分类器的前五行分类进行计算
public class CalculateG {
    public double[] QU;
    double V[];
    double max;
    double maxIndex;
    ArrayList<double[]> array = new ArrayList();//实现动态数组


    public double[] QU(double[][] data) {

        int L = data.length;//L是样本总数
        int s = 0;//s是被成功分类的样本数
        int c = data[0].length - 1;//c是类的总个数,样本里为40
        int cVol[] = new int[40];//这个装的整份data数据里每一个分类(共40个)各有多少个(真实值)

        //首先判断这个分类器做出的分类是否是正确的，是正确的话留下这一行信息
        for (int i = 0; i < L; i++) {
            max = data[i][0];
            maxIndex = 0;
            int label = (int) data[i][data[i].length - 1];
            for (int j = 0; j < data[i].length - 1; j++) {//遍历找出最大值
                if (max < data[i][j]) {
                    max = data[i][j];
                    maxIndex = j;
                }
            }
            if (maxIndex == label) {//说明分类成功
                s++;
                array.add(data[i]);//进入s矩阵
            }
            cVol[label]++;
        }
        //将array倒出来进行分类

        //SZI
        double tempRes;
        double SZI;//分类器将样本正确分类时，单个样本的相似度

        QU = new double[c];
        HashMap<Integer, Double> map = new HashMap<>();//这个哈希表的作用理解起来有点复杂，这样解释：
        //对每个真实归类为Cj的样本，都有两种情况，一种是预测正确，被归为Cj类，一种是预测错误，被归为非Cj类，
        // 所以这里就有，对每个类Cj，与真实值相等的预测值/真实值----->s'/l'
        //前面用cVol记录了l'，那么对s矩阵，就要根据s内每一个样本（每一行）的label进行Cj的映射，确定s'

        for (int i = 0; i < s; i++) { //遍历s矩阵
            tempRes = 0;

            double temp[] = array.get(i);
            int label = (int) temp[temp.length - 1];
            //查看分类器之前有没有预测为label的样本

            for (int j = 0; j < temp.length - 1; j++) {
                if (label == j) {
                    tempRes += Math.abs(temp[j] - 1);
                } else {
                    tempRes += Math.abs(temp[j]);
                }
            }
            tempRes /= c;
            SZI = 1 - tempRes;
            if (map.containsKey(label)) {//如果之前已有label
                map.put(label, map.get(label) + SZI);
            } else {//还没有这个label
                map.put(label, SZI);
            }
        }

        //遍历map里的label
        Set<Integer> labels = map.keySet();
        for (Integer label : labels) {
            QU[label] = map.get(label) / cVol[label];
        }
        //打印输出QU的值看一下
        System.out.println("QU值：");
        for (double i : QU) {
            System.out.print(i + " ");
        }
        System.out.println();
        return QU;
    }

    public double[] V(double[][] data) {
        int L = data.length;//L是样本总数
        int c = data[0].length - 1;//c是类的总个数,样本里为40

    /*测试，证明array是传进来了
            System.out.println("测试");
            for (int i = 0; i <array.size() ; i++) {
                double[]res=array.get(i);
                for(double k:res){
                    System.out.print(k+" ");
                }
                System.out.println();*/
        V = new double[c];
        HashMap<Integer, Integer> map2 = new HashMap<>();//这个哈希表的作用理解起来有点复杂，这样解释：
        //对每个真实归类为Cj的样本，都有两种情况，一种是预测正确，被归为Cj类，一种是预测错误，被归为非Cj类，
        // 所以这里就有，对每个类Cj，与真实值相等的预测值/真实值----->s'/l'
        //前面用cVol记录了l'，那么对s矩阵，就要根据s内每一个样本（每一行）的label进行Cj的映射，确定s'

        for (int i = 0; i < L; i++) {//扫描一次全部样本，看每个label下有多少个样本，存储在map中
            double temp[] = data[i];
            int label = (int) temp[temp.length - 1];
            if (map2.containsKey(label)) {//如果之前已有label
                map2.put(label, map2.get(label) + 1);
            } else {//还没有这个label
                map2.put(label, 1);
            }
        }
        Set<Integer> labels = map2.keySet();
        for (Integer label : labels) {
            double belong = 0;
            double notBelong = 0;
            for (int i = 0; i < L; i++) {
                int tempLabel = (int) data[i][data[i].length - 1];
                double test = data[i][label];
                if (label == tempLabel) {//该样本属于Cj
                    belong += 2 * test - 1;
                } else {
                    notBelong += 1 - 2 * test;
                }
            }
            int s = map2.get(label);
            int t = L - s;
            V[label] = belong / s + notBelong / t;
        }

        //打印输出V的值看一下
        System.out.println("V值：");
        for (double i : V) {
            System.out.print(i + " ");
        }
        System.out.println();
        return V;
    }

    public double[] G(double[][] data) {
        int L = data.length;//L是样本总数
        int s = 0;//s是被成功分类的样本数
        int c = data[0].length - 1;//c是类的总个数,样本里为40
        double[] G = new double[c];
        double e = Math.E;
        for (int i = 0; i < G.length; i++) {
            G[i] = QU[i] * Math.pow(e, -(2 - V[i]) / 4);
        }
        //打印输出G的值看一下
        System.out.println("G值：");
        for (double i : G) {
            System.out.print(i + " ");
        }
        System.out.println();
        return G;

    }
}