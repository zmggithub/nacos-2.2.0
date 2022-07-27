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

import com.alibaba.nacos.common.spi.NacosServiceLoader;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Abstract Naming Interceptor Chain 抽象的命名服务拦截器链，用于定义拦截器链的工作流程.
 * 实现了 NacosNamingInterceptorChain 所定义的对 NacosNamingInterceptor 的操作。
 * 在构造方法中提供了具体的拦截器实现类的加载，它这里使用了SPI方式加载。默认可以加载的拦截器必须是NacosNamingInterceptor的实例。
 * 在拦截器的执行方法doInterceptor()中会按优先级调用每一个拦截器，首先判断被拦截的对象是否是此拦截器处理，接着调用拦截器的intercept()方法,成功后调用被拦截对象的afterIntercept()方法。
 * 若未拦截成功则调用被拦截对象的passIntercept()方法。因此在拦截器中的intercept()方法中可以定义拦截器对被拦截对象的处理逻辑，而被拦截对象则可以在afterIntercept()和passIntercept()方法中定义自身的处理逻辑。
 * 从而实现在拦截器中被处理和自身处理任务依赖于拦截器来触发.
 *
 * @author xiweng.yy
 */
public abstract class AbstractNamingInterceptorChain<T extends Interceptable>
        implements NacosNamingInterceptorChain<T> {

    /**
     * 存储多个拦截器
     */
    private final List<NacosNamingInterceptor<T>> interceptors;

    // 限制使用范围为当前包或者其子类
    protected AbstractNamingInterceptorChain(Class<? extends NacosNamingInterceptor<T>> clazz) {
        this.interceptors = new LinkedList<>();

        // 使用SPI模式加载指定的拦截器类型，而且NacosNamingInterceptor内部有判断它需要拦截对象的类型，因此非常灵活
        interceptors.addAll(NacosServiceLoader.load(clazz));
        interceptors.sort(Comparator.comparingInt(NacosNamingInterceptor::order));
    }
    
    /**
     * Get all interceptors.
     *
     * @return interceptors list
     */
    protected List<NacosNamingInterceptor<T>> getInterceptors() {
        return interceptors;
    }
    
    @Override
    public void addInterceptor(NacosNamingInterceptor<T> interceptor) {

        // 若手动添加，则需要再次进行排序
        interceptors.add(interceptor);
        interceptors.sort(Comparator.comparingInt(NacosNamingInterceptor::order));
    }
    
    @Override
    public void doInterceptor(T object) { // 使用当前拦截器链内部的所有拦截器对被拦截对象进行处理，并且组织了被拦截对象被拦截之后的方法调用流程.

        // 因为内部的拦截器已经排序过了，所以直接遍历
        for (NacosNamingInterceptor<T> each : interceptors) {

            // 若当前拦截的对象不是当前拦截器所要处理的类型则调过
            if (!each.isInterceptType(object.getClass())) {
                continue;
            }

            // 执行拦截操作成功之后，继续执行拦截后操作
            if (each.intercept(object)) {
                object.afterIntercept();
                return;
            }
        }

        // 未拦截的操作
        object.passIntercept();
    }
}
