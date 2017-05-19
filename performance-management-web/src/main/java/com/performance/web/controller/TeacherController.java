package com.performance.web.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSON;
import com.performance.persist.common.JsonPage;
import com.performance.persist.common.JsonResult;
import com.performance.persist.domain.Person;
import com.performance.persist.domain.TeacherPerformance;
import com.performance.service.TeacherService;

@Controller
@RequestMapping(value = "/teacher")
public class TeacherController {

    private static Pattern NAME_PATTERN = Pattern.compile("[A-Za-z0-9()\\-\\+\\*_\u4e00-\u9fa5]*");
    private static Pattern Number_PATTERN = Pattern.compile("[1-9][0-9]*");

    @Autowired
    private TeacherService teacherService;

    @RequestMapping(value = "/SubMyPerformance")
    private String SubMyPerformance() {
        return "SubMyPerformance";
    }

    @RequestMapping(value = "/showMyPerformance")
    private String showMyPerformance() {
        return "showMyPerformance";
    }

    @RequestMapping(value = "/editMyInfomation")
    private String editMyInfomation() {
        return "editMyInfomation";
    }

    //        @RequestMapping(value = "/upload")
    //        private String upload() {
    //            return "upload";
    //        }

    @RequestMapping(value = "/myPerformance")
    private String myPerformance() {
        return "myPerformance";
    }

    /**
     * 教师注册
     * */
    @RequestMapping(value = "/teacherRegister", method = RequestMethod.POST)
    public ResponseEntity<JsonResult<Boolean>> teacherRegister(String teacherJSON) {
        JsonResult<Boolean> jr = new JsonResult<Boolean>();
        if (teacherJSON == null) {
            jr.setStatus(3); //3-参数为空
            jr.setMsg("参数为空");
            jr.setData(false);
        } else {
            Person person = JSON.parseObject("teacherJSON", Person.class);
            try {

                if (!Number_PATTERN.matcher(person.getId().toString()).matches()) {
                    throw new RuntimeException("参数错误：工号不符合格式要求");
                }

                if (!NAME_PATTERN.matcher(person.getName()).matches()
                    || person.getName().length() < 0 || person.getName().length() > 20) {
                    throw new RuntimeException("参数错误：名字不符合格式要求");
                }

                if (!NAME_PATTERN.matcher(person.getPassword()).matches()
                    || person.getPassword().length() < 0 || person.getPassword().length() > 20) {
                    throw new RuntimeException("参数错误：密码不符合格式要求");
                }

                if (!person.getSex().equals("女") && !person.getSex().equals("男")) {
                    throw new RuntimeException("参数错误：性别不符合要求");
                }

                if (person.getAge() < 0 || person.getAge() > 120) {
                    throw new RuntimeException("参数错误：年龄不符合要求");
                }

                if (!person.getTitle().equals("助教") && !person.getTitle().equals("讲师")
                    && !person.getTitle().equals("副教授") && !person.getTitle().equals("教授")) {
                    throw new RuntimeException("参数错误：职称不符合要求");
                }

                Boolean result = teacherService.saveRegisterPerson(person);
                if (true == result) {
                    jr.setStatus(0);
                    jr.setMsg("注册成功");
                    jr.setData(result);
                } else {
                    jr.setStatus(1);
                    jr.setMsg("注册失败");
                    jr.setData(result);
                }
            } catch (Exception e) {
                if (e.getMessage().startsWith("参数错误")) {
                    jr.setStatus(4);
                    jr.setMsg(e.getMessage());
                } else {
                    jr.setStatus(2);
                    jr.setMsg("系统异常");
                }
                jr.setData(false);
            }

        }
        return new ResponseEntity<JsonResult<Boolean>>(jr, HttpStatus.OK);
    }

    /**
     * 验证注册id是否已存在
     * */
    @RequestMapping(value = "/idValidate", method = RequestMethod.GET)
    public ResponseEntity<JsonResult<Boolean>> idValidate(Long id) {
        JsonResult<Boolean> jr = new JsonResult<Boolean>();
        if (null == id) {
            jr.setData(false);
            jr.setMsg("参数为空");
            jr.setStatus(3);
        } else {
            try {
                Person person = teacherService.idVilidate(id);
                if (null == person) {
                    jr.setData(true);
                    jr.setMsg("该id可用");
                    jr.setStatus(0);
                } else {
                    jr.setData(false);
                    jr.setMsg("用户已存在");
                    jr.setStatus(1);
                }
            } catch (Exception e) {
                jr.setData(false);
                jr.setMsg("系统异常");
                jr.setStatus(2);
            }
        }
        return new ResponseEntity<JsonResult<Boolean>>(jr, HttpStatus.OK);
    }

