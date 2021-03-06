import java.io.IOException;


import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;



/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */
public class Main {

	public static void main( String[] args ) {


		if (args.length != 1){

			System.out.println("Usage: java Main <url>");

		}


		String JSON = "{\n\t";	//output string

		String url = args[0];

		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {

			e.printStackTrace();
		}

		//TITLE

		Elements title = doc.getElementsByClass("id-app-title");

		JSON += "\"title\": \""+title.text()+"\",\n\t";


		//DESC

		Elements description = doc.getElementsByAttributeValue("jsname", "C4s9Ed");

		String desc = description.toString();

		String jsname = "<div jsname=\"C4s9Ed\"> \n";

		String descMod = "";
		
		if(desc.contains("<br>")){
			
			String[] parts = desc.split("<br>");
			descMod= parts[0];
			descMod = descMod.substring(descMod.indexOf(jsname)+jsname.length(), descMod.length()-1).trim();
		
		}else{ //if description does not have any <br> tags
			
			descMod = description.text();
		}


	

		JSON += "\"description\": \""+descMod+"\",\n\t";


		//PUBLISHER
		Elements publisher = doc.getElementsByClass("document-subtitle primary");

		JSON += "\"publisher\": \""+publisher.text()+"\",\n\t";



		//PRICE
		Elements price = doc.getElementsByClass("price buy id-track-click id-track-impression");


		String priceActual = "";

		if(price.text().contains("$")){

			priceActual = price.get(0).text().substring(0,price.get(0).text().indexOf(" ") );

		}else{

			priceActual = "free";

		}

		JSON += "\"price\": \""+priceActual+"\",\n\t";



		//RATING

		Elements rating = doc.getElementsByClass("score");


		JSON += "\"rating\": \""+rating.text()+"\",\n}";

		System.out.println(JSON);



	}

}
