package Utils;

import Controller.vehicleCTR;
import Vehicle.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonEditor extends DefaultCellEditor
{
    protected JButton btn;
    private String lbl;
    private Boolean clicked;
    private Vehicle vehicleDTO;
    int idLogged;
    public ButtonEditor(JTextField txt, int idLogged) {
        super(txt);
        this.idLogged = idLogged;
        btn=new JButton();
        btn.setOpaque(true);

        //WHEN BUTTON IS CLICKED
        btn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                fireEditingStopped();
            }
        });
    }

    //OVERRIDE A COUPLE OF METHODS
    @Override
    public Component getTableCellEditorComponent(JTable table, Object obj,
                                                 boolean selected, int row, int col) {

        //SET TEXT TO BUTTON,SET CLICKED TO TRUE,THEN RETURN THE BTN OBJECT
        lbl=(obj==null) ? "":obj.toString();
        clicked=true;
        if(table.getName().equals("Vehicle")) {
            vehicleDTO = new Vehicle();
            vehicleDTO.setId((Integer) table.getValueAt(table.getSelectedRow(), 0));
            if(new vehicleCTR().deleteVehile(vehicleDTO)){
                vehicleDTO.setIdOwner(idLogged);
                table.setModel(new PopulatedVehicleTable().populate(idLogged,table));
            }else{
                JOptionPane.showMessageDialog(null, "Falha ao Remover Veículo","Error",JOptionPane.ERROR_MESSAGE);
            }

        }
        return btn;
    }

    //IF BUTTON CELL VALUE CHNAGES,IF CLICKED THAT IS
    @Override
    public Object getCellEditorValue() {

        if(clicked)
        {
            //SHOW US SOME MESSAGE

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