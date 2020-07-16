package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import connection.DB;
import dao.DepartmentDao;
import entities.Department;
import entities.Seller;
import exception.DBException;

public class DepartmentDaoImpl implements DepartmentDao {

	private Connection conn;

	public DepartmentDaoImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department dep) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("INSERT INTO department(Id, Name) VALUES(null, ?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, dep.getName());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					dep.setId(id);
				}
			} else {
				throw new DBException("Erro de inserção: Nenhum dado inserido.");
			}

		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Department dep) {

		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("UPDATE department SET Name = ? " + "WHERE Id = ?");

			st.setString(1, dep.getName());
			st.setInt(2, dep.getId());

			st.executeUpdate();

		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void deleteById(Integer id) {

		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("DELETE from department WHERE Id = ?");

			st.setInt(1, id);

			st.executeUpdate();

		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public Department findById(Integer id) {

		PreparedStatement st = null;
		ResultSet rs = null;

		Department dep = null;

		try {

			st = conn.prepareStatement("SELECT Id, Name FROM department WHERE Id = ?");

			st.setInt(1, id);

			rs = st.executeQuery();

			if (rs.next()) {
				dep = new Department(rs.getInt("Id"), rs.getString("Name"));
			}

		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}

		return dep;
	}

	@Override
	public List<Department> findAll() {

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement("SELECT Id, Name FROM department ORDER BY Id ASC");

			rs = stmt.executeQuery();

			List<Department> listaDep = new ArrayList<>();

			while (rs.next()) {
				listaDep.add(new Department(rs.getInt("Id"), rs.getString("Name")));
			}

			return listaDep;

		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(stmt);
		}
	}

}
