package fr.insa.sosa.RestProject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("etudiants")
public class EtudiantRessource {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Etudiant getEtudiants(){
		Etudiant etudiant=new Etudiant();
		etudiant.setNom("AAA");
		return etudiant;
	}
}
