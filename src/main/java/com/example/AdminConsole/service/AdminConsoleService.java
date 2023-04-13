package com.example.AdminConsole.service;

import com.example.AdminConsole.config.domain.AdminConsoleUser;
import com.example.AdminConsole.config.domain.request.AdminConsoleRequest;
import com.example.AdminConsole.config.domain.response.AdminData;
import com.example.AdminConsole.enumeration.Role;
import com.example.AdminConsole.repository.EmployeeRepo;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import eu.i3market.seedsindex.SearchEngineIndexRecord;
import eu.i3market.seedsindex.SeedsIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class AdminConsoleService {
    private EmployeeRepo employeeRepo;

    @Autowired
    public AdminConsoleService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }
    public void registerEmployee(AdminConsoleRequest employeeRequest){
        final AdminConsoleUser build = AdminConsoleUser.builder()
                .email(employeeRequest.getEmail())
                .password(employeeRequest.getPassword())
                .username(employeeRequest.getUsername())
                .profession(employeeRequest.getProfession())
                .joinDate(new Date())
                .role(Role.ROLE_USER.name())
                .authorities(Role.ROLE_USER.getAuthorities())
                .build();
        employeeRepo.save(build);
    }
    public AdminConsoleUser findByUserName(String username){
        final AdminConsoleUser email = employeeRepo.findByUsername(username);
        return email;
    }

    public List<AdminData> getAllUpdates(int page , int size) {

        List<AdminData> dataList = new ArrayList<>();
        final List<String> locations = getLocationsFromSeedIndex();

        locations.stream().forEach(e-> System.out.println(e));
         HttpClient httpClient = HttpClient.newHttpClient();



        for(int i =0; i<locations.size(); i++){

            System.out.println("Value of i " +i + " "+(locations.get(i).substring(0, locations.get(i).length() - 5)));

            try {
                final HttpRequest uri = HttpRequest.newBuilder()
                        .GET()
                        .uri(new URI(locations.get(i).substring(0, locations.get(i).length() - 5) + ":8082/admin/latestUpdate")).build();

                HttpResponse<String> send = httpClient.send(uri, HttpResponse.BodyHandlers.ofString());
                if(send.statusCode()==200){

                    System.out.println(send.body());
                    Type listType = new TypeToken<ArrayList<AdminData>>(){}.getType();
                    List<AdminData> lis = new Gson().fromJson(send.body(), listType);

//                   lis.stream().forEach(e->System.out.println(e));

                    dataList.addAll(lis);
                }
            } catch (IOException e) {

            } catch (InterruptedException e) {

            } catch (URISyntaxException e) {

            }


        }
        return dataList;


    }

    public List<AdminData> getByType(String type,int page , int size) {

        List<AdminData> dataList = new ArrayList<>();
        final List<String> locations = getLocationsFromSeedIndex();

        locations.stream().forEach(e-> System.out.println(e));
        HttpClient httpClient = HttpClient.newHttpClient();



        for(int i =0; i<locations.size(); i++){

            System.out.println("Value of i " +i + " "+(locations.get(i).substring(0, locations.get(i).length() - 5)));

            try {
                final HttpRequest uri = HttpRequest.newBuilder()
                        .GET()
                        .uri(new URI(locations.get(i).substring(0, locations.get(i).length() - 5) + ":8082/admin/getByType/"+type)).build();

                HttpResponse<String> send = httpClient.send(uri, HttpResponse.BodyHandlers.ofString());
                if(send.statusCode()==200){

                    System.out.println(send.body());
                    Type listType = new TypeToken<ArrayList<AdminData>>(){}.getType();
                    List<AdminData> lis = new Gson().fromJson(send.body(), listType);

//                   lis.stream().forEach(e->System.out.println(e));

                    dataList.addAll(lis);
                }
            } catch (IOException e) {

            } catch (InterruptedException e) {

            } catch (URISyntaxException e) {

            }


        }
        return dataList;


    }

    public List<AdminData> getByAddress(String address,int page , int size) {

        List<AdminData> dataList = new ArrayList<>();
        final List<String> locations = getLocationsFromSeedIndex();

        locations.stream().forEach(e-> System.out.println(e));
        HttpClient httpClient = HttpClient.newHttpClient();



        for(int i =0; i<locations.size(); i++){

            System.out.println("Value of i " +i + " "+(locations.get(i).substring(0, locations.get(i).length() - 5)));

            try {
                final HttpRequest uri = HttpRequest.newBuilder()
                        .GET()
                        .uri(new URI(locations.get(i).substring(0, locations.get(i).length() - 5) + ":8082/admin/getByAddress/"+address)).build();

                HttpResponse<String> send = httpClient.send(uri, HttpResponse.BodyHandlers.ofString());
                if(send.statusCode()==200){

                    System.out.println(send.body());
                    Type listType = new TypeToken<ArrayList<AdminData>>(){}.getType();
                    List<AdminData> lis = new Gson().fromJson(send.body(), listType);

//                   lis.stream().forEach(e->System.out.println(e));

                    dataList.addAll(lis);
                }
            } catch (IOException e) {

            } catch (InterruptedException e) {

            } catch (URISyntaxException e) {

            }


        }
        return dataList;


    }
    private List<String> getLocationsFromSeedIndex(){
        SeedsIndex seedsIndex = new SeedsIndex("http://95.211.3.244" + ":8545",
                "0x91ca5769686d3c0ba102f0999140c1946043ecdc1c3b33ee3fd2c80030e46c26");
        return getLocations(seedsIndex);
    }


    private List<String> getLocations(SeedsIndex seedsIndex){
        List<String>  location= new ArrayList<>();
        try {

            seedsIndex.init();
            final Collection<SearchEngineIndexRecord> byDataCategory = seedsIndex.findByDataCategory(null);
            for(var se : byDataCategory){
                location.add(String.valueOf(se.getLocation())) ;
            }
            return location;

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            seedsIndex.shutdown();
        }
        return null;
    }
}
