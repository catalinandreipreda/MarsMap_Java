import java.awt.*;
import java.lang.reflect.Constructor;

public class MarsDisplay extends Plot {
    //    Variables
    private MarsData md;
    private int size;
    private double longInc;
    private double latInc;
    private double minLong;
    private double maxLong;
    private double minLat;
    private double maxLat;

    //    Constructor
    MarsDisplay(int width, int height, String file) {
        //Creating Mars data
        md = new MarsData(file);

        //Setting up variables
        size = md.getSize();
        longInc = md.getLongitudeIncrement();
        latInc = md.getLatitudeIncrement();
        minLong = md.getFirstLongitude();
        maxLong = minLong + (2 * size - 1) * longInc;
        minLat = md.getFirstLatitude();
        maxLat = minLat + (size - 1) * latInc;


        //Setting up variables for plotting
        this.width = width;
        this.height = height;
        setScaleX(minLong, maxLong);
        setScaleY(minLat, maxLat);
    }

    private Color getColor(double altitude) {
        //Setting color based on altitude
        double altPercent = (altitude - md.getMinAltitude()) / (md.getMaxAltitude() - md.getMinAltitude());
        int red = (int) (255 * altPercent);
        int green = (int) red / 2;
        int blue = 0;
        if (altPercent > 0.9) blue = red / 5;


        Color c = new Color(red, green, blue);

        return c;
    }

    @Override
    public void paintComponent(Graphics graphics) {


        Graphics2D dot = (Graphics2D) graphics;
        for (double x = minLong; x <= maxLong; x += longInc)
            for (double y = minLat; y <= maxLat; y += latInc) {
                //Setting color based on altitude
                double altitude = md.getAltitude(x, y);
                dot.setColor(getColor(altitude));

                //Setting pixel size
                dot.fillOval(scaleX(x), scaleY(y), 10, 10);
            }


    }
}
