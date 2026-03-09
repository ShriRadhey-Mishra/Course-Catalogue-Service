package com.kotlinspring.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CourseDTO(
    val id: Int?,
    @get: NotBlank(message = "courseDTO.name must not be blank")
    val name: String,
    @get: NotBlank(message = "courseDTO.category must not be blank")
    val category: String,
    @get: NotNull(message = "courseDTO.instructorId must not be null")
    @JsonProperty("instructor_id")
    val instructorId: Int? = null
)

