package com.imooc.mvcdemo.controller;

import com.imooc.mvcdemo.model.Course;
import com.imooc.mvcdemo.service.CourseService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Map;


@Controller
@RequestMapping("/courses")
public class CourseController {
    private static Logger log = LoggerFactory.getLogger(CourseController.class);

    private CourseService courseService;

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    //本方法将处理 /courses/view?courseId=123 形式的URL
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String viewCourse(@RequestParam("courseId") Integer courseId, Model model) {
        log.debug("In viewCourse, courseId = {}", courseId);
        Course course = courseService.getCoursebyId(courseId);
        model.addAttribute(course);
        return "course_overview";
    }

    //本方法将处理 /courses/view1/123 形式的URL
    @RequestMapping("/view1/{courseId}")
    public String viewCourse1(@PathVariable("courseId") Integer courseId, Map<String, Object> model) {
        log.debug("In viewCourse1, courseId = {}", courseId);
        Course course = courseService.getCoursebyId(courseId);
        model.put("course", course);
        return "course_overview";
    }

    //本方法将处理 /courses/view2?courseId=123 形式的URL
    @RequestMapping("/view2")
    public String viewCourse2(HttpServletRequest request) {
        log.debug("In viewCourse2, courseId = {}");
        Integer courseId = Integer.valueOf(request.getParameter("courseId"));
        Course course = courseService.getCoursebyId(courseId);
        request.setAttribute("course",course);

        return "course_overview";
    }

    //本方法将处理 /courses/admin?add 形式的URL
    @RequestMapping(value = "/admin", method = RequestMethod.GET, params = "add")
    public String createCourse() {
        return "course_admin/edit";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String doSave(Course course) {
        log.debug("Info of Course:");
        log.debug(ReflectionToStringBuilder.toString(course));

        //在此进行业务操作，比如数据库持久化
        course.setCourseId(123);
        return "redirect:view1/"+course.getCourseId();
    }

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String showUploadPage() {
        return "course_admin/file";
    }

    @RequestMapping(value = "/doUpload", method = RequestMethod.POST)
    public String doUploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            log.debug("Process file: {}", file.getOriginalFilename());
            FileUtils.copyInputStreamToFile(file.getInputStream(),
                    new File("E:\\temp\\", System.currentTimeMillis() + file.getOriginalFilename()));
        }
        return "success";
    }
}















