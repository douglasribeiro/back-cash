package com.cash.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cash.model.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long> {

}
