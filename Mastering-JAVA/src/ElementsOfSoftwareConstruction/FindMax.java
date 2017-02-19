package ElementsOfSoftwareConstruction;

import static ElementsOfSoftwareConstruction.FindMax.max;

public class FindMax {


    public static int max (int[] list) {
        int max = list[0];
        for (int i = 1; i < list.length-1; i++) {
            if (max < list[i]) {
                max = list[i];
            }
        }

        return max;
    }

}
