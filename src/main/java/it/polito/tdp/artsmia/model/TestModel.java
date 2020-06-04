package it.polito.tdp.artsmia.model;

import java.util.List;

import org.jgrapht.Graphs;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();
		model.creaGrafo("Designer");
		Artists a = model.getDatabaseArtisti().get(5347);

		System.out.println(Graphs.neighborListOf(model.getGraph(), a));
		for (Artists art : Graphs.neighborListOf(model.getGraph(), a))
			System.out.println(art.getNome());
		model.calcolaPercorso(a);

	}

}
