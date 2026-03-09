package com.kotlinspring.service

import com.kotlinspring.dto.CourseDTO
import com.kotlinspring.entity.Course
import com.kotlinspring.entity.Instructor
import com.kotlinspring.repository.CourseRepository
import mu.KLogging
import org.springframework.stereotype.Service
import java.util.Optional
import kotlin.text.category

@Service
class CourseService(val courseRepository: CourseRepository, val instructorService: InstructorService) {

    companion object : KLogging()

    //    adding courses to the database
    fun addCourse(courseDTO: CourseDTO): CourseDTO {

        val instructorId = courseDTO.instructorId
            ?: throw IllegalArgumentException("InstructorId must not be null")

        val instructor = instructorService.findInstructorById(instructorId)

        val course = Course(
            id = null,
            name = courseDTO.name,
            category = courseDTO.category,
            instructor = instructor
        )

        val savedCourse = courseRepository.save(course)

        return CourseDTO(
            savedCourse.id,
            savedCourse.name,
            savedCourse.category,
            savedCourse.instructor?.id
        )
    }

    //    retrieving all the courses from the database
    fun retrieveAllCourses(courseName: String?): List<CourseDTO> {
        val courses = courseName?.let {
            courseRepository.findByName(courseName)
        } ?: courseRepository.findAll()

        logger.info("Courses retrieved")

        return courses
            .map {
                CourseDTO(it.id, it.name, it.category, it.instructor?.id)
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