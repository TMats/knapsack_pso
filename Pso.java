import java.util.Arrays;
import java.util.ArrayList;


class Particle{
    // Consts
    public static double W = 0.3; // -0.2
    public static double C1 = 0.6; //1.2
    public static double C2 = 0.6; //1.2

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
        fit = TestFunctions.get_fit(x);
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
        fit = TestFunctions.get_fit(x);
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
    public static double[] execute(int N, int D, int T){
        Particle.global_best_x = new int[N];
        Particle.global_best_fit = 0;

        ArrayList<Particle> particle_list = new ArrayList<Particle>();
        for(int i=0;i<N;i++){
            Particle part = new Particle(D);
            particle_list.add(part);
            particle_list.get(i).update_best();
            //print debug
            if(i==0){
                System.out.println(Arrays.toString(particle_list.get(i).get_x()));
                System.out.println(particle_list.get(i).get_fit());
            }
        }

        double[] result = new double[T+1];
        result[0] = Particle.global_best_fit;

        for(int t=1;t<T+1;t++){
            for(int i=0;i<N;i++){
                particle_list.get(i).update();
                //print debug
                if(i==0){
                    System.out.println(Arrays.toString(particle_list.get(i).get_x()));
                    System.out.println(particle_list.get(i).get_fit());
                }
                particle_list.get(i).update_best();
            }
            result[t] = Particle.global_best_fit;
        }
        System.out.println("num_particles:"+N+" num_dims:"+D+" num_iters:"+T);
        System.out.println(Arrays.toString(Particle.global_best_x));
        System.out.println(Particle.global_best_fit);
        System.out.println();
        return result;
    }

    public static void main(String[] args) {
        // number of particles
        int N = 10;
        // dimension
        int D = 7;
        // number of iteration
        int T = 10;

        double[] result = execute(N,D,T);
    }

}
