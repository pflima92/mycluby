package br.com.techfullit.mycluby.services.employee.business;

import org.springframework.transaction.annotation.Transactional;

import br.com.techfullit.mycluby.common.models.Employee;
import br.com.techfullit.mycluby.common.models.ResponseEntity;

public interface EmployeeService {

    @Transactional
    public ResponseEntity insert(Employee employee) throws Exception;

    @Transactional
    public ResponseEntity update(Employee employee) throws Exception;

    @Transactional
    public ResponseEntity delete(Employee employee) throws Exception;

}