    /**
     * 教师登录
     * */
    @RequestMapping(value = "/teacherLogin", method = RequestMethod.GET)
    public ResponseEntity<JsonResult<Boolean>> teacherLogin(Long id, String password,
                                                            HttpServletRequest request) {

        //public String teacherLogin(Long id, String password, HttpServletRequest request) {

        JsonResult<Boolean> jr = new JsonResult<Boolean>();
        if (null == id || null == password) {
            jr.setData(false);
            jr.setMsg("参数为空");
            jr.setStatus(3); //3-参数为空
        } else {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", id);
            map.put("password", password);
            map.put("grade", 2); //教师
            try {
                Person person = teacherService.teacherLogin(map);
                if (null == person || "1".equals(person.getStatus())) { //删除
                    jr.setStatus(1);
                    jr.setMsg("账号或密码错误");
                    jr.setData(false);
                } else if ("2".equals(person.getStatus())) { //审核中
                    jr.setStatus(4);
                    jr.setMsg("审核中...");
                    jr.setData(false);
                } else if ("3".equals(person.getStatus())) { //未通过
                    jr.setStatus(5);
                    jr.setMsg("很抱歉，您未通过审核！");
                    jr.setData(false);
                } else if ("0".equals(person.getStatus())) { //正常
                    jr.setStatus(0);
                    jr.setMsg("登录成功");
                    jr.setData(true);
                    request.getSession().setAttribute("teacherId", id);
                }
            } catch (Exception e) {
                jr.setStatus(2);
                jr.setMsg("系统异常");
                jr.setData(false);
            }
        }
        return new ResponseEntity<JsonResult<Boolean>>(jr, HttpStatus.OK);
    }

    /**
     * 获取教师姓名
     * */
    @RequestMapping(value = "/getTeacherName", method = RequestMethod.GET)
    public ResponseEntity<JsonResult<String>> getTeacherName(HttpServletRequest request) {
        JsonResult<String> jr = new JsonResult<String>();
        Long id = (Long) request.getSession().getAttribute("teacherId");
        if (null == id) {
            jr.setData(null);
            jr.setMsg("未获取教师工号");
            jr.setStatus(3);
        } else {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", id);
            map.put("grade", 2); //教师
            map.put("status", "0"); //正常
            try {
                String name = teacherService.getTeacherName(map);
                if (null == name) {
                    jr.setData(null);
                    jr.setMsg("该教师不存在或已被删除");
                    jr.setStatus(1);
                } else {
                    jr.setData(name);
                    jr.setMsg("查询成功");
                    jr.setStatus(0);
                }
            } catch (Exception e) {
                jr.setData(null);
                jr.setMsg("系统异常");
                jr.setStatus(2);
            }
        }
        return new ResponseEntity<JsonResult<String>>(jr, HttpStatus.OK);
    }

    /**
     * 查看教师信息
     * */
    @RequestMapping(value = "/getTeacherInfo", method = RequestMethod.GET)
    public ResponseEntity<JsonResult<Person>> getTeacherInfo(HttpServletRequest request) {
        JsonResult<Person> jr = new JsonResult<Person>();
        Long id = (Long) request.getSession().getAttribute("teacherId");
        if (null == id) {
            jr.setStatus(3);
            jr.setMsg("参数为空");
            jr.setData(null);
        } else {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", id);
            map.put("grade", 2); //教师
            map.put("status", 0);
            try {
                Person person = teacherService.getTeacherInfo(map);
                if (null == person) {
                    jr.setStatus(2);
                    jr.setMsg("查询失败，无该用户");
                    jr.setData(null);
                } else {
                    jr.setStatus(0);
                    jr.setMsg("查询成功");
                    jr.setData(person);
                }
            } catch (Exception e) {
                jr.setStatus(2);
                jr.setMsg("系统异常");
                jr.setData(null);
            }
        }
        return new ResponseEntity<JsonResult<Person>>(jr, HttpStatus.OK);
    }

    /**
     * 修改密码
     * */
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public ResponseEntity<JsonResult<Boolean>> updatePassword(HttpServletRequest request,
                                                              String password) {
        JsonResult<Boolean> jr = new JsonResult<Boolean>();
        Long id = (Long) request.getSession().getAttribute("teacherId");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("password", password);
        try {
            String result = teacherService.updatePassword(map);
            if (result.equals("修改成功")) {
                jr.setData(true);
                jr.setMsg(result);
                jr.setStatus(0);
            } else if (result.equals("该用户不存在")) {
                jr.setData(false);
                jr.setMsg(result);
                jr.setStatus(3);
            } else {
                jr.setData(false);
                jr.setMsg(result);
                jr.setStatus(1);
            }
        } catch (Exception e) {
            jr.setData(false);
            jr.setMsg("系统异常");
            jr.setStatus(2);
        }
        return new ResponseEntity<JsonResult<Boolean>>(jr, HttpStatus.OK);
    }

