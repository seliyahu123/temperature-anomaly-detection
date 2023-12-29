package temperature;

import org.apache.flink.streaming.api.functions.windowing.ProcessAllWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.time.LocalDateTime;

public class TemperatureAnomalyProcessor extends ProcessAllWindowFunction<TemperatureReading, TemperatureReading, TimeWindow> {

    @Override
    public void process(Context context, Iterable<TemperatureReading> temperatureReadings, Collector<TemperatureReading> temperatureAnomalies) {
        double temperatureAvg = calculateAvg(temperatureReadings);

        System.out.println("##### time: " + LocalDateTime.now() + " temperature avg: "+ temperatureAvg +" #####");
        for (TemperatureReading reading : temperatureReadings) {
            if(Math.abs(reading.getTemperature()-temperatureAvg)>3){
                System.out.println("Device: " + reading.getSensorId()+", measurement: "+ reading.getTemperature() +", time: "+ reading.getTimestamp());
            }
        }

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
