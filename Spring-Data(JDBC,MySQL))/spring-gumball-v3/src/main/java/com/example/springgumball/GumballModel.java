
package com.example.springgumball;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(indexes=@Index(name = "altIndex", columnList = "serialNumber", unique = true))
@Data
@RequiredArgsConstructor
class GumballModel {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
    private String modelNumber ;
    private String serialNumber ;
    private Integer countGumballs ;
    
}

