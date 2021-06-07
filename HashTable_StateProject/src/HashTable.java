import HashTable.Link;
import HashTable.Node;

/**
 * Class HashTable
 * 
 * @author Dalton Black
 * @version 4/13/2021
 */
public class HashTable {

	private Link[] hashArray;
	private int arraySize;
	private static HashTable[] a;
	private static int nElems;
	private static int max = 50;
	private String state;
	private long population;
	private long covidDeath;

	/**
	 * Class Node
	 * 
	 * @author Dalton Black
	 * @version 04/13/2021
	 */
	private class Node {

		String name;
		long population;
		long deaths;
		Node nextNode;

		/**
		 * Constructor to set these variables.
		 * @param name - String state name
		 * @param population - long population
		 * @param deaths - long deaths
		 */
		public Node(String name, long population, long deaths) {
			this.name = name;
			this.population = population;
			this.deaths = deaths;
		}

		/**
		 * Method to print a state and it's death rate.
		 */
		public void printNode() {
			System.out.printf("%-30s %-20.2f\n", name, ((double) deaths/population) * 100000);
		}

	}

	/**
	 * Class Link
	 * 
	 * @author Dalton Black
	 * @version 04/13/2021
	 */
	private class Link {
		private Node first;
		private Node last;
		private String state;
		private long population;
		private long deaths;

		/**
		 * Constructor.
		 */
		public Link() {}

		/**
		 * Method to insert a new state into Linked List.
		 * @param newNode - Node newNode to be inserted
		 */
		public void insert(Node newNode) {
			if(isEmpty())
				first = newNode;
			else 
				last.nextNode = newNode;
			last = newNode;
		}

		/**
		 * Method checks to see if the Linked List is empty.
		 * @return boolean
		 */
		private boolean isEmpty() {
			return first == null;
		}

		/**
		 * Method the display the Linked List by calling printNode in Node class.
		 */
		public void displayList() {
			Node current = first;
			int counter = 0;
			while(current != null) {
				if(counter > 0) 
					System.out.print("    ");
				current.printNode();
				current = current.nextNode;
				counter++;
			}
			System.out.println("");
		}

		/**
		 * Method deletes a link from the Linked List
		 * @param state - String state
		 */
		public void delete(String state) {
			Node previous = null;
			Node current = first;
			while(current != null && state.compareTo(current.name) > 0) {
				previous = current;
				current = current.nextNode;
			}
			try {
				if(previous == null)
					first = first.nextNode;
				else
					previous.nextNode = current.nextNode;
			} catch(NullPointerException e) {}
			System.out.println("\n" + state + " is deleted.");
		}

		/**
		 * Method to search through Linked List and find a state.
		 * @param state - String state 
		 * @return Node
		 */
		public Node find(String state) {
			Node current = first;
			while(current != null && state.compareTo(current.name) <= 0) {
				if(current.name.compareTo(state) < 0)
					return current;
				current = current.nextNode;
			}
			return null;
		}
		
		/**
		 * Method goes through Linked Lists and sees if there is more than 1 link, and if so, returns true.
		 * @return boolean
		 */
		public boolean testCollision() {
			Node current = first;
			boolean test = false;
			int counter = 0;
			while(current != null) {
				if(counter > 0)
					test = true;
				current = current.nextNode;
				counter++;	
			}
			return test;
		}
	}

	/**
	 * Constructor that creates the hash table.
	 */
	public HashTable() {
		arraySize = 101;
		hashArray = new Link[arraySize];
		for(int i = 0; i < arraySize; i++) {
			hashArray[i] = new Link();
		}
	}

	/**
	 * Constructor that sets these variables and creates the object array. 
	 * @param state - String state
	 * @param popu - long population 
	 * @param covidD - long covid deaths
	 */
	public HashTable(String state, long popu, long covidD) {
		this.state = state;
		this.population = popu;
		this.covidDeath = covidD;
		a = new HashTable[max];
	}

	/**
	 * Below are multiple getters for state names, populations, and deaths.
	 * @return
	 */
	public String getState() {
		return this.state; 
	}
	public long getPopulation() {
		return this.population; 
	}
	public long getCovidDeath() {
		return this.covidDeath; 
	}
	
