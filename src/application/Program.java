package application;

import java.util.Date;

import dao.DaoFactory;
import dao.SellerDao;
import entities.Department;
import entities.Seller;

public class Program {

	public static void main(String[] args) {

		Department dep = new Department(1, "Books");

		Seller sel = new Seller(1, "John", "john@gmail.com", new Date(), 3000.0, dep);
		
		SellerDao sellerDao = DaoFactory.createSellerDao();

		System.out.println(sel);

	}

}
