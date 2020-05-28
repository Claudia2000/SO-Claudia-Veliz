package parcial_ciclico; //poder modificar la cantidad de filosofos

import java.util.concurrent.Semaphore;

public class P02 extends Thread {// extension de threads
	Semaphore semaforo;
	public P02(Semaphore s1) { //constructor
		semaforo=s1;
	}
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
	public void comer() { //para que el filosofo coma

		System.out.println(this.getName()+" esta comiendo...");
		
	}
	public void pensar() { //para que el filosofo piense

		System.out.println(this.getName()+" esta pensando...");
		
	}
	public static void main(String[] args)throws InterruptedException {
		
		int N = 5;
		Semaphore s1= new Semaphore(1);
		for(int i =1;i<=5;i++) {
			new P02(s1).start();//inicializacion de threds
		}

	}

}