    /**
     * 修改教师信息
     * */
    @RequestMapping(value = "/updateTeacherInfo", method = RequestMethod.POST)
    public ResponseEntity<JsonResult<Boolean>> updateTeacherInfo(@RequestBody Person person) {
        JsonResult<Boolean> jr = new JsonResult<Boolean>();
        try {
            String result = teacherService.updateTeacherInfo(person);
            if (result.equals("修改成功")) {
                jr.setData(true);
                jr.setMsg(result);
                jr.setStatus(0);
            } else if (result.equals("该用户不存在")) {
                jr.setData(false);
                jr.setMsg(result);
                jr.setStatus(3);
            } else {
                jr.setData(false);
                jr.setMsg(result);
                jr.setStatus(1);
            }
        } catch (Exception e) {
            jr.setData(false);
            jr.setMsg("系统异常");
            jr.setStatus(2);
        }
        return new ResponseEntity<JsonResult<Boolean>>(jr, HttpStatus.OK);
    }

    /**
     * 教师录入绩效
     * */
    @SuppressWarnings("unused")
    @RequestMapping(value = "/entryPerformance", method = RequestMethod.POST)
    public ResponseEntity<JsonResult<Boolean>> entryPerformance(@RequestBody TeacherPerformance teacherPerformance,
                                                                HttpServletRequest request,
                                                                HttpServletResponse response) {
        JsonResult<Boolean> jr = new JsonResult<Boolean>();
        Long id = (Long) request.getSession().getAttribute("teacherId");
        if ((null == teacherPerformance.getContent() || "".equals(teacherPerformance.getContent()))
            && (null == teacherPerformance.getProject()
                || "".equals(teacherPerformance.getProject()))
            && (null == teacherPerformance.getProGrade()
                || "".equals(teacherPerformance.getProGrade()))) {
            jr.setData(false);
            jr.setMsg("未选择绩效");
            jr.setStatus(3);
        } else {
            try {
                if (null != teacherPerformance.getUpload()
                    && !"".equals(teacherPerformance.getUpload())) {
                    String path = request.getSession().getServletContext().getRealPath("upload");
                    String fileName = teacherPerformance.getUpload().split("\\\\")[2];
                    File dir = new File(path, fileName);
                    if (!dir.exists()) {
                        jr.setData(false);
                        jr.setMsg("未上传文件");
                        jr.setStatus(4);
                    } else {
                        String upload = teacherPerformance.getUpload().split("\\\\")[2];
                        teacherPerformance.setUpload(upload);
                        teacherPerformance.setId(id);
                        Boolean result = teacherService.saveTeacherPerformance(teacherPerformance);
                        if (true == result) {
                            jr.setData(true);
                            jr.setMsg("绩效录入成功，待审核");
                            jr.setStatus(0);
                        } else {
                            jr.setData(false);
                            jr.setMsg("绩效录入失败");
                            jr.setStatus(1);
                        }
                    }
                } else {
                    teacherPerformance.setId(id);
                    Boolean result = teacherService.saveTeacherPerformance(teacherPerformance);
                    if (true == result) {
                        jr.setData(true);
                        jr.setMsg("绩效录入成功，待审核");
                        jr.setStatus(0);
                    } else {
                        jr.setData(false);
                        jr.setMsg("绩效录入失败");
                        jr.setStatus(1);
                    }
                }
            } catch (Exception e) {
                jr.setData(false);
                jr.setMsg("系统异常");
                jr.setStatus(2);
            }
        }
        return new ResponseEntity<JsonResult<Boolean>>(jr, HttpStatus.OK);
    }

