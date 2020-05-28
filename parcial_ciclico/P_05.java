package parcial_ciclico; //random para comer e incrementar su estómago
import java.util.Random;
import java.util.concurrent.Semaphore;

@SuppressWarnings("unused")
public class P_05 extends Thread {// estension de threads
	int estomago; //Creacion del estómago
	Semaphore semaforo;
	public P_05(Semaphore s1) { //constructor
		this.estomago=100; 
		semaforo=s1;
	}

	@SuppressWarnings("static-access")
	public void run() { //dar paso a los procesos de comer y pensar
		while(true) {
			try {
				semaforo.acquire();
			comer();
				this.sleep(1000);
			}
			catch( InterruptedException e) {
				e.printStackTrace();
				System.out.println("se produjo un error");
			}
			pensar();
			semaforo.release();
	    }
	}
	@SuppressWarnings("static-access")
	public void comer() { //para que el filosofo coma
		int numAleatorio=(int)Math.floor(Math.random()*(2000-1000)+1000); //cantidad aleatoria de tiempo para comer
		try {
			this.sleep(numAleatorio);
		}
		catch( InterruptedException e) {
			e.printStackTrace();
			System.out.println("se produjo un error");
		}
		this.estomago+=25; //Cada vez que come aummenta en 25 su estómago
		System.out.println(this.getName()+" esta comiendo... y su estomago esta en"+this.estomago);//imprime en cuanto está el estómago cuando come
		
	}
	@SuppressWarnings("static-access")
	public void pensar() { //para que el filosofo piense
		int numAleatorio=(int)Math.floor(Math.random()*(2000-1000)+1000); //cantidad aleatoria de tiempo para pensar
		try {
			this.sleep(numAleatorio);
		}
		catch( InterruptedException e) {
			e.printStackTrace();
			System.out.println("se produjo un error");
		}
		this.estomago-=25; ////Cada vez que piensa disminuye en 25 su estómago
		System.out.println(this.getName()+" esta pensando... y su estomago esta en"+this.estomago);//imprime en cuanto está el estómago cuando piensa
		
	}
	public static void main(String[] args)throws InterruptedException {
		
		int N = 5;
		Semaphore s1= new Semaphore(1);
		for(int i =1;i<=5;i++) {
			new P_05(s1).start();//inicializacion de threds
		}

	}

}