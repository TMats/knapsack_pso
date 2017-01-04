import java.util.Arrays;
import java.util.ArrayList;
import java.util.Random;

class Employed{
    public static int num_employed;

    // define 'global_best's
    public static double[] global_best_x;
    public static double global_best_fitness;

    private int id;
    private int func_id;
    private int dim;
    private double[] x;
    private double fitness;
    private int num_stay;

    Employed(int id, int func_id, int dim){
        this.id = id;
        this.func_id = func_id;
        this.dim = dim;
        x = TestFunctions.init_x(func_id,dim);
        fitness = TestFunctions.get_fitness(func_id,x);
        num_stay = 0;
    }

    double[] get_x(){ return x; }
    double get_fitness() { return fitness; }
    int get_num_stay() { return num_stay; }

    void update(){
        Random rnd = new Random();
        // choose dimension
        int d = rnd.nextInt(dim);
        // choose other employed bee
        int m = rnd.nextInt(num_employed);
        while (m==id){
            m = rnd.nextInt(num_employed);
        }
        double[] xm = Abc.employed_list.get(m).get_x();

        // generate candidate x
        double[] v = Arrays.copyOf(x,x.length);
        double r = 2* Math.random() - 1.0;
        v[d] = x[d] + r*(x[d]-xm[d]);
        double fitness_v = TestFunctions.get_fitness(func_id,v);
        if(fitness_v > fitness){
            fitness = fitness_v;
            x = v;
            num_stay = 0;
        }else{
            num_stay += 1;
        }
    }

    void scout(){
        x = TestFunctions.init_x(func_id,dim);
        fitness = TestFunctions.get_fitness(func_id,x);
        num_stay =0;
    }
}


public class Abc{
    public static ArrayList<Employed> employed_list = new ArrayList<Employed>();

    public static double[] execute(int func_id, int N, int D, int T){
        Employed.global_best_x = new double[N];
        Employed.global_best_fitness = 0;
        double[] result = new double[T+1];
        double fit;

        // limit number of 'stay's
        int stay_limit = 10;

        // init emoployed bee
        Employed.num_employed = N;
        double[] fits = new double[N];
        for(int i=0;i<N;i++){
            Employed emp = new Employed(i, func_id, D);
            employed_list.add(emp);
            fits[i] = employed_list.get(i).get_fitness();
            if(employed_list.get(i).get_fitness()>Employed.global_best_fitness){
                Employed.global_best_fitness = employed_list.get(i).get_fitness();
                Employed.global_best_x = Arrays.copyOf(employed_list.get(i).get_x(),employed_list.get(i).get_x().length);
            }
        }
        result[0]=TestFunctions.get_value(func_id,Employed.global_best_x);

        for(int t=1;t<T+1;t++) {
            for(int i = 0; i<N; i++) {
                employed_list.get(i).update();
                fits[i] = employed_list.get(i).get_fitness();
            }

            // onlooker
            for(int i=0; i<N; i++){
                int idx = ArrayTools.get_roulette_index(fits);
                employed_list.get(idx).update();
                fits[idx] = employed_list.get(idx).get_fitness();
            }

            // scout
            for(int i=0; i<N; i++){
                if(employed_list.get(i).get_num_stay()>stay_limit) {
                    employed_list.get(i).scout();
                    fits[i] = employed_list.get(i).get_fitness();
                }
            }

            // renew 'best's
            for(int i=0;i<N;i++){
                if(employed_list.get(i).get_fitness()>Employed.global_best_fitness){
                    Employed.global_best_fitness = employed_list.get(i).get_fitness();
                    Employed.global_best_x = Arrays.copyOf(employed_list.get(i).get_x(),employed_list.get(i).get_x().length);
                }
            }
            result[t] = TestFunctions.get_value(func_id, Employed.global_best_x);
        }
        System.out.println("func_id="+func_id+" num_employed_bees:"+N+" num_dims:"+D+" num_iters:"+T);
        System.out.println(Arrays.toString(Employed.global_best_x));
        System.out.println(TestFunctions.get_value(func_id, Employed.global_best_x));
        System.out.println();
        return result;
    }

    public static void main(String[] args) {
        // function_id
        int func_id = 6;
        // number of particles
        int N = 1000;
        // dimension
        int D = 5;
        // number of iteration
        int T = 1000;

        double[] result = execute(func_id,N,D,T);
    }

}