    /**
     * 教师查看待审核绩效
     * */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/getCheckPerformance", method = RequestMethod.GET)
    public ResponseEntity<JsonPage<List<TeacherPerformance>>> getCheckPerformance(HttpServletRequest request,
                                                                                  Integer pageSize,
                                                                                  Integer pageNum) {

        JsonPage<List<TeacherPerformance>> jp = new JsonPage<List<TeacherPerformance>>();
        Long id = (Long) request.getSession().getAttribute("teacherId");
        int pSize = pageSize == null ? 20 : pageSize;
        int pNum = pageNum == null ? 1 : pageNum;
        if (null == id) {
            jp.setData_list(null);
            jp.setMsg("参数为空");
            jp.setPageNum(pNum);
            jp.setPageSize(pSize);
            jp.setStatus(3);
            jp.setTotal(0);
        } else {
            try {
                Map<String, Object> map = teacherService.getCheckPerformance(id, pSize, pNum);
                if (null == map.get("teacherPerformanceList")) {
                    jp.setData_list(null);
                    jp.setMsg("无待审核绩效");
                    jp.setPageNum(pNum);
                    jp.setPageSize(pSize);
                    jp.setStatus(1);
                    jp.setTotal((int) map.get("total"));
                } else {
                    jp.setData_list((List<TeacherPerformance>) map.get("teacherPerformanceList"));
                    jp.setMsg("查询待审核绩效成功");
                    jp.setPageNum(pNum);
                    jp.setPageSize(pSize);
                    jp.setStatus(0);
                    jp.setTotal((int) map.get("total"));
                }
            } catch (Exception e) {
                jp.setData_list(null);
                jp.setMsg("系统异常");
                jp.setPageNum(pNum);
                jp.setPageSize(pSize);
                jp.setStatus(2);
                jp.setTotal(0);
            }
        }
        return new ResponseEntity<JsonPage<List<TeacherPerformance>>>(jp, HttpStatus.OK);
    }

    /**
     * 教师删除待审核绩效
     * */
    @RequestMapping(value = "/deleteCheckPerformance", method = RequestMethod.POST)
    public ResponseEntity<JsonResult<Boolean>> deleteCheckPerformance(@RequestBody List<Long> idList) {
        JsonResult<Boolean> jr = new JsonResult<Boolean>();
        if (null == idList) {
            jr.setData(false);
            jr.setMsg("未选择要删除的绩效");
            jr.setStatus(3);
        } else {
            try {
                int result = teacherService.deleteCheckPerformance(idList);
                if (idList.size() == result) {
                    jr.setData(true);
                    jr.setMsg("删除成功");
                    jr.setStatus(0);
                } else {
                    jr.setData(false);
                    jr.setMsg("删除失败");
                    jr.setStatus(1);
                }
            } catch (Exception e) {
                jr.setData(false);
                jr.setMsg("系统异常");
                jr.setStatus(2);
            }
        }
        return new ResponseEntity<JsonResult<Boolean>>(jr, HttpStatus.OK);
    }

    /**
     * 总分排名
     * */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/totalRank", method = RequestMethod.GET)
    public ResponseEntity<JsonPage<List<Person>>> totalRank(HttpServletRequest request,
                                                            Integer pageSize, Integer pageNum) {
        JsonPage<List<Person>> jp = new JsonPage<List<Person>>();
        int pSize = pageSize == null ? 20 : pageSize;
        int pNum = pageNum == null ? 1 : pageNum;
        Long id = (Long) request.getSession().getAttribute("teacherId");
        if (null == id) {
            jp.setData_list(null);
            jp.setMsg("参数为空");
            jp.setStatus(3);
            jp.setPageNum(pNum);
            jp.setPageSize(pSize);
            jp.setTotal(0);
        } else {
            try {
                Map<String, Object> map = teacherService.totalRank(id, pSize, pNum);
                if (null == map.get("personList")) {
                    jp.setData_list(null);
                    jp.setMsg("查询失败");
                    jp.setPageNum(pNum);
                    jp.setPageSize(pSize);
                    jp.setStatus(1);
                    jp.setTotal((int) map.get("count"));
                } else {
                    jp.setData_list((List<Person>) map.get("personList"));
                    jp.setMsg("当前排名为" + ((int) map.get("rank") + 1) + "名");
                    jp.setPageNum(pNum);
                    jp.setPageSize(pSize);
                    jp.setStatus(0);
                    jp.setTotal((int) map.get("count"));
                }
            } catch (Exception e) {
                jp.setData_list(null);
                jp.setMsg("系统异常");
                jp.setPageNum(pNum);
                jp.setPageSize(pSize);
                jp.setStatus(2);
                jp.setTotal(0);
            }
        }
        return new ResponseEntity<JsonPage<List<Person>>>(jp, HttpStatus.OK);
    }

