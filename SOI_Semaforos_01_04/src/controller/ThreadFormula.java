package controller;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadFormula extends Thread{
    
    private int idCarro;
    private String escCarro;
    private int numCarro;
    public int[] voltaCarro;
    public int[] voltaid;
    public String[] voltaCarroS;
    private int meVolta = 1310;
    private Semaphore scuderia;
    private Semaphore sem2 = new Semaphore(1);
    private Semaphore pista = new Semaphore (5);
    private static AtomicInteger classificação = new AtomicInteger(0);
    private int n;
    
    public ThreadFormula(int idCarro, int numCarro, Semaphore scuderia, int[]voltaCarro, int[]voltaId, String[] voltaCarroS){
        this.idCarro = idCarro;
        this.numCarro = numCarro;
        this.scuderia = scuderia;
        this.voltaCarro = voltaCarro;
        this.voltaid = voltaId;
        this.voltaCarroS = voltaCarroS;
    }
    
    public void run(){
        CriaCarro();
        try {
			scuderia.acquire();
			pista.acquire();
	        Corrida();
		} catch (InterruptedException e) {
			System.err.println(e);
		}finally {
			scuderia.release();
			pista.release();
		}
    }
    
    public void CriaCarro(){
        switch (idCarro){
            case 1:
                   escCarro = "Red Bull Racing";
                   break;
            case 2:
                   escCarro = "Mercedes F1";
                   break;
            case 3:
                   escCarro = "Scuderia Ferrari";
                   break;
            case 4: 
                   escCarro = "Williams";
                   break;
            case 5:
                   escCarro = "Alpine";
                   break;
            case 6: 
                   escCarro = "McLaren";
                   break;
            case 7:
                   escCarro = "Alfa Romeo";
                   break;
        }
    }
    
    public void Corrida() {
    	int lastLap;
    	for(int i = 0; i < 3; i ++) {
    		lastLap = (int)(Math.random()*250)+1050;
    		System.out.println("O tempo da volta do carro "+numCarro+" da escuderia "+escCarro+" foi de #"+lastLap);
    		if(lastLap<meVolta) {
    			meVolta = lastLap;
    		}
    	}
    	
    	try {
			sem2.acquire();
			n = classificação.getAndIncrement();
			voltaCarro [n] = meVolta;
			voltaid[n] = numCarro;
			voltaCarroS [n] = escCarro;
		} catch (InterruptedException e) {
			System.err.println(e);
		}finally {
			sem2.release();
		}
    }
    
}
