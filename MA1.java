import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class MA1 {

	public static void main(String[] args) throws FileNotFoundException {
		
		//Setup input
		File f = new File("input.txt");
		Scanner s = new Scanner(f);
		
		//Storing houses of each size separately
		int houses = s.nextInt();
		ArrayList<Integer> h_1 = new ArrayList<Integer>();
		ArrayList<Integer> h_2 = new ArrayList<Integer>();
		ArrayList<Integer> h_3 = new ArrayList<Integer>();
		
		int[] houseList = new int[houses];		//for calculating cost later
		int[] sizeList = new int[houses];		//for calculating cost later
		
		//ArrayList Population
		int currentHouseY, currentSize;
		for(int x = 0; x < houses; x ++) {
			//BEGIN FOR
			currentHouseY 	= s.nextInt();
			currentSize 	= s.nextInt();
			
			houseList[x] = currentHouseY;
			sizeList[x] = currentSize;
			
			switch(currentSize) {
				case 1:
					h_1.add(currentHouseY);
					break;
				case 2:
					h_2.add(currentHouseY);
					break;
				case 3:
					h_3.add(currentHouseY);
					break;
			}
			
			//END FOR
		}
		h_1 = quicksort(h_1);
		h_2 = quicksort(h_2);
		h_3 = quicksort(h_3);
		
		int h1_Optimal = getOptimalY(h_1);
		int h2_Optimal = getOptimalY(h_2);
		int h3_Optimal = getOptimalY(h_3);

		
		int h1_cost = calculateCost(h1_Optimal,houseList,sizeList);
		int h2_cost = calculateCost(h2_Optimal,houseList,sizeList);
		int h3_cost = calculateCost(h3_Optimal,houseList,sizeList);
		
		//COMPARE RESULTS AND PRINT FINAL Y LOCATION 
		//RESULT = LOWEST COST OUT OF THE THREE POSSIBLE OPTIMAL Y LOCATIONS FOR EACH HOUSE SIZE
		if(h1_cost > h2_cost) {
			if(h2_cost > h3_cost)
				System.out.println(h3_Optimal);
			else
				System.out.println(h2_Optimal);
		}
		else if(h3_cost>h1_cost) {
			System.out.println(h1_Optimal);
		}
		else
			System.out.println(h3_Optimal);
			
		s.close(); // :)
	}
	public static ArrayList<Integer> quicksort(ArrayList<Integer> input){
		
		if(input.size() <= 1){
			return input;
		}
		
		int middle = (int) Math.ceil((double)input.size() / 2);
		int pivot = input.get(middle);

		ArrayList<Integer> less = new ArrayList<Integer>();
		ArrayList<Integer> greater = new ArrayList<Integer>();
		
		for (int i = 0; i < input.size(); i++) {
			if(input.get(i) <= pivot){
				if(i == middle){
					continue;
				}
				less.add(input.get(i));
			}
			else{
				greater.add(input.get(i));
			}
		}
		
		return concatenate(quicksort(less), pivot, quicksort(greater));
	}
	
	public static ArrayList<Integer> concatenate(ArrayList<Integer> less, int pivot, ArrayList<Integer> greater){
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		for (int i = 0; i < less.size(); i++) {
			list.add(less.get(i));
		}
		
		list.add(pivot);
		
		for (int i = 0; i < greater.size(); i++) {
			list.add(greater.get(i));
		}
		
		return list;
	}

	public static int getOptimalY(ArrayList<Integer> list){

		//OPTIMAL POWER LINE Y LOCATION 
		int n = list.size();
		if(n==0)
			return 0;
		if(n==1)
			return list.get(0);
		if(n%2 == 0)
		{
			int middleH = list.get(n/2);
			int middleL = list.get(n/2-1);
			
			if((middleH+middleL)%2==0)
				return (middleH+middleL)/2;		//Middle two mean
			else
				return ((middleH+middleL)/2)+1;	//Middle two mean upper bound
		}
		return list.get((n/2)); //Middle
	}
	
	public static int calculateCost(int y, int[] houseList, int[] sizeList){
		int total = 0;
		
		for(int x = 0; x < houseList.length ; x++){
			
			int dif = Math.abs(y-houseList[x]);	
			total+= dif*sizeList[x];
		}
		return total;
	}
	
}
