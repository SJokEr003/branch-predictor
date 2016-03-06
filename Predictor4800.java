import java.util.Random;

public class Predictor4800 extends Predictor {
	
	// // pap predictor
	private Table pap_pht, pap_ght;
	private int pap_n ,pap_k,pap_n1 ,pap_satcs;
	
	public static int randInt(int Min, int Max) {
	    return Min + (int)(Math.random() * ((Max - Min) + 1));
	}

	private void pap_init(){
		pap_n=4;
		pap_k=6;
		pap_n1=8;
		pap_satcs = 3;
		pap_pht = new Table(1<<(pap_k+pap_n),pap_satcs);
		pap_ght = new Table(1<<pap_n1,pap_k);
		for (int i=0; i<(1<<(pap_k+pap_n)); i++) {
			pap_pht.setInteger(i,0,pap_satcs-1,randInt(0,(1<<pap_satcs)-1)); //(1<<pap_satcs)/2);
		}
	}

	private int pap_getIndPht(long address){
		int lastn = ((int)address) & ((1<<pap_n)-1);
		int lastn1 = ((int)address) & ((1<<pap_n1)-1);
		int ghtval = pap_ght.getInteger(lastn1,0,pap_k-1);
		int ind = ((lastn<<pap_k) +  ghtval);
		return ind;
	}

	private int pap_getIndGht(long address){
		int lastn1 = ((int)address) & ((1<<pap_n1)-1);
		return lastn1;
	}

	public void pap_Train(long address, boolean outcome, boolean predict) {
		int ind = pap_getIndPht(address);
		int entry = pap_pht.getInteger(ind,0,pap_satcs-1);
		if (outcome) entry++;
		else entry--;
		entry = Math.min((1<<pap_satcs)-1,entry);
		entry = Math.max(0,entry);
		pap_pht.setInteger(ind,0,pap_satcs-1,entry);

		int ghrind = pap_getIndGht(address);
		int ghr = pap_ght.getInteger(ghrind,0,pap_k-1);
		ghr >>= 1;
		ghr += (outcome ? (1<<(pap_k-1)) : 0);

		pap_ght.setInteger(ghrind,0,pap_k-1,ghr);
	}

	public boolean pap_predict(long address){
		int ind = pap_getIndPht(address);
		return pap_pht.getBit(ind, 0);
	}
	
	
	/////////////
	private Table pap_share_pht, pap_share_ght;
	private int pap_share_n ,pap_share_k,pap_share_n1 ,pap_share_satcs;

	private void pap_share_init(){
		pap_share_n=10;
		pap_share_k=4;
		pap_share_n1=6;
		pap_share_satcs = 2;
		pap_share_pht = new Table(1<<Math.max(pap_share_n, pap_share_k),pap_share_satcs);
		pap_share_ght = new Table(1<<pap_share_n1,pap_share_k);
		for (int i=0; i<(1<<Math.max(pap_share_n, pap_share_k)); i++) {
			pap_share_pht.setInteger(i,0,pap_share_satcs-1,randInt(0,(1<<pap_share_satcs)-1)); //(1<<pap_satcs)/2);
		}
	}

	private int pap_share_getIndPht(long address){
		int lastn = ((int)address) & ((1<<pap_share_n)-1);
		int lastn1 = ((int)address) & ((1<<pap_share_n1)-1);
		int ghtval = pap_share_ght.getInteger(lastn1,0,pap_share_k-1);
		int ind = (lastn ^  ghtval);
		return ind;
	}

	private int pap_share_getIndGht(long address){
		int lastn1 = ((int)address) & ((1<<pap_share_n1)-1);
		return lastn1;
	}

	public void pap_share_Train(long address, boolean outcome, boolean predict) {
		int ind = pap_share_getIndPht(address);
		int entry = pap_share_pht.getInteger(ind,0,pap_share_satcs-1);
		if (outcome) entry++;
		else entry--;
		entry = Math.min((1<<pap_share_satcs)-1,entry);
		entry = Math.max(0,entry);
		pap_share_pht.setInteger(ind,0,pap_share_satcs-1,entry);

		int ghrind = pap_share_getIndGht(address);
		int ghr = pap_share_ght.getInteger(ghrind,0,pap_share_k-1);
		ghr >>= 1;
		ghr += (outcome ? (1<<(pap_share_k-1)) : 0);

		pap_share_ght.setInteger(ghrind,0,pap_share_k-1,ghr);
	}

	public boolean pap_share_predict(long address){
		int ind = pap_share_getIndPht(address);
		return pap_share_pht.getBit(ind, 0);
	}
	
