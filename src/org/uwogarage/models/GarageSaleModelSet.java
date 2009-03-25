package org.uwogarage.models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import org.uwogarage.util.Location;

/**
 * Deal with garage sale model sets, and specifically, loading a set from a file.
 * 
 * version $Id$
 */
public class GarageSaleModelSet extends ModelSet<GarageSaleModel> {
    
    private static final long serialVersionUID = 1994134622442988501L;
    
    //keep track of the number of garage sales created, use in error messages
    private static int line_num;

    //indexes of information in file format (order it is read from file)
    private static final int AREA = 0,
                             STREET = 1,
                             CITY = 2,
                             PROVINCE = 3,
                             DATE = 4,
                             TIME = 5,
                             
                             // number of fields to be read
                             FIELDS = 6;
    
    /**
     * Read a line from input and match it against a regular expression. If it
     * doesn't match then throw an exception.
     * 
     * If a line cannot be read then an IOException is thrown.
     * 
     * @param fin
     * @param regex
     * @return
     * @throws Exception
     */
    private static String matchReadLine(BufferedReader fin, String regex) throws Exception {
        ++line_num;
        
        String line = fin.readLine();
        if(!line.matches(regex)) {
            throw new Exception(
                "Parse error: Line "+ line_num +" is incorrectly formatted:\n"+
                line
            );
        }
        return line;
    }

    /**
     * Given the user and the name of the bulk load file, reads the information out of the file
     * and creates new GarageSaleModels with it. Returns a list containing all the GarageSaleModels
     * created from the file
     * @param user the user to whom these garage sales belong
     * @param filename the name of the file to be read
     * @return a linked list containing all the GarageSaleModels that were created from the file
     */
    public static GarageSaleModelSet
    loadFromFile(UserModel user, String filename) throws Exception {

        // reset the line number
        line_num = 0;

        //the file reader
        BufferedReader fin;

        //empty list to store the GarageSaleModels created
        GarageSaleModelSet sales = new GarageSaleModelSet();

        //list of each block of information read from the file (a "block" being the collection
        //of information required to create one garage sale)
        LinkedList<String[]> blocksRead = new LinkedList<String[]>();

        //open the file
        try {
            fin = new BufferedReader(new FileReader("bulk.txt"));
        } catch (Exception e) {
            return null;
        }
        
        //At each iteration, read one garage sale's information from the file. Stop
        //when the end of the file has been reached.
        try {
            while (fin.ready()) {
    
                //array of information read in this block
                String[] infoRead = new String[FIELDS];
                
                try {
                    infoRead[AREA] = matchReadLine(fin,
                        "area: -?([0-9]{1,3})\\.([0-9]{3,6}) -?([0-9]{1,3})"+
                        "\\.([0-9]{3,6})"
                    );
                    infoRead[STREET] = matchReadLine(fin, "stre: .{1,50}");
                    infoRead[CITY] = matchReadLine(fin, "city: .{1,20}");
                    infoRead[PROVINCE] = matchReadLine(fin, 
                        "prov: (AB|BC|MB|NB|NL|NS|ON|PE|QC|SK)"
                    );
                    infoRead[DATE] = matchReadLine(fin,
                        "date: ((0?[1-9])|[12][0-9]|3[01])/((0?[1-9])|10|11|"+
                        "12)/([12][0-9]{3})"
                    );
                    infoRead[TIME] = matchReadLine(fin,
                        "time: ((0?[1-9])|1[0-9]|2[0-4]):((0[1-9])|[1-5][1-9])"
                    );
                    
                    // add the new information read only if the end of file 
                    // wasn't reached by the time the last field was read
                    blocksRead.add(infoRead);
                    
                // the file was improperly formatted, i.e. it is missing information
                // for a sale
                } catch(IOException e) {
                    throw new Exception(
                        "Parse error: there is information missing from the "+
                        "bulk load file on line "+ line_num
                    );
                }
            }
        } catch(IOException e) {
            if(0 == blocksRead.size()) {
                throw new Exception(
                    "The file was empty or did not contain a complete garage "+
                    "sale record to be added."
                );
            }
        }
        
        // iterate over the blocks read and attempt to verify their contents
        // against the model layer
        int count = 1;
        
        StringBuffer errors = new StringBuffer();
        String[] latlong;
        GregorianCalendar calendar;
        GarageSaleModel saleRead;
        Location newLoc;
        
        for(String[] infoRead : blocksRead) {
            
            //create a new GarageSale
            saleRead = new GarageSaleModel(user);

            //-----------------area-------------------------------  

            // find where the space between the numbers is, and get substrings
            // of the numbers before and after this space 

            latlong = infoRead[AREA].substring(6).split(" ");

            // try to set the geo position, append error message to errors if it 
            // doesn't work.
            try {
                if (!saleRead.setGeoPosition(latlong[0], latlong[1])) {
                    errors.append(
                        "Couldn't set position of " + count + "th garage sale.\n"
                    );
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                errors.append(
                    "Invalid position for " + count + "th garage sale.\n"
                );
            }

            //-----------------location-------------------------------  

            //Try creating a new location from the information read, then setting it
            //for the garage sale. Add to error messages if it doesn't work.
            try {
                newLoc = new Location(
                    infoRead[STREET], 
                    infoRead[PROVINCE],
                    infoRead[CITY]
                );
                
                if(!saleRead.setLocation(newLoc)){
                    errors.append(
                        "Could not set Location for " + count + "th garage sale.\n"
                    );
                }
                
            } catch (Exception e) {
                errors.append(
                    "Could not create Location for " + count + "th garage sale.\n"
                );
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

                calendar = new GregorianCalendar(
                    year, month, day, hours, minutes
                );

                if (!saleRead.setTime(calendar)) {
                    errors.append(
                        "Could not set time for " + count + "th garage sale.\n"
                    );
                }

            }
            //This exception will be encountered if the split operation doesn't recover 
            //enough strings, meaning the date or time is in an invalid format
            catch (ArrayIndexOutOfBoundsException e) {
                errors.append(
                    "Invalid date and/or time for " + count + "th garage sale.\n"
                );
            }

            //-----------------------------------------------------
            
            // have any errors occurred while creating this model?
            if(0 < errors.length()) {
                throw new Exception(errors.toString());
            }
            
            //add this new GarageSale to the set
            sales.add(saleRead);
            
            ++count;
        }
        
        // if, for some reason, the set is empty, throw an error saying that
        // an unknown error occurred
        if(0 == sales.size()) {
            throw new Exception(
                "An unknown error occurred while trying to bulk loa the "+
                "garage sales from disk."
            );
        }

        return sales;
    }    
}
