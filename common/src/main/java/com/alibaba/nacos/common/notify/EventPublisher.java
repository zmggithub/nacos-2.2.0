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

package com.alibaba.nacos.common.notify;

import com.alibaba.nacos.common.lifecycle.Closeable;
import com.alibaba.nacos.common.notify.listener.Subscriber;

/**
 * Event publisher.
 *
 * @author <a href="mailto:liaochuntao@live.com">liaochuntao</a>
 * @author zongtanghu
 *
 * zmg@2022-07-03
 * 发布者的主要功能就是新增订阅者、通知订阅者,目前有两种类型的发布者分别是
 * {@link com.alibaba.nacos.common.notify.DefaultPublisher }和 {@link com.alibaba.nacos.common.notify.DefaultSharePublisher}
 */
public interface EventPublisher extends Closeable {
    
    /**
     * Initializes the event publisher.
     * 初始化事件发布者
     * @param type       {@link Event >}
     * @param bufferSize Message staging queue size
     */
    void init(Class<? extends Event> type, int bufferSize);
    
    /**
     * The number of currently staged events.
     * 当前暂存的事件数量
     * @return event size
     */
    long currentEventSize();
    
    /**
     * Add listener.
     * 添加订阅者
     * @param subscriber {@link Subscriber}
     */
    void addSubscriber(Subscriber subscriber);
    
    /**
     * Remove listener.
     * 移除订阅者
     * @param subscriber {@link Subscriber}
     */
    void removeSubscriber(Subscriber subscriber);
    
    /**
     * publish event.
     * 发布事件
     * @param event {@link Event}
     * @return publish event is success
     */
    boolean publish(Event event);
    
    /**
     * Notify listener.
     * 通知订阅者
     * @param subscriber {@link Subscriber}
     * @param event      {@link Event}
     */
    void notifySubscriber(Subscriber subscriber, Event event);
    
}
