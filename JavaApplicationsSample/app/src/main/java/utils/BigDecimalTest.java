package utils;

import java.math.BigDecimal;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BigDecimalTest {

    //	1.scale
    private static void testScale(BigDecimal value) {
        System.out.println(value.scale() + "");
    }

    private static void testValue(BigDecimal value) {
        log.info(value.scale() + "====" + value);
        log.info(value.negate().toString());
    }

    public static void main(String[] args) {
        BigDecimal testValue1 = BigDecimal.valueOf(20.5588);
        BigDecimal testValue2 = BigDecimal.valueOf(3.56);
        testValue(testValue1.multiply(testValue2.negate()));
    }

}
