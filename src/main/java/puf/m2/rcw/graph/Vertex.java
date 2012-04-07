package puf.m2.rcw.graph;

public class Vertex{
    private String name;
    private char label;
    private VertexSet adjacentVertices = new VertexSet();

    public Vertex(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public char getLabel() {
        return label;
    }

    public void setLabel(char label) {
        this.label = label;
    }

    public VertexSet getAdjacentVertices() {
		return adjacentVertices;
	}

	public void addAjacentVertex(Vertex v) {
        adjacentVertices.add(v);
    }
    
    public boolean isAdjacentTo(Vertex v) {
        return adjacentVertices.contains(v);
    }
    
    public boolean equals(Object o) {
        if (!(o instanceof Vertex)) {
            return false;
        }
        Vertex v = (Vertex) o;
        return name.equals(v.getName());
    }

    public Vertex clone() {
    	return new Vertex(name);
    }
    
    public void cleanAdjacency() {
        adjacentVertices = new VertexSet();
    }
    
    public String toString() {
        String str = name + ": ";
        for (Vertex v : adjacentVertices.vertices()) {
            str = str + v.getName() + ",";
        }
        return str;
    }
}
