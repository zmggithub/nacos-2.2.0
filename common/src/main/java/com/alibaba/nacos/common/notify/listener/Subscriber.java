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

package com.alibaba.nacos.common.notify.listener;

import com.alibaba.nacos.common.notify.Event;

import java.util.concurrent.Executor;

/**
 * An abstract subscriber class for subscriber interface.
 * 这里的单事件订阅者指的是当前的订阅者只能订阅一种类型的事件。 单事件订阅者
 * @author <a href="mailto:liaochuntao@live.com">liaochuntao</a>
 * @author zongtanghu
 *
 * zmg@2022-07-03
 * SmartSubscriber和Subscriber的区别是一个可以订阅多个事件，一个只能订阅一个事件，处理它们的发布者也不同。{@link com.alibaba.nacos.common.notify.listener.SmartSubscriber}
 */
@SuppressWarnings("PMD.AbstractClassShouldStartWithAbstractNamingRule")
public abstract class Subscriber<T extends Event> {
    
    /**
     * Event callback.
     * 事件处理入口，由对应的事件发布器调用
     * @param event {@link Event}
     */
    public abstract void onEvent(T event);
    
    /**
     * Type of this subscriber's subscription.
     * 订阅的事件类型
     * @return Class which extends {@link Event}
     */
    public abstract Class<? extends Event> subscribeType();
    
    /**
     * It is up to the listener to determine whether the callback is asynchronous or synchronous.
     * 线程执行器，由具体的实现类来决定是异步还是同步调用
     * @return {@link Executor}
     */
    public Executor executor() {
        return null;
    }
    
    /**
     * Whether to ignore expired events.
     * 是否忽略过期事件
     * @return default value is {@link Boolean#FALSE}
     */
    public boolean ignoreExpireEvent() {
        return false;
    }
}