	/**
	 * Insert excel objects into object array.
	 * @param arr - HashTable array
	 * @param nElem - int number of elements
	 */
	public void insertArr(HashTable arr[], int nElem) {
		this.nElems = nElem;
		this.a = arr;
	}

	/**
	 * Method to search the object array for the population and deaths of a state and to call insert(). 
	 * @param stateIn
	 */
	public void insertState(String stateIn) {
		long population = 0;
		long deaths = 0;
		long results[] = search(stateIn);
		population = results[0];
		deaths = results[1];
		insert(stateIn, population, deaths);
		System.out.println("\n" + stateIn + " is inserted.");
	}

	/**
	 * Method to insert a state into the hash table. 
	 * @param state - String state
	 * @param population - long population
	 * @param deaths - long population
	 */
	public void insert(String state, long population, long deaths) {
		Node newNode = new Node(state, population, deaths);
		int hashVal = hashFunc(state, population, deaths);
		hashArray[hashVal].insert(newNode);
	}

	/**
	 * Method to search the object array for the population and deaths of a state and to call find(). 
	 * @param searchState - String state
	 * @return double DR
	 */
	public double find(String searchState) {
		long population = 0;
		long deaths = 0;
		long results[] = search(searchState);
		population = results[0];
		deaths = results[1];
		double DR = find(searchState, population, deaths);
		return DR;
	}

	/**
	 * Method to find a state in the hash table. 
	 * @param state - String state
	 * @param population - long population
	 * @param deaths - long population
	 * @return double DR
	 */
	public double find(String state, long population, long deaths) {
		int hashVal = hashFunc(state, population, deaths);
		Node theNode = hashArray[hashVal].find(state);
		System.out.print("\n" + state + " is found at index " + hashVal);
		return ((double) deaths/population) * 100000;
	}

	/**
	 * Method to search the object array for the population and deaths of a state and to call delete(). 
	 * @param stateName - String state
	 */
	public void deleteNode(String stateName) {
		long population = 0;
		long deaths = 0;
		long results[] = search(stateName);
		population = results[0];
		deaths = results[1];
		if(population < 1 || deaths < 1)
			System.out.println("\n" + stateName + " is not a state.");
		else
			delete(stateName, population, deaths);
	}

	/**
	 * Method to delete a state in the hash table. 
	 * @param state - String state
	 * @param population - long population
	 * @param deaths - long population
	 */
	public void delete(String state, long population, long deaths) {
		int hashVal = hashFunc(state, population, deaths);
		hashArray[hashVal].delete(state);
	}

	/**
	 * Method to display the hash table.
	 */
	public void display() {
		for(int i = 0; i < arraySize; i++) {
			System.out.print(i + ". ");
			hashArray[i].displayList();
		}
	}

	/**
	 * Method to find and print the number of empty cells and collisions. 
	 */
	public void printEmptyAndCollisions() {
		int empty = 0;
		int collision = 0;
		boolean test = false;
		for(int i = 0; i < arraySize; i++) {
			if(hashArray[i].isEmpty()) 
				empty++;
		}
		for(int j = 0; j < arraySize; j++) {
			test = hashArray[j].testCollision();
			if(test)
				collision++;
		}
		System.out.println("\nThere are " + empty + " empty cells and " + collision + " collisions in the hash table.");
	}

	/**
	 * Hash function formula.
	 * @param state - String state
	 * @param population - long population
	 * @param deaths - long population
	 * @return int index of hash
	 */
	public int hashFunc(String state, long population, long deaths) {
		int hashVal = 0;
		int letter = 0;
		for(int i = 0; i < state.length(); i++) {
			letter += state.charAt(i);
		}
		hashVal = ((int) (letter + ((population + deaths))) % 101);
		return hashVal;
	}

	/**
	 * Method used to search the object array and return an array of the population and deaths. 
	 * @param target - String state
	 * @return - long array with population and deaths
	 */
	public long[] search(String target) {
		int i = 0;
		long population = 0;
		long deaths = 0;
		long[] results = new long[2];
		while (i < nElems) {
			if (a[i].getState().equals(target))
				break;
			i++;
			try {
				population = a[i].getPopulation();
				deaths = a[i].getCovidDeath();
			} catch(ArrayIndexOutOfBoundsException e) {}
		}
		if (i == nElems) {
			population = 0;
			deaths = 0;
		}
		results[0] = population;
		results[1] = deaths;
		return results;
	}
	
}