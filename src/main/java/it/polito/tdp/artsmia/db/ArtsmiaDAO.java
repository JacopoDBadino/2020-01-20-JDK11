package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Artists;
import it.polito.tdp.artsmia.model.CoppieArtisti;
import it.polito.tdp.artsmia.model.Exhibition;

public class ArtsmiaDAO {

	public List<ArtObject> listObjects() {

		String sql = "SELECT * from objects";
		List<ArtObject> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"),
						res.getString("continent"), res.getString("country"), res.getInt("curator_approved"),
						res.getString("dated"), res.getString("department"), res.getString("medium"),
						res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"),
						res.getString("rights_type"), res.getString("role"), res.getString("room"),
						res.getString("style"), res.getString("title"));

				result.add(artObj);
			}
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Exhibition> listExhibitions() {

		String sql = "SELECT * from exhibitions";
		List<Exhibition> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Exhibition exObj = new Exhibition(res.getInt("exhibition_id"), res.getString("exhibition_department"),
						res.getString("exhibition_title"), res.getInt("begin"), res.getInt("end"));

				result.add(exObj);
			}
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<String> listRoles() {

		String sql = "SELECT DISTINCT role FROM authorship";
		List<String> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				result.add(res.getString("role"));
			}
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Artists> listArtistForRole(String role) {
		String sql = "SELECT * FROM artists WHERE artist_id IN "
				+ "(SELECT DISTINCT artist_id FROM authorship WHERE role = ?)";
		List<Artists> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, role);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				result.add(new Artists(res.getInt("artist_id"), res.getString("name")));
			}
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public List<CoppieArtisti> getArchi(String role) {
		String sql = "SELECT ar1.artist_id AS idArtista1, ar2.artist_id AS idArtista2, COUNT(DISTINCT ex1.exhibition_id) AS qt "
				+ "FROM artists ar1, authorship au1, exhibition_objects ex1, artists ar2, authorship au2, exhibition_objects ex2 WHERE ar1.artist_id = au1.artist_id AND ex1.object_id = au1.object_id AND ar2.artist_id = au2.artist_id AND ex2.object_id = au2.object_id "
				+ "AND ex1.exhibition_id = ex2.exhibition_id AND au1.role = ? AND au2.role = ? AND ar1.artist_id > ar2.artist_id "
				+ "GROUP by ar1.artist_id, ar2.artist_id " + "ORDER BY qt desc";

		List<CoppieArtisti> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, role);
			st.setString(2, role);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				result.add(new CoppieArtisti(res.getInt("qt"), res.getInt("idArtista1"), res.getInt("idArtista2")));
			}
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
