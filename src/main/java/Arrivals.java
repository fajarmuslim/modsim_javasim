import java.io.IOException;

import org.javasim.RestartException;
import org.javasim.SimulationException;
import org.javasim.SimulationProcess;
import org.javasim.streams.ExponentialStream;

public class Arrivals extends SimulationProcess {
    private ExponentialStream InterArrivalTime;

    public Arrivals(double mean) {
        InterArrivalTime = new ExponentialStream(mean);
    }

    public void run() {
        while (!terminated()) {
            try {
                hold(InterArrivalTime.getNumber());
            } catch (SimulationException e) {
            } catch (RestartException e) {
            } catch (IOException e) {
            }

            new Job();
        }
    }
}
