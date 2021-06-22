package com.moneycounter.counter.repositories;

import com.moneycounter.counter.models.Month;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonthRepository extends JpaRepository<Month, Long> {
    Month getByUsernameAndMonthNumAndYearNum(String username, int monthNum, int yearNum);
}
