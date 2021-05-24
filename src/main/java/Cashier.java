import java.io.IOException;

import org.javasim.RestartException;
import org.javasim.Scheduler;
import org.javasim.SimulationException;
import org.javasim.SimulationProcess;
import org.javasim.streams.ExponentialStream;

public class Cashier extends SimulationProcess {
    private ExponentialStream simulationTime;
    private boolean isOperate;
    private boolean isWorking;
    private Job job;

    public Cashier(double mean) {
        simulationTime = new ExponentialStream(mean);
        isOperate = true;
        isWorking = false;
        job = null;
    }

    public boolean isOperate() {
        return isOperate;
    }

    public void setOperate(boolean operate) {
        isOperate = operate;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public double serviceTime() {
        try {
            return simulationTime.getNumber();
        } catch (IOException e) {
            return 0.0;
        }
    }

    public void run() {
        double ActiveStart, ActiveEnd;

        while (!terminated()) {
            isWorking = true;

            while (!IndomaretShop.JobQ.isEmpty()) {
                ActiveStart = currentTime();

                job = IndomaretShop.JobQ.dequeue();

                try {
                    hold(serviceTime());
                } catch (SimulationException e) {
                } catch (RestartException e) {
                }

                ActiveEnd = currentTime();
                IndomaretShop.CashierActiveTime = IndomaretShop.CashierActiveTime + (ActiveEnd - ActiveStart);
                IndomaretShop.ProcessedJobs = IndomaretShop.ProcessedJobs + 1;

                job.setResponseTime(Scheduler.currentTime() - job.getArrivalTime());
                IndomaretShop.TotalResponseTime = IndomaretShop.TotalResponseTime + job.getResponseTime();
            }

            isWorking = false;

            try {
                cancel();
            } catch (RestartException e) {
            }
        }
    }
}
