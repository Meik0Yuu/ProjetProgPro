package model;

public abstract class Personnage {
	private String nom;
    private String role;
    private String description;

    public Personnage(String nom, String role, String description) {
        this.nom = nom;
        this.role = role;
        this.description = description;
    }

    public abstract String sePresenter();

    public String getNom(){
    	return nom; 
    }
    public void setNom(String nom){ 
    	this.nom = nom; 
    }
    public String getRole(){ 
    	return role; 
    }
    public void setRole(String role){ 
    	this.role = role; 
    }
    public String getDescription(){ 
    	return description; 
    }
    public void setDescription(String d){ 
    	this.description = d; 
    }

    @Override
    public String toString(){ 
    	return "[" + role + "] " + nom; 
    }
}
