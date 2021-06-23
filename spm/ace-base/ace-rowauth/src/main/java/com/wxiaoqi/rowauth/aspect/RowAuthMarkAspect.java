package com.wxiaoqi.rowauth.aspect;


import com.github.wxiaoqi.merge.core.BeanFactoryUtils;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.wxiaoqi.rowauth.util.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Description:Redis切面
 * @Author: lifay
 * @datetime:2019/5/28 10:54
 */
@Aspect
public class RowAuthMarkAspect {

    private final Logger logger = LoggerFactory.getLogger(RowAuthMarkAspect.class);//自己修改serviceImpl包路径


    /**
     * 定义切入点，使用了 @DataRwPermissionMarkAnnotation 的方法
     */
    @Pointcut("@annotation(com.wxiaoqi.rowauth.aspect.RowAuth)")
    public void dataRwPermissionMarkPoint() {

    }


    /**
     * 环绕通知 处理（处理链接点）
     * @param pdj
     * @return
     * @throws Throwable 这里不处理，往外抛出让上层去处理
     */
    @Around("dataRwPermissionMarkPoint()")
    public Object point(ProceedingJoinPoint pdj) throws Throwable {
            Class<?> classTarget = pdj.getTarget().getClass();
            Class<?>[] pts = ((MethodSignature) pdj.getSignature()).getParameterTypes();
            Method objMethod = classTarget.getDeclaredMethod(pdj.getSignature().getName(), pts);
            RowAuth annotation = pdj.getTarget().getClass().getDeclaredMethod(pdj.getSignature().getName(), pts).getAnnotation(RowAuth.class);
            Class<?> logicClass = annotation.logicClass();
            if (annotation != null && logicClass != null) {
                Object response = objMethod.invoke(pdj.getTarget(), pdj.getArgs());
                response = rowAuthHandel(classTarget, logicClass, response);
                return response;
            } else {
                //方法没加注解直接返回原方法结果
                return pdj.proceed();
            }

    }

    /**
     * 处理行间权限标识
     *
     * @param invokeClass 处理模板方法所属类Class
     * @param logicClass  权限逻辑类Class
     * @param response    待处理结果数据集
     * @return 返回添加权限标识后的数据集
     */
    private Object rowAuthHandel(Class<?> invokeClass, Class<?> logicClass, Object response) {

        Object logicInstance = BeanFactoryUtils.getBean(logicClass);//取得权限逻辑实例对象
        if (logicInstance == null) {
            new RuntimeException(logicClass.getName() + " not in the spring container ");
        }
        if (response instanceof TableResultResponse) {
            TableResultResponse resultResponse  = (TableResultResponse) response;
            List changList = new ArrayList();
            //数据行间处理
            resultResponse.getData().getRows().forEach(row -> {
                LinkedHashMap<String, Object> rowLinkMap = handelRow(logicClass, logicInstance, row);
                changList.add(rowLinkMap);
            });
            resultResponse.getData().setRows(changList);
            return resultResponse;
        }else if(response instanceof ObjectRestResponse) {
            ObjectRestResponse resultResponse = (ObjectRestResponse) response;
            Object row =resultResponse.getData();
            LinkedHashMap<String, Object> rowLinkMap = handelRow(logicClass, logicInstance, row);
            resultResponse.setData(rowLinkMap);
            return  resultResponse;
        }else{

            throw new RuntimeException(" The " + invokeClass.getName() + " method return type definition must be " + TableResultResponse.class);
        }
    }

    /**
     * 处理单行数据的授权标识
     * @param logicClass
     * @param logicInstance
     * @param row
     * @return
     */
    private LinkedHashMap<String, Object>  handelRow(Class<?> logicClass, Object logicInstance,Object row) {
        LinkedHashMap<String, Object> rowLinkMap = null;
        //扩展逻辑标识字段
        List<Method> logicMethods = null;
        try {
            rowLinkMap = ObjectUtils.objectToLinkedHashMap(row);
            logicMethods = getClassMethodByAnnotation(logicClass, RowAuthLogic.class);//获取权限标记逻辑注解方法
            if(logicMethods.size()<1){
                throw new RuntimeException(" The " + logicClass.getName() + "do not have any logical annotation("+RowAuthLogic.class+") methods.");
            }
            for (Method logicMethod : logicMethods) {
                String fieldName = null;
                try {
                    RowAuthLogic logicAnnotaion = logicMethod.getAnnotation(RowAuthLogic.class);
                    //获取权限标识字段名、逻辑方法名
                    fieldName = !StringUtils.isEmpty(logicAnnotaion.authField()) ? logicAnnotaion.authField() : logicMethod.getName();//默认使用方法名作为属性名
                    Object logicMethodBon = logicMethod.invoke(logicInstance, row);
                    if (!(logicMethodBon instanceof Boolean)) {
                        throw new RuntimeException(" The " + logicMethod.getName() + " method return type definition must be " + Boolean.class);
                    }
                    //执行逻辑方法、添加字段标识及逻辑方法返回值 key-value
                    rowLinkMap.put(fieldName, logicMethodBon);
                } catch (Exception e) {
                    rowLinkMap.put(fieldName, false);//异常默认为false
                    e.printStackTrace();

                }
            }
        } catch (NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return  rowLinkMap;
    }


    /**
     * 得到Class中包含有传入Annotation类型的方法
     *
     * @param clz     Class类型
     * @param annoClz Annotation类型
     * @return 传入Annotation类型标记的方法
     * @throws Exception
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static List<Method> getClassMethodByAnnotation(Class clz, Class annoClz) throws NoSuchMethodException {

        //clz = Class.forName(clz.getName(), true, clz.getClassLoader());

        List<Method> result = new ArrayList<Method>();

        for (Method method : clz.getMethods()) {

            if (method.getAnnotation(annoClz) != null) {
                result.add(clz.getMethod(method.getName(), method.getParameterTypes()));
            }
        }

        return result;
    }
}
