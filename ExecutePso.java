import java.util.Arrays;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class ExecutePso{

    public static double[] evaluate(int N, int T, double[] params){
        int trial = 100;
        double[] eval_result = new double[8];
        for(int id=1;id<=8;id++){
            int correct_num = 0;
            for(int i=0;i<trial;i++){
                int[] result = Pso.execute(N,T,id,params);
                int[] optimal = TestFunctions.get_optimal(id);
                if(Arrays.equals(result,optimal)){
                    correct_num +=1;
                }
            }
            eval_result[id-1] = 1.0*correct_num/trial;
        }
        return eval_result;
    }



    public static void main(String[] args){
        try {
            FileWriter fw = new FileWriter("test.csv", true);
            PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
            pw.print("N,T,W,C1,C2,1,2,3,4,5,6,7,8");
            pw.println();

            // parameters(defaults)
            int N = 20;
            int T = 20;
            double[] params ={1.0,2.0,2.0};

            //change w, c1, c2
            double[] ws={0.0,0.5,1.0,2.0};
            double[] c1s={0.0,0.5,1.0,1.5,2.0,2.5};
            double[] c2s={0.0,0.5,1.0,1.5,2.0,2.5};
            for(int i=0;i<ws.length;i++){
                for(int j=0;j<c1s.length;j++){
                    for(int k=0;k<c2s.length;k++){
                        double p[] = {ws[i],c1s[j],c2s[k]};
                        double[] results = evaluate(N,T,p);
                        pw.print(N+","+T+","+ws[i]+","+c1s[j]+","+c2s[k]+",");
                        for(int l=0;l<8;l++){
                            pw.print(results[l]+",");
                        }
                    pw.println();
                    }
                }
            }

            // change N
            int[] Ns ={10,20,50,100};
            for(int i=0;i<Ns.length;i++){
                double[] results = evaluate(Ns[i],T,params);
                pw.print(Ns[i]+","+T+","+params[0]+","+params[1]+","+params[2]+",");
                for(int l=0;l<8;l++){
                    pw.print(results[l]+",");
                }
                pw.println();
            }

            // change T
            int[] Ts ={10,20,50,100};
            for(int i=0;i<Ts.length;i++){
                double[] results = evaluate(N,Ts[i],params);
                pw.print(N+","+Ts[i]+","+params[0]+","+params[1]+","+params[2]+",");
                for(int l=0;l<8;l++){
                    pw.print(results[l]+",");
                }
                pw.println();
            }

            pw.close();
            System.out.println("finished");
        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }
}