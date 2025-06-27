/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package expedicionesapp.dao;

import java.util.List;

/**
 *
 * @author 54224
 */
public interface DAO {
    public boolean create(Object o);
    public boolean modify(Object o);
    public boolean delete(int id);
    public void showObjectById(int id);
    public Object getEntityById(int id);
}
