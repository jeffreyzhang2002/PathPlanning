package robot;

public class Odometry
{
    private double globalX, globalY;
    private double theta;
    private double robotRadius = 5;
    private double robotAuxRadius = 5;

    public Odometry(double initialGlobalX, double initialGlobalY, double initialTheta)
    {
        globalX = initialGlobalX;
        globalY = initialGlobalY;
        theta = initialTheta;
    }

    public Odometry()
    {

    }

    public void setStartPosition(double initialGlobalX, double initialGlobalY, double initialTheta)
    {
        globalX = initialGlobalX;
        globalY = initialGlobalY;
        theta = initialTheta;
    }

    public void update(double rightDistance, double leftDistance, double auxDistance)
    {
        // angleCalculation;
        double deltaTheta =  (rightDistance - leftDistance) / (2 * robotRadius);
        double centerDistance = (leftDistance + rightDistance)/ 2;

        double hypot;

        if(deltaTheta == 0)
            hypot = centerDistance;
        else
            hypot = (centerDistance / deltaTheta) * (Math.sin(deltaTheta) / Math.cos(deltaTheta / 2));

        //assuming perfect aux wheel;
        double deltaXStrafe = auxDistance * Math.cos(theta - Math.PI/2 + deltaTheta/2);
        double deltaYStrafe = auxDistance * Math.sin(theta - Math.PI/2 + deltaTheta/2);

        globalX += Math.cos(theta + deltaTheta/2) * hypot + deltaXStrafe;
        globalY += Math.sin(theta + deltaTheta/2) * hypot + deltaYStrafe;
        theta += deltaTheta;
    }

    public double getGlobalX()
    { return globalX; }

    public double getGlobalY()
    { return  globalY; }

    public double getTheta()
    { return theta; }
}
