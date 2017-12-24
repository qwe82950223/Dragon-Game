import java.util.Random;
import java.util.Vector;

public class Dragon implements Runnable{
	
	static Vector<Adventurers> dragon_line = new Vector<Adventurers>();
	Adventurers current_adventurer;//current adventurer that are in fighting with dragon
	static boolean finish = false;//is all adventurer go home
	private static Random r = new Random();
	int time;
	static String[] items = new String[4];
	Thread t;
	
	public Dragon(){
		time=r.nextInt(999);
		t = new Thread(this);
		items[0]="stone";
		items[1]="ring";
		items[2]="chain";
		items[3]="earring";
	}
	
	public void run(){
		while(finish==false){//if there is still adventurer not go home
			if(!dragon_line.isEmpty()){//check is there adventurer waiting for fight
				int r = randomChoose(dragon_line.size());
				current_adventurer=dragon_line.remove(r);//randomly pick adventurer
				current_adventurer.t.interrupt();
				System.out.println("dragon is fight with adventurer"+current_adventurer.adventurer_num);
				waiting(500);
				System.out.println("adventurer"+current_adventurer.adventurer_num+" finish fighting");
				if(win()){//check is dragon win
					int currentItem=selectItem(current_adventurer);
					System.out.println("adventurer"+current_adventurer.adventurer_num+" get "+items[currentItem]+".");
					current_adventurer.checkStatus();//adventurer check does he has enough stone
				}
				else{
					System.out.println("adventurer"+current_adventurer.adventurer_num+" lose");
					
					current_adventurer.fightAgain();
				}
			}
		}
	}
	
	public void start() {
		t.start();

	}//end start
	
	public void waiting(int time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}//end waiting 
	
	public static boolean win(){//one out of two dragon will give stone
		int give = r.nextInt(2);
		if(give==0){
			return true;
		}
		else{
			return false;
		}
	}//end giveStone
	
	public static int selectItem(Adventurers adventurer){//dragon choose the item that give to adventurer
		int n = r.nextInt(4);
		int currentItem=n;
		if(currentItem==0){
			adventurer.stone_num++;
		}
		if(currentItem==1){
			adventurer.ring_num++;
		}
		if(currentItem==2){
			adventurer.earring_num++;
		}
		if(currentItem==0){
			adventurer.necklace_num++;
		}
		return currentItem;
	}
	
	public int randomChoose(int n){//get a random number for adventurer
		return r.nextInt(n);
	}//end randomChoose
	
	public synchronized static void lineUp_dragon(Adventurers adventurer){
		System.out.println("adventurer"+adventurer.adventurer_num+" is in line to fight dragon.");
		dragon_line.add(adventurer);
		adventurer.waiting(99999999);//wait until get interrupt
	}//end lineUp_dragon
}//end class