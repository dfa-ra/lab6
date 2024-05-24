package com.ra.client;

import com.ra.common.User;
import com.ra.common.communication.Request;
import com.ra.common.communication.Response;
import com.ra.common.forms.Form;
import com.ra.common.forms.typeForms.LoginForm;
import com.ra.common.message.Message;
import com.ra.common.message.MessageType;
import com.ra.common.message.Sender;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.List;

public class LogIn {
    public static boolean initUser(Handler handler, User user) throws ParseException {
        Form form = new Form(new LoginForm());
        Response response;
        List<String> answer;
        form.formReady();
        answer = form.collectInfo(new BufferedReader(new InputStreamReader(System.in)));
        Request request = new Request("sign_in", "");
        request.setUser(new User(answer.get(0), generateSHA1(answer.get(1))));
        System.out.println(request.getLogin());
        System.out.println(generateSHA1(answer.get(1)));
        handler.sendRequest(request);
        response = handler.dataReception();
        if (response.getAdditional().equals("ok")) {
            user.setLogin(answer.get(0));
            user.setPassword(generateSHA1(answer.get(1)));
            return true;
        }
        Sender.send(new Message(MessageType.WARNING, response.getAdditional(), "\n"));
        return false;
    }

    public static boolean addUser(Handler handler, User user){
        Form form = new Form(new LoginForm());
        Response response;
        List<String> answer;
        form.formReady();
        answer = form.collectInfo(new BufferedReader(new InputStreamReader(System.in)));
        Request request = new Request("sign_up", "");
        request.setUser(new User(answer.get(0), generateSHA1(answer.get(1))));
        System.out.println(request.getLogin());
        System.out.println(generateSHA1(answer.get(1)));
        handler.sendRequest(request);
        response = handler.dataReception();
        if (response.getAdditional().equals("ok")) {
            Sender.send(new Message(MessageType.INFO, "User successfully registered!", "\n"));
            user.setLogin(answer.get(0));
            user.setPassword(generateSHA1(answer.get(1)));
            return true;
        }
        Sender.send(new Message(MessageType.WARNING, response.getAdditional(), "\n"));
        return false;
    }

    private static String generateSHA1(String str){
        String sha1 = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            sha1 = new String(md.digest(str.getBytes()));
        }catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return sha1;
    }
}
