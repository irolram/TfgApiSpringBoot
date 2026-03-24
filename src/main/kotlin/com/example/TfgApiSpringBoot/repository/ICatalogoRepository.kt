package com.example.TfgApiSpringBoot.repository


import com.example.TfgApiSpringBoot.model.CatalogoEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ICatalogoRepository : JpaRepository<CatalogoEntity, Long> {
    fun findByNombreContainingIgnoreCase(nombre: String): List<CatalogoEntity>
}