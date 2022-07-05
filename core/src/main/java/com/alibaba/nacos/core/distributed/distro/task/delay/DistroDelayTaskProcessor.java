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

package com.alibaba.nacos.core.distributed.distro.task.delay;

import com.alibaba.nacos.common.task.NacosTask;
import com.alibaba.nacos.common.task.NacosTaskProcessor;
import com.alibaba.nacos.core.distributed.distro.component.DistroComponentHolder;
import com.alibaba.nacos.core.distributed.distro.entity.DistroKey;
import com.alibaba.nacos.core.distributed.distro.task.DistroTaskEngineHolder;
import com.alibaba.nacos.core.distributed.distro.task.execute.DistroSyncChangeTask;
import com.alibaba.nacos.core.distributed.distro.task.execute.DistroSyncDeleteTask;

/**
 * Distro delay task processor.
 *
 * @author xiweng.yy
 */
public class DistroDelayTaskProcessor implements NacosTaskProcessor {
    
    private final DistroTaskEngineHolder distroTaskEngineHolder;
    
    private final DistroComponentHolder distroComponentHolder;
    
    public DistroDelayTaskProcessor(DistroTaskEngineHolder distroTaskEngineHolder,
            DistroComponentHolder distroComponentHolder) {
        this.distroTaskEngineHolder = distroTaskEngineHolder;
        this.distroComponentHolder = distroComponentHolder;
    }

    // 这里又会封装成DistroSyncChangeTask，然后交给Worker线程执行，
    // 我们之前分析过，在worker处理的时候会调用taks#run方法，所以我们看下DistroSyncChangeTask#run

    @Override
    public boolean process(NacosTask task) {
        if (!(task instanceof DistroDelayTask)) {
            return true;
        }
        DistroDelayTask distroDelayTask = (DistroDelayTask) task;
        DistroKey distroKey = distroDelayTask.getDistroKey();
        switch (distroDelayTask.getAction()) {
            case DELETE:
                DistroSyncDeleteTask syncDeleteTask = new DistroSyncDeleteTask(distroKey, distroComponentHolder);
                distroTaskEngineHolder.getExecuteWorkersManager().addTask(distroKey, syncDeleteTask);
                return true;
                // change?
            case CHANGE:

            case ADD:
                // 封装同步改变任务
                DistroSyncChangeTask syncChangeTask = new DistroSyncChangeTask(distroKey, distroComponentHolder);
                // 交给worker执行
                distroTaskEngineHolder.getExecuteWorkersManager().addTask(distroKey, syncChangeTask);
                return true;
            default:
                return false;
        }
    }
}
