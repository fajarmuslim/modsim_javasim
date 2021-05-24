import org.javasim.RestartException;
import org.javasim.Simulation;
import org.javasim.SimulationException;
import org.javasim.SimulationProcess;

public class IndomaretShop extends SimulationProcess {
    public static Cashier C = null;
    public static Queue JobQ = new Queue();
    public static double TotalResponseTime = 0.0;
    public static long TotalJobs = 0;
    public static long ProcessedJobs = 0;
    public static double CashierActiveTime = 0.0;
    public static double CashierFailedTime = 0.0;

    public IndomaretShop() {
        TotalResponseTime = 0.0;
        TotalJobs = 0;
        ProcessedJobs = 0;
        CashierActiveTime = 0.0;
        CashierFailedTime = 0.0;
    }

    public void run() {
        try {
            Breaks B = null;
            Arrivals A = new Arrivals(2);
            IndomaretShop.C = new Cashier(2);
            Job J = new Job();

            A.activate();

            B = new Breaks();
            B.activate();

            Simulation.start();

            while (IndomaretShop.ProcessedJobs < 1000) {
                hold(1000);
            }

            System.out.println("Total pelanggan yang datang " + TotalJobs);
            System.out.println("Total pelanggan yang berhasil dilayani " + ProcessedJobs);
            System.out.println("Waktu rata-rata melayani pelanggan = " + (TotalResponseTime / ProcessedJobs));
            System.out.println("Peluang komputer kasir bekerja dengan baik = " + ((CashierActiveTime - CashierFailedTime) / currentTime()));
            System.out.println("Peluang komputer kasir rusak = " + (CashierFailedTime / CashierActiveTime));

            Simulation.stop();

            A.terminate();
            IndomaretShop.C.terminate();

            B.terminate();

            SimulationProcess.mainResume();
        } catch (SimulationException e) {
        } catch (RestartException e) {
        }
    }

    public void await() {
        this.resumeProcess();
        SimulationProcess.mainSuspend();
    }
}