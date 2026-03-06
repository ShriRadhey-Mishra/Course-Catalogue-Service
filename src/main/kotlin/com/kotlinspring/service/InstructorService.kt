package com.kotlinspring.service

import com.kotlinspring.dto.InstructorDTO
import com.kotlinspring.entity.Instructor
import com.kotlinspring.repository.InstructorRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class InstructorService(val instructorRepository: InstructorRepository) {

    companion object: KLogging()

//    Create Instructor
    fun createInstructor(instructorDTO: InstructorDTO): InstructorDTO {
        val instructor: Instructor = instructorDTO.let {
            Instructor(null, it.name)
        }

        logger.info("Instructor created: $instructor")

        instructorRepository.save(instructor);

        return instructor.let {
            InstructorDTO(null, it.name)
        }
    }
}