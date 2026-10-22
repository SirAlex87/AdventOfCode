package service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServiceTest1 {

	private static final Logger logger = LogManager.getLogger(ServiceTest1.class);
	
	private static final int DIALSTART=50;
	private int count=0;
	private int countTot=0;
	private static final int POINTING_NUMBER=0;
	private int beforecurrentPointer=DIALSTART;
	private int lastCurrentPointer;
	
	
	public void countPassingToZero(String a) {
		countTot++;
		logger.debug(a);
		int number=Integer.parseInt(a.substring(1));
		number=number%100;
		if (number > 200) {
			logger.debug(number);
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
		if (lastCurrentPointer==POINTING_NUMBER) {
			count++;
		}
		beforecurrentPointer=lastCurrentPointer;
		logger.debug(
			    "ServiceTest1 [pointingNumber={}, beforecurrentPointer={}, lastCurrentPointer={}, count={}]",
			    POINTING_NUMBER,
			    beforecurrentPointer,
			    lastCurrentPointer,
			    count
			);
	}
	
	public void countTimesPassingToZero(String a) {
		logger.debug(a);
		int number=Integer.parseInt(a.substring(1));
		if (number > 200) {
			logger.debug(number);
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
		logger.debug(
			    "ServiceTest1 [pointingNumber={}, beforecurrentPointer={}, lastCurrentPointer={}, count={}]",
			    POINTING_NUMBER,
			    beforecurrentPointer,
			    lastCurrentPointer,
			    count
			);
	}

	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}
}
