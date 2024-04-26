package com.empresa.InventoryReportsAPI.Repository;

import com.empresa.InventoryReportsAPI.Entities.Inform;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InformRepository extends CrudRepository<Inform, Long> {
}
