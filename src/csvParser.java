import java.util.Scanner;  
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
public class csvParser {
	//Parse the string version of the number to see if number is valid profit value
	public static boolean isNum(String num) {
		if(num == null) {
			return false;
		}
		try {
			double d = Double.parseDouble(num);
		    return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public static void saveToFile(File file, ArrayList<String> row) {
		String year;
		String rank;
		String company;
		String revenue;
		String profit; 
		try {
			FileWriter json = new FileWriter(file);
			json.write("{\n");
			
			for(int i = 0; i < row.size(); i++) {
				// get the character to see if it is a ", a quote represents a comma being enclosed
				char c = row.get(i).split(",")[2].charAt(0);
				//if a comma is enclosed, combine the left and right side of the comma inside quotes
				if(c == '"') {
					year = row.get(i).split(",")[0];
					rank = row.get(i).split(",")[1];
					company = row.get(i).split(",")[2] + row.get(i).split(",")[3];
					revenue = row.get(i).split(",")[4];
					profit = row.get(i).split(",")[5];
				} else{
					year = row.get(i).split(",")[0];
					rank = row.get(i).split(",")[1];
					company = row.get(i).split(",")[2];
					revenue = row.get(i).split(",")[3];
					profit = row.get(i).split(",")[4];
				}
				
				
				json.write("\t\t{\n");
				json.write("\t\t\t\"Year\":\"" + year + "\",\n");
				json.write("\t\t\t\"Rank\":\"" + rank + "\",\n");
				json.write("\t\t\t\"Company\":\"" + company + "\",\n");

				json.write("\t\t\t\"Revenue\":\"" + revenue + "\",\n");
				json.write("\t\t\t\"Profit\":\"" + profit + "\"");
				
				if(i == row.size() - 1) {
					json.write("\n\t\t}\n");
				}else {
					json.write("\n\t\t},\n");
				}	
			}
			json.write("}");
			json.close();
			
		}catch (IOException i) {
			i.printStackTrace();
			return;
		}
	}
	
	public static void createFile(File json) {
		try {
			  json.createNewFile();
			} catch (IOException e) {
			  System.out.println("An error occurred.");
			  e.printStackTrace();
			}
	}
	//merges the two sorted arrays. Solution based off https://www.geeksforgeeks.org/merge-sort/ 
	public static void merge(ArrayList<String> arr, int left, int mid, int right){
        // Find sizes of two subarrays to be merged
        int n1 = mid - left + 1;
        int n2 = right - mid;
 
        // temporary arrays
        ArrayList<String> tempLeft = new ArrayList<String>();
        ArrayList<String> tempRight = new ArrayList<String>();
 
        /*Copy data to temp arrays*/
        for (int i = 0; i < n1; i++) {
        	tempLeft.add(arr.get(left + i));
        }
        for (int j = 0; j < n2; j++) {
        	tempRight.add(arr.get(mid + 1 + j));
        }
        /* Merge the temp arrays */
 
        // Initial indexes of first and second subarrays
        int i = 0, j = 0;
 
        // Initial index of merged subarray array
        int k = left;
        while (i < n1 && j < n2) {
        	char checkLeft = tempLeft.get(i).split(",")[2].charAt(0);
        	char checkRight = tempRight.get(j).split(",")[2].charAt(0);
        	double leftVal;
        	double rightVal;
        	
			if(checkLeft == '"') {
				leftVal = Double.parseDouble(tempLeft.get(i).split(",")[5]);
			} else{
				leftVal = Double.parseDouble(tempLeft.get(i).split(",")[4]);
			}
			if(checkRight == '"') {
				rightVal = Double.parseDouble(tempRight.get(j).split(",")[5]);
			} else{
				rightVal = Double.parseDouble(tempRight.get(j).split(",")[4]);
			}
			
            if (leftVal <= rightVal) {
            	arr.set(k, tempLeft.get(i));
                i++;
            }
            else {
                arr.set(k, tempRight.get(j));
                j++;
            }
            k++;
        }
 
        /* Copy remaining elements of left side if any */
        while (i < n1) {
            arr.set(k, tempLeft.get(i));
            i++;
            k++;
        }
 
        /* Copy remaining elements of right side if any */
        while (j < n2) {
            arr.set(k, tempRight.get(j));
            j++;
            k++;
        }
    }
 
    // Main function that sorts array using
    // merge()
    public static void sort(ArrayList<String> arr, int left, int right) {
        if (left < right) {
            // Find the middle point
            int mid = left + (right-left)/2;
   
            // Sort first and second halves
            sort(arr, left, mid);
            sort(arr, mid + 1, right);
 
            // Merge the sorted halves
            merge(arr, left, mid, right);
        }
    }
	public static void main(String[] args) throws Exception {
		File csv_input = new File("csv/data.csv");
		// call create file to set up the json file to be written to
		File json = new File("data2.json");
		createFile(json);
		
		Scanner sc = new Scanner(csv_input);  
		sc.useDelimiter("\n");
		
		int numLines = 0; 
		int validProf = 0;
		String row;
		String num;
		ArrayList<String> validRow = new ArrayList<String>();
		
		while (sc.hasNext()){
			row = sc.next();
			char c = row.split(",")[2].charAt(0);
			if(c == '"') {
				num = row.split(",")[5];
			} else{
				num = row.split(",")[4];
			}
			if (isNum(num)) {
				validProf++;
				validRow.add(row);
			}
			numLines++;
		}  
		saveToFile(json, validRow);
		//do numLines - 1 to not include headers
		System.out.println("Number of data rows: " + (numLines - 1));
		System.out.println("Number of valid data rows: " + validProf);
		sort(validRow, 0, validRow.size() - 1);
		for(int i = 0; i < 20; i++) {
			System.out.println((i + 1) + ". " + validRow.get(validRow.size() - 1 - i));
		}
		sc.close();  
		
	}
}
