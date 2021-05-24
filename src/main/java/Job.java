import org.javasim.RestartException;
import org.javasim.Scheduler;
import org.javasim.SimulationException;

public class Job {
    private double ResponseTime;
    private double ArrivalTime;

    public double getResponseTime() {
        return ResponseTime;
    }

    public void setResponseTime(double responseTime) {
        ResponseTime = responseTime;
    }

    public double getArrivalTime() {
        return ArrivalTime;
    }

    public Job() {

        ResponseTime = 0.0;
        ArrivalTime = Scheduler.currentTime();

        boolean empty = IndomaretShop.JobQ.isEmpty();
        IndomaretShop.JobQ.enqueue(this);
        IndomaretShop.TotalJobs = IndomaretShop.TotalJobs+1;

        if (empty && !IndomaretShop.C.isWorking()
                && IndomaretShop.C.isOperate()) {
            try {
                IndomaretShop.C.activate();
            } catch (SimulationException e) {
            } catch (RestartException e) {
            }
        }
    }
}
