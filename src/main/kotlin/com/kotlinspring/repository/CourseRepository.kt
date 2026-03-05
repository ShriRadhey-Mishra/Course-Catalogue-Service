package com.kotlinspring.repository

import com.kotlinspring.entity.Course
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface CourseRepository: CrudRepository<Course, Int> {

    @Query(value = "SELECT * FROM courses WHERE name = %?1%", nativeQuery = true)
    fun findByName(name: String): List<Course>
}