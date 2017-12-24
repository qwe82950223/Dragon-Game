import java.util.Random;

public class Adventurers implements Runnable{
	
	int adventurer_num;//adventurer id
	int ring_num=0;//ring that this adventurer has
	int earring_num=0;//earring that this adventurer has
	int necklace_num=0;//necklace that this adventurer has
	int magic_ring=0;
	int magic_earring=0;
	int magic_necklace=0;
	int fortune_size;//how many fortune they have 
	int stone_num=0;//how many stone they have 
	int current_fortune=0;
	boolean lineUp=false;
	boolean need_assistance=true;//Does he combine the magic fortune
	boolean getStone=false;//does he get the magic stone
	boolean fightAgain=false;
	boolean checkstatue=false;
	private Random r = new Random();
	int time;
	Thread t;
	
	public Adventurers(int adventurer_num,int fortune_size){
		this.adventurer_num=adventurer_num;
		this.fortune_size=fortune_size;
		
		time=r.nextInt(999);
		t = new Thread(this);
	}
	
	
	public void run(){
		waiting(time);
		getFortune();//randomly get fortune
		
		
		while(current_fortune!=fortune_size){
			System.out.println("adventurer"+adventurer_num+" go to dragon.");
			Dragon.lineUp_dragon(this);//waiting for dragon until get interrupt by use sleep 999999
		
			while(getStone==false){//check does he get the stone
				if(fightAgain==true){
					Thread.yield();//wait for other adventurer go first
					Dragon.lineUp_dragon(this);
					fightAgain=false;
				}
				if(lineUp==true){//go back to line to fight dragon
					Dragon.lineUp_dragon(this);
					lineUp=false;
				}
				if(checkstatue==true){//check is ok to go to shop
					checkStatus();
					checkstatue=false;
				}
				waiting(1);
			}
			getStone=false;
		
			System.out.println("adventurer"+adventurer_num+" go to shop.");
		
			waiting(r.nextInt(300));//in the way to the shop
		
			Clerks.lineUp_fortune(this);//waiting to combine fortune
		
			while(need_assistance==true){
				waiting(1);
			}
			need_assistance=true;
			
		}
		
		while(Game.isAlive(adventurer_num+1)){//check his next adventurer is still alive 
			waiting(1);
		}
		
		System.out.println("adventurer"+adventurer_num+" go home, and get "+magic_ring+" magic ring, "+magic_necklace+" magic necklace and "+magic_earring+" magic ear ring.");
		
		Clerks.goHome();//let clerk know he is finish
		
	}
	
	public void start() {
		t.start();
	}
	
	public void waiting(int time){//sleep for some time
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Thread.currentThread().interrupt();
		}
	}//end waiting
	
	public void getFortune(){//adventurers get the random number of fortune 
			int select;
			select = r.nextInt(4);
			ring_num=select;
			select = r.nextInt(4);
			earring_num=select;
			select =r.nextInt(4);
			necklace_num=select;
			
		System.out.println("adventurer"+adventurer_num+" get "+ring_num+" ring, "+earring_num+" earring and "+necklace_num+" necklace.");
	}//end getFortune


	public void checkStatus() {//check is the stone enough for combine
		if((ring_num>=1||necklace_num>=1)&&(stone_num>=1)){
			getStone=true;
		}
		else if(stone_num>=2&&earring_num>=2){
			getStone=true;
		}
		else{//if not ,continue to fight dragon
			lineUp=true;
		}
	}//end checkStatus
	
	public void fightAgain(){//fight the dragon again if lose
		System.out.println("adventurer"+adventurer_num+" fight again.");
		if(Dragon.win()){//if adventurer win
			int item=Dragon.selectItem(this);
			System.out.println("adventurer"+adventurer_num+" get "+Dragon.items[item]+".");
			checkstatue = true;//to check is ok to go to shop
		}
		else{
			System.out.println("adventurer"+adventurer_num+" lose.");
			fightAgain=true;//to let other adventurer go first and line up again
		}
	}//end fightAgain

	
	public void combine(){//randomly combine the fortune
		if(earring_num>=2&&stone_num>=2){//combine magic earring and destroy ring and stone
			stone_num--;
			earring_num--;
			earring_num--;
			System.out.println("adventurer"+adventurer_num+" get magic earring.");
			current_fortune++;
			magic_earring++;
			return;
		}
		else if(ring_num>=1&&stone_num>=1){//combine magic ring and destroy necklace and stone
			System.out.println("adventurer"+adventurer_num+" get magic ring.");
			ring_num--;
			stone_num--;
			current_fortune++;
			magic_ring++;
			return;
		}
		else{//combine magic necklace and destroy necklace  and stone
			necklace_num--;
			stone_num--;
			System.out.println("adventurer"+adventurer_num+" get magic necklace.");
			current_fortune++;
			magic_necklace++;
		}
	}//end combine
	
}