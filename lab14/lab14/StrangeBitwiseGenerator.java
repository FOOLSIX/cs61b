package lab14;

import lab14lib.Generator;

public class StrangeBitwiseGenerator implements Generator {
    int period;
    int state;

    public StrangeBitwiseGenerator(int p) {
        period = p;
    }

    private double normalize(int s) {
        return (double) s * 2 / period - 1;
    }

    @Override
    public double next() {
        state++;
        int weirdState = state & (state >>> 3) % period;
        return normalize(weirdState % period);
    }
}
