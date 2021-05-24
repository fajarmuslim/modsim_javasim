import java.io.IOException;

import org.javasim.RestartException;
import org.javasim.SimulationException;
import org.javasim.SimulationProcess;
import org.javasim.streams.UniformStream;

public class Breaks extends SimulationProcess {
    private UniformStream RepairTime;
    private UniformStream OperativeTime;
    private boolean interrupted_service;

    public Breaks() {
        RepairTime = new UniformStream(10, 100);
        OperativeTime = new UniformStream(200, 500);
        interrupted_service = false;
    }

    public void run() {
        while (!terminated()) {
            try {
                double failedTime = RepairTime.getNumber();

                hold(OperativeTime.getNumber());

                IndomaretShop.C.setOperate(false);
                IndomaretShop.C.cancel();

                if (!IndomaretShop.JobQ.isEmpty())
                    interrupted_service = true;

                hold(failedTime);

                IndomaretShop.CashierFailedTime += failedTime;
                IndomaretShop.C.setOperate(true);

                if (interrupted_service)
                    IndomaretShop.C.activateAt(IndomaretShop.C.serviceTime()
                            + currentTime());
                else
                    IndomaretShop.C.activate();

                interrupted_service = false;
            } catch (SimulationException e) {
            } catch (RestartException e) {
            } catch (IOException e) {
            }
        }
    }
}
