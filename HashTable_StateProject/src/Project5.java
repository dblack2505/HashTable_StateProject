import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * COP 3530: Project 5 - Hash Tables
 * <p>
 * This program reads in data from an excel sheet and inserts the data into a Binary Search Tree. A menu is then
 * displayed with options like printing, deleting, and searching.
 * 
 * <p>
 * 
 * @author Dalton Black
 * @version 04/13/2021
 */

class Project5 {
	static Scanner input = new Scanner(System.in);

	/**
	 * Below is the main function.
	 * <p>
	 * It takes user input from an excel file and passes the state name, population, and deaths to hashTable.insert()
	 * to be hashed. An object array is also created to be used for searching for the population and deaths in later 
	 * methods. An option menu is then displayed with 6 options - 6 being to quit.
	 * @param args
	 */
	public static void main (String[] args) {
		int maxSize = 50;
		HashTable[] arr;
		arr = new HashTable [maxSize];
		HashTable hashTable = new HashTable();

		System.out.println("COP3530 Project 5");
		System.out.println("Instructor: Xudong Liu\n");
		System.out.println("Hash Table");

		String[] data; //excel data
		String line = "";
		String path = null;
		int nElem = 0;

		System.out.print("Enter the file name: ");
		path = input.nextLine();
		System.out.println("");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {	
			br.readLine();
			while((line = br.readLine()) != null) {
				data = line.split(",");
				String state = data[0];
				long popu = Integer.parseInt(data[4]);
				long covidD = Integer.parseInt(data[6]);
				hashTable.insert(state, popu, covidD);
				arr[nElem] = new HashTable(state, popu, covidD);
				nElem++;
			}
			hashTable.insertArr(arr, nElem);
			System.out.println("There were " + nElem + " records read.\n");

		} catch (IOException e) {
			e.printStackTrace();
		}


		int userIn = 0;
		while(userIn != 6) {
			System.out.print("\n");
			System.out.println("1) Print hash table");
			System.out.println("2) Delete a state of a given name");
			System.out.println("3) Insert a state of a given name");
			System.out.println("4) Search and print a state and its DR for a given name");
			System.out.println("5) Print numbs of empty cells and collisions");
			System.out.println("6) Exit");
			System.out.print("Please enter your choice: ");

			try {
				userIn = input.nextInt();
			} catch (Exception e) {
				input.next();
				System.out.println();
			}

			switch(userIn) {
			case 1:
				hashTable.display();
				break;
			case 2: 
				Scanner in = new Scanner(System.in);
				String stateName;
				System.out.print("Please enter a state to search for: ");
				stateName = in.nextLine();
				hashTable.deleteNode(stateName);
				break;
			case 3: 
				Scanner inState = new Scanner(System.in);
				String stateIn;
				System.out.print("Please enter a state to insert: ");
				stateIn = inState.nextLine();
				hashTable.insertState(stateIn);
				break;
			case 4:
				Scanner search = new Scanner(System.in);
				String searchState;
				System.out.print("Please enter a state to search for: ");
				searchState = search.nextLine();
				double DR = hashTable.find(searchState);
				System.out.printf(" with a DR of %-20.2f\n", DR);
				break;
			case 5:
				System.out.println();
				hashTable.printEmptyAndCollisions();
				break;
			case 6:
				System.out.println("Have a great day!");
				break;
			default:
				System.out.println("Invalid choice. Please enter 1-6\n");
				break;
			}
		}
		input.close();

	}
}