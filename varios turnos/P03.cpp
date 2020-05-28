//filosofos pensando, se detienen para comer
//creacion del estómago para cada filosofo
#include <iostream>
#include <mutex>
#include <thread>
#include <ctime>
#include <chrono>
using namespace std;

const int num_Phil = 5; 
thread philosphers[num_Phil]; 
mutex mtx[num_Phil]; 
mutex cout_mutex; 
int ate[num_Phil];
int intrupt = 50;

void estomagos(){//estomago para cada filosofo
	for(int i=0;i<num_Phil;i++){
		ate[i]=100;
	}
}
//int ate[num_Phil] = {100,100,100,100,100}; 

void print(string str){  
    cout_mutex.lock(); 
    cout <<str<<endl;
    cout_mutex.unlock(); 
}

void think(int id){ 
    this_thread::sleep_for(chrono::milliseconds(600)); 
    print("Philosopher " + to_string(id) + " is thinking."); 
    
}

bool eat(int id, int left, int right) { 
    
    while(1) if (mtx[left].try_lock()) { 
    	
        print("Philosopher " + to_string(id) + " got the fork " + to_string(left));
       
        if (mtx[right].try_lock()) {
            print("Philosopher " + to_string(id) + " got the fork " + to_string(right) + 
                "\nPhilosopher " + to_string(id) + " eats."); // print
            return true;
        } else {
            mtx[left].unlock(); 
            think(id);
        }
    }
    return false; 
}

void putDownForks(int left, int right) {
    mtx[left].unlock(); 
    mtx[right].unlock(); 
}

void dinner_started(int philID) {
	
    int lIndex = min(philID, (philID + 1) % (num_Phil));
    int rIndex = max(philID, (philID + 1) % (num_Phil));

    while (intrupt-- > 0) { 
        if (eat(philID, lIndex, rIndex)) { 
            putDownForks(lIndex, rIndex); 
            ate[philID]++; 
            this_thread::sleep_for(chrono::milliseconds(600)); 
        }
    }
}

void dine(){

    for (int i = 0; i < num_Phil; ++i){
    	philosphers[i] = thread(dinner_started, i);
	}
	for (int i = 0; i < num_Phil; ++i){
		philosphers[i].join();
	}
}

int main() { 
	estomagos();
    dine(); 
    for (int i = 0; i < num_Phil; ++i){
    	cout << i << " = " << ate[i] << endl;
	}
}
