package com.example.AdminConsole.conttroller;

import com.example.AdminConsole.cache.CacheClass;
import com.example.AdminConsole.config.domain.AdminConsoleUser;
import com.example.AdminConsole.config.domain.request.AdminConsoleRequest;
import com.example.AdminConsole.config.domain.response.AdminData;
import com.example.AdminConsole.service.AdminConsoleService;
import com.example.AdminConsole.service.AdminConsoleUserDetails;
import com.example.AdminConsole.utility.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class AdminController {

    private AdminConsoleService employeeService;

    private CacheClass cacheClass;

    private JWTTokenProvider jwtTokenProvider;

    private AuthenticationManager authenticationManager;

    @Autowired
    public AdminController(AdminConsoleService employeeService, CacheClass cacheClass, JWTTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.employeeService = employeeService;
        this.cacheClass = cacheClass;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @PreAuthorize("hasAuthority('user:read')")
    @GetMapping("/test")
    public String getTest(){
        return "test ap";
    }

    @PostMapping("/testt/{name}")
    public String getTestTw(@PathVariable(value = "name") String name){
        cacheClass.adduserValue(name);
        return "test cache";
    }
    @GetMapping("/testtt/{name}")
    public Integer getTestCache(@PathVariable(value = "name") String name){

        return  cacheClass.getValue(name);
    }

    @PreAuthorize("hasAuthority('user:read')")
    @GetMapping("/getAllUpdates")
    public List<AdminData> getAllUpdates() {
       return  employeeService.getAllUpdates(0,0);
    }

    @PostMapping(value = "/login" )
    public ResponseEntity<Integer> login(@RequestBody AdminConsoleRequest employeeRequest){

        final AdminConsoleUser byUserName = employeeService.findByUserName(employeeRequest.getUsername());

        authenticate(employeeRequest.getUsername(),employeeRequest.getPassword());
        AdminConsoleUserDetails employeeUserDetails = new AdminConsoleUserDetails(byUserName);

        HttpHeaders httpHeaders = new HttpHeaders();
        final String s = jwtTokenProvider.generateToken(employeeUserDetails);
        httpHeaders.add("jwt-token",s);
      return  new ResponseEntity<>(124,httpHeaders, HttpStatus.OK);
    }

    private void authenticate(String username ,String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
    }

    @PostMapping("/register")
    public String registerEmployee(@RequestBody AdminConsoleRequest employeeRequest){
        employeeService.registerEmployee(employeeRequest);
        return "Done";
    }

}
