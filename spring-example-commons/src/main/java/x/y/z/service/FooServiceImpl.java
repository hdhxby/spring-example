package x.y.z.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import x.y.z.repository.FooRepository;

@Transactional
@Service
public class FooServiceImpl implements FooService{

    @Autowired
    private FooRepository fooRepository;

    @Override
    public String foo() {
        return "foo";
    }

    public FooRepository getFooRepository() {
        return fooRepository;
    }

    public void setFooRepository(FooRepository fooRepository) {
        this.fooRepository = fooRepository;
    }
}
