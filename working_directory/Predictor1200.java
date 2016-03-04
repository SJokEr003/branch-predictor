
public class Predictor1200 extends Predictor {
	
	// pap predictor
//	private class Pap{
//		private Table pht, ght;
//		private int n = 8,k = 2,n1 = 8,satcs = 2;
//		
//		public Pap() {
//			pht = new Table(1<<(Math.max(k,n)),satcs);
//			ght = new Table(1<<n1,k);
//		}
//
//		// private int combine()
//
//		private int getIndPht(long address){
//			int lastn = ((int)address) & ((1<<n)-1);
//			int lastn1 = ((int)address) & ((1<<n1)-1);
//			int ghtval = ght.getInteger(lastn1,0,k-1);
//			int ind = (lastn ^ ghtval);
//			return ind;
//		}
//
//		private int getIndGht(long address){
//			int lastn1 = ((int)address) & ((1<<n1)-1);
//			return lastn1;
//		}
//
//		public void Train(long address, boolean outcome, boolean predict) {
//			int ind = getIndPht(address);
//			int entry = pht.getInteger(ind,0,satcs-1);
//			if (outcome) entry++;
//			else entry--;
//			entry = Math.min((1<<satcs)-1,entry);
//			entry = Math.max(0,entry);
//			pht.setInteger(ind,0,satcs-1,entry);
//
//			int ghrind = getIndGht(address);
//			int ghr = ght.getInteger(ghrind,0,k-1);
//			ghr <<= 1;
//			ghr |= (outcome ? 1 : 0);
//			ghr = ghr & ((1<<k)-1);
//
//			ght.setInteger(ghrind,0,k-1,ghr);
//		}
//
//		public boolean predict(long address){
//			int ind = getIndPht(address);
//			int entry = pht.getInteger(ind,0,satcs-1);
//			return (entry >= (1<<(satcs-1)));
//		}
//	}
//	
//	// pap predictor
//	private class Gap{
//		private Table pht;
//		private Register ghr;
//		private int n = 8,k = 2,satcs = 2;
//		
//		public Gap() {
//			pht = new Table(1<<(n+k),satcs);
//			ghr = new Register(k);
//		}
//
//		private int getIndPht(long address){
//			int lastn = ((int)address) & ((1<<n)-1);
//			int ghrVal = ghr.getInteger(0, k-1);
//			int ind = (lastn<<k) + ghrVal;
//			return ind;
//		}
//
//		public void Train(long address, boolean outcome, boolean predict) {
//			int ind = getIndPht(address);
//			int entry = pht.getInteger(ind,0,satcs-1);
//			if (outcome) entry++;
//			else entry--;
//			entry = Math.min((1<<satcs)-1,entry);
//			entry = Math.max(0,entry);
//			pht.setInteger(ind,0,satcs-1,entry);
//
//			int ghrVal = ghr.getInteger(0, k-1);
//			ghrVal<<= 1;
//			ghrVal |= (outcome ? 1 : 0);
//			ghrVal = ghrVal & ((1<<k)-1);
//
//			ghr.setInteger(0, k-1, ghrVal);
//		}
//
//		public boolean predict(long address){
//			int ind = getIndPht(address);
//			int entry = pht.getInteger(ind,0,satcs-1);
//			return (entry >= (1<<(satcs-1)));
//		}
//	}
	
	private Table pht, ght;
	private int n = 8,k = 2,n1 = 8,satcs = 2;
	
	public Predictor1200() {
		pht = new Table(1<<(Math.max(k,n)),satcs);
		ght = new Table(1<<n1,k);
	}

	// private int combine()

	private int getIndPht(long address){
		int lastn = ((int)address) & ((1<<n)-1);
		int lastn1 = ((int)address) & ((1<<n1)-1);
		int ghtval = ght.getInteger(lastn1,0,k-1);
		int ind = (lastn ^ ghtval);
		return ind;
	}

	private int getIndGht(long address){
		int lastn1 = ((int)address) & ((1<<n1)-1);
		return lastn1;
	}

	public void Train(long address, boolean outcome, boolean predict) {
		int ind = getIndPht(address);
		int entry = pht.getInteger(ind,0,satcs-1);
		if (outcome) entry++;
		else entry--;
		entry = Math.min((1<<satcs)-1,entry);
		entry = Math.max(0,entry);
		pht.setInteger(ind,0,satcs-1,entry);

		int ghrind = getIndGht(address);
		int ghr = ght.getInteger(ghrind,0,k-1);
		ghr <<= 1;
		ghr |= (outcome ? 1 : 0);
		ghr = ghr & ((1<<k)-1);

		ght.setInteger(ghrind,0,k-1,ghr);
	}

	public boolean predict(long address){
		int ind = getIndPht(address);
		int entry = pht.getInteger(ind,0,satcs-1);
		return (entry >= (1<<(satcs-1)));
	}

}


// public class Predictor1200 extends Predictor {
	
// 	private Table tb;
// 	private Register reg;

// 	int n=4,k=3, satCout=3, dis=2;

// 	public Predictor1200() {
// 		tb = new Table( 1<<(n+k) , dis+satCout );
// 		reg = new Register(k);
// 	}

// 	private int getInd(long address){
// 		int ind = ((int)address) & ((1<<n)-1);
// 		ind <<= k;
// 		ind |= reg.getInteger(0, k-1);
// 		return ind;
// 	}

// 	public void Train(long address, boolean outcome, boolean predict) {
// 		int ind = getInd(address);
// 		int entry = tb.getInteger(ind,0,satCout-1);
		
// 		// Update Shift register
// 		int currVal = reg.getInteger(0,k-1);
// 		currVal=((outcome==true?1:0) + (currVal<<1))&((1<<k)-1);
// 		reg.setInteger(0,k-1,currVal);

// 		// update table entry
// 		int dInt = (((int)address>>n)&((1<<dis)-1));
// 		if (outcome) { 
// 			int oldDisInt = tb.getInteger(ind,satCout,satCout+dis-1);
// 			if (oldDisInt==dInt) {
// 				if (outcome){
// 					entry++;
// 				}
// 				else{
// 					entry--;
// 				}
// 				entry = Math.min( (1<<satCout) - 1,entry);
// 				entry = Math.max(0,entry);
// 				tb.setInteger(ind,0,satCout-1, entry );
// 			}else{
// 				tb.setInteger(ind,0,satCout-1, (1<<(satCout-1)) );
// 			}
// 			tb.setInteger(ind,satCout,satCout+dis-1,dInt);
// 		}else{
// 			// tb.setInteger(ind,satCout,satCout+dis-1, 0 ); // doubt ????
// 			// tb.setInteger(ind,satCout,satCout+dis-1,dInt);
// 			tb.setInteger(ind,0,satCout-1, (1<<(satCout-1))-1 );
// 		}
// 	}

// 	public boolean predict(long address){
// 		int ind = getInd(address);
// 		int entry = tb.getInteger(ind,0,satCout-1);
// 		int dBits = (((int)address>>n)&((1<<dis)-1));
// 		return (tb.getInteger(ind,satCout,satCout+dis-1)==dBits && entry >= (1<<(satCout-1)) );
// 	}

// }