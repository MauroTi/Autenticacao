package com.example.autenticacao;

import java.util.ArrayList;
import java.util.List;

public class LoginController {

    static List<Login> listaLogins = new ArrayList<>();


    public void cadastroLogin(Login login) {

        listaLogins.add(login);
    }

    public static String exibeLogin() {
        //System.out.println(listaLogins.toString());
        return listaLogins.toString();

    }

    public List<Login> reLogin() {
        return this.listaLogins;
    }

}
