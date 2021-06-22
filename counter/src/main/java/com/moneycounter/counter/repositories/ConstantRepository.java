package com.moneycounter.counter.repositories;

import com.moneycounter.counter.models.Constant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConstantRepository extends JpaRepository<Constant, Long> {
    List<Constant> findAllByUsername(String username);
}
