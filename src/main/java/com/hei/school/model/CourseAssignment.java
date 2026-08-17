package com.hei.school.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "course_assignment" , uniqueConstraints = @UniqueConstraint(columnNames = {"course_id" , "teacher_id" , "group_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CourseAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id" , nullable = false)
    private Course course;

    @ManyToOne(optional = false)
    @JoinColumn(name = "teacher_id" , nullable = false)
    private Teacher teacher;

    @ManyToOne(optional = false)
    @JoinColumn(name = "group_id" , nullable = false)
    private Group group;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CourseAssignment other)) return false;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

