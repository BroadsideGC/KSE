package lib;

import javafx.util.Pair;
import org.apache.commons.math3.complex.Complex;

import java.util.ArrayList;
import java.util.List;

/**
 * KSE was created by Big_Z on 08.12.16.
 */
public class NewtonsMethod {

    private final static double EPS = 1e-5;
    private final static int MAX_ITERATIONS = 500;

    private static Complex getPhi(Complex x) {
        return x.subtract(x.pow(3).subtract(1).divide(x.pow(2).multiply(3)));
    }

    static public Pair<Integer, Integer> countConvergence(Complex x0) {
        Complex curX = x0;
        int iter = 0;
        while (iter < MAX_ITERATIONS && curX.subtract(getPhi(curX)).abs() > EPS) {
            curX = getPhi(curX);
            iter++;
        }
        int point = 0;
        if (curX.getReal() < 0) {
            if (curX.getImaginary() > 0) {
                point = 1;
            } else {
                point = 2;
            }
        }
        return new Pair<>(point, iter);
    }

    static public List<Complex> getWay(Complex x0) {
        //System.out.println("");
        Complex curX = x0;
        List<Complex> res = new ArrayList<>();
        int i = 0;
        while (i < MAX_ITERATIONS && curX.subtract(getPhi(curX)).abs() > EPS) {
            res.add(curX);
            curX = getPhi(curX);
            i++;
            //System.out.println(curX);
        }

        res.add(curX);
        curX = getPhi(curX);
        //System.out.println(curX);
        res.add(curX);

        if (i == MAX_ITERATIONS) {
            return new ArrayList<>();
        }

        return res;
    }

}
