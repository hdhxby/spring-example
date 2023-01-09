package x.y.z.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import x.y.z.manager.FooManager;
import x.y.z.repository.FooRepository;
import x.y.z.service.FooService;

import java.util.List;

@Transactional
@Service
public /* abstract */ class FooServiceImpl implements FooService {

    @Autowired(required = false)
    private FooRepository fooRepository;

    @Autowired(required = false)
    private FooManager fooManager;

    @Override
    public long count() {
        return fooRepository.count();
    }

    public FooRepository getFooRepository() {
        return fooRepository;
    }

    public void setFooRepository(FooRepository fooRepository) {
        this.fooRepository = fooRepository;
    }

    public FooManager getFooManager() {
        return fooManager;
    }

    public void setFooManager(FooManager fooManager) {
        this.fooManager = fooManager;
    }

//    /**
//     * 运行时生成
//     * @return
//     */
//    @Lookup
//    public abstract FooManager getFooManager();
}
