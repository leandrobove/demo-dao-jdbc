package application;

import connection.DB;
import dao.DaoFactory;
import dao.DepartmentDao;
import entities.Department;

public class Program2 {

	public static void main(String[] args) {

		DepartmentDao depDao = DaoFactory.createDepartmentDao();

		System.out.println("=== Test 1 : insert department ==");

		Department novoDep = new Department(null, "AudioBooks");
		//depDao.insert(novoDep);
		System.out.println("Inserted! New id = " + novoDep.getId());

		System.out.println("=== Test 2 : update department ==");
		novoDep.setName("Travel");
		//depDao.update(novoDep);
		System.out.println("Updated!");

		System.out.println("=== Test 3 : deleteById department ==");
		depDao.deleteById(6);
		System.out.println("Deleted!");

		DB.closeConnection();
	}

}
