import java.util.Arrays;
import java.util.ArrayList;


class Particle{
    // Consts
    public static double W;
    public static double C1;
    public static double C2;
    public static int dataset_id;

    // define 'global_best's
    public static int[] global_best_x;
    public static double global_best_fit;

    private int dim;
    private int[] x;
    private double[] v;
    private double fit;
    private int[] best_x;
    private double best_fit;

    Particle(int dim){
        this.dim = dim;
        x = TestFunctions.init_x(dim);
        v = new double[dim];
        fit = TestFunctions.get_fit(dataset_id,x);
        best_x = Arrays.copyOf(x,x.length);
        best_fit = fit;
    }

    int[] get_x(){ return x; }
    double get_fit(){ return fit; }

    void update() {
        int[] new_x = Arrays.copyOf(x,x.length);
        double[] new_v = Arrays.copyOf(v,v.length);
        double r1 = Math.random();
        double r2 = Math.random();
        new_x = TestFunctions.update_x(v);
        for (int i = 0; i < dim; i++) {
            new_v[i] = W * v[i] + C1 * r1 * (best_x[i] - x[i]) + C2 * r2 * (global_best_x[i] - x[i]);
        }
        x = new_x;
        v = new_v;
        fit = TestFunctions.get_fit(dataset_id,x);
    }

    void update_best(){
        if(fit>best_fit){
            best_fit = fit;
            best_x = Arrays.copyOf(x,x.length);
        }
        if(fit>global_best_fit){
            global_best_fit = fit;
            global_best_x = Arrays.copyOf(x,x.length);

        }
    }

}


public class Pso{
    public static int[] execute(int N, int T, int dataset_id, double[] params){
        Particle.dataset_id = dataset_id;
        Particle.W =params[0];
        Particle.C1=params[1];
        Particle.C2=params[2];

        Particle.global_best_x = new int[N];
        Particle.global_best_fit = 0;

        int D = TestFunctions.get_weight(dataset_id).length;
        ArrayList<Particle> particle_list = new ArrayList<Particle>();
        for(int i=0;i<N;i++){
            Particle part = new Particle(D);
            particle_list.add(part);
            particle_list.get(i).update_best();
        }

        for(int t=1;t<T+1;t++){
            for(int i=0;i<N;i++){
                particle_list.get(i).update();
                particle_list.get(i).update_best();
            }
        }
        return Particle.global_best_x;
    }

    public static void main(String[] args) {
        // number of particles
        int N = 10;
        // number of iteration
        int T = 10;
        // parameters
        double[] params ={0.3,0.6,0.6};
        // dataset
        int dataset_id = 4;

        int[] result = execute(N,T,dataset_id,params);
    }

}
