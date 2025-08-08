//package io.github.jfelixy.fake_requisitions.config;
//import io.github.jfelixy.fake_requisitions.bean.FakeRequisitions;
//import io.github.jfelixy.fake_requisitions.service.SendRequisitions;
//import org.springframework.beans.factory.annotation.Autowired;
//
//
//import java.util.*;
//
//public class MainApp {
//    @Autowired
//    public FakeRequisitions gerador;
//    public static void main(String[] args) throws Exception {
//
//        // 2. Definir campos personalizados
//        Map<String, String> fields = new HashMap<>();
//        fields.put("nome", gerador.name().fullName());
//        fields.put("email", gerador.internet().emailAddress());
//        fields.put("matricula", String.valueOf(gerador.number().numberBetween(0000, 9999)));
//
//        List<String> jsons = gerador.generateJsons(10, fields);
//        gerador.sendRequisition(jsons);
//
//    }
//}