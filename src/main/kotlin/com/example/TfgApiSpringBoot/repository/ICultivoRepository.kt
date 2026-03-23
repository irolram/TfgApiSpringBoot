package com.example.TfgApiSpringBoot.repository


import com.example.TfgApiSpringBoot.model.CultivoEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ICultivoRepository : JpaRepository<CultivoEntity, String> {
    fun findByHuertoId(huertoId: String): List<CultivoEntity>
}