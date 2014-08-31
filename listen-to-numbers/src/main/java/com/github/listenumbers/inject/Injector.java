package com.github.listenumbers.inject;

import dagger.ObjectGraph;

public interface Injector {
    void inject(Object object);
    ObjectGraph getObjectGraph();
}
