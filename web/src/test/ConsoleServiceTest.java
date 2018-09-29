import com.google.common.collect.Maps;
import com.treefinance.saas.management.console.ConsoleServerApplication;
import com.treefinance.saas.management.console.dao.entity.TaskAndTaskAttribute;
import com.treefinance.saas.merchant.center.facade.request.console.GetMerchantByIdRequest;
import com.treefinance.saas.merchant.center.facade.service.MerchantBaseInfoFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by haojiahong on 2017/12/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ConsoleServerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ConsoleServiceTest {


    @Autowired
    private MerchantBaseInfoFacade merchantBaseInfoFacade;

    @Test
    public void ss(){
        GetMerchantByIdRequest request = new GetMerchantByIdRequest();
        request.setId(44026148916563971L);

        merchantBaseInfoFacade.getBaseInfoById(request);
    }

    @Test
    public void testStream() {
        Stream.iterate(0,x->x+1).limit(10).forEach(System.out::println);
        Stream.generate(()->2).limit(10).forEach(System.out::println);
    }


}