    /**
     * 按科研总分排名
     * */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/scientificResearchRank", method = RequestMethod.GET)
    public ResponseEntity<JsonPage<List<Person>>> scientificResearchRank(Long id, Integer pageSize,
                                                                         Integer pageNum) {
        JsonPage<List<Person>> jp = new JsonPage<List<Person>>();
        if (null == id) {
            jp.setData_list(null);
            jp.setMsg("参数为空");
            jp.setPageNum(pageNum);
            jp.setPageSize(pageSize);
            jp.setStatus(3);
            jp.setTotal(0);
        } else {
            int pSize = pageSize == null ? 20 : pageSize;
            int pNum = pageNum == null ? 1 : pageNum;
            try {
                Map<String, Object> map = teacherService.scientificResearchRank(id, pSize, pNum);
                if (null == map.get("personList")) {
                    jp.setData_list(null);
                    jp.setMsg("查询失败");
                    jp.setPageNum(pNum);
                    jp.setPageSize(pSize);
                    jp.setStatus(1);
                    jp.setTotal((int) map.get("count"));
                } else {
                    jp.setData_list((List<Person>) map.get("personList"));
                    jp.setMsg("当前排名为" + ((int) map.get("rank") + 1) + "名");
                    jp.setPageNum(pNum);
                    jp.setPageSize(pSize);
                    jp.setStatus(0);
                    jp.setTotal((int) map.get("count"));
                }
            } catch (Exception e) {
                jp.setData_list(null);
                jp.setMsg("系统异常");
                jp.setPageNum(pNum);
                jp.setPageSize(pSize);
                jp.setStatus(2);
                jp.setTotal(0);
            }
        }
        return new ResponseEntity<JsonPage<List<Person>>>(jp, HttpStatus.OK);
    }

    /**
     * 按教研总分排名
     * */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/teachingResearchRank", method = RequestMethod.GET)
    public ResponseEntity<JsonPage<List<Person>>> teachingResearchRank(Long id, Integer pageSize,
                                                                       Integer pageNum) {
        JsonPage<List<Person>> jp = new JsonPage<List<Person>>();
        if (null == id) {
            jp.setData_list(null);
            jp.setMsg("参数为空");
            jp.setPageNum(pageNum);
            jp.setPageSize(pageSize);
            jp.setStatus(3);
            jp.setTotal(0);
        } else {
            int pSize = pageSize == null ? 20 : pageSize;
            int pNum = pageNum == null ? 1 : pageNum;
            try {
                Map<String, Object> map = teacherService.teachingResearchRank(id, pSize, pNum);
                if (null == map.get("personList")) {
                    jp.setData_list(null);
                    jp.setMsg("查询失败");
                    jp.setPageNum(pNum);
                    jp.setPageSize(pSize);
                    jp.setStatus(1);
                    jp.setTotal((int) map.get("count"));
                } else {
                    jp.setData_list((List<Person>) map.get("personList"));
                    jp.setMsg("当前排名为" + ((int) map.get("rank") + 1) + "名");
                    jp.setPageNum(pNum);
                    jp.setPageSize(pSize);
                    jp.setStatus(0);
                    jp.setTotal((int) map.get("count"));
                }
            } catch (Exception e) {
                jp.setData_list(null);
                jp.setMsg("系统异常");
                jp.setPageNum(pNum);
                jp.setPageSize(pSize);
                jp.setStatus(2);
                jp.setTotal(0);
            }
        }
        return new ResponseEntity<JsonPage<List<Person>>>(jp, HttpStatus.OK);
    }

    /**
     * 获取科研内容
     * */
    @RequestMapping(value = "/getSciContent", method = RequestMethod.GET)
    public ResponseEntity<JsonResult<List<String>>> getSciContent() {
        JsonResult<List<String>> jr = new JsonResult<List<String>>();
        try {
            List<String> sciContentList = teacherService.getSciContent();
            if (null == sciContentList) {
                jr.setData(null);
                jr.setMsg("查无数据");
                jr.setStatus(1);
            } else {
                jr.setData(sciContentList);
                jr.setMsg("查询成功");
                jr.setStatus(0);
            }
        } catch (Exception e) {
            jr.setData(null);
            jr.setMsg("系统异常");
            jr.setStatus(2);
        }
        return new ResponseEntity<JsonResult<List<String>>>(jr, HttpStatus.OK);
    }

    /**
     * 获取教研内容
     * */
    @RequestMapping(value = "/getTeaContent", method = RequestMethod.GET)
    public ResponseEntity<JsonResult<List<String>>> getTeaContent() {
        JsonResult<List<String>> jr = new JsonResult<List<String>>();
        try {
            List<String> teaContentList = teacherService.getTeaContent();
            if (null == teaContentList) {
                jr.setData(null);
                jr.setMsg("查无数据");
                jr.setStatus(1);
            } else {
                jr.setData(teaContentList);
                jr.setMsg("查询成功");
                jr.setStatus(0);
            }
        } catch (Exception e) {
            jr.setData(null);
            jr.setMsg("系统异常");
            jr.setStatus(2);
        }
        return new ResponseEntity<JsonResult<List<String>>>(jr, HttpStatus.OK);
    }

