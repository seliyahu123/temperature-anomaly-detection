# System Components:
1. TemperatureSimulator - Simulates data from 20 devices, each generates 100 temperature readings in a minute.
2. TemperatureReading - Represents a temperature reading, contains: sensorId, timestamp, temperature.
3. TemperatureAnomalyProcessor - consume and process the collected temperature readings: calculates avg and prints temperature readings which deviates from avg.
4. TemperatureStreamingJob - Simulate a data stream using TemperatureSimulator, defines processing time windows of 1 minute, for which the TemperatureAnomalyProcessor process the collected temperature readings.

# Run:
Run the application by running  TemperatureStreamingJob main function.
