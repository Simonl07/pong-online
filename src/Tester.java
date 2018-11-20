import client.util.AverageRateOfChangeQueue;

public class Tester {

	public static void main(String args[]){
		
		
		AverageRateOfChangeQueue<Integer> a = new AverageRateOfChangeQueue<>(3);
		
		a.add(3);
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		a.add(5);
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		a.add(6);
		System.out.println(a);
		
		System.out.println(a.getValueROC());	
		System.out.println(a.getEntryFrequency(10));
	}
}
