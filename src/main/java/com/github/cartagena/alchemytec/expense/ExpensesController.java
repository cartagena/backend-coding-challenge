package com.github.cartagena.alchemytec.expense;

import static java.util.stream.Collectors.joining;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/expenses")
public class ExpensesController {

    private final ExpenseRepository repository;

    @Autowired
    public ExpensesController(final ExpenseRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = POST)
    public ResponseEntity<Void> create(@RequestBody final Expense expense) {
        if(expense == null) {
            return badRequest().build();
        }

        repository.save(expense);

        return ok().build();
    }

    @RequestMapping(method = GET)
    public List<Expense> list() {
        return repository.findAll();
    }

    @ExceptionHandler({ConstraintViolationException.class})
    void handleIllegalArgumentException(final HttpServletResponse response, final ConstraintViolationException e) throws IOException {
        final String msg = e.getConstraintViolations()
                .stream()
                .map(s -> {
                    return s.getPropertyPath().toString() + " " + s.getMessage();
                })
                .collect(joining(", "));

        response.sendError(BAD_REQUEST.value(), msg);
    }

}
