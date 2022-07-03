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

import java.util.List;

/**
 * Subscribers to multiple events can be listened to.
 * 可以侦听多个事件的订阅服务器  多事件订阅者
 * @author <a href="mailto:liaochuntao@live.com">liaochuntao</a>
 * @author zongtanghu
 */
@SuppressWarnings("PMD.AbstractClassShouldStartWithAbstractNamingRule")
public abstract class SmartSubscriber extends Subscriber {
    
    /**
     * Returns which event type are smartsubscriber interested in.
     * 区别于父类，这里支持多个事件类型
     * @return The interestd event types.
     */
    public abstract List<Class<? extends Event>> subscribeTypes();
    
    @Override
    public final Class<? extends Event> subscribeType() {
        // 采用final修饰，禁止使用单一事件属性
        return null;
    }
    
    @Override
    public final boolean ignoreExpireEvent() {
        return false;
    }
}
