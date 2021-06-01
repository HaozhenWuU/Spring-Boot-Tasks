package com.example.springgumball;

import org.springframework.data.repository.CrudRepository;
import com.example.springgumball.GumballModel;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;



// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface DataRepository extends CrudRepository<GumballModel, String> {

	@Query("SELECT gd.modelNumber FROM GumballModel gd where gd.modelNumber = :modelNumber") 
    public String findByModelNumber(@Param("modelNumber") String modelNumber);

    @Query("SELECT gd.serialNumber FROM GumballModel gd where gd.serialNumber = :serialNumber") 
    public String findBySerialNumber(@Param("serialNumber") String serialNumber);
}