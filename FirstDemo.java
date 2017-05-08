package org.jgrapht.demo;

import java.io.*;
import java.util.*;

import org.jgrapht.*;
import org.jgrapht.alg.interfaces.AStarAdmissibleHeuristic;
import org.jgrapht.alg.shortestpath.AStarShortestPath;
import org.jgrapht.alg.shortestpath.BellmanFordShortestPath;
import org.jgrapht.alg.shortestpath.BidirectionalDijkstraShortestPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.shortestpath.FloydWarshallShortestPaths;
import org.jgrapht.alg.shortestpath.KShortestPaths;
import org.jgrapht.graph.*;
import org.jgrapht.traverse.*;

public class FirstDemo {
	public static void main(String[] args){
		 long time = System.currentTimeMillis();

        reportPerformanceFor("starting at", time);

        int numVertices = 100;
        int porcetajeAparicion = 10; //Esta es la probabilidad que existe de crear una arista entre 2 vertices
        int[][] matrizAdyacencia = new int[numVertices][numVertices];
        int nAleatorioEntero;
        int posAleatoria;
        int j = 0; 
        
        /*
         * Esta parte del código se encarga de leer la matriz de adyaciencia de un futuro grafo de un archivo
         * **/
        
    /*    File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
           // Apertura del fichero y creacion de BufferedReader para poder
           // hacer una lectura comoda (disponer del metodo readLine()).
           archivo = new File ("D:/DOCS/Investigación/prueba.txt");
           fr = new FileReader (archivo);
           br = new BufferedReader(fr);

           // Lectura del fichero
           String linea;
           numVertices = Integer.parseInt(br.readLine());
           int fila = 0;
           while((linea=br.readLine())!=null){
        	   //System.out.println("Prueba " +linea);
        	   for (int x=0; x < linea.length() ;x++){
        		   matrizAdyacencia[fila][x] = Character.getNumericValue(linea.charAt(x));
        	   	   //System.out.println("PRUEBA MATRIZ[" + fila + "][" + x + "]: " + matrizAdyacencia[fila][x]);
        	   }
        	   fila ++;
           }
        }
        catch(Exception e){
           e.printStackTrace();
        }finally{
           // En el finally cerramos el fichero, para asegurarnos
           // que se cierra tanto si todo va bien como si salta 
           // una excepcion.
           try{                    
              if( null != fr ){   
                 fr.close();     
              }                  
           }catch (Exception e2){ 
              e2.printStackTrace();
           }
        }*/
        
        /*
         * Generación de la matriz de adyaciencia*/
        
        for(int i = 0; i < numVertices; i++){	//Recorremos la fila
        	nAleatorioEntero = (int) ((Math.random() * ((numVertices-1)*porcetajeAparicion/100)) + 1);//Se encarga de generar la cantidad de vertices por linea.
            System.out.println("Cantidad de unos en fila: " + nAleatorioEntero);
            j = 0;
            while(j< nAleatorioEntero){	//Recorremos la columna
            	posAleatoria = (int) (Math.random() * numVertices);//Se encarga de generar la cantidad de vertices por linea.
                System.out.println("Posicionen en fila " + i +": " + posAleatoria);
            	if(posAleatoria != i){
            		if(matrizAdyacencia[i][posAleatoria] != 1){
	            		matrizAdyacencia[i][posAleatoria] = 1;
	            		System.out.println("Matriz [" + i + "][" + posAleatoria + "]: " + matrizAdyacencia[i][posAleatoria]);
	            		j ++;
            		}
            	}
            }
        }
        
        System.out.println("MATRIZ DE ADYACENCIA");
        for (int i = 0; i < matrizAdyacencia.length; i++) {
			for (int k = 0; k < matrizAdyacencia[i].length; k++) {
				System.out.println("Matriz [" + i + "][" + k + "]: " + matrizAdyacencia[i][k]);
			}
		}
        
        
        //Esta Parte del código se encarga de escribir el grafo en un archivo

        
        FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter("D:/DOCS/Investigación/prueba.txt");
            pw = new PrintWriter(fichero);
            pw.println(numVertices);
            for (int i = 0; i < matrizAdyacencia.length; i++) {
    			for (int k = 0; k < matrizAdyacencia[i].length; k++) {
    				pw.print(matrizAdyacencia[i][k]);
    			}
    			pw.print("\n");
    		}
            

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    
        
        System.out.println("GENERACIÓN DE MATRIZ ACABADA");
        
        SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> g = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
        
        /*
         * Generamos los vertices
         * */
        
        for (int i = 0; i < numVertices; i++) {
            g.addVertex("V" + i);
        }
        
        /*
         * Generación de aristas
         * */
        for (int fil = 0; fil < matrizAdyacencia.length; fil++) {
			for (int col = 0; col < matrizAdyacencia[fil].length; col++) {
				if(matrizAdyacencia[fil][col] == 1){
					g.addEdge("V" + fil, "V" + col);
				}
			}
        }
        
        reportPerformanceFor("graph allocation", time);
        System.out.println("Num of edges: " + g.edgeSet().size());
        System.out.println("Num of vertex: " + g.vertexSet().size());
        
        String vInicio = "V0";
        String vFinal = "V7";
        
        
        /*
         * Mostrar el grafo. Es decir de que vertice a vertice tiene algo*/
        System.out.println("Grafo generado aleatoriamente: " + g.toString() + "\n");
        
        //* Path 
        long timeStar = System.currentTimeMillis();
        reportPerformanceFor("Began A* Shortest Path Algorithm", timeStar);
        AStarAdmissibleHeuristic<String> admissibleHeuristic = new heuristicoPrueba();
        AStarShortestPath<String, DefaultWeightedEdge> astar= new AStarShortestPath<>(g, admissibleHeuristic);
        GraphPath<String, DefaultWeightedEdge> cycleStar = astar.getPath(vInicio,vFinal);
        System.out.println("Recorrido de A*" + cycleStar.toString());
        reportPerformanceFor("Finish A* Shortest Path Algorithm", timeStar);
        System.out.println("\n");
        
        //BellmanFord Path
        long timeBF = System.currentTimeMillis();
        reportPerformanceFor("Began Bellman Ford Shortest Path Algorithm", timeBF);
        GraphPath<String, DefaultWeightedEdge> cycleBF = BellmanFordShortestPath.findPathBetween(g, vInicio,vFinal);
        System.out.println("Recorrido de BellmanFord" + cycleBF.toString());
        reportPerformanceFor("Finish Bellman Ford Shortest Path Algorithm", timeBF);
        System.out.println("\n");
        
        //BellmanFord Path
        long timeBD = System.currentTimeMillis();
        reportPerformanceFor("Began Bidirectional Dijkstra Shortest Path Algorithm", timeBD);
        GraphPath<String, DefaultWeightedEdge> cycleBD = BidirectionalDijkstraShortestPath.findPathBetween(g, vInicio,vFinal);
        System.out.println("Recorrido de BidirectionalDijkstra" + cycleBD.toString());
        reportPerformanceFor("Finish Bidirectional Dijkstra Shortest Path Algorithm", timeBD);
        System.out.println("\n");
        
        //Dijkstra Path
        long timeD = System.currentTimeMillis();
        reportPerformanceFor("Began Dijkstra Shortest Path Algorithm", timeD);
        GraphPath<String, DefaultWeightedEdge> cycleD = DijkstraShortestPath.findPathBetween(g,vInicio, vFinal);
        System.out.println("Recorrido de BellmanFord" + cycleD.toString());
        reportPerformanceFor("Finish Dijkstra Shortest Path Algorithm", timeD);
        System.out.println("\n");
        
        //Floyd Warshall Path
        long timeFW = System.currentTimeMillis();
        reportPerformanceFor("Began Floyd Warshall Path Algorithm", timeFW);
        FloydWarshallShortestPaths<String, DefaultWeightedEdge> gF = new FloydWarshallShortestPaths<>(g);
        GraphPath<String, DefaultWeightedEdge> cycleFW = gF.getPath(vInicio, vFinal);
        System.out.println("Recorrido de Floyd Warshall" + cycleFW.toString());
        reportPerformanceFor("Finish Floyd Warshall Shortest Path Algorithm", timeFW);
        System.out.println("\n");
        
      //K Shorted Path
        long timeK = System.currentTimeMillis();
        reportPerformanceFor("Began K Shortest Path Algorithm", timeK);
        KShortestPaths<String, DefaultWeightedEdge> gK = new KShortestPaths<>(g,1);
        List<GraphPath<String, DefaultWeightedEdge>> cycleK = gK.getPaths(vInicio, vFinal);
        System.out.println("Recorrido de K Shortest" + cycleK.toString());
        reportPerformanceFor("Finish K Shortest Path Algorithm", timeK);
        System.out.println("\n");
	}
	
	private static void reportPerformanceFor(String msg, long refTime)
    {
        double time = (System.currentTimeMillis() - refTime) / 1000.0;
        double mem = usedMemory() / (1024.0 * 1024.0);
        mem = Math.round(mem * 100) / 100.0;
        System.out.println(msg + " (" + time + " sec, " + mem + "MB)");
    }

    private static long usedMemory()
    {
        Runtime rt = Runtime.getRuntime();

        return rt.totalMemory() - rt.freeMemory();
    }
}
