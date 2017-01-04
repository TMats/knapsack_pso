import java.io.*;
import java.util.*;

public class TestFunctions{

    // generate initial x
    public static int[] init_x(int dim) {
        int[] x = new int[dim];

        Random rnd = new Random();
        for(int i=0;i<dim;i++){
            x[i] = rnd.nextInt(2);
        }

        return x;
    }

    public static double sigmoid(double a) {
        return 1.0/(1.0+Math.exp((-1.0)*a));
    }

    public static int[] update_x(double[] v) {
        int dim = v.length;
        int[] x = new int[v.length];

        for(int i=0;i<dim;i++){
            double r = Math.random();
            if(r<sigmoid(v[i])){
                x[i]=1;
            }else{
                x[i]=0;
            }
        }

        return x;
    }

    // calcuate function fitness
    public static double get_fit(int dataset_id, int[] x){
        int dim = x.length;
        double limit = get_limit(dataset_id);
        double[] weight = get_weight(dataset_id);
        double[] reward = get_profit(dataset_id);

        double sum_weight = 0;
        double sum_reward = 0;

        for(int i=0;i<dim;i++){
            sum_weight = sum_weight + (weight[i]*x[i]);
            sum_reward = sum_reward +(reward[i]*x[i]);
        }
        if(sum_weight>limit){
            sum_reward = 0;
        }

        return sum_reward;
    }


    public static String[] fileRead(String filePath) {
        List<String> list = new ArrayList<String>();
        try {
            File f = new File(filePath);
            BufferedReader br = new BufferedReader(new FileReader(f));

            String line = br.readLine();
            while (line != null) {
                list.add(line);
                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        String[] array = list.toArray(new String[list.size()]);
        return array;
    }

    public static double[] get_weight(int dataset_id){
        String filepath = "dataset/p0"+dataset_id+"_w.txt";
        String[] ar = fileRead(filepath);
        double[] weight = new double[ar.length];
        for(int i=0;i<ar.length;i++){
            weight[i] = Double.valueOf(ar[i]);
        }
        return weight;
    }

    public static double[] get_profit(int dataset_id){
        String filepath = "dataset/p0"+dataset_id+"_p.txt";
        String[] ar = fileRead(filepath);
        double[] profit = new double[ar.length];
        for(int i=0;i<ar.length;i++){
            profit[i] = Double.valueOf(ar[i]);
        }
        return profit;
    }

    public static double get_limit(int dataset_id){
        String filepath = "dataset/p0"+dataset_id+"_c.txt";
        String[] ar = fileRead(filepath);
        return Double.valueOf(ar[0]);
    }

    public static int[] get_optimal(int dataset_id){
        String filepath = "dataset/p0"+dataset_id+"_s.txt";
        String[] ar = fileRead(filepath);
        int[] optimal = new int[ar.length];
        for(int i=0;i<ar.length;i++){
            optimal[i] = Integer.valueOf(ar[i]);
        }
        return optimal;
    }

}
