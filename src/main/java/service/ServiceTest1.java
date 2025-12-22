package service;

public class ServiceTest1 {
	private final static int dialStart=50;
	private static int count=0;
	private static int countTot=0;
	private final int pointingNumber=0;
	private static int beforecurrentPointer=dialStart;
	private static int lastCurrentPointer;
	
	
	public void countPassingToZero(String a) {
		countTot++;
		System.out.println(a);
		String direction=a.substring(0, 1);
		int number=Integer.parseInt(a.substring(1));
		number=number%100;
		if (number > 200) {
			System.out.println(number);
		}
		if (a.substring(0, 1).equals("L")){
			
			lastCurrentPointer = beforecurrentPointer-number;
			if (lastCurrentPointer<0) {
				lastCurrentPointer=100+lastCurrentPointer;				
			}			
		}
		else {
			lastCurrentPointer = beforecurrentPointer+number;
			if (lastCurrentPointer>99) {
				lastCurrentPointer=lastCurrentPointer%100;
			}
		}
		if (lastCurrentPointer==pointingNumber) {
			count++;
		}
		beforecurrentPointer=lastCurrentPointer;
		System.out.println(this.toString());
		//System.out.println(countTot);
	}
	
	public void countTimesPassingToZero(String a) {
		System.out.println(a);
		String direction=a.substring(0, 1);
		int number=Integer.parseInt(a.substring(1));
		if (number > 200) {
			System.out.println(number);
		}
		countTot=number/100;
		number=number%100;		
		if (a.substring(0, 1).equals("L")){
			
			lastCurrentPointer = beforecurrentPointer-number;
			if (lastCurrentPointer<=0) {
				lastCurrentPointer=(100+lastCurrentPointer)%100;
				if(beforecurrentPointer!=0)
					count++;
			}			
		}
		else {
			lastCurrentPointer = beforecurrentPointer+number;
			if (lastCurrentPointer>=100) {
				lastCurrentPointer=lastCurrentPointer%100;
				if(beforecurrentPointer!=0)
					count++;
			}
		}
		
		count=count+countTot;
		beforecurrentPointer=lastCurrentPointer;
		System.out.println(this.toString());
		//System.out.println(countTot);
	}


	public static int getCount() {
		return count;
	}


	public static void setCount(int count) {
		ServiceTest1.count = count;
	}


	@Override
	public String toString() {
		return "ServiceTest1 [pointingNumber=" + pointingNumber + "],"
				+"beforecurrentPointer=" + beforecurrentPointer + "],"
				+"lastCurrentPointer=" + lastCurrentPointer + "],"
				+"count=" + count + "]";
	}
	
	
	
	

}
