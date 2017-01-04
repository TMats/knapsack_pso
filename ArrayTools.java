public class ArrayTools{
    public static int get_min_index(double[] array){
        int idx = 0;
        for(int i=1; i<array.length; i++) {
            idx = (array[i] < array[idx]) ? i : idx;
        }
        return idx;
    }

    public static double get_min_value(double[] array){
        return array[get_min_index(array)];
    }

    public static int get_max_index(double[] array){
        int idx = 0;
        for(int i=1; i<array.length; i++) {
            idx = (array[i] > array[idx]) ? i : idx;
        }
        return idx;
    }

    public static double get_max_value(double[] array){
        return array[get_max_index(array)];
    }

    public static double get_sum(double[] array){
        double sum = 0;
        for(int i=1; i<array.length; i++) {
            sum +=array[i];
        }
        return sum;
    }

    public static int get_roulette_index(double[] array){
        double sum = get_sum(array);
        double r = Math.random() * sum;
        int idx=0;
        while(r-array[idx]>0){
            r -= array[idx];
            idx++;
        }
        return idx;
    }
}