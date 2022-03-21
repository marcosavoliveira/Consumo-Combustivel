package Utils.TableFuncions;

import Controller.vehicleCTR;
import Vehicle.Vehicle;

import javax.swing.*;
import java.awt.*;

public class ButtonEditor extends DefaultCellEditor
{
    protected JButton btn;
    private Boolean clicked;
    int idLogged;
    public ButtonEditor(JTextField txt, int idLogged) {
        super(txt);
        this.idLogged = idLogged;
        btn=new JButton();
        btn.setOpaque(true);

        //WHEN BUTTON IS CLICKED
        btn.addActionListener(e -> fireEditingStopped());
    }

    //OVERRIDE A COUPLE OF METHODS
    @Override
    public Component getTableCellEditorComponent(JTable table, Object obj,
                                                 boolean selected, int row, int col) {

        //SET TEXT TO BUTTON,SET CLICKED TO TRUE,THEN RETURN THE BTN OBJECT

        clicked=true;
        if(table.getName().equals("Vehicle")) {
            Vehicle vehicleDTO = new Vehicle();
            vehicleDTO.setIdOwner(idLogged);
            vehicleDTO.setId((Integer) table.getValueAt(row, 0));
            new vehicleCTR().deleteVehile(vehicleDTO,table);
        }
        return btn;
    }

    //IF BUTTON CELL VALUE CHANGES,IF CLICKED THAT IS
    @Override
    public Object getCellEditorValue() {

        if(clicked)
        {
            System.out.println("Click");
        }
        //SET IT TO FALSE NOW THAT ITS CLICKED
        clicked=false;
       // return new String(lbl);
        return null;
    }

    @Override
    public boolean stopCellEditing() {

        //SET CLICKED TO FALSE FIRST
        clicked=false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        // TODO Auto-generated method stub
        super.fireEditingStopped();
    }
}