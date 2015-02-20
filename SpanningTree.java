import java.io.*;
import java.util.*;
import java.text.*;

class SpanningTree{
	
	static Comparator comp;

	public static void main(String[] args){
		Reader r = new Reader();
		try{
			r.read(args[1]);
			SimpleDateFormat d = new SimpleDateFormat("EEE d MMMMMMMMMMMMMM yyyy HH:mm");
		
			if(args[0].equals("-p1")){
				System.out.println("Total Cable Needed: " + String.format("%.2f", 1000*totalEdgeWeight(r.graph())) + "m");
			}else if(args[0].equals("-p2")){
				System.out.println("Price: " + String.format("%.2f", totalEdgeWeight(getPrice(r.graph()))));
				System.out.println("Hours of Disrupted Travel: " + String.format("%.2f", totalEdgeWeight(getHours(r.graph()))) + "h");
				System.out.println("Completion Date: " + d.format(getDate(totalEdgeWeight(getDays(r.graph())))));
			}else if(args[0].equals("-p3")){
				System.out.println("Price: " + String.format("%.2f", totalEdgeWeight(getPrimMST(getPrice(r.graph())))));
				System.out.println("Hours of Disrupted Travel: " + String.format("%.2f", totalEdgeWeight(getPrimMST(getHours(r.graph())))) + "h");
				System.out.println("Completion Date: " + d.format(getDate(totalEdgeWeight(getPrimMST(getDays(r.graph()))))));
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
	
	private static Date getDate(double sum){
		
		Date date = new Date(2014 - 1900, 1, 15);
		
		date.setTime((long) (date.getTime()+(sum*24*60*60*1000)));
		
		return date;
		
	}
	
	private static Graph getDays(Graph g){
		Graph NewGraph = new Graph();
		
		for(Node n :g.nodes()){
			NewGraph.add(n);
		}
	
		for(Edge e :g.edges()){
			if(e.isLocalRoad()){
				NewGraph.add(new Edge(e.id1(), e.id2(), (e.weight()/0.2), e.type()));
			}else if(e.isMainRoad()){
				NewGraph.add(new Edge(e.id1(), e.id2(),(e.weight()/0.6), e.type()));
			}else if(e.isUnderground()){
				NewGraph.add(new Edge(e.id1(), e.id2(),(e.weight()/0.9), e.type()));
			}
		}
		return NewGraph;
	}
	
	private static Graph getPrimMST(Graph g){
		
		comp = new Comparator(){

			@Override
			public int compare(Object o1, Object o2) {
				double one = ((Edge) o1).weight();
				double two = ((Edge) o2).weight();
				return Double.compare(one, two);
			}
		};
		
		Graph newG = new Graph();
		List<Edge> sortedEdges = new ArrayList<Edge>(g.edges());
		Collections.sort(sortedEdges, comp);
		
		Edge firstEdge = sortedEdges.iterator().next();
		newG.add(firstEdge);
		newG.add(g.find(firstEdge.id1()));
		newG.add(g.find(firstEdge.id2()));
		
		sortedEdges.remove(firstEdge);
		
		while(!newG.nodes().equals(g.nodes())){
			
			for(Edge e: sortedEdges){
				if( (newG.find(e.id1()) != null) && (newG.find(e.id2()) != null) ){
					sortedEdges.remove(e);
					break;
				}else if(newG.find(e.id1()) != null){
					newG.add(e);
					newG.add(g.find(e.id2()));
					sortedEdges.remove(e);
					break;
				}else if(newG.find(e.id2()) != null){
					newG.add(e);
					newG.add(g.find(e.id1()));
					sortedEdges.remove(e);
					break;
				}
			}	
		}
			
		return newG;	
	}
}