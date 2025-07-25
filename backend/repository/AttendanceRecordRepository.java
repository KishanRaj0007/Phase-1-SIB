package com.shouldibunk.backend.repository;

import com.shouldibunk.backend.domain.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Long> {
}
