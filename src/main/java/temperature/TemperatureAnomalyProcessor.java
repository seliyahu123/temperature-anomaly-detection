package temperature;

import org.apache.flink.streaming.api.functions.windowing.ProcessAllWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

public class TemperatureAnomalyProcessor extends ProcessAllWindowFunction<TemperatureReading, TemperatureReading, TimeWindow> {

    @Override
    public void process(Context context, Iterable<TemperatureReading> temperatureReadings, Collector<TemperatureReading> temperatureAnomalies) {
        double temperatureAvg = calculateAvg(temperatureReadings);

        for (TemperatureReading reading : temperatureReadings) {
            if(Math.abs(reading.getTemperature()-temperatureAvg)>3){
                temperatureAnomalies.collect(reading);
            }
        }
        System.out.println(temperatureAnomalies);
    }

    private double calculateAvg(Iterable<TemperatureReading> elements){
        double temperatureSum = 0.0;
        double temperatureAvg = 0.0;
        int temperatureCount = 0;

        for (TemperatureReading reading : elements) {
            temperatureSum += reading.getTemperature();
            temperatureCount++;
        }

        if (temperatureCount > 0) {
            temperatureAvg = temperatureSum / temperatureCount;
        }
        return temperatureAvg;

    }
}
