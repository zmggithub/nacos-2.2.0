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
 * Nacos naming interceptor chain 调度者主要是用来管理拦截器的组织方式，触发拦截器的拦截操作.
 * 整体的构成由 NacosNamingInterceptorChain 定义基本框架，
 *  AbstractNamingInterceptorChain 实现通用逻辑，
 *  HealthCheckInterceptorChain 和 InstanceBeatCheckTaskInterceptorChain 则分别服务于健康检查和心跳检查.
 * @author xiweng.yy
 */
public interface NacosNamingInterceptorChain<T extends Interceptable> {
    
    /**
     * Add interceptor.
     *
     * @param interceptor interceptor
     */
    void addInterceptor(NacosNamingInterceptor<T> interceptor);
    
    /**
     * Do intercept by added interceptors.
     *
     * @param object be interceptor object
     */
    void doInterceptor(T object);
}
