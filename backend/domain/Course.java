package com.shouldibunk.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String courseName;

    @ManyToOne(fetch = FetchType.LAZY) // LAZY = don't load the user unless we explicitly ask for it
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private double attendanceThreshold = 75.0;

    private LocalDate semesterEndDate;

    // These fields are for quick percentage calculation
    private int totalClassesHeld = 0;
    private int classesAttended = 0;

    // Stores the weekly schedule --> {MONDAY : 2, FRIDAY : 1}
    @ElementCollection
    @CollectionTable(name = "course_weekly_schedule")
    @MapKeyEnumerated(EnumType.STRING)
    private Map<DayOfWeek, Integer> weeklySchedule;

}
