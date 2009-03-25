package org.uwogarage;

import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.ListIterator;
import org.uwogarage.models.GarageSaleModel;
import org.uwogarage.models.UserModel;
import org.uwogarage.util.Location;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Reads a bulk load file to create new garage sales.
 * 
 * @author ECORMIEB
 */
public class BulkLoad {

	//error string, stores errors encountered by the reader during its last attempt
	private static String errors = "";
	
	//keep track of the number of garage sales created, use in error messages
	int count = 0;

	//FILE FORMAT INFORMATION:

	//number of fields to be read
	private static int FIELDS = 6;

	//indexes of information in file format (order it is read from file)
	private static final int AREA = 0;
	private static final int STREET = 1;
	private static final int CITY = 2;
	private static final int PROVINCE = 3;
	private static final int DATE = 4;
	private static final int TIME = 5;

	/**
	 * Returns a String with error messages to record each error encountered during last
	 * bulk reading process.
	 * 
	 * @return String representation of bulk loading errors
	 */
	public String getErrors() {
		return errors;
	}

	/**
	 * Given the user and the name of the bulk load file, reads the information out of the file
	 * and creates new GarageSaleModels with it. Returns a list containing all the GarageSaleModels
	 * created from the file
	 * @param user the user to whom these garage sales belong
	 * @param filename the name of the file to be read
	 * @return a linked list containing all the GarageSaleModels that were created from the file
	 */
	public static LinkedList<GarageSaleModel> bulkLoad(UserModel user,
			String filename) {

		//reset the error string
		errors = "";

		//the filereader
		BufferedReader fin;

		//whether end of file has been reached
		boolean eof = false;

		//empty list to store the GarageSaleModels created
		LinkedList<GarageSaleModel> sales = new LinkedList<GarageSaleModel>();

		//list of each block of information read from the file (a "block" being the collection
		//of information required to create one garage sale)
		LinkedList<String[]> blocksRead = new LinkedList<String[]>();

		//open the file
		try {
			fin = new BufferedReader(new FileReader("bulk.txt"));
		} catch (Exception e) {
			errors += "Unable to open file. ";
			return null;
		}

		//At each iteration, read one garage sale's information from the file. Stop
		//when the end of the file has been reached.
		while (!eof) {

			//array of information read in this block
			String[] infoRead = new String[FIELDS];

			for (int i = 0; i < FIELDS; i++) {
				//read a line
				try {
					infoRead[i] = fin.readLine().substring(6);
				}
				//if it can't be read, end of file has probably been reached.
				catch (Exception e) {
					eof = true;
					break;
				}
			}

			//add the new information read only if the end of file wasn't reached by the time the
			//last field was read
			if (!eof) {
				blocksRead.add(infoRead);
			}

		}

		//make an iterator of the blocks read
		ListIterator<String[]> infoIt = (ListIterator<String[]>) blocksRead.iterator();

		//keep track of the garage sales created for more useful error messages
		int count = 0;
		
		while (infoIt.hasNext()) {

			count++;

			//find the block of information read
			String[] infoRead = infoIt.next();

			//create a new GarageSale
			GarageSaleModel saleRead = new GarageSaleModel(user);

			//-----------------area-------------------------------	

			//find where the space between the numbers is, and get substrings
			//of the numbers before and after this space 

			String[] latlong = infoRead[AREA].split(" ");

			//try to set the geoposition, append error message to errors if it doesn't work.
			try {
				if (!saleRead.setGeoPosition(latlong[0], latlong[1])) {
					errors += "Couldn't set position of " + count
							+ "th garage sale.\n";
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				errors += "Invalid position for " + count + "th garage sale.\n";
			}

			//-----------------location-------------------------------	

			//Try creating a new location from the information read, then setting it
			//for the garage sale. Add to error messages if it doesn't work.
			Location newLoc;

			try {
				newLoc = new Location(infoRead[STREET], infoRead[PROVINCE],
						infoRead[CITY]);
				if(!saleRead.setLocation(newLoc)){
					errors += "Could not set Location for " + count
					+ "th garage sale.\n";
				}
				
			} catch (Exception e) {
				errors += "Could not create Location for " + count
						+ "th garage sale.\n";
			}

			//-----------------date/time-------------------------------	

			try {

				//split the date read into day, month, year
				String[] dmy = infoRead[DATE].split("/");

				int day = Integer.parseInt(dmy[0]);
				int month = Integer.parseInt(dmy[1]);
				int year = Integer.parseInt(dmy[2]);

				//split the time read into hours and minutes
				String[] hm = infoRead[TIME].split(":");

				int hours = Integer.parseInt(hm[0]);
				int minutes = Integer.parseInt(hm[1]);

				GregorianCalendar calendar = new GregorianCalendar(year, month,
						day, hours, minutes);

				if (!saleRead.setTime(calendar)) {
					errors += "Could not set time for " + count
							+ "th garage sale.\n";
				}

			}
			//This exception will be encountered if the split operation doesn't recover 
			//enough strings, meaning the date or time is in an invalid format
			catch (ArrayIndexOutOfBoundsException e) {
				errors += "Invalid date and/or time for " + count
						+ "th garage sale.\n";
			}

			//-----------------------------------------------------

			//add this new GarageSale to the list
			sales.add(saleRead);

		}

		return sales;

	}

	//---------------------------------------------------------------------
	//Test methods to confirm validity of data read. Remove later.

	/*
	//Prints information for debugging. Just comment out the System.out.prinln to
	//stop these printouts
	public static void print(Object s) {
		System.out.println(s);
	}

	//test method	
	public static void main(String[] args) {

		UserModel u = new UserModel();
		print(u.setFirstName("Name"));
		print(u.setDefaultZoom(4));
		print(u.setGeoPosition("44.123", "-81.012"));
		print(u.setLastName("Lastname"));
		print(u.setPassword("aaa"));
		print(u.setUserId("bob1"));
		print("user toString(): " + u.toString());

		print("loading");
		LinkedList<GarageSaleModel> list = bulkLoad(u, "bulk.txt");

		print("done loading");

		if (null == list) {
			print("---------\nnull list returned");
		} else {
			ListIterator<GarageSaleModel> it = (ListIterator<GarageSaleModel>) list
					.iterator();
			while (it.hasNext()) {
				try {
					print(it.next().toString());
				} catch (Exception e) {
					print("error in toString()");
				}
			}
		}

		print("errors: " + errors);

	}
	*/

}
