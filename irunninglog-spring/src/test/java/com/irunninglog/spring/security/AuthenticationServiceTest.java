package com.irunninglog.spring.security;

import com.irunninglog.api.Gender;
import com.irunninglog.api.Unit;
import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.security.AuthzException;
import com.irunninglog.api.security.*;
import com.irunninglog.api.Endpoint;
import com.irunninglog.spring.AbstractTest;
import com.irunninglog.spring.profile.IProfileEntityRepository;
import com.irunninglog.spring.profile.ProfileEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.junit.Assert.*;

@SuppressWarnings("SpringJavaAutowiredMembersInspection")
public class AuthenticationServiceTest extends AbstractTest {

    @Autowired
    private IAuthenticationService authenticationService;
    @Autowired
    private IProfileEntityRepository profileEntityRepository;
    @Autowired
    private IUserEntityRepository userEntityRepository;
    @Autowired
    private IAuthorityEntityRepository authorityEntityRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private ProfileEntity myprofile;
    private ProfileEntity admin;
    private ProfileEntity none;

    @Before
    public void before() {
        myprofile = save("allan@irunninglog.com", "password", "MYPROFILE");
        admin = save("admin@irunninglog.com", "password", "ADMIN");
        none = save("none@irunninglog.com", "password");
    }

    private ProfileEntity save(String email, String password, String ... authorities) {
        ProfileEntity entity = new ProfileEntity();
        entity.setEmail(email);
        entity.setPassword(passwordEncoder.encode(password));
        entity.setFirstName("Allan");
        entity.setLastName("Lewis");
        entity.setBirthday(LocalDate.now());
        entity.setGender(Gender.Male);
        entity.setPreferredUnits(Unit.English);
        entity.setWeekStart(DayOfWeek.MONDAY);

        entity = profileEntityRepository.save(entity);

        UserEntity userEntity = userEntityRepository.findOne(entity.getId());
        for (String authority : authorities) {
            AuthorityEntity authorityEntity = new AuthorityEntity();
            authorityEntity.setName(authority);
            authorityEntity = authorityEntityRepository.save(authorityEntity);

            userEntity.getAuthorities().add(authorityEntity);
        }
        userEntityRepository.save(userEntity);

        return entity;
    }

    @After
    public void after() {
        userEntityRepository.deleteAll();
        authorityEntityRepository.deleteAll();
    }

    @Test
    public void success() throws AuthnException, AuthzException {
        IUser user = authenticationService.authenticate(Endpoint.GetDashboard,
                "/profiles/" + myprofile.getId(),
                "Basic YWxsYW5AaXJ1bm5pbmdsb2cuY29tOnBhc3N3b3Jk");
        assertEquals("allan@irunninglog.com", user.getUsername());
        assertEquals(1, user.getAuthorities().size());
    }

    @Test
    public void notFound() throws AuthzException {
        try {
            authenticationService.authenticate(Endpoint.GetDashboard,
                    null,
                    "Basic bm9ib2R5QGlydW5uaW5nbG9nLmNvbTpwYXNzd29yZA==");

            fail("Should have thrown");
        } catch (AuthnException ex) {
            assertTrue(ex.getMessage().contains("not found"));
        }
    }

    @Test
    public void wrongPassword() throws AuthzException {
        try {
            authenticationService.authenticate(Endpoint.GetDashboard,
                    null,
                    "Basic YWxsYW5AaXJ1bm5pbmdsb2cuY29tOndyb25n");

            fail("Should have thrown");
        } catch (AuthnException ex) {
            assertTrue(ex.getMessage().contains("don't match"));
        }
    }

    @Test
    public void denyAll() throws AuthnException {
        try {
            authenticationService.authenticate(Endpoint.Forbidden,
                    null,
                    "Basic YWxsYW5AaXJ1bm5pbmdsb2cuY29tOnBhc3N3b3Jk");

            fail("Should have thrown");
        } catch (AuthzException ex) {
            assertTrue(ex.getMessage().contains("Not authorized for endpoint"));
        }
    }

    @Test
    public void canViewMyProfile() throws AuthnException, AuthzException {
        IUser user = authenticationService.authenticate(Endpoint.GetProfile,
                "/profiles/" + myprofile.getId(),
                "Basic YWxsYW5AaXJ1bm5pbmdsb2cuY29tOnBhc3N3b3Jk");

        assertNotNull(user);
    }

    @Test
    public void admin() throws AuthnException, AuthzException {
        IUser user = authenticationService.authenticate(Endpoint.GetProfile,
                "/profiles/" + myprofile.getId() + "" + admin.getId(),
                "Basic YWRtaW5AaXJ1bm5pbmdsb2cuY29tOnBhc3N3b3Jk");

        assertNotNull(user);
    }

    @Test
    public void none() throws AuthnException {
        try {
            authenticationService.authenticate(Endpoint.GetProfile,
                    "/profiles/" + none.getId(),
                    "Basic bm9uZUBpcnVubmluZ2xvZy5jb206cGFzc3dvcmQ=");

            fail("Should have thrown");
        } catch (AuthzException ex) {
            ex.printStackTrace();
        }
    }

}