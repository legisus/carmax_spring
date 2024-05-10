package core.utils;

public class ConvertorUtils {
    public static String getEstimationStringK(int estimation) {
        int thousands = estimation / 1000;
        int remainder = estimation % 1000;
        if (remainder > 0) {
            return String.format("%d,%d", thousands, remainder / 100);
        } else {
            return String.format("%d", thousands);
        }
    }

    public static String getStringMilesInK(int miles) {
        int thousands = miles / 1000;

            return String.format("%d", thousands);

    }
}
