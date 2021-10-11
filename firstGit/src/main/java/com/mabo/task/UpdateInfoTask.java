package com.mabo.task;

import com.mabo.manager.AdminManager;
import com.mabo.manager.SystemLogManager;
import com.mabo.manager.TeacherManager;
import com.mabo.manager.UserManager;
import com.mabo.utils.LogUtil;

/**
 * @Author mabo
 * @Description   定时器
 */

public class UpdateInfoTask {
    private LogUtil log;
    private TeacherManager teacherManager;
    private AdminManager adminManager;
    private UserManager userManager;
    private SystemLogManager systemLogManager;

    public void setSystemLogManager(SystemLogManager systemLogManager) {
        this.systemLogManager = systemLogManager;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public void setAdminManager(AdminManager adminManager) {
        this.adminManager = adminManager;
    }

    public void setTeacherManager(TeacherManager teacherManager) {
        this.teacherManager = teacherManager;
    }

    public LogUtil getLog() {
        return log;
    }

    public void setLog(LogUtil log) {
        this.log = log;
    }

/**
 * @Author mabo
 * @Description   输出时间的定时器
 */

    public  void  updateInfoTask(){
        try {
            systemLogManager.setDateBaseSystemLog();
            log.info("systemLogManager.setDateBaseSystemLog()=定时器执行");
            teacherManager.updateAllTeacherStudentTodayReportInfo();
            log.info("teacherManager.updateAllTeacherStudentTodayReportInfo()=定时器执行");
            adminManager.updateTodayReportInfo();
            log.info("adminManager.updateTodayReportInfo()=定时器执行");
            userManager.updateUserBaseInfo();
            log.info(" userManager.updateUserBaseInfo()=定时器执行");

        } catch (Exception e) {
            StringBuilder message = new StringBuilder();
            message.append(e.getMessage());
            message.append("()定时器方法执行失败");
            System.out.println(message);
            log.error(message.toString());
        }
    }

/**
 * @Author mabo
 * @Description   更新系统日志
 */
    public  void  updateSystemLogTask(){
        try {
            systemLogManager.deleteSystemLog();
            log.info("  systemLogManager.deleteSystemLog()=定时器执行");
        } catch (Exception e) {
            StringBuilder message = new StringBuilder();
            message.append(e.getMessage());
            message.append("()定时器方法执行失败");
            System.out.println(message);
            log.error(message.toString());
        }
    }
}
