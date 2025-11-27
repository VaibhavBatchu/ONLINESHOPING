package com.klef.fsd.service;

import java.util.List;

import com.klef.fsd.model.Address;

public interface AddressService {
	Address addAddress(Address address, String buyerId);
	void deleteAddress(String addressId);
	List<Address> getAddressesByBuyer(String buyerId);
}