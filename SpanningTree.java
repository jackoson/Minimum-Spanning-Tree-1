import java.io.*;
import java.util.*;
import java.text.*;

class SpanningTree{

	public static void main(String[] args){
		Reader r = new Reader();
		try{
			r.read(args[1]);
			SimpleDateFormat d = new SimpleDateFormat("EEEEEEEEEEEE dd MMMMMMMMMMMMMM yyyy HH:mm");
		
			if(args[0].equals("-p1")){
				System.out.println("Total Cable Needed: " + String.format("%.2f", totalEdgeWeight(r.graph())) + "m");
			}else if(args[0].equals("-p2")){
				System.out.println("Price: " + String.format("%.2f", getPrice(r.graph())));
				System.out.println("Hours of Disrupted Travel: " + String.format("%.2f", getHours(r.graph())) + "h");
				System.out.println("Completion Date: " + d.format(getDate(r.graph())));
			}

		}catch(IOException e){
			System.err.println("Wrong Filename Idiot");
		}
	}
	
	private static double totalEdgeWeight(Graph g){
		double sum = 0;
		for(Edge e :g.edges()){
			sum = sum + e.weight();
		}
		return sum;
	}

	private static double getPrice(Graph g){
		double sum = 0;
		for(Edge e :g.edges()){
			if(e.isLocalRoad()){
				sum = sum + 5000 + (4500*e.weight());
			}else if(e.isMainRoad()){
				sum = sum + (4000*e.weight()); 
			}else if(e.isUnderground()){
				sum = sum + (1000*e.weight());
			}
		}
		return sum;
	}

	private static double getHours(Graph g){
		double sum = 0;
		for(Edge e :g.edges()){
			if(e.isLocalRoad()){
				sum = sum + (0.2*e.weight());
			}else if(e.isMainRoad()){
				sum = sum + (0.5*e.weight()); 
			}else if(e.isUnderground()){
				sum = sum + (1*e.weight());
			}
		}
		return sum;
	}
	
	private static Date getDate(Graph g){
		double sum = 0;
		for(Edge e :g.edges()){
			if(e.isLocalRoad()){
				sum = sum + (0.2*e.weight());
			}else if(e.isMainRoad()){
				sum = sum + (0.6*e.weight()); 
			}else if(e.isUnderground()){
				sum = sum + (0.9*e.weight());
			}
		}
		int days = (int)sum;
		Date d = new Date(2014 - 1900, 1, 15+days);
		double hours = (sum - days)*24;
		d.setHours((int)hours);
		double minutes = (hours - (int)hours)*60;
		d.setMinutes((int)minutes);
		return d;
	}
}		
