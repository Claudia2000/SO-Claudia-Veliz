package parcial_ciclico;// creación del estómago

import java.util.concurrent.Semaphore;

public class P_03 extends Thread {// estension de threads
	int estomago; //Creacion del estómago
	Semaphore semaforo;
	public P_03(Semaphore s1) { //constructor
		this.estomago=100; 
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

		System.out.println(this.getName()+" esta comiendo... y su estomago esta en"+this.estomago);//imprime en cuanto está el estómago cuando come
		
	}
	public void pensar() { //para que el filosofo piense

		System.out.println(this.getName()+" esta pensando... y su estomago esta en"+this.estomago);//imprime en cuanto está el estómago cuando piensa
		
	}
	public static void main(String[] args)throws InterruptedException {
		
		int N = 5;
		Semaphore s1= new Semaphore(1);
		for(int i =1;i<=5;i++) {
			new P_03(s1).start();//inicializacion de threds
		}

	}

}
