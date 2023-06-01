package domhwangcha.voca.lib;

import java.util.Random;

public class RandomIndexGenerator {
    public int[] getRandomIndex(int size){
        int[] randomIndex = new int[size];
        boolean[] randomCheck = new boolean[size];
        Random rand = new Random();
        for(int i = 0; i<size;i++){
            int int_random = rand.nextInt(size);
            while(randomCheck[int_random]){
                int_random = rand.nextInt(size);
            }
            randomIndex[i] = int_random;
            randomCheck[int_random] = true;
        }
        return randomIndex;
    }
}
