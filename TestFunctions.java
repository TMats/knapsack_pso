import java.util.Random;

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


    // calcuate function value
    // public static double get_fit(double[] weight, double[] reward, double limit, double[] x){
    public static double get_fit(int[] x){
        int dim = x.length;
        double limit = 50;
        double[] weight ={31,10,20,19,4,3,6};
        double[] reward ={70,20,39,37,7,5,10};

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

}
