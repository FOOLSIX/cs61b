package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {
    int period;
    int state;
    private final double ACCELERATING_FACTOR;
    public AcceleratingSawToothGenerator(int initialPeriod, double acceleratingFactor) {
        period = initialPeriod;
        ACCELERATING_FACTOR = acceleratingFactor;
    }

    private double normalize(int s) {
        return (double) s * 2 / period - 1;
    }

    @Override
    public double next() {
        state++;
        if (state % period == 0) {
            period = (int) Math.round(period * ACCELERATING_FACTOR);
            state = period;
        }
        return normalize(state % period);
    }
}
