package com.moneycounter.counter.services;

import com.moneycounter.counter.models.Constant;
import com.moneycounter.counter.models.Month;
import com.moneycounter.counter.models.Position;
import com.moneycounter.counter.repositories.ConstantRepository;
import com.moneycounter.counter.repositories.MonthRepository;
import com.moneycounter.counter.repositories.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@Service
public class CounterService {

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private MonthRepository monthRepository;

    @Autowired
    private ConstantRepository constantRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpServletRequest request;

    public Month getMonth(int monthNum, int yearNum) {
        String username = getCurrentUserUsername();
        return monthRepository.getByUsernameAndMonthNumAndYearNum(username, monthNum, yearNum);
    }

    private String getCurrentUserUsername() {

        HttpHeaders headers = new HttpHeaders();
        String header = request.getHeader("Authorization");
        String token = header.replace("Bearer ", "");

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<?> entity = new HttpEntity<Object>(headers);

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8762/username", HttpMethod.GET, entity, String.class);
        String username = response.getBody();
        if (username == null || username.equals("")) {
            throw new RuntimeException("User is not authenticated!");
        }
        return username;
    }


    public Month addPosition(Position position) {
        Month currentMonth = getCurrentMonth();
        position.setMonth(currentMonth);
        positionRepository.save(position);
        return getCurrentMonth();
    }

    public Month getCurrentMonth() {
        int month = LocalDate.now().getMonthValue();
        int year = LocalDate.now().getYear();
        Month currentMonth = getMonth(month, year);
        if (currentMonth == null) {
            currentMonth = startNewMonth(month, year);
        }
        return currentMonth;
    }

    private Month startNewMonth(int month, int year) {
        Month currentMonth;
        currentMonth = new Month();
        currentMonth.setUsername(getCurrentUserUsername());
        currentMonth.setMonthNum(month);
        currentMonth.setYearNum(year);
        monthRepository.save(currentMonth);

        for (Constant constant : getConstants()) {
            addPositionFromConstant(constant);
        }

        return currentMonth;
    }

    public Month changePosition(Position position) {
        if (position == null) {
            return null;
        }
        if (position.getId()==null || !positionRepository.existsById(position.getId())) {
            return null;
        }
        Position oldPosition = positionRepository.findById(position.getId()).get();
        oldPosition.setCount(position.getCount());
        oldPosition.setConfirmed(position.isConfirmed());
        oldPosition.setDate(position.getDate());
        oldPosition.setName(position.getName());
        positionRepository.save(oldPosition);
        return monthRepository.getById(oldPosition.getMonth().getId());
    }

    public Month deletePosition(Long id) {
        if (!positionRepository.existsById(id)) {
            return null;
        }
        positionRepository.deleteById(id);
        return getCurrentMonth();
    }

    public List<Constant> addConstant(Constant constant, boolean toThisMonth) {
        if (toThisMonth) {
            addPositionFromConstant(constant);
        }
        constantRepository.save(constant);
        return getConstants();
    }

    private void addPositionFromConstant(Constant constant) {
        Position position = new Position();
        position.setName(constant.getName());
        position.setConfirmed(constant.isConfirmed());
        position.setCount(constant.getCount());
        position.setDate("constant");
        addPosition(position);
    }

    public List<Constant> getConstants() {
        return constantRepository.findAllByUsername(getCurrentUserUsername());
    }

    public List<Constant> changeConstant(Constant constant) {
        if (!constantRepository.existsById(constant.getId())) {
            return null;
        }
        Constant oldConstant = constantRepository.findById(constant.getId()).get();
        oldConstant.setName(constant.getName());
        oldConstant.setCount(constant.getCount());
        oldConstant.setConfirmed(constant.isConfirmed());
        oldConstant.setUsername(constant.getUsername());
        constantRepository.save(oldConstant);
        return getConstants();
    }

    public List<Constant> deleteConstant(Long id) {
        if (!constantRepository.existsById(id)) {
            return null;
        }
        constantRepository.deleteById(id);
        return getConstants();
    }

}
