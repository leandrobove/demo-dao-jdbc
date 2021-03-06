package dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import connection.DB;
import dao.SellerDao;
import entities.Department;
import entities.Seller;
import exception.DBException;

public class SellerDaoImpl implements SellerDao {

	private Connection conn;

	public SellerDaoImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller sel) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("INSERT INTO seller(Id, Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES(null,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

			st.setString(1, sel.getName());
			st.setString(2, sel.getEmail());
			st.setDate(3, new Date(sel.getBirthDate().getTime()));
			st.setDouble(4, sel.getBaseSalary());
			st.setInt(5, sel.getDepartament().getId());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				rs = st.getGeneratedKeys();

				if (rs.next()) {
					int id = rs.getInt(1);
					sel.setId(id);
				}
			} else {
				throw new DBException("Erro de inser��o: Nenhum dado inserido.");
			}
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Seller sel) {
		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("UPDATE seller "
					+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? " + "WHERE Id = ?",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, sel.getName());
			st.setString(2, sel.getEmail());
			st.setDate(3, new Date(sel.getBirthDate().getTime()));
			st.setDouble(4, sel.getBaseSalary());
			st.setInt(5, sel.getDepartament().getId());

			st.setInt(6, sel.getId());

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

			st = conn.prepareStatement("DELETE FROM seller WHERE Id = ?");

			st.setInt(1, id);

			int rowsAffected = st.executeUpdate();

			if (rowsAffected == 0) {
				throw new DBException("Vendedor n�o encontrado.");
			}

		} catch (

		SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement("SELECT s.*, d.Name as DepName FROM seller s INNER JOIN department d "
					+ "ON s.DepartmentId = d.Id WHERE s.Id = ?");

			stmt.setInt(1, id);

			rs = stmt.executeQuery();

			if (rs.next()) {
				Department dep = instantiateDepartment(rs);
				Seller sel = instantiateSeller(rs, dep);

				return sel;
			}
			return null;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(stmt);
		}

	}

	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller sel = new Seller();

		sel.setId(rs.getInt("Id"));
		sel.setName(rs.getString("Name"));
		sel.setEmail(rs.getString("Email"));
		sel.setBirthDate(rs.getDate("BirthDate"));
		sel.setBaseSalary(rs.getDouble("BaseSalary"));
		sel.setDepartament(dep);

		return sel;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();

		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));

		return dep;
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement("SELECT s.*, d.Name as DepName " + "FROM seller s INNER JOIN department d "
					+ "ON s.DepartmentId = d.Id " + "ORDER BY s.Name");

			rs = stmt.executeQuery();

			List<Seller> listaSeller = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();

			while (rs.next()) {

				Department departamento = map.get(rs.getInt("DepartmentId"));

				if (departamento == null) {
					departamento = instantiateDepartment(rs);

					map.put(rs.getInt("DepartmentId"), departamento);
				}

				Seller sel = instantiateSeller(rs, departamento);

				listaSeller.add(sel);

			}
			return listaSeller;

		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(stmt);
		}
	}

	@Override
	public List<Seller> findByDepartment(Department dep) {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement("SELECT s.*, d.Name as DepName FROM seller s INNER JOIN department d "
					+ "ON s.DepartmentId = d.Id WHERE s.DepartmentId = ? ORDER BY s.Name");

			stmt.setInt(1, dep.getId());

			rs = stmt.executeQuery();

			List<Seller> listaSeller = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();

			while (rs.next()) {

				Department departamento = map.get(rs.getInt("DepartmentId"));

				if (departamento == null) {
					departamento = instantiateDepartment(rs);

					map.put(rs.getInt("DepartmentId"), departamento);
				}

				Seller sel = instantiateSeller(rs, departamento);

				listaSeller.add(sel);

			}
			return listaSeller;

		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(stmt);
		}
	}

}
