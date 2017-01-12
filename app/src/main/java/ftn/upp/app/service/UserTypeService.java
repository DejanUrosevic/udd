package ftn.upp.app.service;

import java.util.List;

import ftn.upp.app.model.UserType;

public interface UserTypeService {

	public UserType findOne(int id);
	
	public List<UserType> findAll();
}
