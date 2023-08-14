/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package context.core;

/**
 *
 * @author songhuiyue
 */
public class Ontology {
    
    protected String type;
    protected String name;
    protected String uri;
    protected int id;

    public Ontology(){}
    
    public Ontology(String type, String name, String uri, int id) {
        this.type = type;
        this.name = name;
        this.uri = uri;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
    
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ontology other = (Ontology) o;

        // Define your criteria for object equality here
        return id == other.id 
                && name.equals(other.name) 
                && type.equals(other.type) 
                && uri.equals(other.uri);
    }
    
}
