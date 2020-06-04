package it.polito.tdp.artsmia.model;

public class CoppieArtisti implements Comparable<CoppieArtisti> {

	private int numeroOpere;
	private int idArtista1;
	private int idArtista2;

	public CoppieArtisti(int numeroOpere, int idArtista1, int idArtista2) {
		super();
		this.numeroOpere = numeroOpere;
		this.idArtista1 = idArtista1;
		this.idArtista2 = idArtista2;
	}

	public int getNumeroOpere() {
		return numeroOpere;
	}

	public int getIdArtista1() {
		return idArtista1;
	}

	public int getIdArtista2() {
		return idArtista2;
	}

	@Override
	public int compareTo(CoppieArtisti o) {
		// TODO Auto-generated method stub
		return -(this.numeroOpere - o.getNumeroOpere());
	}
}
