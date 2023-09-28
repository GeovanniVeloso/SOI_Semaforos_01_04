package view;

import java.util.concurrent.Semaphore;
import controller.ThreadFormula;

public class Principal {

	public static void main(String[] args) {

		Semaphore[] scuderia = new Semaphore[7];
		int carros = 7;
		Thread[] carroThread = new ThreadFormula[carros];
	    int[] voltaCarro = new int[14];
	    int[] voltaId = new int[14];
	    String[] voltaCarroS = new String[14];

		for (int i = 0; i < 7; i++) {
			scuderia[i] = new Semaphore(1);
			carroThread[i] = new ThreadFormula(i+1, 1, scuderia[i], voltaCarro, voltaId, voltaCarroS);
			carroThread[i].start();
			carroThread[i] = new ThreadFormula(i+1, 2, scuderia[i], voltaCarro, voltaId, voltaCarroS);
			carroThread[i].start();
		}

		for (Thread qualificacao : carroThread) {

            try {
                qualificacao.join();
            } catch (Exception e) {
                String msgError = e.getMessage();
                System.err.println(msgError);
            }

          }

		int[] indices = new int[14];
    	for (int i = 0; i < carros; i++) {
			indices[i] = i;	
    	}
		
		int n = 13;
		int aux = 0;
		int aux1 = 0;
		String aux2 = "";
		do {
			for (int i = 0; i < n; i++) {
				if (voltaCarro[i] > voltaCarro[i + 1]) {
					aux = voltaCarro[i];
					voltaCarro[i] = voltaCarro[i+1];
					voltaCarro[i+1] = aux;
					
					aux1 = voltaId[i];
					voltaId[i] = voltaId[i+1];
					voltaId[i+1] = aux1;
					
					aux2 = voltaCarroS[i];
					voltaCarroS[i] = voltaCarroS[i+1];
					voltaCarroS[i+1] = aux2;
					
				}
			}
			n -=1;
		} while (n!= 0);
		
		
		System.out.println("\\nGrid de Largada:\\n");
		for(int i = 0; i < 14; i++) {
			System.out.println(" Carro #"+voltaId[i]+" da escuderia "+voltaCarroS[i]+" está na posição #"+i+" com o melhor tempo de volta de "+voltaCarro[i]);
		}
	}
}
