/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestion_clientes_SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cecilia
 */
public class CienteDao {
    
    //Esta es la clase que tiene la conexion a la base de datos
    
    List<Cliente> listado = new ArrayList<>();
    
    public Connection conectar(){
    
        
            String baseDeDatos = "java";
            String usuario = "root";
            String password = "root";
            String host = "localhost";
            String puerto = "3306";
            String driver = "com.mysql.jdbc.Driver";
            String conexionUrl = "jdbc:mysql://"+host+":"+puerto+"/"+baseDeDatos+"?useSSL=false";
            
            
            Connection conexion = null;
            
            try {
            // Registrarel driver de MySql
            Class.forName(driver);
            
            //Establecer la conexion:
            conexion = DriverManager.getConnection(conexionUrl,usuario,password);
        
      
              
        } catch (Exception ex) {
            Logger.getLogger(CienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
           return conexion; 
    }
    
    public void agregarCliente(Cliente cliente){
     try {
        Connection conexion = conectar();
        String sql = "INSERT INTO `clientes` (`id`,`nombre`,`apellido`,`telefono`,`email`)"
                + " VALUES (NULL, '"+cliente.getNombre()+"', '"+cliente.getApellido()+"', '"+cliente.getTelefono()+"', '"+cliente.getEmail()+"')";
        Statement statement;
        statement = conexion.createStatement();
        statement.execute(sql);
        
        
        
        } catch (SQLException ex) {
            Logger.getLogger(CienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Cliente> listar(){
 try {
        Connection conexion = conectar();
        String sql = "SELECT * FROM `clientes`";
        Statement statement;
        statement = conexion.createStatement();
        ResultSet respuesta =  statement.executeQuery(sql);
        
        //Recorro el ResultSet y agrego cada cliente al listado
        while(respuesta.next()){
        Cliente cliente = new Cliente();
        cliente.setNombre(respuesta.getString("nombre"));
        cliente.setApellido(respuesta.getString("apellido"));
        cliente.setEmail(respuesta.getString("email"));
        cliente.setTelefono(respuesta.getString("telefono"));
        cliente.setId(respuesta.getInt("id"));
        listado.add(cliente);
        }
        
        
        } catch (SQLException ex) {
            Logger.getLogger(CienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }

 return listado;
}

    public void eliminar(int id){
    
    try{
    
    Connection conexion = conectar();
    String sql = "DELETE FROM `clientes` WHERE `clientes` . `id` = " + id;
    Statement statement = conexion.createStatement();
    statement.execute(sql);
    
    }catch(Exception ex){
    
    Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    }
    
    public void actualizar(Cliente cliente){
    
        try {
            Connection conexion = conectar();
            
            String sql = "UPDATE `clientes` SET `nombre` = '"+cliente.getNombre()+"',"
                    + " `apellido` = '"+cliente.getApellido()+"', `telefono` = '"+cliente.getTelefono()+"', `email` = '"+cliente.getEmail()+"' WHERE `clientes` . `id` = "+cliente.getId()+";";
            
            //UPDATE `clientes` SET `nombre` = ``, `apellido` = ``, `telefono` = ``, `email` = `` WHERE `clientes` . `id` = 1;
            
            Statement statement = conexion.createStatement();
            statement.execute(sql);
        } catch (SQLException ex) {
            Logger.getLogger(CienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   public void guardar(Cliente cliente, String idLabel) {
      
          
        
       if(idLabel.equals("")){
           
       agregarCliente(cliente);
       }else{
          
       actualizar(cliente);
       }
    }
}


