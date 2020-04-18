package dao;

import dao.impl.SellerDaoImpl;

public class DaoFactory {

	public static SellerDao createSellerDao() {
		return new SellerDaoImpl();
	}
}
