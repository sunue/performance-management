package com.performance.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.performance.persist.common.JsonPage;
import com.performance.persist.common.JsonResult;
import com.performance.persist.domain.Person;
import com.performance.persist.domain.TeacherPerformance;
import com.performance.service.AdministratorService;

@Controller
@RequestMapping(value = "/administrator")
public class AdministratorController {

    @Autowired
    private AdministratorService administratorService;

    /**
     * 管理员登录
     * */
    @RequestMapping(value = "/administratorLogin")
    public ResponseEntity<JsonResult<Boolean>> administratorLogin(Long id, String name) {
        JsonResult<Boolean> jr = new JsonResult<Boolean>();
        Map<String, Object> map = new HashMap<String, Object>();
        if (null == id || name == null) {
            jr.setData(false);
            jr.setMsg("参数为空");
            jr.setStatus(3);
        } else {
            try {
                map.put("id", id);
                map.put("name", name);
                Boolean result = administratorService.administratorLogin(map);
                if (true == result) {
                    jr.setData(true);
                    jr.setMsg("登录成功");
                    jr.setStatus(0);
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
        return new ResponseEntity<JsonResult<Boolean>>(jr, HttpStatus.OK);
    }

    /**
     * 职工注册待审核人数
     * */
    @RequestMapping("/teacherRegisterCheckSum")
    public ResponseEntity<JsonResult<Integer>> teacherRegisterCheckSum() {
        JsonResult<Integer> jr = new JsonResult<Integer>();
        try {
            int sum = administratorService.teacherRegisterCheckSum();
            if (sum >= 0) {
                jr.setData(sum);
                jr.setMsg("查询待审核教师人数成功");
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
     * 职工注册信息审核
     * */
    @RequestMapping(value = "/teacherRegisterCheck")
    public ResponseEntity<List<Person>> teacherRegisterCheck() {
        JsonResult<List<Person>> jr = new JsonResult<>();
        List<Person> personList = administratorService.teacherRegisterCheck();
        try {
            if (null == personList) {
                jr.setData(null);
                jr.setMsg("无待审核教师");
                jr.setStatus(1);
            } else {
                jr.setData(personList);
                jr.setMsg("查询待审核教师成功");
                jr.setStatus(0);
            }
        } catch (Exception e) {
            jr.setData(null);
            jr.setMsg("系统异常");
            jr.setStatus(2);
        }
        return new ResponseEntity<List<Person>>(personList, HttpStatus.OK);
    }

    /**
     * 同意职工注册
     * */
    @RequestMapping(value = "teacherRegisterAgree")
    public ResponseEntity<JsonResult<Boolean>> teacherRegisterAgree(Long id) {
        JsonResult<Boolean> jr = new JsonResult<Boolean>();
        int result = administratorService.teacherRegisterAgree(id);
        try {
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
        return new ResponseEntity<JsonResult<Boolean>>(jr, HttpStatus.OK);
    }

    /**
     * 拒绝员工注册
     * */
    @RequestMapping(value = "teachersRegisterFail")
    public ResponseEntity<JsonResult<Boolean>> teacherRegisterFail(Long id) {
        JsonResult<Boolean> jr = new JsonResult<Boolean>();
        int result = administratorService.teacherRegisterFail(id);
        try {
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
        return new ResponseEntity<JsonResult<Boolean>>(jr, HttpStatus.OK);
    }

    /**
     * 增加教师 
     **/
    @RequestMapping("/addTeacher")
    public ResponseEntity<JsonResult<Boolean>> addTeacher(Person person) {
        JsonResult<Boolean> jr = new JsonResult<Boolean>();
        if (null == person) {
            jr.setData(false);
            jr.setMsg("参数为空");
            jr.setStatus(3);
        } else {
            try {
                int result = administratorService.addTeacher(person);
                if (1 == result) {
                    jr.setData(true);
                    jr.setMsg("增加教师成功");
                    jr.setStatus(0);
                } else {
                    jr.setData(false);
                    jr.setMsg("增加教师失败");
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
     * 删除教师
     * */
    @RequestMapping("/deleteTeacher")
    public ResponseEntity<JsonResult<Boolean>> deleteTeacher(List<Long> idList) {
        JsonResult<Boolean> jr = new JsonResult<Boolean>();
        if (null == idList) {
            jr.setData(false);
            jr.setMsg("未选择要删除的教师");
            jr.setStatus(3);
        } else {
            try {
                int result = administratorService.deleteTeacher(idList);
                if (1 == result) {
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
    @RequestMapping("/getTeacher")
    public ResponseEntity<JsonPage<List<Person>>> getTeacher(Long id, String name, Integer pageSize,
                                                             Integer pageNum) {
        JsonPage<List<Person>> jp = new JsonPage<List<Person>>();
        int pSize = pageSize == null ? 20 : pageSize;
        int pNum = pageNum == null ? 1 : pageNum;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("name", name);
        map.put("firstdata", (pSize - 1) * pNum);
        map.put("nums", pNum);
        try {
            Map<String, Object> resultMap = administratorService.getTeacher(map);
            if (null == resultMap.get("personList")) {
                jp.setData_list(null);
                jp.setMsg("查无数据");
                jp.setStatus(1);
                jp.setTotal(0);
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
    @RequestMapping("/updateTeacher")
    public ResponseEntity<JsonResult<Boolean>> updateTeacher(Person person) {
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
    @RequestMapping("/getAdministratorInfo")
    public ResponseEntity<JsonResult<Person>> getAdministratorInfo(Long id) {
        JsonResult<Person> jr = new JsonResult<Person>();
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
    @RequestMapping("/updateAdministratorInfo")
    public ResponseEntity<JsonResult<Boolean>> updateAdministratorInfo(Person person) {
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
    @RequestMapping("/updatePassword")
    public ResponseEntity<JsonResult<Boolean>> updatePassword(Long id, String password) {
        JsonResult<Boolean> jr = new JsonResult<Boolean>();
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
    @RequestMapping("/teacherPerformanceCheckInfo")
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

    public ResponseEntity<JsonResult<List<TeacherPerformance>>> teacherPerformanceCheck() {
        JsonResult<List<TeacherPerformance>> jr = new JsonResult<List<TeacherPerformance>>();
        List<TeacherPerformance> teacherPerformanceList = administratorService
            .teacherPerformanceCheck();
        return null;
    }

}
