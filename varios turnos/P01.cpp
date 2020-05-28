//filosofos pensando, se detienen para comer
//creacion de threads y mutex
#include <iostream>
#include <mutex>
#include <thread>
#include <ctime>
#include <chrono>
using namespace std;

const int num_Phil = 5; // num filosofos
thread philosphers[num_Phil]; // Filosofos como threads
mutex mtx[num_Phil]; //tenedores
mutex cout_mutex; 
int ate[num_Phil] = {0}; 
int intrupt = 50; 

void print(string str){ // 
    cout_mutex.lock(); 
    cout <<str<<endl;
    cout_mutex.unlock(); 
}

void think(int id){ // Philosopher "id" is thinking
    this_thread::sleep_for(chrono::milliseconds(600)); 
    print("Philosopher " + to_string(id) + " is thinking."); // print
    
}

bool eat(int id, int left, int right) { // Function, Philosopher "id" is eating (trying to)
    
    while(1) if (mtx[left].try_lock()) { // Philosopher "id" trying to pick up left fork. (keeps going until success)
    	
        print("Philosopher " + to_string(id) + " got the fork " + to_string(left));
        // He got the fork... So print it.
    	
        if (mtx[right].try_lock()) {
            // Philosopher "id" trying to pick up right fork.
            print("Philosopher " + to_string(id) + " got the fork " + to_string(right) + 
                "\nPhilosopher " + to_string(id) + " eats."); // print
            return true;
        } else {
            mtx[left].unlock(); 
            think(id);
            // Drop the left fork since Philosopher cannot pick up the right one
            // Added so that we can prevent dleadlock & starvation
        }
    }
    return false; // Never going to reach here ...
}

void putDownForks(int left, int right) {
    mtx[left].unlock(); // Put down the left fork
    mtx[right].unlock(); // Put down the right fork
}

void dinner_started(int philID) {
	
    // Left fork and Right forks indexes are determined here
    // There is a method implemented here, Taking the min numbered
    // Fork first...

	// int lIndex = philID;
	// int rIndex = (philID + 1) % (num_Phil);
    int lIndex = min(philID, (philID + 1) % (num_Phil));
    int rIndex = max(philID, (philID + 1) % (num_Phil));

    //////////////

    while (intrupt-- > 0) { // Intrupt is the var, that I use to give a bound to dinner time
        if (eat(philID, lIndex, rIndex)) { // Philosopher "philID" is trying to eat, w/ forks; lIndex & rIndex
            putDownForks(lIndex, rIndex); // Put down the forks
            ate[philID]++; // This philosopher:"philID" ate one more time...
            this_thread::sleep_for(chrono::milliseconds(600)); //cannot acquire eating right away
            //think(philID);
        }
    }
}

void dine(){
	
    // Threads created. Each thread is a philospher. Every philosopher is called function "dinner_started"
    for (int i = 0; i < num_Phil; ++i){
    	philosphers[i] = thread(dinner_started, i);
	}
    // Threads joined...
	for (int i = 0; i < num_Phil; ++i){
		philosphers[i].join();
	}
}

int main() { 
	//const int num_Phil=0;
	//cin>>num_Phil;
    dine(); // Dinner starts...
    //print how many times philosophers ate
    for (int i = 0; i < num_Phil; ++i){
    	cout << i << " = " << ate[i] << endl;
	}
}
