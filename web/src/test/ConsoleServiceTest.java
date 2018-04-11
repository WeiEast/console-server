import com.google.common.collect.Maps;
import com.treefinance.saas.management.console.ConsoleServerApplication;
import com.treefinance.saas.management.console.dao.entity.TaskAndTaskAttribute;
import com.treefinance.saas.management.console.dao.mapper.TaskAndTaskAttributeMapper;
import com.treefinance.saas.merchant.center.facade.request.console.GetMerchantByIdRequest;
import com.treefinance.saas.merchant.center.facade.service.MerchantBaseInfoFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

/**
 * Created by haojiahong on 2017/12/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ConsoleServerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ConsoleServiceTest {

    @Autowired
    private TaskAndTaskAttributeMapper taskAndTaskAttributeMapper;

    @Autowired
    private MerchantBaseInfoFacade merchantBaseInfoFacade;

    @Test
    public void ss(){
        GetMerchantByIdRequest request = new GetMerchantByIdRequest();
        request.setId(44026148916563971L);

        merchantBaseInfoFacade.getBaseInfoById(request);
    }

    @Test
    public void test_TaskAndTaskAttributeMapper() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("bizType", 1);
        map.put("start", 0);
        map.put("limit", 20);
        long total = taskAndTaskAttributeMapper.countByExample(map);
        List<TaskAndTaskAttribute> list = taskAndTaskAttributeMapper.getByExample(map);
        System.out.println(total);
        System.out.println(list);
    }

}