	//Gap predictor
	// private class Gap{
	// 	private Table pht;
	// 	private Register ghr;
	// 	private int n ,k,satcs;
		
	// 	public Gap(int _n,int _k,int _satcs) {
	// 		n=_n;
	// 		k=_k;
	// 		satcs = _satcs;
	// 		pht = new Table(1<<(n+k),satcs);
	// 		ghr = new Register(k);
	// 	}

	// 	private int getIndPht(long address){
	// 		int lastn = ((int)address) & ((1<<n)-1);
	// 		int ghrVal = ghr.getInteger(0, k-1);
	// 		int ind = (lastn<<k) + ghrVal;
	// 		return ind;
	// 	}

	// 	public void Train(long address, boolean outcome, boolean predict) {
	// 		int ind = getIndPht(address);
	// 		int entry = pht.getInteger(ind,0,satcs-1);
	// 		if (outcome) entry++;
	// 		else entry--;
	// 		entry = Math.min((1<<satcs)-1,entry);
	// 		entry = Math.max(0,entry);
	// 		pht.setInteger(ind,0,satcs-1,entry);

	// 		int ghrVal = ghr.getInteger(0, k-1);
	// 		ghrVal<<= 1;
	// 		ghrVal |= (outcome ? 1 : 0);
	// 		ghrVal = ghrVal & ((1<<k)-1);

	// 		ghr.setInteger(0, k-1, ghrVal);
	// 	}

	// 	public boolean predict(long address){
	// 		int ind = getIndPht(address);
	// 		int entry = pht.getInteger(ind,0,satcs-1);
	// 		return (entry >= (1<<(satcs-1)));
	// 	}
	// }
	
	// Pag predictor
	// private class Pag{
	// 	private Table pht,ght;
	// 	private int n ,k,satcs;
		
	// 	public Pag(int _n,int _k,int _satcs) {
	// 		n=_n;
	// 		k=_k;
	// 		satcs = _satcs;
	// 		pht = new Table(1<<k,satcs);
	// 		ght = new Table(1<<n,k);
	// 	}

	// 	private int getIndPht(long address){
	// 		int lastn = ((int)address) & ((1<<n)-1);
	// 		int ind = ght.getInteger(lastn, 0, k-1);
	// 		return ind;
	// 	}
		
	// 	private int getIndGht(long address){
	// 		int ind = ((int)address) & ((1<<n)-1);
	// 		return ind;
	// 	}

	// 	public void Train(long address, boolean outcome, boolean predict) {
	// 		int ind = getIndPht(address);
	// 		int entry = pht.getInteger(ind,0,satcs-1);
	// 		if (outcome) entry++;
	// 		else entry--;
	// 		entry = Math.min((1<<satcs)-1,entry);
	// 		entry = Math.max(0,entry);
	// 		pht.setInteger(ind,0,satcs-1,entry);

	// 		ind = getIndGht(address);
	// 		int ghrVal = ght.getInteger(ind, 0, k-1);
	// 		ghrVal<<= 1;
	// 		ghrVal |= (outcome ? 1 : 0);
	// 		ghrVal = ghrVal & ((1<<k)-1);

	// 		ght.setInteger(ind, 0, k-1, ghrVal);
	// 	}

	// 	public boolean predict(long address){
	// 		int ind = getIndPht(address);
	// 		int entry = pht.getInteger(ind,0,satcs-1);
	// 		return (entry >= (1<<(satcs-1)));
	// 	}
	// }


//  We are using only Pap predictor here since it was giving maximum accuracy.

//	Table chooser;
//	int n = 7;
	boolean r1=true,r2=false;
	
	public Predictor4800() {
		pap_init();
//		int n,n1,// parameters
//		chooser = new Table(1<<n, 1);
		//n ,k,n1 ,satcs;
	}
	
//	private int getInd(long address){
//		int ind = ((int)address) & ((1<<n)-1);
//		return ind;
//	}

	public void Train(long address, boolean outcome, boolean predict) {
		pap_Train(address, outcome, predict);
//		pap_Train(address, outcome, predict);
//		int ind = getInd(address);
//		if(outcome==r1 ||  outcome!=r2 || true){
//			chooser.setInteger(ind, 0, 0, 0);
//		}
//		else{
//			chooser.setInteger(ind, 0, 0, 1);
//		}
	}


	public boolean predict(long address){
		return pap_predict(address);
//		r2  = pap_predict(address);
		
//		int ind = getInd(address);
//		if( chooser.getInteger(ind, 0, 0)==0 || true){
//			return r1;
//		}
//		else{
////			return r2;
//		}
//		return true;
		
	}

}