import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;


public class MarsData {
    //    Variables
    private double[][] arrayOfMars;



    private double firstLongitude;
    private double firstLatitude;
    private double longitudeIncrement;
    private double latitudeIncrement;
    private int size;

    private double maxAltitude = -99999999;
    private double minAltitude = 99999999;

//    Constructor
    MarsData(String file){
        //Getting necessary info about the file
        HashMap<String, Double> parameters = getFileParameters(file);

        firstLongitude = parameters.get("firstLongitude");
        firstLatitude = parameters.get("firstLatitude");
        longitudeIncrement = parameters.get("longitudeIncrement");
        latitudeIncrement = parameters.get("latitudeIncrement");
        size = parameters.get("latitudeCount").intValue();

        readData2D(file);
    }

//    Methods

//    =====================================================================
//    =============            Relevant methods        ====================
//    =====================================================================

    private void readData2D(String file){

        //Allocating memory for arrayOfMars
        arrayOfMars = new double[size][2*size];


        //Getting the altitudes from the file
        try{
            Scanner sc = new Scanner(new File(file));
            while (sc.hasNextLine()){
                //Getting the longitude, latitude and altitude for the current line
                double[] currentLine = parseData(sc.nextLine());
                double longitude = currentLine[0];
                double latitude = currentLine[1];
                double altitude = currentLine[2];

                //Updating min and max altitude
                searchMax(altitude);
                searchMin(altitude);

                //Plotting latitude and longitude to matrix indices
                int i = getIndex(latitude,  latitudeIncrement, firstLatitude ) ;
                int j = getIndex(longitude,  longitudeIncrement, firstLongitude ) ;

                //Populating arrayOfMars with altitude values
                arrayOfMars[i][j] = altitude;


            }
//            printArray(arrayOfMars, size);

        }catch (FileNotFoundException fnf){
            fnf.printStackTrace();
        }

    }

    public double getAltitude(double x, double y){
        double longitude = getClosest(x, firstLongitude, longitudeIncrement);
        double latitude = getClosest(y, firstLatitude, latitudeIncrement);
        int j = getIndex(longitude, longitudeIncrement, firstLongitude);
        int i = getIndex(latitude, latitudeIncrement, firstLatitude);
        return arrayOfMars[i][j];

    }



//    =====================================================================
//    =============             Utility methods        ====================
//    =====================================================================

    private static double[] parseData(String s){
        //Takes a CSV line
        //Returns a double array containing longitude, latitude and altitude
        double[] values = new double[3];
        String[] entry = s.split(",");
        for(int i = 0; i<3; i++){
            values[i] = Double.parseDouble(entry[i]);
        }
        return values;
    }

    private  HashMap<String, Double> getFileParameters(String file){
        //Returns a map with values for the first longitude/latitude,
        //their increment and the number of latitudes in the file provided
        HashMap<String, Double> parameters = new HashMap<String, Double>();
        try{
            //Reading the first CSV file
            Scanner sc = new Scanner(new File(file));
            double [] line = parseData(sc.nextLine());

            //Getting the first longitude
            double firstLong = line[0];
            parameters.put("firstLongitude", firstLong);

            //Getting the first latitude
            double firstLat = line[1];
            parameters.put("firstLatitude", firstLat);

            //Getting the 2nd CSV line
            line = parseData(sc.nextLine());

            //Getting the increment of the latitude
            double latInt = line[1] - firstLat;
            parameters.put("latitudeIncrement", latInt);

            //Getting the number of latitudes in the file
            int count = 2;
            while (sc.hasNextLine() && parseData(sc.nextLine())[0] == firstLong ){
                count++;
            }
            parameters.put("latitudeCount", Double.valueOf(count) );

            //Getting the increment of the longitude
            line = parseData(sc.nextLine());
            double longInt = line[0] - firstLong;
            parameters.put("longitudeIncrement", longInt);


            sc.close();

        }catch (FileNotFoundException fnf){
            fnf.printStackTrace();
        }

        return parameters;
    }

    private static int getIndex(double value, double inc, double first){
        //Maps latitude values to array indices.
        //d - value for latitude
        //inc - increment of the values; e.g for 1.25, 1.5, 1.75 the increment is 0.25
        //first - the first latitude

        return (int)((value + inc - first)/inc) -1;
    }

    private static double getClosest(double x, double start, double step){
        //Get the closest longitude or latitude to x
        while (start < x) start += step;
        return start;
    }

    private void searchMax(double newValue){
        //The case where maxAltitude is not assigned yet
        if(this.maxAltitude == -99999999){
            this.maxAltitude = newValue;
            return;
        }
        //Comparing to maxAltitude
        if(newValue > this.maxAltitude)
            this.maxAltitude = newValue;

    }

    private void searchMin(double newValue){
        //The case where minAltitude is not assigned yet
        if(this.minAltitude == 99999999){
            this.minAltitude = newValue;
            return;
        }
        //Comparing to minAltitude
        if(newValue < this.minAltitude)
            this.minAltitude = newValue;

    }


    public void printArray(double[][] a, int n){
            for(int i = 0; i< n; i++){
                for(int j=0; j< 2*n; j++)
                    System.out.print(a[i][j] + " ");
                System.out.println("\n");
            }

    }
// Getters
    public double getFirstLongitude() {
        return firstLongitude;
    }

    public double getFirstLatitude() {
        return firstLatitude;
    }

    public int getSize() {
        return size;
    }

    public double getLongitudeIncrement() {
        return longitudeIncrement;
    }

    public double getLatitudeIncrement() {
        return latitudeIncrement;
    }

    public double getMinAltitude(){
        return minAltitude;
    }

    public double getMaxAltitude(){
        return maxAltitude;
    }








}
