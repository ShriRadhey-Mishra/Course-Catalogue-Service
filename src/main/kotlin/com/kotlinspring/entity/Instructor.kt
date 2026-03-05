package com.kotlinspring.entity

import jakarta.persistence.*

@Entity
@Table(name = "Instructors")
data class Instructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,
    var name: String,
    @OneToMany(
        mappedBy = "instructor",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    var courses: List<Course> =mutableListOf()
)
