package parcial_ciclico;// crear los threads, la cantidad de filosofos es estática

import java.util.concurrent.Semaphore;

public class P01 extends Thread {// estension de threads
	Semaphore semaforo;
	public P01(Semaphore s1) { //constructor
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
		
		Semaphore s1= new Semaphore(1); //siempre inicia en 1
		for(int i =1;i<=3;i++) {
			//inicializacion de threds			
			new P01(s1).start(); //aquí usa el semáforo
			//N2--;
			//new Filosofo().start();
		}
	}
}