package lab14;

import lab14lib.Generator;

public class SawToothGenerator implements Generator {
    int period;
    int state;

    public SawToothGenerator(int p) {
        period = p;
    }

    private double normalize(int s) {
        return (double) s * 2 / period - 1;
    }

    @Override
    public double next() {
        state++;
        return normalize(state % period);
    }
}
