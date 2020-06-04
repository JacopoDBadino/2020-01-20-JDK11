package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.model.Artists;
import it.polito.tdp.artsmia.model.CoppieArtisti;
import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.shape.Box;

public class ArtsmiaController {

	private Model model;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button btnCreaGrafo;

	@FXML
	private Button btnArtistiConnessi;

	@FXML
	private Button btnCalcolaPercorso;

	@FXML
	private ComboBox<String> boxRuolo;

	@FXML
	private TextField txtArtista;

	@FXML
	private TextArea txtResult;

	@FXML
	void doArtistiConnessi(ActionEvent event) {
		txtResult.clear();
		txtResult.appendText("Artisti connessi: \n");
		try {
			for (CoppieArtisti ca : model.artistiConnessi(boxRuolo.getValue()))
				txtResult.appendText(String.format("\n(%d, %d) - %d", ca.getIdArtista1(), ca.getIdArtista2(), ca.getNumeroOpere()));
		} catch (Exception e) {
			txtResult.appendText("ERRORE RICERCA ARTISTI CONNESSI!");
		}
	}

	@FXML
	void doCalcolaPercorso(ActionEvent event) {
		txtResult.clear();
		txtResult.appendText("Calcolo percorso: ");
		try {
			
			Artists source = model.getDatabaseArtisti().get(Integer.parseInt(txtArtista.getText()));
			
			for (Artists a : model.calcolaPercorso(source))
				txtResult.appendText(String.format("Artista %s", a.getNome()));
			
		} catch (Exception e) {
			txtResult.appendText("ERRORE CALCOLO PERCORSO!");
		}
	}

	@FXML
	void doCreaGrafo(ActionEvent event) {
		txtResult.clear();
		try {
			txtResult.appendText("Grafo creato: ");
			model.creaGrafo(boxRuolo.getValue());
			txtResult.appendText("\n\nVertici: #" + model.getGraph().vertexSet().size());
			txtResult.appendText("\nArchi: #" + model.getGraph().edgeSet().size());
		} catch (Exception e) {
			txtResult.appendText("ERRORE CREAZIONE GRAFO!");
		}
	}

	public void setModel(Model model) {
		this.model = model;

		boxRuolo.getItems().addAll(model.getRoles());
	}

	@FXML
	void initialize() {
		assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Artsmia.fxml'.";
		assert btnArtistiConnessi != null : "fx:id=\"btnArtistiConnessi\" was not injected: check your FXML file 'Artsmia.fxml'.";
		assert btnCalcolaPercorso != null : "fx:id=\"btnCalcolaPercorso\" was not injected: check your FXML file 'Artsmia.fxml'.";
		assert boxRuolo != null : "fx:id=\"boxRuolo\" was not injected: check your FXML file 'Artsmia.fxml'.";
		assert txtArtista != null : "fx:id=\"txtArtista\" was not injected: check your FXML file 'Artsmia.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

	}
}
