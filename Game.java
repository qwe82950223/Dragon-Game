public class Game{
	
	static Adventurers[] adventurers;//array to put adventurers
	static Clerks[] clerks;//array to put clerks
	private static final long START_TIME = System.currentTimeMillis();
	static int num_adventurer;
	
	protected static final long age() {
		return System.currentTimeMillis() - START_TIME;
	}
	
	public static void main(String[] args){
		
		num_adventurer=Integer.parseInt(args[0]);
		int fortune_size=Integer.parseInt(args[1]);
		int num_clerk=Integer.parseInt(args[2]);
		
		
		adventurers=new Adventurers[num_adventurer];
		clerks=new Clerks[num_clerk];
		
		for(int i=0;i<num_adventurer;i++){
			adventurers[i]=new Adventurers(i,fortune_size);//create thread for each adventurer
		}//end for
		
		for(int i=0;i<num_clerk;i++){
			clerks[i]=new Clerks(i,num_adventurer);//create thread for each clerks
		}
		
		Dragon dragon = new Dragon();//create thread for dragon
		dragon.start();
		
		for(int i=0;i<num_clerk;i++){
			clerks[i].start();//start all clerks thread
		}
		
		for(int i=0;i<num_adventurer;i++){
			adventurers[i].start();//start all adventurer thread
		}//end for
		
		
	}//end main
	
	public static boolean isAlive(int num){//check the adventurer is alive or not
		if(num==num_adventurer){//if it is the last adventure don't have to wait
			return false;
		}
		return adventurers[num].t.isAlive();
	}
	
}//end DatingGame class