    /**
     * 通过科研内容获取科研具体工作
     * */
    @RequestMapping(value = "/getSciProjectBySciContent", method = RequestMethod.POST)
    public ResponseEntity<JsonResult<List<String>>> getSciProjectBySciContent(String sciContent) {
        JsonResult<List<String>> jr = new JsonResult<List<String>>();
        Map<String, Object> map = new HashMap<String, Object>();
        if (!StringUtils.isEmpty(sciContent)) {
            map.put("sciContent", sciContent);
        }
        map.put("status", "0"); //正常
        try {
            List<String> sciProjectList = teacherService.getSciProjectBySciContent(map);
            if (null == sciProjectList) {
                jr.setData(null);
                jr.setMsg("查无数据");
                jr.setStatus(1);
            } else {
                jr.setData(sciProjectList);
                jr.setMsg("查询成功");
                jr.setStatus(0);
            }
        } catch (Exception e) {
            jr.setData(null);
            jr.setMsg("系统异常");
            jr.setStatus(2);
        }
        return new ResponseEntity<JsonResult<List<String>>>(jr, HttpStatus.OK);
    }

    /**
     * 根据教研内容获取教研具体工作
     * */
    @RequestMapping(value = "/getTeaProjectByTeaContent", method = RequestMethod.POST)
    public ResponseEntity<JsonResult<List<String>>> getTeaProjectByTeaContent(String teaContent) {
        JsonResult<List<String>> jr = new JsonResult<List<String>>();
        Map<String, Object> map = new HashMap<String, Object>();
        if (!StringUtils.isEmpty(teaContent)) {
            map.put("teaContent", teaContent);
        }
        map.put("status", "0");
        try {
            List<String> teaContentList = teacherService.getTeaProjectByTeaContent(map);
            if (null == teaContentList) {
                jr.setData(null);
                jr.setMsg("查无数据");
                jr.setStatus(1);
            } else {
                jr.setData(teaContentList);
                jr.setMsg("查询成功");
                jr.setStatus(0);
            }
        } catch (Exception e) {
            jr.setData(null);
            jr.setMsg("系统异常");
            jr.setStatus(2);
        }
        return new ResponseEntity<JsonResult<List<String>>>(jr, HttpStatus.OK);
    }

    /**
     * 根据科研内容和科研具体工作获取科研等级
     * */
    @RequestMapping(value = "/getSciGradeBySciContentAndSciProject", method = RequestMethod.POST)
    public ResponseEntity<JsonResult<List<String>>> getSciGradeBySciContentAndSciProject(String sciContent,
                                                                                         String sciProject) {
        JsonResult<List<String>> jr = new JsonResult<List<String>>();
        Map<String, Object> map = new HashMap<String, Object>();
        if (!StringUtils.isEmpty(sciContent)) {
            map.put("sciContent", sciContent);
        }
        if (!StringUtils.isEmpty(sciProject)) {
            map.put("sciProject", sciProject);
        }
        map.put("status", "0");
        try {
            List<String> sciGradeList = teacherService.getSciGradeBySciContentAndSciProject(map);
            if (null == sciGradeList) {
                jr.setData(null);
                jr.setMsg("查无数据");
                jr.setStatus(1);
            } else {
                jr.setData(sciGradeList);
                jr.setMsg("查询成功");
                jr.setStatus(0);
            }
        } catch (Exception e) {
            jr.setData(null);
            jr.setMsg("系统异常");
            jr.setStatus(2);
        }
        return new ResponseEntity<JsonResult<List<String>>>(jr, HttpStatus.OK);
    }

    /**
     * 根据教研内容和教研具体工作获取教研等级
     * */
    @RequestMapping(value = "/getTeaGradeByTeaContentAndTeaProject", method = RequestMethod.POST)
    public ResponseEntity<JsonResult<List<String>>> getTeaGradeByTeaContentAndTeaProject(String teaContent,
                                                                                         String teaProject) {
        JsonResult<List<String>> jr = new JsonResult<List<String>>();
        Map<String, Object> map = new HashMap<String, Object>();
        if (!StringUtils.isEmpty(teaContent)) {
            map.put("teaContent", teaContent);
        }
        if (!StringUtils.isEmpty(teaProject)) {
            map.put("teaProject", teaProject);
        }
        map.put("status", "0");
        try {
            List<String> teaGradeList = teacherService.getTeaGradeByTeaContentAndTeaProject(map);
            if (null == teaGradeList) {
                jr.setData(null);
                jr.setMsg("查无数据");
                jr.setStatus(1);
            } else {
                jr.setData(teaGradeList);
                jr.setMsg("查询成功");
                jr.setStatus(0);
            }
        } catch (Exception e) {
            jr.setData(null);
            jr.setMsg("系统异常");
            jr.setStatus(2);
        }
        return new ResponseEntity<JsonResult<List<String>>>(jr, HttpStatus.OK);
    }

