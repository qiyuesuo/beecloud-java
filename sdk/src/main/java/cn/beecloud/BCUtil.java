package cn.beecloud;

import java.util.Random;
import java.util.UUID;

/**
 * BeeCloud 工具类
 * 
 * @author Ray
 * @since 15/7/08
 */
public class BCUtil {

    public static String generateRandomUUID() {
        return UUID.randomUUID().toString();
    }

    public static String generateRandomUUIDPure() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static Integer generateNumberWith3to24digitals() {
        Random random = new Random();
        Integer intRandom;
        double ran = Math.random();
        if (ran <= 0.17) { // 3 λ
            intRandom = 100 + random.nextInt(900);
        } else if ((ran > 0.17) && (ran <= 0.34)) { // 4 λ
            intRandom = 1000 + random.nextInt(9000);
        } else if ((ran > 0.34) && (ran <= 0.51)) { // 5 λ
            intRandom = 10000 + random.nextInt(90000);
        } else if ((ran > 0.51) && (ran <= 0.68)) { // 6 λ
            intRandom = 100000 + random.nextInt(900000);
        } else if ((ran > 0.68) && (ran <= 0.85)) { // 7 λ
            intRandom = 1000000 + random.nextInt(9000000);
        } else { // 8λ
            intRandom = 10000000 + random.nextInt(90000000);
        }
        return intRandom;
    }
}
