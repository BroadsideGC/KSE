package lib;

import java.util.ArrayList;
import java.util.List;

/**
 * KSE was created by Abdula on 05.12.16.
 */
public class FixedPointMethod {

    static public List<Double> getLinear(double x0, double r, int k) {
        List<Double> res = new ArrayList<>(k);
        double curX = x0;
        for (int i = 0; i < k; i++) {
            res.add(curX);
            curX = r * curX * (1 - curX);
        }
        return res;
    }

    static private double getPhi(double r, double x) {
        return r * x * (1 - x);
    }

    static public List<Double> getBifurcationLine(double r, double x) {
        List<Double> res = new ArrayList<>();
        double curX = x;
        for (int i = 0; i < 400; i++) {
            curX = getPhi(r, curX);
        }
        for (int i = 0; i < 800; i++) {
            res.add(curX);
            curX = getPhi(r, curX);
        }
        return res;
    }

}
