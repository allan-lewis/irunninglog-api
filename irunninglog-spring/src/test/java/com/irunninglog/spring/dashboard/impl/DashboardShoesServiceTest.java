package com.irunninglog.spring.dashboard.impl;

import com.irunninglog.spring.data.impl.ShoeEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("SpringJavaAutowiredMembersInspection")
public class DashboardShoesServiceTest extends AbstractDashboardServicesTest {

    @Autowired
    private DashboardShoesService shoesService;

    @Test
    public void noShoes() {
        assertEquals(0, shoesService.shoes(profileEntity).size());
    }

    @Test
    public void oneShoe() {
        ShoeEntity one = new ShoeEntity();
        one.setName("One");
        one.setUser(userEntity);

        ShoeEntity two = new ShoeEntity();
        two.setName("Two");
        two.setUser(userEntity);
        two.setDashboard(Boolean.TRUE);

        shoeEntityRepository.save(one);
        shoeEntityRepository.save(two);

        assertEquals(1, shoesService.shoes(profileEntity).size());
    }

}