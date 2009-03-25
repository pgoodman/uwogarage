package org.uwogarage;

import java.util.LinkedList;
import java.util.ListIterator;
import org.uwogarage.models.GarageSaleModel;
import org.uwogarage.models.UserModel;
import org.uwogarage.util.Location;
import java.io.BufferedReader;
import java.io.FileReader;

public class BulkLoad {

		
	private static String errors = "";
	
	//number of fields read
	private static int FIELDS = 6;
	
	//indexes of information in file format (order it is read from file)
	private static final int AREA = 0;
	private static final int STREET = 1;
	private static final int CITY = 2;
	private static final int PROVINCE = 3;
	
	public String getErrors(){
		return errors;
	}
	
	//creates a list of garage sales for the parameterized user from the file
	public static LinkedList<GarageSaleModel> bulkLoad(UserModel user, String filename){
		
		print("bulkLoad started.");
		
		//reset the error string
		errors = "";
		
		//create a new empty list
		LinkedList<GarageSaleModel> sales = new LinkedList<GarageSaleModel>();
		
		//the filereader
		BufferedReader fin;
		
		//open the file
		try{
			fin = new BufferedReader(new FileReader("bulk.txt"));
		}
		catch(Exception e){
			errors += "Unable to open file. ";
			print("can't open file");
			return null;
		}
		
				
		//whether end of file has been reached
		boolean eof = false;
		
		//temporary string to hold the last line read
		String temp;
		
		LinkedList<String[]> blocksRead = new LinkedList<String[]>();
		
		//read a garage sale
		while(!eof){
						
			//read all the information for one GarageSale
			String[] infoRead = new String[FIELDS];
			
			for(int i = 0; i < FIELDS; i++){
				//read this line
				try{
					infoRead[i] = fin.readLine().substring(6);
					print("read[] at " + i + ": " + infoRead[i]);
				}
				//if it can't be read, end of file has probably been reached.
				catch(Exception e){
					print("Unable to read line. End of file? Break.");
					eof = true;
					break;
				}
			}
			
			//add the new information read if the end of file wasn't prematurely reached
			if(!eof){
				blocksRead.add(infoRead);
			}
			
		}
			
		print("GarageSales read: " + blocksRead.size());
		
		//make an iterator of the things read to go through each block, creating the garage sale
		ListIterator<String[]> infoIt = (ListIterator<String[]>) blocksRead.iterator();
		
		//count the garage sale it's at, for error messages
		int count = 0;
		
		while(infoIt.hasNext()){
			
			count ++;
			
			//find the block of information read
			String[] infoRead = infoIt.next();
			
			//create a new GarageSale
			GarageSaleModel saleRead = new GarageSaleModel(user);
			
			//-----------------area-------------------------------	
			
			//find where the space between the numbers is, and get substrings
			//of the numbers before and after this space
			int spaceIndex = infoRead[AREA].indexOf(" ");
			String latitude = infoRead[AREA].substring(0, spaceIndex);
			String longitude = infoRead[AREA].substring(spaceIndex+1);
			
			print("lat string: |" + latitude + "|, long: |" + longitude + "|");
			
			//try to set the geoposition, append error message to errors if it doesn't work.
			if(!saleRead.setGeoPosition(latitude,longitude)){
				errors += "Couldn't set Geoposition of " + count + "th garage sale.\n";
			}
			else{
				print("GeoPosition of " + count + "th garage sale set: " + saleRead.getGeoPosition().toString());
			}
			
			//-----------------location-------------------------------	
			
			//Location newLoc = new Location(infoRead[STREET], infoRead[CITY], infoRead[PROVINCE]);
			
			
			//-----------------------------------------------------
			
			//add this new GarageSale to the list
			sales.add(saleRead);
			
		}
			
			
			

		return sales;
		
	}
	
	
	//---------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------
	//TEST METHODS
	
	//print method to print information for debugging. Just comment out the System.out.prinln to
	//stop these printouts
	public static void print(Object s){
		System.out.println(s);
	}
	
	//a "test driver"!
	/*
	public static void main(String[] args){
		
		UserModel u = new UserModel();
		print(u.setFirstName("Name"));
		print(u.setDefaultZoom(4));
		print(u.setGeoPosition("44.123","-81.012"));
		print(u.setLastName("Lastname"));
		print(u.setPassword("aaa"));
		print(u.setUserId("bob1"));
		print("user toString(): " + u.toString());
		
		print("loading");
		LinkedList<GarageSaleModel> list = bulkLoad(u, "bulk.txt");
		
		print("done loading");
		
		if(null == list){
			print("---------\nnull list returned");
		}		
		else{
			print("---------\nlist not null?");
			ListIterator<GarageSaleModel> it = (ListIterator<GarageSaleModel>) list.iterator();
			while(it.hasNext()){
				print(it.next().toString());
			}
		}
		
		print("errors: " + errors);
		
	}
	*/
	

	
	
}
