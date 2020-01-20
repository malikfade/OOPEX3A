package gameClient;


import de.micromata.opengis.kml.v_2_2_0.*;
import de.micromata.opengis.kml.v_2_2_0.Icon;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
/**
 * this class enables exporting the game, including the iron an and the villians to a KML file
 * which can be loaded to Google Maps.
 * On Maps you can view the progress of the game and iterate through the stages of the game.
 * @author malik and fadi.
 *
 */

public class KmlLogger {

	File f = new File("kmlFile.kml");
	
	/**
	 * converts long to string
	 * @return 
	 */
    public String mil_to_str(Long m) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(m));
    }
    /**
     * converts string to long
     * represents millis
     * @param str
     * @return
     * @throws ParseException
     */
    public long str_to_mil(String str) throws ParseException {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        Date d = f.parse(str.toString());
        long millis = d.getTime();
        return millis;
    }
    /**
     * @return the time is specified format.
     */
    public String time() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
    }
    /**
     * splits an array to string.
     * @param arr
     * @return
     */
    public String splitArr(String[] arr){
        String temp= arr[0] + "|" + arr[1] + "|";
        return temp;
    }
    Kml kmlDoc = new Kml(); // new KML_DOC
    Document doc = kmlDoc.createAndSetDocument(); // new DOC
    
    /**
     * Kml parser is used during the game, and it runs with it , when the game starts it creates
     * a file that keeps saving the data during the game.
     * @param myG
     * @param i
     * @throws ParseException
     * @throws InterruptedException
     */ 
    public void make_kml(myGame myG,int i) throws ParseException, InterruptedException {

        int c=0;
       
        if(myG!=null){

                ArrayList<Fruit> fruit = myG.fru_list;
                ArrayList<Robot> robots = myG.robo_list;
                //for every robot- save its data to the file
                for (Robot robot: robots) {
                    Placemark rob_mark = doc.createAndAddPlacemark();
                    //set style
                    IconStyle rob_style = new IconStyle();
                    rob_style.setScale(1);
                    rob_style.setHeading(1);
                    rob_style.setColor("abcdefg");
                    
                    //set the icon that has been implemented.
                    Icon rob_icon = new Icon();
                    rob_style.setIcon(rob_icon);
                    rob_icon.setHref("robot.png");
                    rob_icon.setViewBoundScale(1);
                    rob_icon.setViewRefreshTime(1);
                    rob_icon.withRefreshInterval(1);
                    //set the marks and the time to the specific case.
                    rob_mark.createAndAddStyle().setIconStyle(rob_style);
                    rob_mark.withDescription("\nType: Robot").withOpen(Boolean.TRUE).createAndSetPoint().addToCoordinates(robot.getPos().x(),robot.getPos().y());
                    String rob_t1 = mil_to_str(str_to_mil(time())+c*1000);
                    String rob_t2 = mil_to_str(str_to_mil(time())+(c +1)*1000);
                    String[] timeArr =rob_t1.split(" ");
                    rob_t1=splitArr(timeArr);
                    String[] timeArr2 = rob_t2.split(" ");
                    rob_t2=splitArr(timeArr2);
                    //Creates a time span.
                    TimeSpan rob_span = rob_mark.createAndSetTimeSpan();
                    rob_span.setBegin(rob_t1);
                    rob_span.setEnd(rob_t2);
                }
                //for every villain- saves its data to the file specified for it.
                for(Fruit f :fruit){
                    Placemark f_mark = doc.createAndAddPlacemark();
                    //Create the style and the icon we implemented.
                    Icon f_icon = new Icon();
                    IconStyle f_style = new IconStyle();
                    f_style.setScale(1);
                    f_style.setHeading(1);
                    f_style.setColor("ff007db3");
                    f_style.setIcon(f_icon);
                    //sets icon
                    f_icon.setHref("apple.png");
                    f_icon.setViewBoundScale(1);
                    f_icon.setViewRefreshTime(1);
                    f_icon.withRefreshInterval(1);
                    f_mark.createAndAddStyle().setIconStyle(f_style);
                    f_mark.withDescription("\nType: Fruit").withOpen(Boolean.TRUE).createAndSetPoint().addToCoordinates(f.getPos().x(),f.getPos().y());
                    String f_t1 = mil_to_str(str_to_mil(time())+c*1000);
                    String f_t2 = mil_to_str(str_to_mil(time())+(c+1)*1000);
                    String[] fruitArr= f_t1.split(" ");
                    f_t1=splitArr(fruitArr);
                    String[] fruitArr2 = f_t2.split(" ");
                    f_t2=splitArr(fruitArr2);
                    TimeSpan f_span = f_mark.createAndSetTimeSpan();
                    f_span.setBegin(f_t1);
                    f_span.setEnd(f_t2);

                }
                c++; // count the changes that has been made during the process .
            }

        try {
        	if(i==0) {		
        		i++;
        		kmlDoc.marshal(new File("kmlFile.kml"));
        	}
 	
        }
        catch (Exception e) {e.printStackTrace();}
    }


    
	public static void main(String[] args) {
		
	}
}

