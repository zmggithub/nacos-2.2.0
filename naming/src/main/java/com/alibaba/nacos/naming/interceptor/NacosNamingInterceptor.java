/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.nacos.naming.interceptor;

/**
 * Nacos naming interceptor 定义一个拦截器的基本功能，同时限定了传入的拦截对象类型必须为 Interceptable
 * 这里只定义了基本的功能和基本的限定拦截对象。这里将其描述为基本的功能，那就意味着它的实现将会有更高级的功能.
 *
 * @author xiweng.yy
 */
public interface NacosNamingInterceptor<T extends Interceptable> {
    
    /**
     * Judge whether the input type is intercepted by this Interceptor.
     * 此拦截器的实例将会判断传入的对象是否是他需要处理的类型，此方法可以实现不同拦截器处理不同对象的隔离操作,只需要判断对象类型是否需要拦截,不是拦截逻辑.
     *
     * <p>This method only should judge the object type whether need be do intercept. Not the intercept logic.
     *
     * @param type type
     * @return true if the input type is intercepted by this Interceptor, otherwise false
     */
    boolean isInterceptType(Class<?> type);
    
    /**
     * Do intercept operation 执行实际的拦截操作.
     *
     * <p>This method is the actual intercept operation.
     *
     * @param object need intercepted object
     * @return true if object is intercepted, otherwise false
     */
    boolean intercept(T object);
    
    /**
     * The order of interceptor. The lower the number, the earlier the execution.
     * 拦截器的顺序。数字越小，执行越早.
     *
     * @return the order number of interceptor
     */
    int order();
}
