package application;

import java.util.List;
import java.util.Scanner;

import connection.DB;
import dao.DaoFactory;
import dao.SellerDao;
import entities.Department;
import entities.Seller;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

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

		System.out.println("=== Test 4 : insert seller ==");
		/*
		 * Seller novoSeller = new Seller(null, "Greg", "greg@gmail.com", new
		 * java.util.Date(), 4000.0, dep); sellerDao.insert(novoSeller);
		 * System.out.println("Inserted! New id = " + novoSeller.getId());
		 */

		System.out.println("=== Test 5 : update seller ==");
		/*
		 * sel = sellerDao.findById(1); sel.setName("Marta Wayne");
		 * 
		 * sellerDao.update(sel);
		 */

		System.out.println("=== Test 6 : deleteById seller ==");
		System.out.print("Informe um vendedor ID para ser deletado: ");
		int id = sc.nextInt();

		sellerDao.deleteById(id);
		System.out.println("Deletado com sucesso.");

		DB.closeConnection();
		sc.close();
	}

}
