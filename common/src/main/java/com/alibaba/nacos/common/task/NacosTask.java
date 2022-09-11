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

package com.alibaba.nacos.common.task;

/**
 * Nacos task 作为Nacos内部Task的统一接口。.
 * 基本上系统级别的任务都是通过它的相关子类实现。
 * 此接口的子类分为了两个类型AbstractExecuteTask、AbstractDelayTask。分别代表立即执行的任务和延迟执行的任务, 对任务体系作了更细的划分。它定义了此任务是否需要被执行.
 *
 * @author xiweng.yy
 */
public interface NacosTask {
    
    /**
     * Judge Whether this nacos task should do 判断这个nacos任务是否应该做.
     *
     * @return true means the nacos task should be done, otherwise false, true 表示应该完成 nacos 任务，否则为 false
     */
    boolean shouldProcess();
}
