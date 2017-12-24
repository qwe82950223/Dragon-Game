import java.util.Random;
import java.util.Vector;

public class Clerks implements Runnable{
	
	static Vector<Adventurers> adventurers_line = new Vector<Adventurers>();//linked list for adventurer
	static Adventurers[] clerks = new Adventurers[2];//current adventurer that this clerk is serving
	static int adven_gohome=0;//how many adventurer has went home 
	int num_adventurer;//number of adventurer
	private int clerk_num;//clerk id
	private Random r = new Random();
	int random_time;
	Thread t;
	
	public Clerks(int clerk_num,int num_adventurer){
		this.clerk_num=clerk_num;
		this.num_adventurer=num_adventurer;
		random_time=r.nextInt(999);
		t = new Thread(this);
	}
	
	public static long time = System.currentTimeMillis();
	public void msg(String m){
		System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+": "+m);
	}
	
	private String getName() {
		// TODO Auto-generated method stub
		return t.getName();
	}
	
	public void run(){
		while(adven_gohome!=num_adventurer){//when there is still adventurer no go home 
			if(check(clerk_num)){//check is adventurer here then serve them
				waiting(200);
				clerks[clerk_num].combine();//combine stone
				clerks[clerk_num].need_assistance=false;
			}
		}
		Dragon.finish=true;//let dragon know it is finish
	}
	
	public void start() {
		t.start();
		System.out.println("clerk"+clerk_num+" is in shop.");
	}//end start
	
	public void waiting(int time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}//end waiting
	
	public static synchronized void lineUp_fortune(Adventurers adventurer){//adventurer get to the line to get serve
		adventurers_line.add(adventurer);
		System.out.println("adventurer"+adventurer.adventurer_num+" is in line.");
	}//end linUp_fortune
	
	public static synchronized boolean check(int number){//check is adventurer here then serve them,and only one clerk can do it at a time
		if(!adventurers_line.isEmpty()){
			clerks[number]=adventurers_line.remove(0);//serve the clerk
			System.out.println("adventurer"+clerks[number].adventurer_num+" go to clerk.");
			return true;
		}
		return false;
	}//end check
	
	public static synchronized void goHome(){//count how many adventurer finished
		adven_gohome++;
	}//end goHome
}