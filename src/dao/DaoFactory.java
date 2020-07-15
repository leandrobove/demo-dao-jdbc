package dao;

import connection.DB;
import dao.impl.DepartmentDaoImpl;
import dao.impl.SellerDaoImpl;

public class DaoFactory {

	public static SellerDao createSellerDao() {
		return new SellerDaoImpl(DB.getConnection());
	}
	
	public static DepartmentDao createDepartmentDao() {
		return new DepartmentDaoImpl(DB.getConnection());
	}
}
