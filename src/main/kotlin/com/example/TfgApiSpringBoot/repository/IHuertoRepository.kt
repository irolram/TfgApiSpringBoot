package com.example.TfgApiSpringBoot.repository


import com.example.TfgApiSpringBoot.model.HuertoEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface HuertoRepository : JpaRepository<HuertoEntity, String>