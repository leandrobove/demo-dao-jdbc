package dao;

import java.util.List;

import entities.Seller;

public interface SellerDao {

	void insert(Seller sel);

	void update(Seller sel);

	void deleteById(Integer id);

	Seller findById(Integer id);

	List<Seller> findAll();
}
