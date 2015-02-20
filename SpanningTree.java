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
				System.out.println("Price: " + String.format("%.2f", totalEdgeWeight(getPrice(r.graph()))));
				System.out.println("Hours of Disrupted Travel: " + String.format("%.2f", totalEdgeWeight(getHours(r.graph()))) + "h");
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
		return sum*1000;
	}

	private static Graph getPrice(Graph g){
		
		Graph NewGraph = new Graph();
		
		for(Node n :g.nodes()){
			NewGraph.add(n);
		}
	
		for(Edge e :g.edges()){
			if(e.isLocalRoad()){
				NewGraph.add(new Edge(e.id1(), e.id2(), 5000 + (4500*e.weight()), e.type()));
			}else if(e.isMainRoad()){
				NewGraph.add(new Edge(e.id1(), e.id2(),(4000*e.weight()), e.type()));
			}else if(e.isUnderground()){
				NewGraph.add(new Edge(e.id1(), e.id2(),(1000*e.weight()), e.type()));
			}
		}
		return NewGraph;
	}

	private static Graph getHours(Graph g){
		
		Graph NewGraph = new Graph();
		
		for(Node n :g.nodes()){
			NewGraph.add(n);
		}
	
		for(Edge e :g.edges()){
			if(e.isLocalRoad()){
				NewGraph.add(new Edge(e.id1(), e.id2(), (0.2*e.weight()), e.type()));
			}else if(e.isMainRoad()){
				NewGraph.add(new Edge(e.id1(), e.id2(),(0.5*e.weight()), e.type()));
			}else if(e.isUnderground()){
				NewGraph.add(new Edge(e.id1(), e.id2(),(1*e.weight()), e.type()));
			}
		}
		return NewGraph;
	}
	
	private static Date getDate(Graph g){
		
		double sum = totalEdgeWeight(getDays(g));
		
		int days = (int)sum;
		Date d = new Date(2014 - 1900, 1, 15+days);
		double hours = (sum - days)*24;
		d.setHours((int)hours);
		double minutes = (hours - (int)hours)*60;
		d.setMinutes((int)minutes);
		return d;
	}
	
	private static Graph getDays(Graph g){
		Graph NewGraph = new Graph();
		
		for(Node n :g.nodes()){
			NewGraph.add(n);
		}
	
		for(Edge e :g.edges()){
			if(e.isLocalRoad()){
				NewGraph.add(new Edge(e.id1(), e.id2(), (0.2*e.weight()), e.type()));
			}else if(e.isMainRoad()){
				NewGraph.add(new Edge(e.id1(), e.id2(),(0.6*e.weight()), e.type()));
			}else if(e.isUnderground()){
				NewGraph.add(new Edge(e.id1(), e.id2(),(0.9*e.weight()), e.type()));
			}
		}
		return NewGraph;
	}
	
	private Graph getPrimMST(Graph g){
		
		Graph newG = new Graph();
		Node CurrentNode = g.nodes().iterator().next();
		newG.add(CurrentNode);
		
		Hashtable<Node, Edge> D = new Hashtable<Node, Edge>();
		
		/*while(){
			calculateD(D);
		}*/
		
		return newG;
		
	}

	private void calculateD(Hashtable<Node, Edge> d) {
		// TODO Auto-generated method stub
		
	}
	
}		
