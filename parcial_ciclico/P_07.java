package parcial_ciclico; //notificar si el filosofo muere de hambre l65
import java.util.Random;
import java.util.concurrent.Semaphore;

@SuppressWarnings("unused")
public class P_07 extends Thread {// estension de threads
	int estomago; //Creacion del estómago
	Semaphore semaforo;
	public P_07(Semaphore s1) { //constructor
		this.estomago=100; 
		semaforo=s1;
	}
	@SuppressWarnings({ "static-access", "deprecation" })
	public void run() { //dar paso a los procesos de comer y pensar
		while(true) {
			try {
				semaforo.acquire();
			comer();
			if(this.estomago>100) {
				System.out.println(this.getName()+" tiene el estomago lleno");
				pensar();
				//this.stop();
			}
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
		//if(this.estomago>100) {
			//System.out.println(this.getName()+" tiene el estomago lleno");
		//}
		//else {
			int numAleatorio=(int)Math.floor(Math.random()*(2000-1000)+1000); //cantidad aleatoria de tiempo para comer
			try {
				this.sleep(numAleatorio);
			}
			catch( InterruptedException e) {
				e.printStackTrace();
				System.out.println("se produjo un error");
			}
			this.estomago+=25; //Cada vez que come aummenta en 25 su estómago
			System.out.println(this.getName()+" esta comiendo... y su estomago esta en "+this.estomago);//imprime en cuanto está el estómago cuando come
		//}
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
		System.out.println(this.getName()+" esta pensando... y su estomago esta en "+this.estomago);//imprime en cuanto está el estómago cuando piensa
		if(this.estomago<=100 && numAleatorio>1050) { //verifica que el estómago sea menor e igual a 100 y el tiempo sea mayor a 1050, en ese caso se muere de hambre
			System.out.println(this.getName()+" he muerto de hambre");
		}
		
	}
	public static void main(String[] args)throws InterruptedException {
		
		int N = 5;
		Semaphore s1= new Semaphore(1);
		for(int i =1;i<=N;i++) {
			new P_07(s1).start();//inicializacion de threds
		}

	}

}
