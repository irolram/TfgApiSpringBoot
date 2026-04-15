package com.example.TfgApiSpringBoot.repository


import com.example.TfgApiSpringBoot.model.HuertoEntity
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface IHuertoRepository : JpaRepository<HuertoEntity, String>{

    fun findByCreadorId(creadorId: String): List<HuertoEntity>

    @Query(value = """
        SELECT COUNT(*) FROM huertos h 
        WHERE (6371 * acos(cos(radians(:lat)) * cos(radians(h.latitud)) * cos(radians(h.longitud) - radians(:lng)) + sin(radians(:lat)) * sin(radians(h.latitud)))) <= :radio
    """, nativeQuery = true)
    fun contarHuertosEnRadio(
        @Param("lat") lat: Double,
        @Param("lng") lng: Double,
        @Param("radio") radio: Double
    ): Long


    @Modifying
    @Transactional
    fun deleteByCreadorId(creadorId: String)
}

