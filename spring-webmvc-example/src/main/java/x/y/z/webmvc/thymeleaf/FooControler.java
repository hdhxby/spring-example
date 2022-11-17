package x.y.z.webmvc.thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping("/thymeleaf")
@Controller
public class FooControler {
    @RequestMapping("/foo")
    public String hello(Map<String, Object> map) {
        //通过 map 向前台页面传递数据
        map.put("name", "foo");
        return "/thymeleaf/foo";
    }
}
