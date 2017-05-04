package com.performance.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.performance.persist.common.JsonPage;
import com.performance.persist.common.JsonResult;
import com.performance.persist.domain.Person;
import com.performance.persist.domain.ScientificResearch;
import com.performance.persist.domain.TeacherPerformance;
import com.performance.persist.domain.TeachingResearch;
import com.performance.service.AdministratorService;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value = "/administrator")
public class AdministratorController {

    private static Pattern NAME_PATTERN = Pattern.compile("[A-Za-z0-9()\\-\\+\\*_\u4e00-\u9fa5]*");
    private static Pattern Number_PATTERN = Pattern.compile("[1-9][0-9]*");
    //  private static Pattern Chinese_PATTERN = Pattern.compile("[\u4e00-\u9fa5]");

    @Autowired
    private AdministratorService administratorService;

    @RequestMapping("/1")
    public String change() {
        return "1";
    }

    @RequestMapping(value = "/managePerformance")
    public String managePerformance() {
        return "managePerformance";
    }

    @RequestMapping(value = "/registerReview")
    public String registerReview() {
        return "registerReview";
    }

    @RequestMapping(value = "/performanceRulesManage")
    public String performanceRulesManage() {
        return "performanceRulesManage";
    }

    @RequestMapping(value = "/userInfoManage")
    public String userInfoManage() {
        return "userInfoManage";
    }

    @RequestMapping(value = "/showTeacherRank")
    public String showTeacherRank() {
        return "showTeacherRank";
    }

    @RequestMapping(value = "/manageCenter")
    public String manageCenter() {
        return "manageCenter";
    }

    /**
     * 管理员登录
     * */
    @RequestMapping(value = "/administratorLogin", method = RequestMethod.GET)
    //public ResponseEntity<JsonResult<Boolean>> administratorLogin(Long id, String password) {
    public String administratorLogin(Long id, String password, HttpServletRequest request) {
        JsonResult<Boolean> jr = new JsonResult<Boolean>();
        Map<String, Object> map = new HashMap<String, Object>();
        if (null == id || StringUtils.isEmpty(password)) {
            jr.setData(false);
            jr.setMsg("参数为空");
            jr.setStatus(3);
        } else {
            try {
                map.put("id", id);
                map.put("password", password);
                Boolean result = administratorService.administratorLogin(map);
                if (true == result) {
                    jr.setData(true);
                    jr.setMsg("登录成功");
                    jr.setStatus(0);
                    request.getSession().setAttribute("id", id);
                    return "managePerformance";
                } else {
                    jr.setData(false);
                    jr.setMsg("该用户不存在");
                    jr.setStatus(1);
                }
            } catch (Exception e) {
                jr.setData(false);
                jr.setMsg("系统异常");
                jr.setStatus(2);
            }
        }
        return "adminLogin.html";
    }

