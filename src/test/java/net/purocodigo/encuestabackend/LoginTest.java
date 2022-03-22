package net.purocodigo.encuestabackend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;

import net.purocodigo.encuestabackend.services.UserService;
import  net.purocodigo.encuestabackend.repositories.UserRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import net.purocodigo.encuestabackend.models.request.UserRegisterRequestModel;

import net.purocodigo.TestUtil;
import net.purocodigo.encuestabackend.models.request.UserLoginRequestModel;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class LoginTest {

    private static final String API_LOGIN_URL="/users/login";
    
    @Autowired
    TestRestTemplate testRestTemplate;
    @Autowired
    UserService userService; 
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void cleanup(){
        userRepository.deleteAll();
    }


    @Test
    public void postLogin_sinCredenciales_retornaForbidden(){

        ResponseEntity<Object> response=login(null,Object.class);
        assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);

    }


    @Test
    public void postLogin_conCredencialesIncorrectas_retornaForbidden() {
        UserRegisterRequestModel user = TestUtil.createValidUser();
        userService.createUser(user);
        UserLoginRequestModel model = new UserLoginRequestModel();
        model.setEmail("asd@gmail.com");
        model.setPassword("12345678");
        ResponseEntity<Object> response = login(model, Object.class);
        assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
    }



    @Test
    public void postLogin_conCredencialesCorrectas_retornaok(){

        UserRegisterRequestModel user= TestUtil.createValidUser();
        userService.createUser(user);        

        UserLoginRequestModel model=new UserLoginRequestModel();
        model.setEmail(user.getEmail());
        model.setPassword(user.getPassword());

        ResponseEntity<Object> response=login(model,Object.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);

    }




        public <T> ResponseEntity<T> login(UserLoginRequestModel data,Class<T> responseType){
           
           
            return testRestTemplate.postForEntity(API_LOGIN_URL, data, responseType);
        }


}
