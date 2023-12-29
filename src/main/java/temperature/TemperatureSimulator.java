package temperature;

import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;

import java.util.Random;

public class TemperatureSimulator extends RichParallelSourceFunction<TemperatureReading> {

    private boolean running = true;

    @Override
    public void run(SourceContext<TemperatureReading> context) throws Exception {
        Random random = new Random();
        while (running) {
            // Simulate readings from 20 devices (Should be 2000)
            for (int i = 0; i < 20; i++) {
                // Generate a unique sensor ID for each device
                String sensorId = "device-" + i;

                // Generate 100 readings in 1 minute
                for (int j = 0; j < 100; j++) {
                    // Simulate a timestamp and temperature
                    long timestamp = System.currentTimeMillis();
                    double temperature = random.nextInt(22);
                    context.collect(new TemperatureReading(sensorId, timestamp, temperature));
                }
            }
            // Simulate a minute delay time
            Thread.sleep(60000);
        }
    }

    @Override
    public void cancel() {
        running = false;
    }
}
