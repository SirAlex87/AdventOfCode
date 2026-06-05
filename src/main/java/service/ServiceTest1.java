package service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServiceTest1 {
	private static Logger logger = LogManager.getLogger(ServiceTest1.class);
	
	private static final int dialStart=50;
	private static int count=0;
	private static int countTot=0;
	private final int pointingNumber=0;
	private static int beforecurrentPointer=dialStart;
	private static int lastCurrentPointer;
	
	
	public void countPassingToZero(String a) {
		countTot++;
		logger.info(a);
		int number=Integer.parseInt(a.substring(1));
		number=number%100;
		if (number > 200) {
			logger.info(number);
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
		logger.info(this.toString());
		//System.out.println(countTot);
	}
	
	public void countTimesPassingToZero(String a) {
		logger.info(a);
		String direction=a.substring(0, 1);
		int number=Integer.parseInt(a.substring(1));
		if (number > 200) {
			logger.info(number);
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
		logger.info(this.toString());
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
