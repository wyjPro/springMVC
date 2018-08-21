package com.imooc.mvcdemo.service;

import com.imooc.mvcdemo.model.Course;
import org.springframework.stereotype.Service;

@Service
public interface CourseService {
    Course getCoursebyId(Integer courseId);
}
