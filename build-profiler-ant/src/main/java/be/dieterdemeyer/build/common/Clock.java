package be.dieterdemeyer.build.common;

public interface Clock {

    long currentTimeMillis();

    Clock SYSTEM = new Clock() {
        public long currentTimeMillis() {
            return System.currentTimeMillis();
        }
    };

}