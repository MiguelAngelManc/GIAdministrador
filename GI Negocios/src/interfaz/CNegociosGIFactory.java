/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

/**
 *
 * @author Home
 */
public class CNegociosGIFactory {
    public static CNegociosGI buildDatos() throws Exception{
        return  CNegociosGI.getInstance();
    }
}
