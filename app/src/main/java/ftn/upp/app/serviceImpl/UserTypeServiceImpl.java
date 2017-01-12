package ftn.upp.app.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.upp.app.model.UserType;
import ftn.upp.app.repository.UserTypeRepository;
import ftn.upp.app.service.UserTypeService;

@Service
public class UserTypeServiceImpl implements UserTypeService{

	@Autowired
	UserTypeRepository userTypeRepository;

	@Override
	public UserType findOne(int id) {
		return userTypeRepository.findOne(id);
	}

	@Override
	public List<UserType> findAll() {
		return (List<UserType>) userTypeRepository.findAll();
	}
	
	
}
