package com.TrainScheduling.TrainSystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.TrainScheduling.TrainSystem.model.Train;

public interface TrainRepository extends JpaRepository<Train, Integer>{

	Optional<Train> findByNumber(String number);
	@Query("SELECT DISTINCT t FROM Train t JOIN t.stations s1 JOIN t.stations s2 " +
		       "WHERE s1.name = ?1 AND s2.name = ?2")
	List<Train> findTrainsByStation(String source, String destination);
}
