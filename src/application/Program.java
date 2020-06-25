package application;

import java.util.List;

import connection.DB;
import dao.DaoFactory;
import dao.SellerDao;
import entities.Department;
import entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		System.out.println("=== Test 1 : findById seller ==");
		
		Seller sel = sellerDao.findById(3);

		System.out.println(sel);
		
		System.out.println("=== Test 2 : findByDepartment seller ==");
		Department dep = new Department(2, null);
		List<Seller> listaSeller = sellerDao.findByDepartment(dep);
		
		for (Seller s : listaSeller) {
			System.out.println(s);
		}
		
		System.out.println("=== Test 3 : findAll seller ==");
		listaSeller = sellerDao.findAll();
		
		for (Seller s : listaSeller) {
			System.out.println(s);
		}
		
		DB.closeConnection();

	}

}
