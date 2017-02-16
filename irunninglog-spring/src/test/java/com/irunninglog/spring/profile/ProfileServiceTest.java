package com.irunninglog.spring.profile;

import com.irunninglog.api.Gender;
import com.irunninglog.api.Unit;
import com.irunninglog.api.profile.IProfileService;
import com.irunninglog.api.ResponseStatusException;
import com.irunninglog.spring.AbstractTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@SuppressWarnings("SpringJavaAutowiredMembersInspection")
public class ProfileServiceTest extends AbstractTest {

    @Autowired
    private IProfileService profileService;
    @Autowired
    private IProfileEntityRepository profileEntityRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private long goodId;
    private long badId;

    @Before
    public void before() {
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setEmail("allan@irunninglog.com");
        profileEntity.setPassword(passwordEncoder.encode("psssword"));
        profileEntity.setFirstName("Allan");
        profileEntity.setLastName("Lewis");
        profileEntity.setBirthday(LocalDate.now());
        profileEntity.setGender(Gender.Male);
        profileEntity.setWeekStart(DayOfWeek.MONDAY);
        profileEntity.setPreferredUnits(Unit.English);

        profileEntity = profileEntityRepository.save(profileEntity);
        goodId = profileEntity.getId();
        badId = profileEntity.getId() + 1;
    }

    @After
    public void after() {
        profileEntityRepository.deleteAll();
    }

    @Test
    public void good() {
        assertNotNull(profileService.get(goodId));
    }

    @Test
    public void bad() {
        try {
            profileService.get(badId);
            fail("Should have thrown");
        } catch (ResponseStatusException ex) {
            assertTrue(Boolean.TRUE);
        }
    }

}