package org.smirnovav.moexFuturesData.calculations.calcutils.indicators;

import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;
import org.smirnovav.moex_lib.collectors.futures.FuturesDayHistoryInfo;

import java.util.ArrayList;
import java.util.List;


public class AtrIndicator {


    public static double[] atrInPrcWithoutZerosFromMoexCandleList(List<FuturesDayHistoryInfo> quote, int period) {
        if (quote.size() < period) {
            return new double[] {0.0};
        }
        double[] highPrices = new double[quote.size()];
        double[] lowPrices = new double[quote.size()];
        double[] closePrices = new double[quote.size()];
        for (int i = 0; i < quote.size(); i++) {
            if (quote.get(i).getHigh() == 0) {
                if (i > 0) {
                    highPrices[i] = closePrices[i - 1];
                } else {
                    highPrices[i] = 0;
                }
            } else {
                highPrices[i] = quote.get(i).getHigh();
            }

            if (quote.get(i).getLow() == 0) {
                if (i > 0) {
                    lowPrices[i] = closePrices[i - 1];
                } else {
                    lowPrices[i] = 0;
                }
            } else {
                lowPrices[i] = quote.get(i).getLow();
            }

            if (quote.get(i).getClose() == 0) {
                if (i > 0) {
                    closePrices[i] = closePrices[i - 1];
                } else {
                    closePrices[i] = 0;
                }
            } else {
                closePrices[i] = quote.get(i).getClose();
            }
        }
        return atrInPercentWithoutZeros(highPrices, lowPrices, closePrices, period);
    }

    public static double[] atrInPercentWithoutZeros(double[] highPrices, double[] lowPrices, double[] closePrices, int period) {
        double[] atrPtc = atrInPercent(highPrices, lowPrices, closePrices, period);
        if (atrPtc.length == 1 && atrPtc[0] == 0) {
            return new double[] {0.0};
        }
        return deleteBeginingZeros(atrPtc);
    }

    public static double[] atrInPercent(double[] highPrices, double[] lowPrices, double[] closePrices, int period) {
        double[] atrInPercent = new double[closePrices.length];
        double[] atr = atr(highPrices, lowPrices, closePrices, period);
        if (atr.length == 1 && atr[0] == 0) {
            return new double[] {0.0};
        }
        for (int i = 0; i < atr.length; i++) {
            double tempValue = atr[i] / ((highPrices[i] + lowPrices[i]) / 2) * 100;
            if (Double.isNaN(tempValue)) {
                tempValue = 0;
            } else if (Double.isInfinite(tempValue)) {
                if (i > 0) {
                    tempValue = atrInPercent[i - 1];
                } else {
                    tempValue = 0;
                }
            }
            atrInPercent[i] = tempValue;
        }
        return atrInPercent;
    }

    /**
     * Расчёт индикатора ATR
     * @param highPrices Массив с наивысшими ценами свечей.
     * @param lowPrices Массив с самыми низкими ценами свечей.
     * @param closePrices Массив с ценами закрытия свечей.
     * @param period Период расчёта показателя.
     * @return Массив со значениями индикатора ATR
     */
    public static double[] atr(double[] highPrices, double[] lowPrices, double[] closePrices, int period) {
        if (closePrices.length < period) {
            return new double[] {0.0};
        }
        MInteger outBegIdx = new MInteger();
        MInteger outNbElement = new MInteger();
        double[] outReal = new double[highPrices.length];
        int startIdx = 0;
        int endIdx = highPrices.length - 1;
        Core core = new Core();
        core.atr(startIdx, endIdx, highPrices, lowPrices, closePrices, period, outBegIdx, outNbElement, outReal);
        return normalizer(outBegIdx, outNbElement, outReal);
    }

    /**
     * Метод для переноса нулевых значений массива outReal из конца в начало.
     * @param outBegIdx число нулей, которое нужно перенести из конца в начало (разгонный отрезок индекса).
     * @param outNbElement количество заполненных значений индикатора в массиве.
     * @param outReal итоговый массив возвращаемый методом из библиотеки TA lib.
     * @return Итоговый массив с нулями переведенными из конца в начало.
     */
    public static double[] normalizer(MInteger outBegIdx, MInteger outNbElement, double[] outReal) {
        double[] newArr = new double[outReal.length];
        for (int i = 0; i < outBegIdx.value; i++) {
            newArr[i] = outReal[outNbElement.value + i];
        }
        for (int i = outBegIdx.value; i < outReal.length; i++) {
            newArr[i] = outReal[i - outBegIdx.value];
        }
        return newArr;
    }

    /**
     * Убирает начальные нули, обусловленные расчетом скользящей средней в массиве.
     * @param data Массив double с данными
     * @return Массив double с данными без начальных нулей, если они были.
     */
    public static double[] deleteBeginingZeros(double[] data) {
        List<Double> newData = new ArrayList<>();
        boolean isZerosInRow = true;
        for (int i = 0; i < data.length; i++) {
            if (isZerosInRow) {
                if (data[i] != 0) {
                    isZerosInRow = false;
                    newData.add(data[i]);
                }
            } else {
                newData.add(data[i]);
            }
        }
        return newData.stream().mapToDouble(i -> i).toArray();
    }


}
