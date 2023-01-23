package com.example.demo1.repository;

import com.example.demo1.entities.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

    @Query("SELECT r FROM record r LEFT JOIN r.device device WHERE device.id = :device_id")
    public List<Record> findRecordsbyDevice(@Param("device_id") Long deviceId);

    @Query("SELECT SUM (r.value) FROM record r WHERE r.year =:year")
    public int totalByYear(@Param ("year") Integer year);

    @Query("SELECT r  FROM record r WHERE r.month =:month and r.year=:year")
    public Record oneMonth(@Param ("month") Integer month, @Param ("year") Integer year);

    public Page<Record> findAll(Pageable pageable);

    @Query("SELECT  r  FROM record r WHERE r.year=:year")
    public List<Record> allMonth(@Param ("year") Integer year);



}
