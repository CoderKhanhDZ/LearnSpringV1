package com.learnspring.java11.LearnSpringV1.controller;

import com.learnspring.java11.LearnSpringV1.model.DemoObject;
import com.learnspring.java11.LearnSpringV1.model.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//@Controller = @RestController + @ResponseBody

@RestController// danh dau day la controller
@RequestMapping("/demo") // mapping request tat ca duong dan con phai di qua day
public class DemoController {

    @Autowired // tiem
    DemoObject demoObject;

    // return-type khi controller duoc danh dau la @Controller
    // 1- @ResponseBody: danh dau day la responseBody
    // 2- ResponseEntity<T>
    @GetMapping(path ="/hello")
    public String Demo(){
        return "Hello " + demoObject.getName();
    }

    @GetMapping("/demo-object")
    public ResponseEntity<ResponseObject> DemoObject(@RequestParam("name") String name,@RequestParam("age") int age){
        try{
            DemoObject demoObjects = new DemoObject();
            demoObjects.setName(name);
            demoObjects.setAge(age);

            return demoObjects.getName() != null && !demoObjects.getName().isEmpty() ?
                    ResponseEntity.status(HttpStatus.OK).body(
                            new ResponseObject(HttpStatus.OK.toString(),"success",demoObjects))
                    :ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                            new ResponseObject(HttpStatus.NOT_IMPLEMENTED.toString(),"fail",demoObjects));
        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject(HttpStatus.NOT_IMPLEMENTED.toString(),e.getMessage(),""));
        }

    }


}
