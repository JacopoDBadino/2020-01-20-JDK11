package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {

	ArtsmiaDAO dao;
	SimpleWeightedGraph<Artists, DefaultWeightedEdge> graph;
	Map<Integer, Artists> databaseArtisti;
	int bstSize;

	List<Artists> soluzione;
	List<Artists> parziale;

	public Model() {
		dao = new ArtsmiaDAO();

	}

	public List<String> getRoles() {
		return dao.listRoles();
	}

	public void creaGrafo(String role) {
		graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

		databaseArtisti = new HashMap<Integer, Artists>();
		for (Artists a : dao.listArtistForRole(role))
			databaseArtisti.put(a.getId(), a);

		for (Artists a : dao.listArtistForRole(role))
			this.graph.addVertex(databaseArtisti.get(a.getId()));

		for (CoppieArtisti ca : dao.getArchi(role)) {

			int peso = ca.getNumeroOpere();
			Artists a1 = databaseArtisti.get(ca.getIdArtista1());
			Artists a2 = databaseArtisti.get(ca.getIdArtista2());

			DefaultWeightedEdge vecchioArco = this.graph.getEdge(a1, a2);

			if (vecchioArco == null)
				Graphs.addEdge(graph, a1, a2, peso);
			else
				this.graph.setEdgeWeight(vecchioArco, this.graph.getEdgeWeight(vecchioArco) + peso);

		}
	}

	public List<CoppieArtisti> artistiConnessi(String role) {
		List<CoppieArtisti> result = dao.getArchi(role);
		Collections.sort(result);
		return result;
	}

	public SimpleWeightedGraph<Artists, DefaultWeightedEdge> getGraph() {
		return graph;
	}

	public Map<Integer, Artists> getDatabaseArtisti() {
		return databaseArtisti;
	}

	public List<Artists> calcolaPercorso(Artists source) {
		parziale = new ArrayList<Artists>();
		soluzione = new ArrayList<Artists>();
		bstSize = 0;

		parziale.add(source);

		ricorsione(parziale, 0.0);

		return soluzione;
	}

	public void ricorsione(List<Artists> parz, double peso) {

		if (parz.size() > bstSize)
			soluzione = new ArrayList<Artists>();

		double pesoAtt;
		Artists sourceAtt = parz.get(parz.size() - 1);

		for (Artists a : Graphs.neighborListOf(graph, sourceAtt)) {

			pesoAtt = peso;

			if (parziale.size() == 1)
				pesoAtt = this.graph.getEdgeWeight(this.graph.getEdge(sourceAtt, a));

			if (this.graph.getEdgeWeight(this.graph.getEdge(a, sourceAtt)) == pesoAtt) {
				parziale.add(a);
				ricorsione(parziale, pesoAtt);
				parziale.remove(parziale.size() - 1);
			}
		}

	}

	

}
