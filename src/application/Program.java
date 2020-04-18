package application;

import connection.DB;
import dao.DaoFactory;
import dao.SellerDao;
import entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		Seller sel = sellerDao.findById(3);

		System.out.println(sel);
		
		DB.closeConnection();

	}

}
