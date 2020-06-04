package it.polito.tdp.artsmia.db;

import java.util.List;

import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Artists;
import it.polito.tdp.artsmia.model.CoppieArtisti;
import it.polito.tdp.artsmia.model.Exhibition;

public class TestArtsmiaDAO {

	public static void main(String[] args) {

		ArtsmiaDAO dao = new ArtsmiaDAO();

		System.out.println("Test objects:");
		List<Artists> l = dao.listArtistForRole("Photographer");
		System.out.print(l.size());
		
		System.out.println("\nTest archi:");
		List<CoppieArtisti> c = dao.getArchi("Photographer");
		System.out.print(c.size());
		

	}

}
