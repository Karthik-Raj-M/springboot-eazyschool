package com.eazybytes.eazyschool.repository;

import com.eazybytes.eazyschool.bean.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
}
