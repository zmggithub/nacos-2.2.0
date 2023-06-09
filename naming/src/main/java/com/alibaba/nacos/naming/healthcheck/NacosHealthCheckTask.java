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

package com.alibaba.nacos.naming.healthcheck;

import com.alibaba.nacos.naming.interceptor.Interceptable;

/**
 * Nacos health check task 负责执行健康检查的任务,定义了健康检查的基本方法
 * 目前有两种实现 ClientBeatCheckTaskV2 和 HealthCheckTaskV2
 * 前者处理心跳相关的状态，后者处理各种连接的状态.
 * 继承Interceptable说明其可以被拦截器处理。继承Runnable说明其是一个线程，可被线程执行器调度
 *
 * @author xiweng.yy
 */
public interface NacosHealthCheckTask extends Interceptable, Runnable {
    
    /**
     * Get task id.
     *
     * @return task id.
     */
    String getTaskId();
    
    /**
     * Do health check.
     */
    void doHealthCheck();
}
