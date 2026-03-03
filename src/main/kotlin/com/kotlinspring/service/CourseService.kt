package com.kotlinspring.service

import com.kotlinspring.dto.CourseDTO
import com.kotlinspring.entity.Course
import com.kotlinspring.repository.CourseRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class CourseService(val courseRepository: CourseRepository) {

    companion object : KLogging()

    //    adding courses to the database
    fun addCourse(courseDTO: CourseDTO): CourseDTO {
        val course: Course = courseDTO.let {
            Course(null, it.name, it.category)
        }

        courseRepository.save(course)

        logger.info("Course added: $course")
        return course.let {
            CourseDTO(it.id, it.name, it.category)
        }
    }

    //    retrieving all the courses from the database
    fun retrieveAllCourses(): List<CourseDTO> {
        logger.info("Courses retrieved")

        return courseRepository
            .findAll()
            .map {
                CourseDTO(it.id, it.name, it.category)
            }
    }

    //    updating a course information
    fun updateCourse(courseDTO: CourseDTO, id: Int): CourseDTO {
        val course = courseRepository.findById(id)

        return if (course.isPresent) {
            course
                .get()
                .let {
                    it.name = courseDTO.name
                    it.category = courseDTO.category
                    courseRepository.save(it)
                    logger.info("Course updated: $it")
                    CourseDTO(it.id, it.name, it.category)
                }
        } else {
            logger.error("Course with id $id not found")
            throw NoSuchElementException("Course with id $id not found")
        }
    }

    //    Delete an existing course from the database
    fun deleteCourse(id: Int) {
        val course = courseRepository.findById(id)

        if (course.isPresent) {
            courseRepository.deleteById(id)
            logger.info("Course with id $id deleted")
        } else {
            logger.error("Course with id $id not found")
            throw NoSuchElementException("Course with id $id not found")
        }
    }
}