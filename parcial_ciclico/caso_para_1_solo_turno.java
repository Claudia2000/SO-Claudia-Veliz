package parcial_ciclico; //creacion de un unico plato
import java.util.Random;
import java.util.concurrent.*; 

@SuppressWarnings("unused")

public class caso_para_1_solo_turno extends Thread {// estension de threads
	int estomago; //Creacion del estómago
	int plato=10;
	Semaphore semaforo;
	public caso_para_1_solo_turno(Semaphore s1) { //constructor
		this.estomago=100; 
		semaforo=s1;
	}

	@SuppressWarnings({ "static-access", "deprecation" })
	public void run() { //dar paso a los procesos de comer y pensar
		while(plato>0) {
			try {
			semaforo.acquire();//permiso, disponibilidad
			comer();
			if(this.estomago>100) {
				System.out.println(this.getName()+" tiene el estomago lleno");
				pensar();
				//this.stop();
			}
			plato--;
				//this.sleep(1000);
			}
			catch( InterruptedException e) {
				e.printStackTrace();
				System.out.println("se produjo un error");
			}
			pensar();
			semaforo.release();//liberacion de permiso
			
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
		if(this.estomago<=100 || numAleatorio>1500) {
			System.out.println(this.getName()+" he muerto de hambre");
		}
		
	}
	
	public static void main(String[] args)throws InterruptedException {
		int N = 5;
		Semaphore s1= new Semaphore(1); //siempre inicia en 1
		for(int i =1;i<=N;i++) {
			//inicializacion de threds			
			new caso_para_1_solo_turno(s1).start(); //aquí usa el semáforo
			//N2--;
			//new Filosofo().start();
		}

	}

}