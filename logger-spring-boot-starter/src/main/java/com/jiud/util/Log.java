package com.jiud.util;

import org.slf4j.Logger;
import org.slf4j.MDC;

import java.io.PrintStream;

public class Log {

    // 源生日志记录对象
    // private static final Logger log = LoggerFactory.getLogger("custom_log");
    // 本对象的路径
    private static String thisClassUrl = Log.class.getName();
    // 控制台输出
    private static PrintStream out = System.out;

    /**
     * 自己的日志工厂
     *
     * @Fields lf
     */
    private static LogFactory lf = LogFactory.newInstance();

    // 私有构造

    private Log() {
    }

    /**
     * 获取日志记录器
     *
     * @return
     */
    private static Logger getLog() {
        Logger logger = lf.get(getClazzName());
        return logger;
    }


    /**
     * 纯粹的syso
     *
     * @param o
     */
    public static void syso(Object o) {
        out.println(o);
    }

    /**
     * 为日志自定义一个变量 seat 日志里可以添加这个位置信息[%X{seat}]
     *
     * @param
     * @return
     */
    private static void setSeat() {
        MDC.put("seat", getInvokInfo());
    }

    /**
     * 返回此方法的调用者信息.<br>
     * 即谁调用此方法就返回调用位置的类名 方法名 行号 <br>
     * 例: >> net.evecom.cwyun.pdk.vmware.Main.main() 第 102 行 方法会从堆栈最上层往下找到第一个 非 Thread、CkUtil的调用者 ，如果没找到就取最底层的调用者信息
     *
     * @return
     */
    private static String getInvokInfo() {
        StackTraceElement[] ste = Thread.currentThread().getStackTrace();
        String className = null;
        String methodName = null;
        int lineNumber = 0;
        // 从栈的最上开始 往下找 找到第一个不为Log和线程的类
        for (int i = 0; i < ste.length; i++) {
            className = ste[i].getClassName();

            if ("java.lang.Thread".equals(className) || thisClassUrl.equals(className)) {
                if (i != (ste.length - 1)) {
                    continue;// 如果不是最后一个就跳过，如果是最后一个那就没办法了，返回这栈信息吧
                }
            }
            methodName = ste[i].getMethodName();
            lineNumber = ste[i].getLineNumber();
            break;
        }
        return className + "." + methodName + "()@" + lineNumber + "";
    }

    /**
     * 获取类名
     *
     * @return
     */
    private static String getClazzName() {
        StackTraceElement[] ste = Thread.currentThread().getStackTrace();
        String className = null;
        // 从栈的最上开始 往下找 找到第一个不为Log和线程的类
        for (int i = 0; i < ste.length; i++) {
            className = ste[i].getClassName();

            if ("java.lang.Thread".equals(className) || thisClassUrl.equals(className)) {
                if (i != (ste.length - 1)) {
                    continue;// 如果不是最后一个就跳过，如果是最后一个那就没办法了，返回这栈信息吧
                }
            }
            break;
        }
        return className;
    }



    /**
     * 系统启动消息
     * @param msg
     */
    public static void startMsg(String msg){
        info("###########################"+msg+"###########################");
    }
    /**
     * 关闭系统消息
     * @param msg
     */
    public static void closeMsg(String msg){
        info("###########################"+msg+"###########################");
    }

    /**
     * 日志记录
     *
     * @param arg0
     */
    public static void debug(String arg0) {
        Logger log = getLog();
        if (log.isDebugEnabled()) {
            setSeat();
            log.debug(arg0);
        }
    }

    /**
     * 日志记录
     *
     * @param arg0
     */
    public static void debug(String arg0, Object arg1) {
        Logger log = getLog();
        if (log.isDebugEnabled()) {
            setSeat();
            log.debug(arg0, arg1);
        }
    }

    /**
     * 日志记录
     *
     * @param arg0
     */
    public static void debug(String arg0, Object[] arg1) {
        Logger log = getLog();
        if (log.isDebugEnabled()) {
            setSeat();
            log.debug(arg0, arg1);
        }
    }

