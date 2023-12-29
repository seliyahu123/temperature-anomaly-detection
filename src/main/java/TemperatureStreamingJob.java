import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import temperature.TemperatureAnomalyProcessor;
import temperature.TemperatureReading;
import temperature.TemperatureSimulator;

public class TemperatureStreamingJob {

    public static void main(String[] args) throws Exception {
        // Set up execution environment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Simulate a stream of TemperatureReadings using TemperatureSimulator
        DataStream<TemperatureReading> temperatureStream = env.addSource(new TemperatureSimulator());

        DataStream<TemperatureReading> temperatureAnomalies = temperatureStream
                .windowAll(TumblingProcessingTimeWindows.of(Time.minutes(1)))
                .process(new TemperatureAnomalyProcessor());

        // Detect temperature anomalies using TemperatureAnomalyProcessor
        temperatureAnomalies.print().setParallelism(1);;

        // Execute the job
        env.execute("TemperatureStreamingJob");


    }
}