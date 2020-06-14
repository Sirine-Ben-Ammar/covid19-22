/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swingcrud.jpa;

import SwingCurd.dao.CassandraConnector;
import com.datastax.driver.core.ResultSet;
import swingcrud.model.Region;

/**
 *
 * @author Souha
 */
public class RegionEntity extends Region {
    
    public RegionEntity(int id, String name, String longitude, String latitude, int pop, int confirmes, int morts) {
        super(id, name, longitude, latitude, pop, confirmes, morts);}
    
    public ResultSet saveRegion(int id, String fname, String longitude, String latitude, int pop, int confirmes, int morts) {
        
            String sql = "INSERT INTO Covid.regions"
                    + "VALUES ('" + id + "','" + fname + "','" + longitude + "','" + latitude + "'," + pop + ",'" + confirmes + "','" + morts + "')";
            
      return CassandraConnector.ExecuteQuery(sql);

//        fetch();
    }

    //update the db
    public ResultSet update(int id, String fname, String longitude, String latitude, int pop, int conf, int morts) {
       
           
            String sql = "UPDATE Covid.regions SET name='" + fname + "',longitude='" + longitude + "',latitude='" + latitude + "',population='" + pop + "',confirmes='" + conf + "',morts='" + morts
                    + "'WHERE id='" + id + "'";
           
        return CassandraConnector.ExecuteQuery(sql);
//        fetch();
    }

    //delete details in the db
    public ResultSet delete(String id) {
       
            String sql = "DELETE FROM Covid.regions WHERE id='" + id + "'";
           return CassandraConnector.ExecuteQuery(sql);
       
//        fetch();
    }
    }
    