    /**
     * 查询教师排名
     * */
    @RequestMapping(value = "/getTeacherRank", method = RequestMethod.GET)
    public ResponseEntity<JsonResult<Map<String, Object>>> getTeacherRank(HttpServletRequest request) {
        JsonResult<Map<String, Object>> jr = new JsonResult<Map<String, Object>>();
        Long id = (Long) request.getSession().getAttribute("teacherId");
        if (null == id) {
            jr.setData(null);
            jr.setMsg("参数为空");
            jr.setStatus(3);
        } else {
            try {
                Map<String, Object> map = teacherService.getTeacherRank(id);
                if (-1 == (int) map.get("flag")) {
                    jr.setData(null);
                    jr.setMsg("无该用户存在");
                    jr.setStatus(1);
                } else {
                    jr.setData(map);
                    jr.setMsg("查询成功");
                    jr.setStatus(0);
                }
            } catch (Exception e) {
                jr.setData(null);
                jr.setMsg("系统异常");
                jr.setStatus(2);
            }
        }
        return new ResponseEntity<JsonResult<Map<String, Object>>>(jr, HttpStatus.OK);
    }

    /**
     * 获取已通过绩效
     * */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/getPassPerformance", method = RequestMethod.GET)
    public ResponseEntity<JsonPage<List<TeacherPerformance>>> getPassPerformance(HttpServletRequest request,
                                                                                 Integer pageSize,
                                                                                 Integer pageNum) {
        JsonPage<List<TeacherPerformance>> jp = new JsonPage<List<TeacherPerformance>>();
        Long id = (Long) request.getSession().getAttribute("teacherId");
        int pSize = pageSize == null ? 20 : pageSize;
        int pNum = pageNum == null ? 1 : pageNum;
        if (null == id) {
            jp.setData_list(null);
            jp.setMsg("参数为空");
            jp.setStatus(3);
            jp.setPageNum(pNum);
            jp.setPageSize(pSize);
            jp.setTotal(0);
        } else {
            try {
                Map<String, Object> map = teacherService.getPassPerformance(id, pSize, pNum);
                if (null == map.get("teacherPerformanceList")) {
                    jp.setData_list(null);
                    jp.setMsg("查无数据");
                    jp.setPageNum(pNum);
                    jp.setPageSize(pSize);
                    jp.setStatus(1);
                    jp.setTotal((int) map.get("total"));
                } else {
                    jp.setData_list((List<TeacherPerformance>) map.get("teacherPerformanceList"));
                    jp.setMsg("查询成功");
                    jp.setPageNum(pNum);
                    jp.setPageSize(pSize);
                    jp.setStatus(0);
                    jp.setTotal((int) map.get("total"));
                }
            } catch (Exception e) {
                jp.setData_list(null);
                jp.setMsg("系统异常");
                jp.setStatus(2);
                jp.setPageNum(pNum);
                jp.setPageSize(pSize);
                jp.setTotal(0);
            }
        }
        return new ResponseEntity<JsonPage<List<TeacherPerformance>>>(jp, HttpStatus.OK);
    }

    /**
     * 获取未通过绩效
     * */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/getFailPerformance", method = RequestMethod.GET)
    public ResponseEntity<JsonPage<List<TeacherPerformance>>> getFailPerformance(HttpServletRequest request,
                                                                                 Integer pageSize,
                                                                                 Integer pageNum) {
        JsonPage<List<TeacherPerformance>> jp = new JsonPage<List<TeacherPerformance>>();
        Long id = (Long) request.getSession().getAttribute("teacherId");
        int pSize = pageSize == null ? 20 : pageSize;
        int pNum = pageNum == null ? 1 : pageNum;
        if (null == id) {
            jp.setData_list(null);
            jp.setMsg("参数为空");
            jp.setStatus(3);
            jp.setPageNum(pNum);
            jp.setPageSize(pSize);
            jp.setTotal(0);
        } else {
            try {
                Map<String, Object> map = teacherService.getFailPerformance(id, pSize, pNum);
                if (null == map.get("teacherPerformanceList")) {
                    jp.setData_list(null);
                    jp.setMsg("查无数据");
                    jp.setPageNum(pNum);
                    jp.setPageSize(pSize);
                    jp.setStatus(1);
                    jp.setTotal((int) map.get("total"));
                } else {
                    jp.setData_list((List<TeacherPerformance>) map.get("teacherPerformanceList"));
                    jp.setMsg("查询成功");
                    jp.setPageNum(pNum);
                    jp.setPageSize(pSize);
                    jp.setStatus(0);
                    jp.setTotal((int) map.get("total"));
                }
            } catch (Exception e) {
                jp.setData_list(null);
                jp.setMsg("系统异常");
                jp.setStatus(2);
                jp.setPageNum(pNum);
                jp.setPageSize(pSize);
                jp.setTotal(0);
            }
        }
        return new ResponseEntity<JsonPage<List<TeacherPerformance>>>(jp, HttpStatus.OK);
    }