    /**
     * 日志记录
     *
     * @param arg0
     */
    public static void debug(String arg0, Throwable arg1) {
        Logger log = getLog();
        if (log.isDebugEnabled()) {
            setSeat();
            log.debug(arg0, arg1);
        }
    }

    /**
     * 日志记录
     *
     * @param arg0
     */
    public static void error(String arg0) {
        Logger log = getLog();
        if (log.isErrorEnabled()) {
            setSeat();
            log.error(arg0);
        }
    }

    /**
     * 日志记录
     *
     * @param arg0
     */
    public static void error(String arg0, Object arg1) {
        Logger log = getLog();
        if (log.isErrorEnabled()) {
            setSeat();
            log.error(arg0, arg1);
        }
    }

    /**
     * 日志记录
     *
     * @param arg0
     */
    public static void error(String arg0, Object[] arg1) {
        Logger log = getLog();
        if (log.isErrorEnabled()) {
            setSeat();
            log.error(arg0, arg1);
        }
    }

    /**
     * 日志记录
     *
     * @param arg0
     */
    public static void error(String arg0, Throwable arg1) {
        Logger log = getLog();
        if (log.isErrorEnabled()) {
            setSeat();
            log.error(arg0, arg1);
        }
    }

    /**
     * 日志记录
     *
     * @param arg0
     */
    public static void info(String arg0) {
        Logger log = getLog();
        if (log.isInfoEnabled()) {
            setSeat();
            log.info(arg0);
        }
    }

    /**
     * 日志记录
     *
     * @param arg0
     */
    public static void info(String arg0, Object arg1) {
        Logger log = getLog();
        if (log.isInfoEnabled()) {
            setSeat();
            log.info(arg0, arg1);
        }
    }

    /**
     * 日志记录
     *
     * @param arg0
     */
    public static void info(String arg0, Object[] arg1) {
        Logger log = getLog();
        if (log.isInfoEnabled()) {
            setSeat();
            log.info(arg0, arg1);
        }
    }

    /**
     * 日志记录
     *
     * @param arg0
     */
    public static void info(String arg0, Throwable arg1) {
        Logger log = getLog();
        if (log.isInfoEnabled()) {
            setSeat();
            log.info(arg0, arg1);
        }
    }

    /**
     * 日志记录
     *
     * @param arg0
     */
    public static void trace(String arg0) {
        Logger log = getLog();
        if (log.isTraceEnabled()) {
            setSeat();
            log.trace(arg0);
        }
    }

    /**
     * 日志记录
     *
     * @param arg0
     */
    public static void trace(String arg0, Object arg1) {
        Logger log = getLog();
        if (log.isTraceEnabled()) {
            setSeat();
            log.trace(arg0, arg1);
        }
    }

    /**
     * 日志记录
     *
     * @param arg0
     */
    public static void trace(String arg0, Object[] arg1) {
        Logger log = getLog();
        if (log.isTraceEnabled()) {
            setSeat();
            log.trace(arg0, arg1);
        }
    }

    /**
     * 日志记录
     *
     * @param arg0
     */
    public static void trace(String arg0, Throwable arg1) {
        Logger log = getLog();
        if (log.isTraceEnabled()) {
            setSeat();
            log.trace(arg0, arg1);
        }
    }

    /**
     * 日志记录
     *
     * @param arg0
     */
    public static void warn(String arg0) {
        Logger log = getLog();
        if (log.isWarnEnabled()) {
            setSeat();
            log.warn(arg0);
        }
    }

    /**
     * 日志记录
     *
     * @param arg0
     */
    public static void warn(String arg0, Object arg1) {
        Logger log = getLog();
        if (log.isWarnEnabled()) {
            setSeat();
            log.warn(arg0, arg1);
        }
    }

    /**
     * 日志记录
     *
     * @param arg0
     */
    public static void warn(String arg0, Object[] arg1) {
        Logger log = getLog();
        if (log.isWarnEnabled()) {
            setSeat();
            log.warn(arg0, arg1);
        }
    }

    /**
     * 日志记录
     *
     * @param arg0
     */
    public static void warn(String arg0, Throwable arg1) {
        Logger log = getLog();
        if (log.isWarnEnabled()) {
            setSeat();
            log.warn(arg0, arg1);
        }
    }
}
