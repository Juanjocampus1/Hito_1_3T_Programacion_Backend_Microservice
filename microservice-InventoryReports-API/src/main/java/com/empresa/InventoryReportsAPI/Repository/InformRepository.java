package com.empresa.InventoryReportsAPI.Repository;

import com.empresa.InventoryReportsAPI.Entities.Inform;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InformRepository extends CrudRepository<Inform, Long> {
}
