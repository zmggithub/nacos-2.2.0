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

package com.alibaba.nacos.naming.healthcheck.heartbeat;

import com.alibaba.nacos.common.task.AbstractExecuteTask;
import com.alibaba.nacos.naming.core.v2.client.impl.IpPortBasedClient;
import com.alibaba.nacos.naming.core.v2.pojo.HealthCheckInstancePublishInfo;
import com.alibaba.nacos.naming.core.v2.pojo.InstancePublishInfo;

/**
 * Client beat update task v2版本的，用于更新某个Client下所有的实例.
 *
 * @author xiweng.yy
 */
public class ClientBeatUpdateTask extends AbstractExecuteTask {

    /**
     * 客户端对账
     */
    private final IpPortBasedClient client;
    
    public ClientBeatUpdateTask(IpPortBasedClient client) {
        this.client = client;
    }
    
    @Override
    public void run() {

        // 获取当前时间，更新Client和Client下的Instance的最新活跃时间
        long currentTime = System.currentTimeMillis();
        for (InstancePublishInfo each : client.getAllInstancePublishInfo()) {
            ((HealthCheckInstancePublishInfo) each).setLastHeartBeatTime(currentTime);
        }
        client.setLastUpdatedTime();
    }
}
