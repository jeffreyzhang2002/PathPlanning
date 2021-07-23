import robot.Odometry;

public class Main
{
    public static void main(String[] args)
    {
        Odometry odom = new Odometry(0,0,0);

        odom.update(15,20, 10);

        System.out.println("x: " + odom.getGlobalX() + " y: " + odom.getGlobalY() + " theta:" + odom.getTheta());
    }
}