    /**
     * 退出
     * */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseEntity<JsonResult<Boolean>> logout(HttpServletRequest request) {
        JsonResult<Boolean> jr = new JsonResult<Boolean>();
        try {
            request.getSession().removeAttribute("teacherId");
            jr.setData(true);
            jr.setMsg("成功退出");
            jr.setStatus(0);
        } catch (Exception e) {
            jr.setData(false);
            jr.setMsg("系统异常");
            jr.setStatus(2);
        }
        return new ResponseEntity<JsonResult<Boolean>>(jr, HttpStatus.OK);
    }

    /*
     *采用spring提供的上传文件的方法
     */
    @RequestMapping(value = "/Upload", method = RequestMethod.POST)
    public void springUpload(HttpServletRequest request) throws IllegalStateException, IOException {
        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
            request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        if (multipartResolver.isMultipart(request)) {
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //获取multiRequest 中所有的文件名
            Iterator iter = multiRequest.getFileNames();

            while (iter.hasNext()) {
                //一次遍历所有文件
                MultipartFile file = multiRequest.getFile(iter.next().toString());
                if (file != null) {

                    String folder = "E:/绩效资料证明/" + new Date().toLocaleString().split(" ")[0];
                    System.out.println(folder);
                    File savefile = new File(folder);
                    if (!savefile.exists() && !savefile.isDirectory()) {
                        System.out.println("目录或文件不存在！");
                        savefile.mkdir();
                        System.out.println(savefile.exists());
                    }

                    String path = "E:/绩效资料证明/" + new Date().toLocaleString().split(" ")[0] + "/"
                                  + file.getOriginalFilename();
                    //上传
                    file.transferTo(new File(path));
                }

            }

        }
        System.out.println("success");
    }

    /**
     * 文件上传功能
     * @param file
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public void upload(MultipartFile file, HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        System.out.println("执行下载方法！！！");
        if (null != file) {
            String path = request.getSession().getServletContext().getRealPath("upload");
            String fileName = file.getOriginalFilename();
            File dir = new File(path, fileName);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            //MultipartFile自带的解析方法
            file.transferTo(dir);
        }
    }

    /**
     * 文件下载功能
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/down", method = RequestMethod.GET)
    public ResponseEntity<JsonResult<Boolean>> down(Long virtualId, HttpServletRequest request,
                                                    HttpServletResponse response) throws Exception {
        JsonResult<Boolean> jr = new JsonResult<Boolean>();
        if (null == virtualId) {
            jr.setData(false);
            jr.setMsg("参数为空");
            jr.setStatus(3);
        } else {
            try {
                TeacherPerformance teacherPerformance = teacherService.down(virtualId);
                if (null == teacherPerformance) {
                    System.out.println("无此绩效");
                    jr.setData(false);
                    jr.setMsg("无此绩效");
                    jr.setStatus(4);
                } else if (StringUtils.isEmpty(teacherPerformance.getUpload())) {
                    //String fileName = "D:/EclipseWorkspace/performance-management/performance-management-web/target/m2e-wtp/web-resources/upload/1.jpg";
                    jr.setData(false);
                    jr.setMsg("无资料");
                    jr.setStatus(5);
                } else {
                    System.out.println("下载数据库内的文件");
                    //String fileName = request.getSession().getServletContext().getRealPath("upload") + "/1.jpg";
                    String fileName = request.getSession().getServletContext().getRealPath("upload")
                                      + "/" + teacherPerformance.getUpload();

                    System.out.println(fileName);
                    //获取输入流
                    InputStream bis = new BufferedInputStream(
                        new FileInputStream(new File(fileName)));
                    //假如以中文名下载的话
                    //String filename = "下载文件.txt";
                    //转码，免得文件名中文乱码
                    String filename = URLEncoder.encode(fileName, "UTF-8");
                    //设置文件下载头
                    response.addHeader("Content-Disposition", "attachment;filename=" + filename);
                    //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型  
                    response.setContentType("multipart/form-data");
                    BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
                    int len = 0;
                    while ((len = bis.read()) != -1) {
                        out.write(len);
                        out.flush();
                    }
                    out.close();

                    jr.setData(true);
                    jr.setMsg("下载成功");
                    jr.setStatus(0);
                }
            } catch (Exception e) {
                jr.setData(false);
                jr.setMsg("系统异常");
                jr.setStatus(2);
            }
        }
        return new ResponseEntity<JsonResult<Boolean>>(jr, HttpStatus.OK);
    }
}
