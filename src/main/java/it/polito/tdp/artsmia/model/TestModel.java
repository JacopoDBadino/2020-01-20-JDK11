package it.polito.tdp.artsmia.model;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();
		model.creaGrafo("Artist");
		System.out.print(model.getGraph().vertexSet().size());
		
	}

}