    /**
     * 获取管理员姓名
     * */
    @RequestMapping(value = "/getAdministratorName", method = RequestMethod.GET)
    public ResponseEntity<JsonResult<String>> getAdministratorName(HttpServletRequest request) {
        System.out.println("调用获取管理员名称方法");
        JsonResult<String> jr = new JsonResult<String>();
        Long id = (Long) request.getSession().getAttribute("id");
        System.out.println(id);
        if (null == id) {
            jr.setData(null);
            jr.setMsg("未获取管理员工号");
            jr.setStatus(3);
        } else {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", id);
            map.put("grade", 1);
            map.put("status", "0");
            try {
                String name = administratorService.getAdministratorName(map);
                if (null == name || "".equals(name)) {
                    jr.setData(null);
                    jr.setMsg("该管理员不存在");
                    jr.setStatus(1);
                } else {
                    System.out.println(name);
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
     * 职工注册待审核人数
     * */
    @RequestMapping(value = "/teacherRegisterCheckSum", method = RequestMethod.GET)
    public ResponseEntity<JsonResult<Integer>> teacherRegisterCheckSum() {
        JsonResult<Integer> jr = new JsonResult<Integer>();
        try {
            Integer sum = administratorService.teacherRegisterCheckSum();
            if (sum > 0) {
                jr.setData(sum);
                jr.setMsg("查询待审核教师人数成功");
                jr.setStatus(0);
            } else if (sum == 0) {
                jr.setData(sum);
                jr.setMsg("无待审核教师");
                jr.setStatus(3);
            } else {
                jr.setData(sum);
                jr.setMsg("查询失败");
                jr.setStatus(1);
            }
        } catch (Exception e) {
            jr.setData(null);
            jr.setMsg("系统异常");
            jr.setStatus(2);
        }
        return new ResponseEntity<JsonResult<Integer>>(jr, HttpStatus.OK);
    }

    /**
     * 职工注册信息审核
     * */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/teacherRegisterCheck", method = RequestMethod.GET)
    public ResponseEntity<JsonPage<List<Person>>> teacherRegisterCheck(Integer pageSize,
                                                                       Integer pageNum) {
        JsonPage<List<Person>> jp = new JsonPage<List<Person>>();

        int pSize = pageSize == null ? 20 : pageSize;
        int pNum = pageNum == null ? 1 : pageNum;

        Map<String, Object> resultMap = administratorService.teacherRegisterCheck(pSize, pNum);
        try {
            if (null == resultMap.get("teacherList")) {
                jp.setData_list(null);
                jp.setMsg("无待审核教师");
                jp.setStatus(1);
                jp.setPageNum(pNum);
                jp.setPageSize(pSize);
                jp.setTotal((int) resultMap.get("total"));
            } else {
                jp.setData_list((List<Person>) resultMap.get("teacherList"));
                jp.setMsg("查询待审核教师成功");
                jp.setStatus(0);
                jp.setPageNum(pNum);
                jp.setPageSize(pSize);
                jp.setTotal((int) resultMap.get("total"));

                //  DateFormat df = DateFormat.getDateInstance();
                //  System.out.println(df.format(
                //      ((List<Person>) resultMap.get("teacherList")).get(0).getAdmissionTime()));
                System.out.println(
                    ((List<Person>) resultMap.get("teacherList")).get(0).getAdmissionTime());
            }
        } catch (Exception e) {
            jp.setData_list(null);
            jp.setMsg("系统异常");
            jp.setStatus(2);
            jp.setPageNum(pNum);
            jp.setPageSize(pSize);
            jp.setTotal(0);
        }
        return new ResponseEntity<JsonPage<List<Person>>>(jp, HttpStatus.OK);
    }

    /**
     * 同意职工注册
     * */
    @RequestMapping(value = "/teacherRegisterAgree", method = RequestMethod.GET)
    public ResponseEntity<JsonResult<Boolean>> teacherRegisterAgree(HttpServletRequest request) {
        JsonResult<Boolean> jr = new JsonResult<Boolean>();
        Long id = (Long) request.getSession().getAttribute("id");
        if (null == id) {
            jr.setData(false);
            jr.setMsg("参数为空");
            jr.setStatus(3);
        } else {
            try {
                int result = administratorService.teacherRegisterAgree(id);
                if (1 == result) {
                    jr.setData(true);
                    jr.setMsg("同意职工注册成功");
                    jr.setStatus(0);
                } else {
                    jr.setData(false);
                    jr.setMsg("同意职工注册失败");
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
     * 拒绝员工注册
     * */
    @RequestMapping(value = "/teachersRegisterFail", method = RequestMethod.GET)
    public ResponseEntity<JsonResult<Boolean>> teacherRegisterFail(HttpServletRequest request) {
        JsonResult<Boolean> jr = new JsonResult<Boolean>();
        Long id = (Long) request.getSession().getAttribute("id");
        if (null == id) {
            jr.setData(false);
            jr.setMsg("参数为空");
            jr.setStatus(3);
        } else {
            try {
                int result = administratorService.teacherRegisterFail(id);
                if (1 == result) {
                    jr.setData(true);
                    jr.setMsg("拒绝职工注册成功");
                    jr.setStatus(0);
                } else {
                    jr.setData(false);
                    jr.setMsg("拒绝职工注册失败");
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

    @RequestMapping("/add")
    public ResponseEntity<JsonResult<Boolean>> add() {
        JsonResult<Boolean> jr = new JsonResult<Boolean>();
        List<Integer> arr = new ArrayList<Integer>();
        int a = 1;
        arr.add(a);
        System.out.println(arr);
        Map map = new HashMap<>();
        map.put(a, "hh");
        System.out.println(map);
        if (true) {
            jr.setData(true);
            jr.setMsg("增加教师成功");
            jr.setStatus(0);
        }
        return new ResponseEntity<JsonResult<Boolean>>(jr, HttpStatus.OK);
    }

    /**
     * 增加教师 
     **/
    @RequestMapping(value = "/addTeacher", method = RequestMethod.POST)
    public ResponseEntity<JsonResult<Boolean>> addTeacher(@RequestBody Person person) {
        JsonResult<Boolean> jr = new JsonResult<Boolean>();
        if (null == person.getId() || null == person.getName() || null == person.getPassword()) {
            jr.setData(false);
            jr.setMsg("参数为空");
            jr.setStatus(3);
        } else {
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

                int result = administratorService.addTeacher(person);
                if (1 == result) {
                    jr.setData(true);
                    jr.setMsg("增加教师成功");
                    jr.setStatus(0);
                } else if (-1 == result) {
                    jr.setData(false);
                    jr.setMsg("工号已存在");
                    jr.setStatus(4);
                } else {
                    jr.setData(false);
                    jr.setMsg("增加教师失败");
                    jr.setStatus(1);
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
     * 删除教师
     * */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/deleteTeacher", method = RequestMethod.GET)
    public ResponseEntity<JsonResult<Boolean>> deleteTeacher(String idListJson) {
        JsonResult<Boolean> jr = new JsonResult<Boolean>();
        List<Long> idList = new ArrayList<Long>();
        System.out.println(idListJson);
        if (null == idListJson || "".equals(idListJson)) {
            jr.setData(false);
            jr.setMsg("未选择要删除的教师");
            jr.setStatus(3);
        } else {
            try {
                JSONArray jsonArray = JSONArray.fromObject(idListJson); //String转换为json
                idList = JSONArray.toList(jsonArray, Long.class);
                System.out.println(idList);
                int result = administratorService.deleteTeacher(idList);
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
     * 查询教师
     * */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/getTeacher", method = RequestMethod.GET)
    public ResponseEntity<JsonPage<List<Person>>> getTeacher(Long id, String name, Integer pageSize,
                                                             Integer pageNum) {
        JsonPage<List<Person>> jp = new JsonPage<List<Person>>();
        int pSize = pageSize == null ? 20 : pageSize;
        int pNum = pageNum == null ? 1 : pageNum;
        Map<String, Object> map = new HashMap<String, Object>();
        if (null != id) {
            map.put("id", id);
        }
        if (!StringUtils.isEmpty(name)) {
            map.put("name", name);
        }
        map.put("grade", 2); //教师
        map.put("status", "0"); //正常
        map.put("firstdata", (pNum - 1) * pSize);
        map.put("nums", pSize);
        try {
            Map<String, Object> resultMap = administratorService.getTeacher(map);
            if (null == resultMap.get("personList")) {
                jp.setData_list(null);
                jp.setMsg("查无数据");
                jp.setStatus(1);
                jp.setTotal((int) resultMap.get("total"));
                jp.setPageSize(pSize);
                jp.setPageNum(pNum);
            } else {
                jp.setData_list((List<Person>) resultMap.get("personList"));
                jp.setMsg("查询成功");
                jp.setStatus(0);
                jp.setTotal((int) resultMap.get("total"));
                jp.setPageNum(pNum);
                jp.setPageSize(pSize);
            }
        } catch (Exception e) {
            jp.setData_list(null);
            jp.setMsg("系统异常");
            jp.setStatus(2);
            jp.setTotal(0);
            jp.setPageNum(pNum);
            jp.setPageSize(pSize);
        }
        return new ResponseEntity<JsonPage<List<Person>>>(jp, HttpStatus.OK);
    }

    /**
     * 修改教师信息
     * */
    @RequestMapping(value = "/updateTeacher", method = RequestMethod.POST)
    public ResponseEntity<JsonResult<Boolean>> updateTeacher(@RequestBody Person person) {
        JsonResult<Boolean> jr = new JsonResult<Boolean>();
        try {
            int result = administratorService.updateTeacher(person);
            if (1 == result) {
                jr.setData(true);
                jr.setMsg("修改成功");
                jr.setStatus(0);
            } else {
                jr.setData(false);
                jr.setMsg("修改失败");
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
     * 获取管理员信息
     * */
    @RequestMapping(value = "/getAdministratorInfo", method = RequestMethod.GET)
    public ResponseEntity<JsonResult<Person>> getAdministratorInfo(HttpServletRequest request) {
        JsonResult<Person> jr = new JsonResult<Person>();
        Long id = (Long) request.getSession().getAttribute("id");
        if (null == id) {
            jr.setData(null);
            jr.setMsg("参数为空");
            jr.setStatus(3);
        } else {
            try {
                Person person = administratorService.getAdministratorInfo(id);
                if (null == person) {
                    jr.setData(null);
                    jr.setMsg("用户不存在");
                    jr.setStatus(1);
                } else {
                    jr.setData(person);
                    jr.setMsg("查询成功");
                    jr.setStatus(0);
                }
            } catch (Exception e) {
                jr.setData(null);
                jr.setMsg("系统异常");
                jr.setStatus(2);
            }
        }
        return new ResponseEntity<JsonResult<Person>>(jr, HttpStatus.OK);
    }

    /**
     * 修改管理员信息 
     * */
    @RequestMapping(value = "/updateAdministratorInfo", method = RequestMethod.POST)
    public ResponseEntity<JsonResult<Boolean>> updateAdministratorInfo(@RequestBody Person person) {
        JsonResult<Boolean> jr = new JsonResult<Boolean>();
        try {
            int result = administratorService.updateAdministratorInfo(person);
            if (1 == result) {
                jr.setData(true);
                jr.setMsg("修改成功");
                jr.setStatus(0);
            } else {
                jr.setData(false);
                jr.setMsg("修改失败");
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
     * 修改密码
     * */
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public ResponseEntity<JsonResult<Boolean>> updatePassword(HttpServletRequest request,
                                                              String password) {
        JsonResult<Boolean> jr = new JsonResult<Boolean>();
        Long id = (Long) request.getSession().getAttribute("id");
        if (null == id || null == password) {
            jr.setData(false);
            jr.setMsg("参数为空");
            jr.setStatus(3);
        } else {
            try {
                int result = administratorService.updatePassword(id, password);
                if (1 == result) {
                    jr.setData(true);
                    jr.setMsg("修改成功");
                    jr.setStatus(0);
                } else {
                    jr.setData(false);
                    jr.setMsg("修改失败");
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
     * 教师绩效待审核数
     * */
    @RequestMapping(value = "/teacherPerformanceCheckInfo", method = RequestMethod.GET)
    public ResponseEntity<JsonResult<Integer>> teacherPerformanceCheckSum() {
        JsonResult<Integer> jr = new JsonResult<Integer>();
        try {
            int sum = administratorService.teacherPerformanceCheckSum();
            if (sum >= 0) {
                jr.setData(sum);
                jr.setMsg("查询待审核绩效成功");
                jr.setStatus(0);
            } else {
                jr.setData(sum);
                jr.setMsg("查询失败");
                jr.setStatus(1);
            }
        } catch (Exception e) {
            jr.setData(null);
            jr.setMsg("系统异常");
            jr.setStatus(2);
        }
        return new ResponseEntity<JsonResult<Integer>>(jr, HttpStatus.OK);
    }

    /**
     * 待审核教师绩效
     * */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/teacherPerformanceCheck", method = RequestMethod.GET)
    public ResponseEntity<JsonPage<List<TeacherPerformance>>> teacherPerformanceCheck(Integer pageSize,
                                                                                      Integer pageNum) {
        JsonPage<List<TeacherPerformance>> jp = new JsonPage<List<TeacherPerformance>>();
        int pSize = pageSize == null ? 20 : pageSize;
        int pNum = pageNum == null ? 1 : pageNum;
        try {
            Map<String, Object> resultMap = administratorService.teacherPerformanceCheck(pSize,
                pNum);
            if (null == (List<TeacherPerformance>) resultMap.get("teacherPerformanceList")) {
                jp.setData_list(null);
                jp.setMsg("查无数据");
                jp.setStatus(1);
                jp.setPageNum(pNum);
                jp.setPageSize(pSize);
                jp.setTotal((int) resultMap.get("total"));
            } else {
                jp.setData_list((List<TeacherPerformance>) resultMap.get("teacherPerformanceList"));
                jp.setMsg("查询待审核绩效成功");
                jp.setStatus(0);
                jp.setPageNum(pNum);
                jp.setPageSize(pSize);
                jp.setTotal((int) resultMap.get("total"));
            }
        } catch (Exception e) {
            jp.setData_list(null);
            jp.setMsg("系统异常");
            jp.setStatus(2);
            jp.setPageNum(pNum);
            jp.setPageSize(pSize);
            jp.setTotal(0);
        }
        return new ResponseEntity<JsonPage<List<TeacherPerformance>>>(jp, HttpStatus.OK);
    }

    /**
     * 同意教师绩效录入
     * */
    @RequestMapping(value = "/teacherPerformanceAgree", method = RequestMethod.GET)
    public ResponseEntity<JsonResult<Boolean>> teacherPerformanceAgree(Long virtualId, Long id) {
        JsonResult<Boolean> jr = new JsonResult<Boolean>();
        if (null == virtualId) {
            jr.setData(false);
            jr.setMsg("参数为空");
            jr.setStatus(3);
        } else {
            try {
                int result = administratorService.teacherPerformanceAgree(virtualId, id);
                if (1 == result) {
                    jr.setData(true);
                    jr.setMsg("同意教师绩效录入成功");
                    jr.setStatus(0);
                } else if (-1 == result) {
                    jr.setData(false);
                    jr.setMsg("查询无该条绩效");
                    jr.setStatus(4);
                } else if (-2 == result) {
                    jr.setData(false);
                    jr.setMsg("该教师不存在");
                    jr.setStatus(5);
                } else if (-3 == result) {
                    jr.setData(false);
                    jr.setMsg("该绩效基础选项不存在或已删除");
                    jr.setStatus(6);
                } else {
                    jr.setData(false);
                    jr.setMsg("同意教师绩效录入失败");
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
     * 拒绝教师绩效录入
     * */
    @RequestMapping(value = "/teacherPerformanceFail", method = RequestMethod.GET)
    public ResponseEntity<JsonResult<Boolean>> teacherPerformanceFail(Long virtualId) {
        JsonResult<Boolean> jr = new JsonResult<Boolean>();
        if (null == virtualId) {
            jr.setData(false);
            jr.setMsg("参数为空");
            jr.setStatus(3);
        } else {
            int result = administratorService.teacherPerformanceFail(virtualId);
            try {
                if (1 == result) {
                    jr.setData(true);
                    jr.setMsg("拒绝教师绩效录入成功");
                    jr.setStatus(0);
                } else if (-1 == result) {
                    jr.setData(false);
                    jr.setMsg("查询无该条绩效");
                    jr.setStatus(4);
                } else {
                    jr.setData(false);
                    jr.setMsg("拒绝教师绩效录入失败");
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
     * 增加科研基础选项
     * */
    @RequestMapping(value = "/addScientificResearchPerformance", method = RequestMethod.POST)
    public ResponseEntity<JsonResult<Boolean>> addScientificResearchPerformance(@RequestBody ScientificResearch scientificResearch) {
        JsonResult<Boolean> jr = new JsonResult<Boolean>();
        if (null == scientificResearch) {
            jr.setData(false);
            jr.setMsg("参数为空");
            jr.setStatus(3);
        } else {
            try {
                int result = administratorService
                    .addScientificResearchPerformance(scientificResearch);
                if (1 == result) {
                    jr.setData(true);
                    jr.setMsg("增加科研基础选项成功");
                    jr.setStatus(0);
                } else {
                    jr.setData(false);
                    jr.setMsg("增加科研基础选项失败");
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
     * 增加教研基础选项
     * */
    @RequestMapping(value = "/addTeachingResearchPerformance", method = RequestMethod.POST)
    public ResponseEntity<JsonResult<Boolean>> addTeachingResearchPerformance(@RequestBody TeachingResearch teachingResearch) {
        JsonResult<Boolean> jr = new JsonResult<Boolean>();
        if (teachingResearch == null) {
            jr.setData(false);
            jr.setMsg("参数为空");
            jr.setStatus(3);
        } else {
            try {
                int result = administratorService.addTeachingResearchPerformance(teachingResearch);
                if (1 == result) {
                    jr.setData(true);
                    jr.setMsg("增加教研基础选项成功");
                    jr.setStatus(0);
                } else {
                    jr.setData(false);
                    jr.setMsg("增加教研基础选项失败");
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
     * 删除科研基础选项
     * */
    @RequestMapping(value = "/deleteScientificResearchPerformance", method = RequestMethod.GET)
    public ResponseEntity<JsonResult<Boolean>> deleteScientificResearchPerformance(String virtualIdListJson) {
        JsonResult<Boolean> jr = new JsonResult<Boolean>();
        List<Long> virtualIdList = new ArrayList<Long>();
        if (null == virtualIdListJson || "".equals(virtualIdListJson)) {
            jr.setData(false);
            jr.setMsg("未选择要删除的项");
            jr.setStatus(3);
        } else {
            try {
                JSONArray jsonArray = JSONArray.fromObject(virtualIdListJson); //String转换为json
                virtualIdList = JSONArray.toList(jsonArray, Long.class);
                int result = administratorService
                    .deleteScientificResearchPerformance(virtualIdList);
                if (virtualIdList.size() == result) {
                    jr.setData(true);
                    jr.setMsg("删除科研基础选项成功");
                    jr.setStatus(0);
                } else {
                    jr.setData(false);
                    jr.setMsg("删除科研基础选项失败");
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
     * 删除教研基础选项
     * */
    @RequestMapping(value = "/deteleTeachingResearchPerformance", method = RequestMethod.GET)
    public ResponseEntity<JsonResult<Boolean>> deteleTeachingResearchPerformance(String virtualIdListJson) {
        JsonResult<Boolean> jr = new JsonResult<Boolean>();
        List<Long> virtualIdList = new ArrayList<Long>();
        if (null == virtualIdListJson || "".equals(virtualIdListJson)) {
            jr.setData(false);
            jr.setMsg("未选择要删除的项");
            jr.setStatus(3);
        } else {
            try {
                JSONArray jsonArray = JSONArray.fromObject(virtualIdListJson); //String转换为json
                virtualIdList = JSONArray.toList(jsonArray, Long.class);
                int result = administratorService.deteleTeachingResearchPerformance(virtualIdList);
                if (virtualIdList.size() == result) {
                    jr.setData(true);
                    jr.setMsg("删除教研基础选项成功");
                    jr.setStatus(0);
                } else {
                    jr.setData(false);
                    jr.setMsg("删除教研基础选项失败");
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
     * 修改科研基础选项
     * */
    @RequestMapping(value = "/updateScientificResearchPerformance", method = RequestMethod.POST)
    public ResponseEntity<JsonResult<Boolean>> updateScientificResearchPerformance(@RequestBody ScientificResearch scientificResearch) {
        JsonResult<Boolean> jr = new JsonResult<Boolean>();
        try {
            int result = administratorService
                .updateScientificResearchPerformance(scientificResearch);
            if (1 == result) {
                jr.setData(true);
                jr.setMsg("修改科研基础选项成功");
                jr.setStatus(0);
            } else {
                jr.setData(false);
                jr.setMsg("修改科研基础选项失败");
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
     * 修改教研基础选项
     * */
    @RequestMapping(value = "/updateTeachingResearchPerformance", method = RequestMethod.POST)
    public ResponseEntity<JsonResult<Boolean>> updateTeachingResearchPerformance(@RequestBody TeachingResearch teachingResearch) {
        JsonResult<Boolean> jr = new JsonResult<Boolean>();
        try {
            int result = administratorService.updateTeachingResearchPerformance(teachingResearch);
            if (1 == result) {
                jr.setData(true);
                jr.setMsg("修改教研基础选项成功");
                jr.setStatus(0);
            } else {
                jr.setData(false);
                jr.setMsg("修改教研基础选项失败");
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
     * 获取科研基础选项
     * */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/getScientificResearchPerformance", method = RequestMethod.GET)
    public ResponseEntity<JsonPage<List<ScientificResearch>>> getScientificResearchPerformance(Integer pageSize,
                                                                                               Integer pageNum) {
        JsonPage<List<ScientificResearch>> jp = new JsonPage<List<ScientificResearch>>();
        int pSize = pageSize == null ? 20 : pageSize;
        int pNum = pageNum == null ? 1 : pageNum;
        try {
            Map<String, Object> resultMap = administratorService
                .getScientificResearchPerformance(pSize, pNum);
            if (null == resultMap.get("scientificResearchList")) {
                jp.setData_list(null);
                jp.setMsg("查无科研基础选项");
                jp.setStatus(1);
                jp.setTotal((int) resultMap.get("total"));
                jp.setPageNum(pNum);
                jp.setPageSize(pSize);
            } else {
                jp.setData_list((List<ScientificResearch>) resultMap.get("scientificResearchList"));
                jp.setMsg("查询科研基础选项成功");
                jp.setStatus(0);
                jp.setTotal((int) resultMap.get("total"));
                jp.setPageNum(pNum);
                jp.setPageSize(pSize);
            }
        } catch (Exception e) {
            jp.setData_list(null);
            jp.setMsg("系统异常");
            jp.setTotal(0);
            jp.setStatus(2);
            jp.setPageNum(pNum);
            jp.setPageSize(pSize);
        }
        return new ResponseEntity<JsonPage<List<ScientificResearch>>>(jp, HttpStatus.OK);
    }

    /**
     * 获取教研基础选项
     * */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/getTeachingResearchPerformance", method = RequestMethod.GET)
    public ResponseEntity<JsonPage<List<TeachingResearch>>> getTeachingResearchPerformance(Integer pageSize,
                                                                                           Integer pageNum) {
        JsonPage<List<TeachingResearch>> jp = new JsonPage<List<TeachingResearch>>();
        int pSize = pageSize == null ? 20 : pageSize;
        int pNum = pageNum == null ? 1 : pageNum;
        try {
            Map<String, Object> resultMap = administratorService
                .getTeachingResearchPerformance(pSize, pNum);
            if (null == resultMap.get("teachingResearchList")) {
                jp.setData_list(null);
                jp.setMsg("查无教研基础选项");
                jp.setStatus(1);
                jp.setTotal((int) resultMap.get("total"));
                jp.setPageSize(pSize);
                jp.setPageNum(pNum);
            } else {
                jp.setData_list((List<TeachingResearch>) resultMap.get("teachingResearchList"));
                jp.setMsg("查询教研基础选项成功");
                jp.setTotal((int) resultMap.get("total"));
                jp.setStatus(0);
                jp.setPageNum(pNum);
                jp.setPageSize(pSize);
            }
        } catch (Exception e) {
            jp.setData_list(null);
            jp.setMsg("系统异常");
            jp.setPageNum(pNum);
            jp.setPageSize(pSize);
            jp.setStatus(2);
            jp.setTotal(0);
        }
        return new ResponseEntity<JsonPage<List<TeachingResearch>>>(jp, HttpStatus.OK);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("id");
        return null;
    }
}
