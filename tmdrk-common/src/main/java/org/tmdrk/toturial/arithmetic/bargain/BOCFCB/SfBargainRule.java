package org.tmdrk.toturial.arithmetic.bargain.BOCFCB;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 砍价的几个因素：
 * 1. 可砍价的人数(N)
 * 2. 可砍掉的总金额(T)
 * 3. 每次砍价的最低砍价金额(M)
 * 4. 总砍价次数(K)
 * <p>
 * 本砍价规则将'每次砍价的最低砍价金额'默认为 := T / (K * 2)。
 */
public class SfBargainRule implements BargainRule {

    @Override
    public List<Integer> getReduceList(BigDecimal totalReduce, int totalReduceTimes) {
        if (totalReduceTimes <= 0) {
            throw new IllegalArgumentException("total reduce times should gte 1");
        }

        // 最低砍价金额默认为： 总砍价金额 / (砍价次数 * 2)
        BigDecimal minReduce = totalReduce.divide(BigDecimal.valueOf(totalReduceTimes).multiply(BigDecimal.valueOf(2)), BigDecimal.ROUND_HALF_EVEN);
        return getReduceList(totalReduce, totalReduceTimes, minReduce);
    }

    public List<Integer> getReduceList(BigDecimal totalReduce, int totalReduceTimes, BigDecimal minReduce) {
        List<Integer> ret = new ArrayList<>();
        Random rand = new Random();
        double[] vs = new double[totalReduceTimes];
        double total = 0d;
        for (int i = 0; i < totalReduceTimes; i++) {
            double v = rand.nextDouble() * totalReduce.doubleValue();
            vs[i] = v;
            total += v;
        }

        double minReduceVal = minReduce.doubleValue();
        BigDecimal t = BigDecimal.valueOf(0d);
        for (int i = 0; i < totalReduceTimes - 1; i++) {
            double vss = vs[i] / total * (totalReduce.doubleValue() - minReduceVal * totalReduceTimes) + minReduceVal;
            BigDecimal reduce = BigDecimal.valueOf(vss).setScale(2, RoundingMode.HALF_EVEN);
            t = t.add(reduce);
            ret.add(reduce.multiply(new BigDecimal("100")).intValue());
        }
        ret.add((totalReduce.subtract(t).setScale(2, RoundingMode.HALF_EVEN)).multiply(new BigDecimal("100")).intValue());
        return ret;
    }


    public static void main(String[] args) {
        int t = new Random().nextInt(20) + 1;
        int i = 0;
        BigDecimal tt = BigDecimal.valueOf(new Random().nextDouble() * 10000 + 1).setScale(2, RoundingMode.HALF_UP);
        List<Integer> reduceList = new SfBargainRule().getReduceList(tt, t);
        int total = 0;
        for (Integer d : reduceList) {
            total = total+d;
            System.out.printf("第%d个人砍掉了%s\n", ++i, d);
        }
        System.out.println("砍价总金额=" + tt.intValue() + "，合计总金额=" + total/100);
    }
}
