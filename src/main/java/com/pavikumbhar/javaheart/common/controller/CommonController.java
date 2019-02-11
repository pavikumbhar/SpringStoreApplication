package com.pavikumbhar.javaheart.common.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pavikumbhar.javaheart.model.Student;
/**
 * 
 * @author pavikumbhar
 *
 */
public class CommonController {

}


@RestController
@RequestMapping("/student")
class StudentController extends GenericController<Student, Long> {
}
