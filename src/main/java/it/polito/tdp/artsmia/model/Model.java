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

	
}
