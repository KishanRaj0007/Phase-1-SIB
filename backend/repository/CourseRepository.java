package com.shouldibunk.backend.repository;

import com.shouldibunk.backend.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
